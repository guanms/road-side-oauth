//获取浏览器url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var loginparams = getQueryString("loginparams");

if (undefined != loginparams && null != loginparams) {
    var base64 = new Base64();
    var paramsStr = base64.decode(loginparams);
    var params = JSON.parse(paramsStr);
    console.log(params);

    var token = params.token;
    var loginName = params.loginName;
    var userName = params.userName;
    var parkingCode = params.parkingCode;
    var ip = params.ip;
    console.log(token);

    if (token && typeof (token) != 'undefined') {
        sessionStorage.setItem("token", token);
        sessionStorage.setItem("loginName", loginName);
        sessionStorage.setItem("userName", userName);
        sessionStorage.setItem("parkingCode", parkingCode);
        sessionStorage.setItem("ip", ip);
    }

    if (ip != "undefined" && ip != null && ip != "") {
        carLaneBaseUrl = "http://" + ip + ":8080";
    } else {
        carLaneBaseUrl = "http://tb.parkpaytech.com:8080";
    }

}


$(document).ajaxComplete(function (event, jqXHR, settings) {
    var sessionstatus = jqXHR.getResponseHeader("session-status");
    if (sessionstatus == "timeout") {

       sessionStorage.removeItem("token");

        jAlert("登录超时！请重新登录！", "提示：", function (r) {
            // 开发环境
            window.location.href = "http://tb.parkpaytech.com/";
            //生产环境e
            // window.location.href="http://10.19.128.254/";
        });
    }
});

var page = $("#page").val();

/**指定分页跳转
 * @author ：jie
 * @date : date
 */
function golastpages() {
    if (page == 1) {
        page = 1
    } else {
        page = parseInt(page) - 1;
    }
    gotopage(page);
}

/**上下分页
 * @author ：jie
 * @date : jintian
 * @param page 当前页数
 */
function gonextpages() {
    page = parseInt(page) + 1;
    gotopage(page);
}

/**控制文本框只能输入数字,绑定onkeypress 事件
 */
function checkNumeric(value) {
    if (!(((event.keyCode >= 48) && (event.keyCode <= 57))
            || event.keyCode == 46 || event.keyCode == 45)) {
        event.keyCode = 0;
    }
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};



//道闸控制 测试数据
function openGate(lcCode) {
    $.ajax({
        url: carLaneBaseUrl + "/api/openGate",
        type: "GET",
        data: {"lcCode": lcCode},
        success: function (res) {
            if (res == "success") {
                alert("开闸成功！");
            } else {
                alert("开闸失败！");
            }
        },
        error: function () {
            alert("开闸失败！");
            return false;
        }
    })
}


/**
 * 隐藏
 */
function hidenye() {
    var zhezhaoPart = document.getElementById("zhezhao");
    zhezhaoPart.style.display = "block";
    zhezhaoPart.style.position = "absolute";
    zhezhaoPart.style.top = "100%";
    zhezhaoPart.style.left = "100%";
    zhezhaoPart.style.marginTop = "-815px";
    zhezhaoPart.style.marginLeft = "-1500px";
    zhezhaoPart.style.background = "rgba(0, 0, 0, -0.5)";
    zhezhaoPart.style.backgroundImage = "url(../images/backdrop.gif)";
    zhezhaoPart.style.backgroundPosition = "center";
    zhezhaoPart.style.backgroundRepeat = "no-repeat";
    zhezhaoPart.style.backgroundAttachment = "fixed";
    zhezhaoPart.style.width = "1680px";
    zhezhaoPart.style.height = "800px";
    zhezhaoPart.style.zIndex = "501";
}

/**
 * 显示
 */
function showye() {
    var zhezhaoPart = document.getElementById("zhezhao");
    zhezhaoPart.style.display = "none";
}


