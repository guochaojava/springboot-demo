function strRepeat(str, count) {
    var ret = "";
    for (var i = 0; i < count; i++) {
        ret += str;
    }
    return ret;
}

function setCat(catId, dataName) {
    var locationData = layui.data(dataName),
        record = locationData.list ? locationData.list : "";
    if (record) {
        for (var i = 0; i < record.length; i++) {
            if (record[i].id == catId) {
                return record[i].title;
            }
        }
    }
}

function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
}

function nullRepeat(str) {
    if(str == null){
        return "";
    }else {
        return str;
    }
}