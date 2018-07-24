/*
 * @Author: Paco
 * @Date:   2017-02-16
 * @lastModify 2017-07-24
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['form', 'layer', 'jqelem', 'jqajax'], function(exports) {
    "use strict";

    var $ = layui.jquery,
        element = layui.jqelem(),
        forms = layui.form(),
        jqajax = layui.jqajax,
        ajax = new jqajax(),
        layer = layui.layer,
        MOD_NAME = 'jqform',
        ELEM = '.layui-form',
        THIS = 'layui-this',
        Jqform = function() {
            this.config = {
                verify: {
                    required: [
                        /[\S]+/, '必填项不能为空'
                    ],
                    phone: [
                        /^1\d{10}$/, '请输入正确的手机号'
                    ],
                    email: [
                        /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/, '邮箱格式不正确'
                    ],
                    url: [
                        /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/, '链接格式不正确'
                    ],
                    number: [
                        /^\d+$/, '只能填写数字'
                    ],
                    date: [
                        /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/, '日期格式不正确'
                    ],
                    identity: [
                        /(^\d{15}$)|(^\d{17}(x|X|\d)$)/, '请输入正确的身份证号'
                    ]
                },
                form: "#form1",
                ajax: true,
                blur: true,
                keyup: true,
                fillAll: false,
                imgClass: ".img"
            };
        };

    Jqform.prototype.on = function(events, callback) {
        return layui.onevent("form", events, callback);
    };

    Jqform.prototype.render = function(type) {
        forms.render(type);
    }

    //验证规则设定
    Jqform.prototype.verify = function(settings) {
        var that = this;
        $.extend(true, that.config.verify, settings);
        return that;
    };

    Jqform.prototype.search = function() {

    }


    /**
     * iframe页面表单绑定数据
     */
    Jqform.prototype.catchBind = function(options) {
        if (!top.global[options.dataName]) {
            layer.alert("dataName名称设置错误或不存在", { icon: 6 });
            return false;
        }
        var _this = this,
            jqtable = top.global[options.dataName],
            data = _this.paramsToObj(options.data),
            record = _this.getRecord(jqtable.options.list.list, options.key, data[options.key]);
        _this.config = $.extend({}, _this.config, options);

        //重置表单
        var params = _this.resetForm(options);
        if (!params) {
            return false;
        }
        _this.beforeBind(jqtable, params, _this.config);
        _this.bindData(record);
        return true;
    }

    Jqform.prototype.resetForm = function(options) {
        var _this = this,
            theForm = $(_this.config.form),
            params = ajax.params(theForm);

        if (theForm.length <= 0) {
            layer.alert("请设置表单ID，默认值为#form1或是弹窗类型设置错误", { icon: 6 });
            return false;
        }

        theForm[0].reset();
        theForm.find("div").removeClass("has-warning");
        theForm.find(".jq-error,.error").remove();
        theForm.find(".defined-error").empty();
        theForm.find(".imgbox").parent("div").remove();
        if (options.data) {
            var data = ajax.paramToObj(options.data);
            _this.bindData(data);
        }
        if (options.action) {
            params.action = options.action;
        }
        if (options.url) {
            theForm.attr("action", options.url);
        }
        params = JSON.stringify(params);
        theForm.data("params", params);
        return params;
    }


    /**
     * iframe页面表单绑定数据
     */
    Jqform.prototype.iframeBind = function() {
        //获取URL的参数
        var _this = this,
            params = this.GetQueryString(),
            keyFiledVal = params[_this.config.key],
            jqtable = top.global[_this.config.dataName],
            record = _this.getRecord(jqtable.options.list.list, _this.config.key, keyFiledVal);
        _this.beforeBind(jqtable, params, _this.config);
        _this.bindData(record);
        _this.afterBind(record, params, _this.config);
    }

    /**
     * 绑定数据前的接口
     */
    Jqform.prototype.beforeBind = function(jqtable, params, options) {}

    /**
     * 绑定数据后的接口
     */
    Jqform.prototype.afterBind = function(jqtable, params, options) {}

    /**
     * 根据参数给定的值获取记录
     * @param object list 缓存数据集
     * @param string 数据集的主健名称，一般为ID
     * @param fixed 主健值，一般为int类型
     */
    Jqform.prototype.getRecord = function(list, key, val) {
        var record;
        $.each(list, function(i, n) {
            if (n[key] == val) {
                record = list[i];
                return;
            }
        })
        return record;
    }

    /**
     * 取得url的参数并转换为对象
     */
    Jqform.prototype.GetQueryString = function() {
        var query = window.location.search.substring(1);
        return this.paramsToObj(query);
    }

    Jqform.prototype.paramsToObj = function(params) {
        var vars = params.split("&"),
            obj = {};
        if (vars) {
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                obj[pair[0]] = pair[1];
            }
        }
        return obj;
    }

    /**
     * @todo 表单绑定数据
     * @param object record 一条记录 
     */
    Jqform.prototype.bindData = function(record) {
        var _this = this,
            theForm = $(_this.config.form);
        if (_this.config.url) {
            theForm.attr("action", _this.config.url);
        }
        //排除的字段
        if (_this.config.except) {
            $.each(_this.config.except, function(i, n) {
                delete record[n];
            })
        }
        if (!record) return false;
        var inputHtml = "";
        for (var key in record) {
            var obj = theForm.find('input[name=' + key + ']'),
                inputHtml;
            if (obj.length > 0) {
                if (obj.prop('type') == "text" || obj.prop('type') == "hidden") {
                    if (theForm.find(_this.config.imgClass + '[name=' + key + ']').length > 0) {
                        var html = '<div class="layui-input-block"><div class="imgbox"><img src="' + record[key] + '" alt="..." class="img-thumbnail"></div></div>';
                        theForm.find(_this.config.imgClass + '[name=' + key + ']').parents("div.layui-input-block").after(html);
                    }
                    obj.val(record[key]);

                    //暂时不支持填充标签
                    // if (key == "tags") {
                    //     var tags = record[key].split("|");
                    //     $.each(tags, function(i, n) {
                    //         var tag = n.split(',');

                    //     })
                    // }
                } else if (obj.prop('type') == "checkbox" || obj.prop('type') == "radio") {
                    obj.each(function(i, n) {
                        if ($(n).val() == record[key]) {
                            $(n).prop("checked", true);
                        }
                    })
                }
            } else if (theForm.find('textarea[name=' + key + ']').length > 0) {
                theForm.find('textarea[name=' + key + ']').val(record[key]);
            } else if (theForm.find('select[name=' + key + ']').length > 0) {
                theForm.find('select[name=' + key + ']').val(record[key]);
            } else {
                if (typeof(ue) != "undefined" && key == "content") {
                    ue.execCommand('inserthtml', record[key]);
                }
                if (_this.config.fillAll || _this.config.action == "add") {
                    inputHtml += '<input type="hidden" name="' + key + '" value="' + record[key] + '" />';
                } else if (record[_this.config.key]) {
                    inputHtml = '<input type="hidden" name="' + _this.config.key + '" value="' + record[_this.config.key] + '" />';
                }
            }
        }
        theForm.append(inputHtml);
        forms.render();

    }

    Jqform.prototype.init = function(options) {
        var _this = this,
            options = $.extend({}, _this.config, options);
        _this.config = options = $.extend({}, _this.config, ajax.params($(_this.config.form)));

        if (_this.config.blur) {
            $(this.config.form).find('*[jq-verify]').blur(function() {
                _this.check($(this));
            });
        }

        if (_this.config.keyup) {
            $(this.config.form).find('*[jq-verify]').keyup(function() {

                _this.check($(this));
            });
        }
        forms.on('select(verify)', function(data) {
            _this.check($(data.elem));
        });

        //this.tabChange();

        if (_this.config.bind) {
            _this.iframeBind();
        }
    }

    /**
     *@todo 根据规则验证数据
     *@param object obj 当前点击的提交按钮对象
     */
    Jqform.prototype.check = function(obj, isTab) {
        var ver = obj.attr('jq-verify'),
            err = obj.attr('jq-error'),
            errorId = obj.attr('error-id'),
            tips = '',
            stop = false,
            DANGER = 'has-warning',
            SUCCESS = 'has-success',
            verify = this.config.verify,
            value = obj.val(),
            itemOuterHeight = 0,
            top = 0;
        obj.parent('div').removeClass(DANGER);
        if (obj.css("display") == "none" && (!obj.attr("hidden-required"))) return false;
        if (!ver) {
            return false;
        } else {
            ver = ver.split('|');
        }
        if (ver.indexOf("required") == -1 && "" == value && ver.indexOf("content") == -1) {
            return;
        }
        if (err) {
            err = err.split('|');
        }
        $.each(ver, function(index, thisVer) {

            var isFn = typeof verify[thisVer] === 'function';
            if (verify[thisVer] && (isFn ? tips = verify[thisVer](value, obj) : !verify[thisVer][0].test(value))) {

                var errHtml = "",
                    errMsg = (tips || (err ? err[index] : false) || verify[thisVer][1]);
                //判断是否是tab选项卡验正
                if (isTab) {
                    var layId = obj.parents('.layui-tab-item').attr('lay-id');
                    var layId = layId ? layId : 1;
                    element.tabChange('check', layId);
                }

                if (errorId == undefined) {
                    if (parseInt(obj.width()) > 200) {
                        errHtml = '<p class="jq-error">' + errMsg + '</p>';
                        if (obj.next('.jq-error').length <= 0) {
                            obj.after(errHtml);
                        } else {
                            obj.next('.jq-error').html(errMsg);
                        }

                    } else {
                        errHtml = '<div class="error"><p>' + errMsg + '</p></div>';
                        if (obj.parent('div').next('.error').length <= 0) {
                            obj.parent('div').after(errHtml);
                        } else {
                            obj.parent('div').next('.error').html(errMsg);
                        }

                    }
                    obj.parent('div').addClass(DANGER);
                    top = obj.parent('div').offset();
                    itemOuterHeight = obj.outerHeight();

                } else {

                    $('#' + errorId).html(errMsg).addClass('defined-error');
                    top = $('#' + errorId).offset();

                    itemOuterHeight = $('#' + errorId).outerHeight();
                }
                var winScrollTop = $(window).scrollTop(),
                    itemOffsetTop = top.top,

                    winHeight = $(window).height();

                if ((winScrollTop > itemOffsetTop + itemOuterHeight) || (winScrollTop < itemOffsetTop - winHeight)) {
                    $('body,html').stop().animate({ scrollTop: top.top - 20 }, 500);
                }

                stop = true;
                return;
            } else {
                if (errorId == undefined) {
                    obj.parent('div').removeClass(DANGER);
                    obj.next('.jq-error').remove();
                    obj.parent('div').next('.error').remove();
                } else {
                    $('#' + errorId).empty();
                }
            }
        })
        return stop;
    }


    /**
     *提交表单时较验
     */
    Jqform.prototype.submit = function(event) {
        var button = $(this),
            form = event.data.form,
            bind = button.attr("bind"),
            stop = null,
            field = {},
            elem = button.parents(ELEM)

        , verifyElem = elem.find('*[jq-verify]') //获取需要校验的元素

        , formElem = button.parents('form')[0] //获取当前所在的form元素，如果存在的话
            , fieldElem = elem.find('input,select,textarea') //获取所有表单域
            , filter = button.attr('jq-filter') //获取过滤器
            , isTab = button.attr('jq-tab');

        //初始化tab内容块的layId
        if (isTab && !bind) {
            $(this).attr("bind", 1);
            elem.find(".layui-tab").find('li').each(function(i, n) {
                var layId = ($(n).attr("lay-id"));
                elem.find(".layui-tab").find(".layui-tab-item").each(function(c, d) {
                    if (i == c) {
                        $(d).attr("lay-id", layId);
                    }
                });
            })
        }
        //开始校验
        layui.each(verifyElem, function(_, item) {
            stop = form.check($(this), isTab);
            return stop;
        });

        if (stop) return false;

        layui.each(fieldElem, function(_, item) {
            if (!item.name) return;
            if ($(item).parents(".edit-hidden").length > 0) return;
            if ($(item).prop('type') == "file") return;
            if (/^checkbox|radio$/.test(item.type) && !item.checked) return;
            //field[item.name] = item.value;
            if (item.name.indexOf('[]') > -1) {
                var real_name = item.name.substring(0, item.name.length - 2);
                if (field[real_name] === undefined) {
                    field[real_name] = [item.value]
                } else {
                    field[real_name].push(item.value);
                }
            } else {
                field[item.name] = item.value;
            }

        });

        if (form.config.ajax) {

            var params = ajax.params(elem);

            params.data = $.extend({}, params.data, field);
            params.url = elem.attr("action");
            params.method = elem.attr("method");
            var options = $.extend({}, ajax.options, params);
            options = $.extend({}, form.config, options);

            ajax.ajax(options);
            return false;
        } else {
            //获取字段
            return layui.event.call(this, 'form', 'submit(' + filter + ')', {
                elem: this,
                form: formElem,
                field: field
            });
        }
    };



    //自动完成渲染
    var form = new Jqform(),
        dom = $(document);
    dom.on('click', '*[jq-submit]', { form: form }, form.submit);

    exports(MOD_NAME, form);
});