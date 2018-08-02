/*
 * @Author: Paco
 * @Date:   2017-02-15
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'laytpl', 'jqform', 'layer', 'upload'], function(exports) {
    var $ = layui.jquery,
        tpl = layui.laytpl,
        layer = layui.layer,
        form = layui.jqform;
        // citys = layui.jqcitys(),

    /**
     * 在绑定数据前，初始化分类下拉框
     */
    form.beforeBind = function(jqtable, params, options) {
        var locationData = layui.data("articleCat"),
            record = locationData.list ? locationData.list : "";
        if (record) {
            var data = {
                list: record
            }
            var getTpl = $("#select-tpl").html();
            var obj = $("#select-cat");
            tpl(getTpl).render(data, function(html) {
                obj.html(html);
            })
        }
    }
    form.init({
        "form": "#form1"
    });
    //自定义
    form.verify({
        username: function(value) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '文章标题不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '文章标题首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d$/.test(value)) {
                return '文章标题不能全为数字';
            }
        },
        pass: [
            /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
        ],
        content: function(value) {
            layedit.sync(editIndex);
            return;
        }
    });



    exports('product', {});
});