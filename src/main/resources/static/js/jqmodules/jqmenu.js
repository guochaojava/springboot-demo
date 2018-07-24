/*
 * @Author: Paco
 * @Date:   2017-03-12
 * @lastModify 2017-05-08
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'layer', 'jqelem', 'tabmenu'], function(exports) {
    var $ = layui.jquery,
        element = layui.jqelem(),
        layer = layui.layer,
        init = true,
        tabmenu = layui.tabmenu(),
        jqmenu = function() {
            this.options = {
                showIcon: true
            }
        };

    /**
     *@todo 初始化数据
     */
    jqmenu.prototype.init = function() {
        var _this = this;
        _this.resize();
        _this.menuBind();
        element.init();
        _this.menuShowType();

    }

    /**
     * 将tabmenu类附到jqmenu上，方法tab的接口调用与重写
     */
    jqmenu.prototype.tabmenu = tabmenu;



    /**
     *@todo 自适应窗口
     */
    jqmenu.prototype.resize = function() {
        $(window).on('resize', function() {
            tabmenu.init();
            tabmenu.tabMove(0, 1);
        }).resize();
    }

    /**
     *@todo 初始化菜单 
     */
    jqmenu.prototype.menuBind = function() {
        var _this = this;
        //初始化时显示第一个菜单

        $('.sub-menu').eq(0).slideDown();
        $('#menu li').removeClass("layui-this").eq(0).addClass("layui-this");

        //绑定左侧树菜单的单击事件
        $('.sub-menu .layui-nav-item,.tab-menu,.menu-list li').bind("click", function() {
            var obj = $(this);
            $('.menu-list').slideUp();
            $('.tab-move-btn').find('i').html("&#xe604;");

            if (obj.find('dl').length <= 0) {
                _this.menuSetOption(obj);
            }
        }).find('dd').bind("click", function() {
            _this.menuSetOption($(this));
        });


        //绑定主菜单单击事件，点击时显示相应的菜单
        element.on('nav(main-menu)', function(elem) {
            var index = (elem.index());
            $('.sub-menu').slideUp().eq(index).slideDown();
        });

    }

    /**
     *@todo 设置菜单项
     */
    jqmenu.prototype.menuSetOption = function(obj) {
        var $a = obj.children('a'),
            href = $a.data('url'),
            icon = $a.children('i:first').data('icon'),
            title = $a.data('title'),
            data = {
                href: href,
                icon: icon,
                title: title
            }
        this.menuOpen(data);
    }


    /**
     *@todo 打开菜单项
     */
    jqmenu.prototype.menuOpen = function(data) {
        tabmenu.tabAdd(data, this.options.fresh);
    }



    jqmenu.prototype.menuShowType = function() {



        if (window.localStorage) {
            var storage = window.localStorage,
                showType = storage.getItem("showType");

            if (!showType) showType = 1;
        }

        var bar = $('.jqamdin-left-bar'),
            _this = this,
            showIcon = $(".menu-type").find("i"),
            minWidth = 50,

            maxWidth = 200;
        if (!_this.options.showIcon) {
            maxWidth = 160;
        }
        switch (parseInt(showType)) {
            case 1:
                $('.jqadmin-body').animate({ left: minWidth });
                bar.animate({ width: minWidth });
                $('.jqadmin-foot').animate({ left: minWidth });
                //showIcon.html('&#xe61a;');
                $('#submenu').find("span").hide();
                $('#submenu').find("ul li").css("width", minWidth).css("padding-left", "0").find('i').css("font-size", "16px");
                $('#submenu').find("ul li").find("a").css("padding-left", "4px").find("em").hasClass("layui-nav-more");
                $('#submenu').find("ul li").children("a").each(function() {
                    $(this).css("border-bottom", "1px solid #CCC");
                    if (!$(this).find("em").hasClass("layui-nav-more")) {
                        $(this).css("padding-left", "14px");
                    }
                })

                $('#submenu').find("ul li dl dd").css("text-indent", "8px");
                $('#submenu').find("ul li").find("a").on('mouseenter', function() {
                    layer.tips($(this).data("title"), $(this));
                });

                if (!_this.options.showIcon) {
                    $('#submenu').find('i').removeClass("hide-icon");
                }


                //$('.menu-btn').find("i").html('&#xe616;');
                break;
            default:
                $('.jqadmin-body').animate({ left: maxWidth });
                bar.animate({ width: maxWidth });
                $('.jqadmin-foot').animate({ left: maxWidth });
                //showIcon.html('&#xe683;');
                $('#submenu').find("span").show();
                $('#submenu').find("ul li").css("width", maxWidth).find("a").css({ "padding-left": "30px", "border-bottom": "none" }).find('i').css("font-size", "14px");
                $('#submenu').find("ul li").find("a").off('mouseenter');

                if (!_this.options.showIcon) {
                    $('.jqadmin-main-menu').find("i").addClass("hide-icon");
                    $('#submenu').find('i').addClass("hide-icon");
                }
                $('#submenu').find("ul li dl dd").css("text-indent", "20px");

                //$('.menu-btn').find("i").html('&#xe618;');
                break;
        }
    }


    exports('jqmenu', jqmenu);
});