//webSocket断开重连
function reconnect() {
    var ip = getServerIp();
    socket = new WebSocket("ws://" + ip + "/websocket");
    socket.onmessage = function (event) {

        var ta = document.getElementById('responseText');
        var whetherVehicleInfo = event.data;
        if (whetherVehicleInfo == "yes") {
            console.log(whetherVehicleInfo);
            query();
        }
    };
    socket.onclose = function (event) {
        socket.onclose = disConnect;
    };
    socket.onerror = function (event) {
        socket.onerror = disConnect;
    }
}

var disConnect = function () {
    setTimeout(function () {
        reconnect();
    }, 5000);
}


function exportCsv(fileName, data) {

    var uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(data);
    var downloadLink = document.createElement("a");
    downloadLink.href = uri;
    downloadLink.download = fileName;
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}


// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 显示遮罩层
 */
function showShade(iconFlag, contents) {

    $("#shade-div").show();

    if (iconFlag) {
        $("#shade-icon").show();
    } else {
        $("#shade-icon").hide();
    }
    if (undefined != contents && null != contents && "" != contents.trim()) {

        $("#shade-contents").html(contents);
        $("#shade-contents").show();
        if (iconFlag) {
            $("#shade-contents").css({"margin-top": "10px"});
        } else {
            $("#shade-contents").css({"margin-top": "20%"});
        }
    }
}

/**
 * 隐藏遮罩层
 */
function hideShade() {
    $("#shade-div").hide();
    $("#shade-icon").hide();
    $("#shade-contents").html("");
    $("#shade-contents").hide();
}


function saveFile(blob, fileName) {
    if (window.navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, fileName);
    } else {
        var src = window.URL.createObjectURL(blob);
        console.info("下载链接:" + src);
        var a = document.createElement('a');
        a.href = src;
        a.download = fileName;
        a.target = '_blank';
        a.click();
    }

}


String.prototype.stringToDate = function () {

    return new Date(Date.parse(this.replace(/-/g, "/")));

}


function getAllParkCode() {
    $.ajax({
        url: carLaneBaseUrl + "/api/virtual/getAllVirtualParks",
        type: "GET",
        contentType: "application/json",
        headers: {"x-auth-token": sessionStorage.getItem("token")},
        success: function (result) {
            if (result != null && result != '' && result != undefined) {
                var html;
                //查询车场以及虚拟车场
                $.each(result, function (index, item) {
                    if (index === 0) {
                        html += "<option value='" + item.virtualParkingCode + "' selected>" + item.parkingName + "</option>";
                    } else {
                        html += "<option value='" + item.virtualParkingCode + "'>" + item.parkingName + "</option>";
                    }
                });
                $(".chosePark").html(html);
            }

        }
    })
}

