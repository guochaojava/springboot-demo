/*
 * @Author: Paco
 * @Date:   2017-02-07
 * @lastModify 2017-03-19
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'layer', 'jqbind'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        ajax = layui.jqajax,
        jqMain = function() {
            this.options = {
                id: 123,
                name: "nihao"
            }
        };


    /**
     *@todo 初始化方法
     */
    jqMain.prototype.init = function() {
        // var a = {
        //     b: "",
        //     c: { al: "234", ae: 35 }

        // }
        // _this = this;
        // var options = [];
        // $.each(a, function(i, n) {
        //     options[i] = $.extend(true, _this.options, n);

        // })

        // console.log(options);

        this.panelToggle();
    }

    /**
     *@todo 绑定面板显示隐藏按钮单击事件
     */
    jqMain.prototype.panelToggle = function() {
        $('.panel-toggle').bind("click", function() {
            var obj = $(this).parent('.panel-heading').next('.panel-body');
            if (obj.css('display') == "none") {
                $(this).find('i').html('&#xe604;');
                obj.slideDown();
            } else {
                $(this).find('i').html('&#xe603;');
                obj.slideUp();
            }
        })
    }

    var main = new jqMain();
    main.init();
    exports('main', {});
});