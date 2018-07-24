/*
 * @Author: Paco
 * @Date:   2017-02-24
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'jqform', 'jqtable'], function(exports) {
    var $ = layui.jquery,
        table = layui.jqtable,
        list = new table(),
        _record,
        conf,
        form = layui.jqform;
    list.init({ tplid: "#list-tpl" });

    /**
     * 列表渲染完后执行,选中已有的权限模块
     */
    list.bindRole = function() {
        var ids;
        if (_record) {
            if (typeof(_record.role) == "object") {
                ids = _record.role;
            } else {
                ids = _record.role.split(",");
            }

            $(conf.form).find("input[lay-filter=role]").each(function(i, n) {
                if ($.inArray($(n).val(), ids) > -1) {
                    $(n).attr("checked", true);
                }
            })
            form.render();
        }
    }

    /**
     * 数据绑定后执行，此处为调出数据
     */
    form.afterBind = function(record, params, config) {
        _record = record;
        conf = config;
    }

    form.init({
        "form": "#form1"
    });


    form.on('checkbox(role)', function(data) {
        //单击顶级菜单
        if ($(data.elem).parent('li').length > 0) {
            $(data.elem).parent('li').find("dl").each(function(i, n) {
                $(n).find('input').prop("checked", function() {
                    return data.elem.checked;
                });
            })
        }

        //单击二级菜单
        if ($(data.elem).parent('dl').length > 0) {
            $(data.elem).parent('dl').find("dd").each(function(i, n) {
                $(n).find('input').prop("checked", function() {
                    return data.elem.checked;
                });
            })
            var had_check = true;
            $(data.elem).parent('dl').parent('li').children('dl').each(function(i, n) {
                if ($(n).find('input').prop("checked") && !data.elem.checked) {

                    had_check = false;
                }
            })
            if (had_check) {
                $(data.elem).parent('dl').parent('li').children('input').prop("checked", function() {
                    return data.elem.checked;
                });
            }
        }

        //单击三级菜单
        if ($(data.elem).parent('dd').length > 0) {

            var had_sub_check = true;
            $(data.elem).parent('dd').parent('dl').children('dd').each(function(i, n) {
                if ($(n).find('input').prop("checked") && !data.elem.checked) {
                    had_sub_check = false;
                }
            })
            if (had_sub_check) {
                $(data.elem).parent('dd').parent('dl').children('input').prop("checked", function() {
                    return data.elem.checked;
                });
            }

            var had_check = true;
            $(data.elem).parent('dd').parent('dl').parent('li').find('dl').each(function(i, n) {

                if ($(n).find('input').prop("checked") && !data.elem.checked) {
                    had_check = false;
                }
            })
            if (had_check) {
                $(data.elem).parent('dd').parent('dl').parent('li').children('input').prop("checked", function() {
                    return data.elem.checked;
                });
            }
        }

        form.render();

    });


    exports('role', {});
});