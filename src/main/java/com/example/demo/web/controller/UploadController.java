package com.example.demo.web.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.config.PropertiesConfig;
import com.example.demo.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 上传文件
 *
 * @author guochao
 */
@Controller
public class UploadController {

    private static final int ROOT_PORT = 80;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @RequestMapping("/upload/img")
    @ResponseBody
    public Object uploadImg(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        String originalFilename = file.getOriginalFilename();
        // 获得后缀
        String newName = generateFileName(originalFilename);

        String path = saveFiles(file, newName);
        return ResponseEntity.buildOk(getBaseUrlNoContext(request) + path);
    }


    @RequestMapping("/upload/editor")
    @ResponseBody
    public void uploadFile(MultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(2);

        Iterator<String> itr = req.getFileNames();
        Console.log(itr.hasNext());
        MultipartFile image = null;
        // 记录存储图片的路径
        List<String> imgurls = new ArrayList<String>();

        // 2. get each file（循环获取每个文件）
        while (itr.hasNext()) {
            // 2.1 get next MultipartFile
            image = req.getFile(itr.next());
            // 图片名称。 例： 123.jpg
            String fileName = image.getOriginalFilename();
            String newFileName = generateFileName(fileName);
            String path = saveFiles(image, newFileName);
            imgurls.add(getBaseUrlNoContext(request) + path);
        }
        map.put("errno", "0");
        map.put("data", imgurls);
        response.getWriter().write(JSONUtil.toJsonStr(map));
    }

    /**
     * 传入原图名称，，获得一个以时间格式的新名称
     *
     * @param fileName 原图名称
     * @return
     */

    public static String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    public String saveFiles(MultipartFile file, String fileName) throws Exception {
        // 基本路径 用于生成文件
        String basePath = "";
        // 相对路径 用于返回路径并持久化
        String path = getUploadPath();
        // 获取当前操作系统 用于判断是windows还是linux
        basePath = propertiesConfig.getUpload().getPath();

        File targetFile = new File(basePath + path, fileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        // 保存
        file.transferTo(targetFile);
        String[] tem = fileName.split("\\.");
        // 获取文件大小 单位 b
        long fileSize = file.getSize();

        if ("jpg".equalsIgnoreCase(tem[1]) || "png".equalsIgnoreCase(tem[1]) || "gif".equalsIgnoreCase(tem[1])
                || "jepg".equalsIgnoreCase(tem[1])) {
            if (fileSize > 300000) {
                //缩放文件
                ImageUtil.scale(targetFile, targetFile, (float) 1);
            }
        }

        return "/upload" + path + "/" + fileName;
    }

    /**
     * 获取上传路径
     *
     * @return String
     */
    public static String getUploadPath() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = (calendar.get(Calendar.MONTH) + 1);
        String path = "/" + year + "/" + month;
        return path;
    }


    /**
     * 获取基本路径不包含项目名称 例如：http://localhost:8080/ @client @client
     *
     * @param request
     * @return
     */
    public String getBaseUrlNoContext(HttpServletRequest request) {
        StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme()).append("://");
        basePath.append(request.getServerName());
        if (request.getServerPort() != ROOT_PORT) {
            basePath.append(":").append(request.getServerPort());
        }
        return basePath.toString();
    }
}


