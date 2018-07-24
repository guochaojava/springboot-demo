/*
 * @Author: Paco
 * @Date:   2017-02-10
 * @lastModify 2017-07-24
 * +----------------------------------------------------------------------
 * | jqadmin [ jq酷打造的一款懒人后台模板 ]
 * | Copyright (c) 2017 http://jqadmin.jqcool.net All rights reserved.
 * | Licensed ( http://jqadmin.jqcool.net/licenses/ )
 * | Author: Paco <admin@jqcool.net>
 * +----------------------------------------------------------------------
 */

layui.define(['jquery', 'laytpl', 'jqajax', 'laypage', 'jqform', 'jqbind'], function(exports) {
    var $ = layui.jquery,
        tpl = layui.laytpl,
        jqajax = layui.jqajax,
        jqbind = layui.jqbind,
        form = layui.jqform,
        laypage = layui.laypage,
        ajax = new jqajax(),
        jqtable = function() {
            this.options = {
                tplid: "#list-tpl",
                listid: "#list",
                pageid: "", //如果留空则不分页
                curr: 1,
                pages: 0, //分页的总页数，通过服务端获取
                server: false,
                pageNum: 2, //每一页记录数
                pageArr: '2,5,30',
                callBack: "",
                list: {},
                pageInfo: true,
                maxtotal: 10000
            };
        };


    /**
     * [参数设置 options]
     */
    jqtable.prototype.init = function(conf) {
        var options = ajax.params($(conf.tplid)),
            _this = this;
        _this.options = $.extend(true, _this.options, options);
        if (conf) {
            _this.options = $.extend(true, _this.options, conf);
        }

        _this.setUrlPage();

        ajax.complete = function(ret) {
            if (ret.data == undefined || ret.data.list == "" || ret.data.list == undefined) {
                _this.options.pages = 0;
                _this.options.list = { "list": [] };
                _this.options.record = { "list": [] };
                //为空时清空原有的列表与分页
                $(_this.options.listid).empty();
                $(_this.options.pageid).empty();
            }
            _this.options.pageTotal = ret.total;
            if (_this.options.server) {
                _this.options.pages = ret.pages;
                _this.options.list = ret.data;
                _this.render();

            } else {
                _this.options.record = ret.data;
                _this.options = _this.setPage();
            }
        }

        form.on('checkbox(check)', function(data) {
            var checked = data.elem.checked,
                name = $(data.elem).data('name');
            $(_this.options.listid).find("input[name=" + name + "]").prop("checked", function() {
                return checked;
            });
            form.render("checkbox");
        });
    };

    jqtable.prototype.render = function() {
        var _this = this;
        if ("" != _this.options.list || undefined != _this.options.list) {
            var getTpl = $(_this.options.tplid).html();
            var obj = $(_this.options.listid);
            tpl(getTpl).render(_this.options.list, function(html) {
                obj.html(html);
            })
            if (_this.options.pageid) {
                _this.page();
                if (_this.options.pageInfo) {
                    _this.pageInfo();
                }
            }

            //取消全选框中
            obj.prev("thead").find("input[type=checkbox]").removeAttr("checked");

            //渲染下拉框
            if (_this.options.select) {
                _this.renderCat(_this.options.list, _this.options.select, _this.options.selectid);
            }

            form.render();

            //监听分页下拉框
            form.on('select(pageNum)', function(data) {
                _this.options.pageNum = data.value;
                _this.options.curr = 1;
                if (_this.options.server) {
                    _this.setUrlPage();
                } else {
                    _this.options = _this.setPage();
                }
            });

            jqbind.init();

            //如果定义有方法，则调用此方法
            if (_this.options.callBack) {
                this[_this.options.callBack](_this.options);
                return false;
            }
        }
    }

    jqtable.prototype.renderCat = function(data, select, selectid) {
        var getTpl = $(select).html();
        var obj = $(selectid);
        tpl(getTpl).render(data, function(html) {
            obj.html(html);
        })
    }

    jqtable.prototype.page = function() {
        var _this = this;
        //var nowpage = location.hash.replace('#!page=', '');
        if (_this.options.curr > _this.options.pages) {
            _this.options.curr = _this.options.pages;
        }
        laypage({
            cont: $(_this.options.pageid),
            pages: _this.options.pages,
            curr: _this.options.curr,
            hash: 'page', //自定义hash值
            jump: function(obj, first) {

                if (!first) {
                    _this.options.curr = obj.curr;
                    if (_this.options.server) {
                        _this.setUrlPage();
                    } else {
                        _this.setPage();
                    }
                }
            }
        });
    }

    /**
     * @todo 显示总共记录数与每页显示的记录数
     * @param object options 配置参数
     */
    jqtable.prototype.pageInfo = function() {
        var _this = this;
        var html = '共 ' + _this.options.pageTotal + ' 条记录/每页 <div class="layui-form page-num"><select name="pageNum" lay-filter="pageNum">';
        var pageOption = _this.options.pageArr.split(',');
        for (var i = 0; i < pageOption.length; i++) {
            html += '<option value="' + pageOption[i] + '"' + ((_this.options.pageNum == pageOption[i]) ? 'selected=selected' : '') + '>' + pageOption[i] + '</option>';
        }
        html += '</select></div>';
        $(_this.options.pageid).prepend(html);
    }

    /**
     *@todo 本地分页
     */
    jqtable.prototype.setPage = function() {
        var _this = this;
        if (_this.options.pageid) {
            if (_this.options.pageTotal >= _this.options.maxtotal) {
                _this.options.server = true;
                _this.setUrlPage();
            }
            _this.options.pages = Math.ceil(_this.options.pageTotal / _this.options.pageNum);
            if (_this.options.curr > _this.options.pages) {
                _this.options.curr = _this.options.pages;
            }
            var arr = [],
                thisData = _this.options.record.list.concat().splice(_this.options.curr * _this.options.pageNum - _this.options.pageNum, _this.options.pageNum);
            layui.each(thisData, function(index, item) {
                arr.push(item);
            });
            _this.options.list.list = arr;
        } else {
            _this.options.list.list = _this.options.record.list;
        }

        _this.render();
        return _this.options;
    }

    /**
     *@todo 设置url中page参数为当前点击的页数
     *@param int curr 需要跳转到的页数（即点击分页按钮后的页数）
     */
    jqtable.prototype.setUrlPage = function() {
        var _this = this;
        if (_this.options.server) {
            if (_this.options.url.indexOf("p=") != -1) {
                _this.options.url = _this.options.url.replace(/p=\d+/, "p=" + _this.options.curr);
                _this.options.url = _this.options.url.replace(/pagesize=\d+/, "pagesize=" + _this.options.pageNum);
            } else {
                if (_this.options.url.indexOf("?") != -1) {
                    _this.options.url = _this.options.url + "&pagesize=" + _this.options.pageNum + "&p=" + _this.options.curr;
                } else {
                    _this.options.url = _this.options.url + "?p=" + _this.options.curr + "&pagesize=" + _this.options.pageNum;
                }
            }
        }
        ajax.ajax(_this.options);
    }

    exports('jqtable', jqtable);
});