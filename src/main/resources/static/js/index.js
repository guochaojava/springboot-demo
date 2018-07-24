/*
 * @Author: Paco
 * @Date:   2017-01-31
 * @lastModify 2017-03-19
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'jqmenu', 'layer'], function (exports) {
    var $ = layui.jquery,
        menu = layui.jqmenu,
        layer = layui.layer,
        mainMenu = new menu();
    jqIndex = function () { };
    top.global.menu = mainMenu;

    /**
     *@todo 初始化方法
     */
    jqIndex.prototype.init = function () {

        mainMenu.init();
        this.showMenu();
        this.refresh();
    }

    /**
     *@todo 绑定刷新按钮单击事件
     */
    jqIndex.prototype.refresh = function () {
        $('.fresh-btn').bind("click", function () {
            $('.jqadmin-body .layui-show').children('iframe')[0].contentWindow.location.reload(true);
        })
    }

    /**
     *@todo 绑定左侧菜单显示隐藏按钮单击事件
     */
    jqIndex.prototype.showMenu = function() {
        $('.menu-type').bind("click", function() {
            if (window.localStorage) {
                var storage = window.localStorage;
                var showType = storage.getItem("showType");
                showType = (showType == 1) ? 2 : 1;
                storage.setItem("showType", showType);

            }
            mainMenu.menuShowType();
        })

    }

    var index = new jqIndex();
    index.init();
    exports('index', {});
});