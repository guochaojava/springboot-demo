package com.example.demo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 树结构处理工具类
 *
 * @author guochao
 * @since 1.0.0
 */
public class TreeObjectUtil {
    /**
     * @param list 需要转换的list（包含parentId的无限极list数据）
     * @return List<MenuObject>
     * @throws
     * @Title: getChildMenuObjects  获取树形结构list
     * @Description: TODO
     */
    public List<TreeObject> getChildMenuObjects(List<TreeObject> list) {
        List<TreeObject> returnList = new ArrayList<TreeObject>();
        for (Iterator<TreeObject> iterator = list.iterator(); iterator.hasNext(); ) {
            TreeObject t = iterator.next();
            //根据父级ID 遍历childen list
            if (t.getPid().equals(0)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * @param list
     * @param t
     * @return void
     * @throws
     * @Title: recursionFn
     * @Description: TODO 递归处理childen数据
     */
    private void recursionFn(List<TreeObject> list, TreeObject t) {
        List<TreeObject> childList = getChildList(list, t);
        t.setSub(childList);
        for (TreeObject tChild : childList) {
            if (hasChild(list, tChild)) {
                Iterator<TreeObject> it = childList.iterator();
                while (it.hasNext()) {
                    TreeObject n = (TreeObject) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }


    /**
     * @param list
     * @param t
     * @return List<MenuObject>
     * @throws
     * @Title: getChildList
     * @Description: TODO 获取子节点list数据
     */
    private List<TreeObject> getChildList(List<TreeObject> list, TreeObject t) {

        List<TreeObject> tlist = new ArrayList<TreeObject>();
        Iterator<TreeObject> it = list.iterator();
        while (it.hasNext()) {
            TreeObject n = (TreeObject) it.next();
            if (n.getPid().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }


    /**
     * @param list
     * @param t
     * @return boolean
     * @throws
     * @Title: hasChild
     * @Description: TODO 判断是否有子节点
     */
    private boolean hasChild(List<TreeObject> list, TreeObject t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}