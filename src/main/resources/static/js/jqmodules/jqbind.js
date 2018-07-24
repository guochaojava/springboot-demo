/*
 * @Author: Paco
 * @Date:   2017-07-24
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'jqajax', 'jqform'], function(exports) {
    var $ = layui.jquery,
        jqajax = layui.jqajax,
        form = layui.jqform,
        ajax = new jqajax(),
        jqbind = function() {
            this.options = {
                focusVal: "",
                type: 1,
                title: false,
                maxmin: false,
                shadeClose: true,
                shade: 0.3,
                content: "",
                area: '',
                anim: 5,
                bindCall: ""
            }
        };

    /**
     * @todo 初始化需要绑定事件的元素
     * 
     */
    jqbind.prototype.init = function() {
        var _this = this;
        $(".ajax:not([bind])").each(function() {
            _this.bind($(this));
            $(this).on('click', function() {
                _this.ajax(this);
            });
        });

        $(".ajax-all:not([bind])").each(function() {
            _this.bind($(this));
            $(this).on('click', function() {
                _this.checkall(this);
            });
        });

        $(".ajax-blur:not([bind])").each(function() {
            _this.bind($(this));
            $(this).bind('focus', function() {
                _this.focus(this);
            });
            $(this).bind('blur', function() {
                _this.blur(this);
            });
        });

        $(".modal:not([bind])").each(function() {
            _this.bind($(this));
            $(this).bind('click', function() {
                _this.modal(this);
            });
        });

        $(".edit:not([bind])").each(function() {
            _this.bind($(this));
            $(this).bind('click', function() {
                _this.edit(this);
            });
        });

        $(".tab-menu:not([bind])").each(function() {
            _this.bind($(this));
            $(this).bind('click', function() {
                _this.menu(this);
            });
        });

        $(".order:not([bind])").each(function() {
            _this.bind($(this));
            $(this).bind('click', function() {
                _this.order(this);
            });
        });



        form.on('switch(ajax)', function(data) {
            var name = $(data.elem).attr("name"),
                arr = {};
            val = 0;
            if (data.elem.checked) {
                val = data.value;
            }
            arr = { name: name, val: val };
            _this.set(arr, $(data.elem));

        });

        form.on('select(ajax)', function(data) {
            var name = $(data.elem).attr("name"),
                arr = {};
            val = 0;
            if (data.elem.checked) {
                val = data.value;
            }

            arr = { name: name, val: val };

            _this.set(arr, $(data.elem));
        });

        form.on('submit(search)', function(data) {
            var params = $.param(data.field),
                options = {};

            if (params == "" || params == undefined) return false;
            var url = $(data.form).attr('action');
            options = ajax.params($(data.form));
            options.curr = 1;
            options.url = url + "?" + params;
            ajax.ajax(options);
            return false;
        })

    }


    /**
     * @todo 绑定单击事件
     * @param string obj ID或是class 如#id、.id
     * @param string call 回调的方法
     */
    jqbind.prototype.click = function(obj, call) {
        var _this = this;
        $(obj).on('click', _this[call]);
    }

    /**
     * @todo 结已绑定的元素添加绑定标识
     */
    jqbind.prototype.bind = function($obj) {
        $obj.attr("bind", 1);
    };

    jqbind.prototype.modal = function(obj) {
        var _this = this,
            params = ajax.params($(obj)),
            options = $.extend({}, _this.options, params),
            _area = ["auto", "auto"];
        if (options.area != "" || options.area != "auto,auto") {
            _area = options.area.split(',');
            var width = parseInt(_area[0]),
                dpr = window.devicePixelRatio,
                maxWidth = $(window).width() - 20;
            if (width > maxWidth) {
                _area[0] = (maxWidth) + "px";
            }
            var height = parseInt(_area[1]),
                maxHeight = $(window).height() - 20;
            if (height > maxHeight) {
                _area[1] = (maxHeight) + "px";
            }
        }
        if (parseInt(options.type) == 2) {
            options.content = options.content + "?" + options.data;
        } else {
            options.content = $(options.content);

            //如果要绑定数据数据
            if (options.bind) {
                if (!form.catchBind(params)) {
                    return false;
                }
            } else {
                //重置表单
                form.resetForm(params);

                if (options.bindCall) {
                    options.data = form.paramsToObj(options.data);
                    _this[options.bindCall](options, obj);
                    form.render();
                }
            }
        }
        if (!options.area) {
            var l = layer.open({
                type: parseInt(options.type),
                title: options.title,
                shade: options.shade,
                shadeClose: options.shadeClose,
                content: options.content
            });
            layer.full(l);
        } else {
            var l = layer.open({
                type: parseInt(options.type),
                title: options.title,
                shade: options.shade,
                shadeClose: options.shadeClose,
                area: _area,
                content: options.content
            });
        }
    }

    jqbind.prototype.edit = function(obj) {
        var _this = this,
            oldTxt = $.trim($(obj).text()),
            td = $(obj),
            options = ajax.params($(obj)),
            input = $("<input type='text' name='" + options.field + "' value='" + oldTxt + "'/>");

        input.css({ "min-width": "80%", "height": "30px", "padding": "5px", "color": "#999" })
        td.html(input);
        input.click(function() {
            return false;
        });
        input.select();
        //文本框失去焦点后提交内容，重新变为文本
        input.blur(function() {
            var newtxt = $(this).val();
            //判断文本有没有修改
            if (newtxt != oldTxt) {
                //异步修改数据
                var data = { name: options.field, val: newtxt };
                options = ajax.set(data, options);
                ajax.ajax(options);
                // td.html(newtxt);
            } else {
                td.html(oldTxt);
            }
        })
    }

    jqbind.prototype.focus = function(obj) {
        var _this = this;
        _this.options.focusVal = $(obj).val();
    }

    jqbind.prototype.blur = function(obj) {
        var _this = this;
        if (_this.options.focusVal == $(obj).val()) {
            return;
        }
        var data = { name: $(obj).attr("name"), val: $(obj).val() };

        var options = ajax.params($(obj));
        options = ajax.set(data, options);
        if (options.confirm) {
            ajax.confirm(options, $(obj));
        } else {
            ajax.ajax(options);
        }

    }

    /**
     * 用于直接提交的ajax，无需设值
     */
    jqbind.prototype.ajax = function(obj) {
        var options = ajax.params($(obj));
        if (options.confirm) {
            ajax.confirm(options, $(obj));
        } else {
            ajax.ajax(options);
        }
    }

    /**
     * 用于switch与select事件
     */
    jqbind.prototype.set = function(data, obj) {
        if (!obj) {
            obj = $(this);
        }
        var options = ajax.params(obj);
        options = ajax.set(data, options);
        if (options.confirm) {
            ajax.confirm(options, $(this));
        } else {
            ajax.ajax(options);
        }
    }

    /**
     * 用于批量操作
     */
    jqbind.prototype.checkall = function(obj) {
        var options = ajax.params($(obj)),
            arr = [],
            data = {};
        $("input[name=" + options.key + "]:checked").each(function() {
            arr.push($(this).val());
        })
        var vals = arr.toString();
        if (vals == "") {
            layer.msg('请选择需要操作的记录');
            return;
        }

        data = { name: options.key, val: vals };
        options = ajax.set(data, options);
        if (options.confirm) {
            ajax.confirm(options, $(obj));
        } else {
            ajax.ajax(options);
        }
    }

    /**
     * 绑定打开菜单
     */
    jqbind.prototype.menu = function(obj) {
        if (top.global.menu) {
            var menu = top.global.menu;
            menu.menuSetOption($(obj));
        } else {
            layer.alert("菜单dataName名称设置错误或不存在", { icon: 6 });
            return false;
        }

    }

    /**
     * 绑定排序
     */
    jqbind.prototype.order = function(obj) {
        var data = params = ajax.params($(obj));
        if (data.sort == "asc") {
            data.sort = "desc";
            $(obj).parents("tr").find("i").remove();
            var html = '<i class="iconfont rotate">&#xe64e;</i>';
            var str = JSON.stringify(data);
            $(obj).data("params", str).append(html);
        } else {
            data.sort = "asc";
            $(obj).parents("tr").find("i").remove();
            var html = '<i class="iconfont">&#xe64e;</i>';
            var str = JSON.stringify(data);
            $(obj).data("params", str).append(html);
        }
        ajax.sort(params);
    }
    var bind = new jqbind();
    bind.init();
    exports('jqbind', bind);
});