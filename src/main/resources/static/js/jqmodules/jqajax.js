/**
 @Name：layui.jqajax 异步提交插件
 @Author：Paco
 @date: 2016-12-03
 @lastModify 2017-07-24
 @web: www.jqcool.net
 */
layui.define(['jquery', 'layer'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        jqajax = function() {
            this.options = {
                "method": 'GET',
                "data": '',
                "dataType": 'json',
                "timeout": 50000,
                "cache": true,
                "confirm": false,
                "loading": true, //是否显示loading图片
                "complete": "complete"
            }
        };
    //调用layer第三方皮肤
    // layer.config({
    //     skin: 'layer-ext-espresso',
    //     extend: 'espresso/style.css'
    // });

    /**
     *@todo  绑定参数
     */
    jqajax.prototype.params = function(obj, conf) {
        var params = obj.data('params'),
            options = {};
        if (undefined == params || null == params) {
            params = {};
        }

        if (typeof params === 'string') {
            params = new Function("return " + params)();
        }
        if (!params.dataName) {
            var data = obj.parents("table").data('params');
            if (undefined == data || null == data) {
                data = {};
            }
            if (typeof data === 'string') {
                data = JSON.parse(data)
            }
            params = $.extend({}, data, params);
        }
        if (conf) {
            options = $.extend({}, this.options, conf);
        }
        var options = $.extend({}, this.options, params);
        return options;
    }


    /**
     * 把参数添加到option的data中
     */
    jqajax.prototype.set = function(data, options) {
        var _this = this;
        if (undefined != options.data && options.data != "") {

            var obj = _this.paramToObj(options.data),
                str = "";
            obj[data.name] = data.val;
            $.each(obj, function(key, val) {
                str += key + "=" + val + "&";
            })
            options.data = str.substring(0, str.length - 1);
        } else {
            options.data = data.name + "=" + data.val;
        }
        return options;
    }


    /**
     *@todo ajax 事件  这里callBack特意采用json格式，如果有需要可以在这里修改
     *@param options object 拼装好的参数对象
     *@param obj object 操作的对象
     **/
    jqajax.prototype.ajax = function(options) {
        var _this = this,
            l;
        if (options == undefined || options == null) {
            return;
        }

        //变为大写
        options.method = options.method.toUpperCase();
        $.ajax({
            type: options.method,
            url: options.url,
            dataType: options.dataType,
            data: options.data,
            timeout: options.timeout,
            cache: options.cache,
            beforeSend: function() {
                if (options.loading == true) {
                    l = layer.load(1);
                }
            },
            error: function(XMLHttpRequest, status, thrownError) {
                layer.msg('网络繁忙，请稍后重试...', { icon: 2 });
            },
            success: function(msg) {
                if (!_this.callBack(msg, options, _this)) return;
            },
            complete: function() {
                if (options.loading == true) {
                    layer.close(l);
                }
            }
        });
    };

    /**
     *@todo ajax成功后执行方法
     *@param ret object 服务端返回的信息 ret={status:200,data:data,url:baidu.com}
     *@param options object 拼装好的参数对象
     **/
    jqajax.prototype.callBack = function(ret, options) {

        if ((undefined == ret) || (null == ret) || options.close)
            return false;

        //调用自定义方法
        this[options.complete](ret, options);
    };

    jqajax.prototype.complete = function(ret, options) {
        var _this = this;
        var index;
        if (window.name) {
            index = parent.layer.getFrameIndex(window.name);
        }
        if (200 == ret.status) {
            if (options.action == "add") {
                options.data[options.key] = ret.data[options.key];
            }

            if (options.action == "list") {
                options.data = ret.data;
                options.pages = ret.pages;
                options.pageTotal = ret.total;
            }


            if ((undefined == ret.url) || (null == ret.url) || ("" == ret.url)) {
                if (index) {
                    parent.layer.close(index);
                    if (ret.msg) {
                        parent.layer.msg(ret.msg, { icon: 6 });
                    }
                } else {
                    layer.closeAll();
                    if (ret.msg) {
                        layer.msg(ret.msg, { icon: 6 });
                    }
                }
            } else {
                if ('reload' == ret.url) {
                    if (index) {
                        parent.layer.close(index);
                        parent.location.reload();
                    } else {
                        location.reload();
                    }
                } else if (index) {
                    parent.layer.close(index);
                    parent.location.href = ret.url;
                } else {
                    location.href = ret.url;
                }
            }
            if (options.action != "nodo") {
                _this.action(options);
            }
        } else {
            if (index) {
                if (ret.msg) {
                    parent.layer.msg(ret.msg, { icon: 5 });
                }
            } else {
                if (ret.msg) {
                    layer.msg(ret.msg, { icon: 5 });
                }
            }
        }
    }

    /**
     *@todo 弹出确认窗口
     *@param options object 拼装好的参数对象
     *@param obj object 当前操作对象
     */
    jqajax.prototype.confirm = function(options, obj) {
        var _this = this;
        if (options.confirm) {
            var title = "";
            if (options.title) {
                title = options.title;
            } else {
                if (obj) {
                    title = obj.html().replace(/<i [\s\S]*<\/i>/ig, '');
                    title = '确认<span class="color-commred">' + title + '</span>操作吗';
                }
            }
            layer.confirm(title, { icon: 3, title: '提示' }, function(index) {
                _this.ajax(options, obj);
                layer.close(index);
            });
        } else {
            _this.ajax(options, obj);
        }
    }


    /**
     * 操作本地数据
     */
    jqajax.prototype.action = function(options) {
        var _this = this,
            jqtable = top.global[options.dataName],
            params = options.data;

        if (typeof(top.global[options.dataName]) == undefined) {
            layer.closeAll();
            if (window.name && parent.layer.getFrameIndex(window.name)) {
                parent.layer.alert("dataName名称错误或无此数据集", { icon: 5 });
            } else {
                layer.alert("dataName名称错误或无此数据集", { icon: 5 });
            }

            return false;
        }

        // count = 0;
        if (typeof options.data === 'string') {
            params = _this.paramToObj(options.data);
        }

        if (options.action == "list") {

            jqtable.options = $.extend({}, jqtable.options, options)
            jqtable.options.curr = 1;
            jqtable.options.pageTotal = options.pageTotal;
            if (jqtable.options.server) {
                jqtable.options.pages = options.pages;
                jqtable.options.list = options.data;
                jqtable.render();
            } else {
                jqtable.options.record = options.data;
                jqtable.options = jqtable.setPage();
            }
            return false;
        }


        if (typeof(params[options.key]) != "string") {
            layer.closeAll();
            if (window.name && parent.layer.getFrameIndex(window.name)) {
                parent.layer.alert("设定的key值错误", { icon: 5 });
            } else {
                layer.alert("设定的key值错误", { icon: 5 });
            }

            return false;
        }

        var ids = params[options.key].split(",");
        for (var i in params) {
            if (params[i]) {
                params[i] = isNaN(Number(params[i])) ? params[i] : Number(params[i]);
            }

        }

        if (jqtable.options.server) {
            if (options.action == "del") {
                jqtable.options.list = _this.delRecord(ids, jqtable.options.list, options.key);
            } else if (options.action == "add") {

                jqtable.options.list = _this.addRecord(jqtable.options.list, params, options);
            } else {
                jqtable.options.list = _this.updateRecord(ids, jqtable.options.list, options.key, params);
            }
            jqtable.setUrlPage();
        } else {

            if (options.action == "del") {
                jqtable.options.record = _this.delRecord(ids, jqtable.options.record, options.key);
                jqtable.options.pageTotal = jqtable.options.pageTotal - ids.length;
            } else if (options.action == "add") {
                jqtable.options.record = _this.addRecord(jqtable.options.record, params, options);

            } else {
                jqtable.options.record = _this.updateRecord(ids, jqtable.options.record, options.key, params);
            }
            jqtable.setPage();
        }
    }


    /**
     * 添加元素到数组
     */
    jqajax.prototype.addRecord = function(record, params, options) {
        if (options.pid && params[options.pid]) {
            record.list = this.ArrAfterInsert(record.list, params[options.pid], options.key, params);
        } else {
            record.list.unshift(params);
        }
        return record;
    }


    /**
     * 根据某字段的值更新数组元素
     */
    jqajax.prototype.updateRecord = function(ids, record, field, params) {
        var _this = this;
        $.each(ids, function(i, n) {
            record.list = _this.ArrUpdate(record.list, n, field, params);
        })
        return record;
    }

    /**
     * 根据某字段的值删除数组元素
     */
    jqajax.prototype.ArrUpdate = function(arr, val, field, data) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i][field] == val) {
                $.each(data, function(key, val) {
                    if (key != field) {
                        arr[i][key] = val;
                    }
                })
            }
        }
        return arr;
    }

    /**
     * 删除本地缓存中选中的数据
     */
    jqajax.prototype.delRecord = function(ids, record, field) {
        var _this = this;
        $.each(ids, function(i, n) {
            record.list = _this.ArrRemove(record.list, n, field);
        })
        return record;
    }

    /**
     * 根据某字段的值把元素插到该记录之后
     */
    jqajax.prototype.ArrAfterInsert = function(arr, val, field, params) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i][field] == val) {
                arr.splice(i + 1, 0, params);
                break;
            }
        }
        return arr;
    }


    /**
     * 根据某字段的值删除数组元素
     */
    jqajax.prototype.ArrRemove = function(arr, val, field) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i][field] == val) {
                arr.splice(i, 1);
            }
        }
        return arr;
    }

    /**
     * 把url的参数转换成对象
     */
    jqajax.prototype.paramToObj = function(pramas) {
        var theRequest = {};
        strs = pramas.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
        return theRequest;
    }

    jqajax.prototype.sort = function(params) {
        var _this = this,
            jqtable = top.global[params.dataName];
        if (jqtable.options.server) {
            //参数交给服务端，让服务端排序
        } else {
            var list = jqtable.options.record.list;
            var asc = function(prop) {
                return function(obj1, obj2) {
                    var val1 = obj1[prop];
                    var val2 = obj2[prop];
                    if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
                        val1 = Number(val1);
                        val2 = Number(val2);
                    }
                    if (val1 < val2) {
                        return -1;
                    } else if (val1 > val2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }

            var desc = function(prop) {
                return function(obj1, obj2) {
                    var val1 = obj1[prop];
                    var val2 = obj2[prop];
                    if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
                        val1 = Number(val1);
                        val2 = Number(val2);
                    }
                    if (val1 < val2) {
                        return 1;
                    } else if (val1 > val2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
            if (params.sort == "asc") {
                list.sort(asc(params.field));
            } else {
                list.sort(desc(params.field));
            }
            jqtable.options.record.list = list;
            jqtable.setPage();
        }

    }

    exports('jqajax', jqajax);
});