$.fn.settings = function (option) {
    if (!option) option = {}
    var _option = {
        id: option.id || 'dialog' + new Date().getTime(),
        header: option.header || '设置',
        body: option.body || '',
        width: option.width || 400,
        height: option.height || 400,
        content: function () {
            if (option.content && option.content instanceof jQuery) {
                return option.content
            } else if (option.content) {
                return option.content
            } else {
                return ''
            }
        },
        confirm: option.confirm || function () {

        },
        before: option.before || function () {

        },
        after: option.after || function () {

        },
        cancel: option.cancel || function () {

        },
        hasBtn: option.hasBtn !== false,
        btnPosition: option.btnPosition || {},
        btn1: option.btn1 || '保存',
        btn2: option.btn2 || '关闭',
        wrapper: function () {
            if (option.wrapper && option.wrapper.length) {
                // return option.wrapper.css({position: 'absolute'})
                return option.wrapper
            } else {
                return $('body')
            }
        },
    }
    if ($('#' + _option.id)[0]) {
        return undefined;
    }
    var box = $('<div class="setting_dialog__wrapper" id="' + _option.id + '" hidden></div>');
    var mask = $('<div class="setting_mask" style="z-index: 2333;display: block"></div>');

    var contentWrapper = $('<div class="setting_content" style="z-index: 2334;display: block;position: absolute;left: 50%;top: 40%;transform: translate(-50%,-50%);margin: 0"></div>');
    contentWrapper.css({width: _option.width, height: _option.height})

    var headerWrapper = $('<div class="header-wrap" style="line-height: 28px;"></div>');
    var header = $('<span>' + _option.header + '</span>');
    var headerClose = $('<span class="close-btn setting_close">×</span>');
    headerWrapper.append(header).append(headerClose);

    var bodyWrapper = $('<div class="body-wrap" style="padding: 5px 0"></div>');
    bodyWrapper.append(_option.content);

    contentWrapper.append(headerWrapper).append(bodyWrapper)
    if (_option.hasBtn) {
        var footer = $('<div class="footer-wrap" style="position: absolute;bottom: 20px;right: 0; margin-right: 5%;">' +
            '            <button class="btn_common btn_common__dark setting_close" style="margin-right: 0">' + _option.btn2 + '</button>' +
            '            <button class="btn_common btn_common__primary setting_confirm">' + _option.btn1 + '</button>' +
            '        </div>');
        if (_option.btnPosition.marginRight) {
            footer.css({marginRight: _option.btnPosition.marginRight})
        }
        if (_option.btnPosition.bottom){
            footer.css({bottom:_option.btnPosition.bottom})
        }
        contentWrapper.append(footer);
    }
    box.append(mask).append(contentWrapper);

    _option.wrapper().append(box);
    _option.after();

    $(this).on('click', function () {
        _option.before();
        box.show();
    })

    $('.setting_mask,.setting_close').on('click', function () {
        box.hide();
        _option.cancel();
    })

    $('.setting_confirm').on('click', function () {
        box.hide();
        _option.confirm();
    })

    return {
        id: _option.id,
        remove: function () {
            $('#' + this.id).remove();
        }
    };

}

var bindColumnSelection = function (boxChanged) {
    $('.block input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
    });
    $('.block input').on('ifChecked', function () {
        var field = $(this).parents('.column_item').data('field');
        if (boxChanged[field] === true || boxChanged[field] === false) {
            delete  boxChanged[field]
        } else {
            boxChanged[field] = true;
        }
    }).on('ifUnchecked', function () {
        var field = $(this).parents('.column_item').data('field');
        if (boxChanged[field] === true || boxChanged[field] === false) {
            delete  boxChanged[field]
        } else {
            boxChanged[field] = false;
        }
    })
}

var createSettings = function (columnState) {
    var columnWrapper = $('<div class="column_wrapper"></div>');
    for (var key in columnState.show) {
        columnWrapper.append('<div class="column_item" data-field="' + key + '">' +
            '<label class="block">\n' +
            '<input type="checkbox" checked>\n' + columnState.show[key] +
            '</label>' +
            '</div>')
    }
    for (var key in columnState.hide) {
        columnWrapper.append('<div class="column_item" data-field="' + key + '">' +
            '<label class="block">\n' +
            '<input type="checkbox">\n' + columnState.hide[key] +
            '</label>' +
            '</div>');
    }
    return columnWrapper;
}

var saveColumnState = function (boxChanged, _table) {
    for (var key in boxChanged) {
        if (boxChanged[key]) {
            _table.bootstrapTable('showColumn', key);
        } else {
            _table.bootstrapTable('hideColumn', key);
        }
        delete boxChanged[key]
    }
}

var cancelColumnState = function (boxChanged) {
    for (var key in boxChanged) {
        if (boxChanged[key]) {
            $('.column_item[data-field=' + key + '] input').iCheck('uncheck');
        } else {
            $('.column_item[data-field=' + key + '] input').iCheck('check');
        }
        delete boxChanged[key]
    }
}

