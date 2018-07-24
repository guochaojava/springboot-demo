/**
 @Name：layui.jqcitys 省市区联动选择。本模块借助与参考了 jquery.citys.js 1.0，http://jquerywidget.com
 @Author：Paco
 @date: 2017-07-26
 @lastModify 2017-07-26
 @web: www.jqcool.net
 */
layui.define(['jquery', "jqajax", 'jqform'], function(exports) {
    var $ = layui.jquery,
        jqajax = layui.jqajax,
        ajax = new jqajax(),
        form = layui.jqform,
        jqcitys = function() {
            this.options = {
                url: './data/citys.json', //数据库地址
                provinceField: 'province', //省份字段名
                cityField: 'city', //城市字段名
                areaField: 'area', //地区字段名
                data: "", //省市区数据
                province: 0, //省份,可以为地区编码或者名称
                city: 0, //城市,可以为地区编码或者名称
                area: 0, //地区,可以为地区编码或者名称
                code: 0, //已选中的地区
                citys: "#citys", //存放省市区下拉框的容器ID或样式类
                required: true, //是否必须选一个
                currData: "",
                hasCity: false
            }
        };



    jqcitys.prototype.init = function(options) {


        var _this = this;
        _this.options = $.extend(true, _this.options, options);
        //如果数据已存在则直接格式，否则异步请求数据
        if (_this.options.data) {
            _this.setData(_this.options.data);
        } else {
            _this.getData();
        }

        form.on("select(citys)", function(data) {

            if (data.elem.name == "province") {
                _this.options.province = parseInt(data.elem.value);
                _this.options.city = 0;
                _this.options.area = 0;
                _this.setData(_this.options.data);
                _this.city();
            } else if (data.elem.name == "city") {
                _this.options.city = parseInt(data.elem.value);
                _this.options.area = 0;
                _this.setData(_this.options.data);
                _this.area();
            } else {
                _this.options.area = parseInt(data.elem.value);
            }
        })
    }

    /**
     * 获取省市区数据
     */
    jqcitys.prototype.getData = function() {
        var _this = this,
            params = ajax.params($(_this.options.citys));

        params = $.extend(true, _this.options, params);
        ajax.ajax(params);
        ajax.complete = function(data) {
            _this.options.data = data;
            _this.setData(_this.options.data);
        }
    }



    jqcitys.prototype.setData = function(data) {
        var _this = this,
            province = {},
            city = {},
            area = {};
        _this.options.hasCity = false;
        for (var code in data) {
            if (!(code % 1e4)) { //获取所有的省级行政单位
                province[code] = data[code];
                if (_this.options.required && !_this.options.province) {
                    if (_this.options.city && !(_this.options.city % 1e4)) { //省未填，并判断为直辖市
                        _this.options.province = _this.options.city;
                    } else {
                        _this.options.province = code;
                    }
                } else if (isNaN(_this.options.province) && data[code].indexOf(_this.options.province) > -1) {
                    _this.options.province = code;
                }
            } else {
                var p = code - _this.options.province;
                if (_this.options.province && p > 0 && p < 1e4) { //同省的城市或地区
                    if (!(code % 100)) {
                        _this.options.hasCity = true;
                        city[code] = data[code];
                        if (_this.options.required && !_this.options.city) {
                            _this.options.city = code;
                        } else if (isNaN(_this.options.city) && data[code].indexOf(_this.options.city) > -1) {
                            _this.options.city = code;
                        }
                    } else if (p > 8000) { //省直辖县级行政单位
                        city[code] = data[code];
                        if (_this.options.required && !_this.options.city) {
                            _this.options.city = code;
                        } else if (isNaN(_this.options.city) && data[code].indexOf(_this.options.city) > -1) {
                            _this.options.city = code;
                        }
                    } else if (_this.options.hasCity) { //非直辖市
                        var c = code - _this.options.city;
                        if (_this.options.city && c > 0 && c < 100) { //同个城市的地区
                            area[code] = data[code];
                            if (_this.options.required && !_this.options.area) {
                                _this.options.area = code;
                            } else if (isNaN(_this.options.area) && data[code].indexOf(_this.options.area) > -1) {
                                _this.options.area = code;
                            }
                        }
                    } else {
                        area[code] = data[code]; //直辖市
                        if (_this.options.required && !_this.options.area) {
                            _this.options.area = code;
                        } else if (isNaN(_this.options.area) && data[code].indexOf(_this.options.area) > -1) {
                            _this.options.area = code;
                        }
                    }
                }
            }
        }
        _this.currData = {
            province: province,
            city: city,
            area: area
        }
        _this.province();



    }


    jqcitys.prototype.province = function() {
        var _this = this,
            $province = $(_this.options.citys).find('select[name="' + _this.options.provinceField + '"]');
        $province.empty();
        if (!_this.options.required) {
            $province.append('<option value=""> - 请选择 - </option>');
        }
        for (var i in _this.currData.province) {
            $province.append('<option value="' + i + '" >' + _this.currData.province[i] + '</option>');
        }
        if (_this.options.province) {
            var value = _this.options.province;
            $province.val(value);
        }
        _this.city();
    }

    jqcitys.prototype.city = function() {
        var _this = this,
            $city = $(_this.options.citys).find('select[name="' + _this.options.cityField + '"]');
        $city.empty();
        if (!_this.options.hasCity) {
            $city.append('<option value=""> - 请选择 - </option>');
            $city.css('display', 'none').parent("div.layui-input-inline").css('display', 'none');
        } else {
            $city.css('display', '').parent("div.layui-input-inline").css('display', '');
            if (!_this.options.required) {
                $city.append('<option value=""> - 请选择 - </option>');
            }
            for (var i in _this.currData.city) {
                $city.append('<option value="' + i + '">' + _this.currData.city[i] + '</option>');
            }
            if (_this.options.city) {
                var value = _this.options.city;
                $city.val(value);
            } else if (_this.options.area) {
                var value = _this.options.area;
                $city.val(value);
            }
        }
        _this.area();
    }

    jqcitys.prototype.area = function() {
        var _this = this,
            $area = $(_this.options.citys).find('select[name="' + _this.options.areaField + '"]');
        $area.empty();

        if (!_this.options.required) {
            $area.append('<option value=""> - 请选择 - </option>');
        }

        for (var i in _this.currData.area) {
            $area.append('<option value="' + i + '">' + _this.currData.area[i] + '</option>');
        }
        if (_this.options.area) {
            var value = _this.options.area;
            $area.val(value);
        }

        form.render("select");
    }


    var citys = new jqcitys();

    exports('jqcitys', function(options) {

        return citys.init(options);
    });
});