function Model(option) {

    this._data = option.data || {};
    this._autoInit = option.autoInit;

    this.init = function () {
        this.bindModel();
        /*if (this._autoInit) {
            this.initData()
        }*/
    }

    this.updateDom = function (key, value) {
        $('[model=' + key + ']').val(value);
    }

    this.setData = function (data) {
        for (var key in data) {
            this._data[key] = data[key]
            this.updateDom(key, data[key]);
        }
    }

    this.bindModel = function () {
        var that = this;
        $(document).on('input propertychange', '[model]', function () {
            var model = $(this).attr('model'),
                value = $(this).val()
            var data = {};
            data[model] = value;
            that.setData(data)
        })
    }

    this.clear = function () {
        for (var key in this._data) {
            delete this._data[key]
        }
        $('[model]').val('')
    }

    /*this.initData = function (){
        var that = this;
        $('[model]:not(select)').each(function () {
            var key = $(this).attr('model');
            var data = {};
            data[key] = ''
            that.setData(data)
        })
    }*/

    this.init();

}


function upOrDown(){

    var flag = true;

    $("#upOrDown").click(function () {

        if(flag){

            flag = false;

            var spanClass = $(this).find("span:first").hasClass("triangle-down");

            if (spanClass) {
                $(this).find("span:first").removeClass("triangle-down");
                $(this).find("span:first").addClass("triangle-up");
                $(this).find("span:last").html("收起");

                $("#upOrDown_content").slideDown("fast",function () {
                    flag = true;

                    var scrollHeight = $('#boot_page_content').prop("scrollHeight");
                    $('#boot_page_content').scrollTop(scrollHeight,"slow");
                });



            }else{
                $(this).find("span:first").removeClass("triangle-up");
                $(this).find("span:first").addClass("triangle-down");
                $(this).find("span:last").html("展开");
                $("#upOrDown_content").slideUp("fast",function () {
                    flag = true;
                });

            }

        }

    })
}

function selectBtnClick(div) {

    var parentdiv = $(div).parent();
    var divfirst = $(div).parent().find("div:first");

    var flag = divfirst.hasClass("select_btn_title_left");
    console.log(flag)

    if(flag){
        divfirst.removeClass();
        divfirst.addClass("select_btn_title_right");
        divfirst.html("否");
        parentdiv.parent().find("select").val(0);

        if(parentdiv.parent().find("select").attr("id")=="thirddata"){
            $("#showPipaParam").hide();
        }

        if(parentdiv.parent().find("select").attr("id")=="etc_enable"){
            $("#showEtcParam").hide();
        }
        if(parentdiv.parent().find("select").attr("id")=="isAlipay"){
            $("#alipayInfo").hide();
        }

    }else{
        divfirst.removeClass();
        divfirst.addClass("select_btn_title_left");
        divfirst.html("是");
        parentdiv.parent().find("select").val(1);

        if(parentdiv.parent().find("select").attr("id")=="thirddata"){
            console.log("出来吧！");
            $("#showPipaParam").show();
        }
        if(parentdiv.parent().find("select").attr("id")=="etc_enable") {
            $("#showEtcParam").show();
        }
        if(parentdiv.parent().find("select").attr("id")=="isAlipay"){
            $("#alipayInfo").show();
        }

    }
    
}


function setSelectValues(div,selectvalue) {

    console.log(div)
    console.log(selectvalue)

    if(selectvalue==0){
        setNo(div)
    }else if(selectvalue==1){
        setYes(div)
    }else if(selectvalue==2){
        setYes(div)
    }

    
}

function setYes(div) {

    var parentdiv = $(div).parent();
    var select_btn_div = parentdiv.find(".select_btn_div");
    var divfirst = select_btn_div.find("div:first");

    divfirst.removeClass();
    divfirst.addClass("select_btn_title_left");
    divfirst.html("是");
}

function setNo(div) {

    var parentdiv = $(div).parent();
    var select_btn_div = parentdiv.find(".select_btn_div");
    var divfirst = select_btn_div.find("div:first");

    divfirst.removeClass();
    divfirst.addClass("select_btn_title_right");
    divfirst.html("否");
}


function showfaildialog(title,content) {
    $("#register_fail_dialog").show();
    $("#register_fail_wrap").show();
    $("#fail_message").html(content);
    $("#fail_title").html(title);
}

function closefaildialog() {
    $("#register_fail_dialog").hide();
    $("#register_fail_wrap").hide();
}




