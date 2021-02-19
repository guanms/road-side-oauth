$(function () {
    var loginType = getQueryString("loginType");
    var username = getQueryString("username");
    var password = getQueryString("pwd");

    if(loginType == "android") {
        $("#userName").val(username);
        $("#passWord").val(password);
        formSubmit();
    }
    getCookie($("#userName"),$("#password"));
})


function showfaildialog(content) {
    $("#register_fail_dialog").show();
    $("#register_fail_wrap").show();
    $("#fail_message").html(content);
}

function closefaildialog() {
    $("#register_fail_dialog").hide();
    $("#register_fail_wrap").hide();
}

function formSubmit() {

    sessionStorage.clear();

    $.ajax({
        // url: carLaneBaseUrl + "/api/accounts/login",
        url :oauthUrl+"/v1.0/signUp/maintenance/page/pageCarLogin",
        // type: "GET",
        type:"post",
        data: {
            "loginName": encrypt($("#userName").val()),
            "password": encrypt($("#passWord").val())
        },
        success: function (result) {

            if (result != null && result != '' && result != undefined) {
                if(result.success){
                    var tokenAndResource = result.data;
                    var loginName = tokenAndResource.userAndResourceVO.userInfo.loginName;
                    // var parkingCode = tokenAndResource.userAndResourceVO.userInfo.identityCode;
                    var parkingCode = tokenAndResource.userAndResourceVO.userInfo.identityCode;
                    var token = tokenAndResource.authTokenInfo.access_token;

                    var userName = $("#userName").val();
                    //此处涉及到两个token，一个是来自鉴权中心的token，一个是来自车场的token
                    if (token && typeof (token) != 'undefined') {
                        // sessionStorage.setItem("token", token);
                        sessionStorage.setItem("loginName", loginName);
                        sessionStorage.setItem("userName", userName);
                        sessionStorage.setItem("parkingCode", parkingCode);
                        sessionStorage.setItem("tokenAndResource",JSON.stringify(tokenAndResource));
                        sessionStorage.setItem("redisId",tokenAndResource.authTokenInfo.access_token);
                        //此处添加判断，从userInfo中拿信息，验证是否是车场的createMan登陆车场
//                             LoginInformations();
                        //增加登陆成功后将选择记住密码的密码保存进cookie中的操作
                        var isRemember = $("#isRemember").val();
                        if(isRemember == "1"){
                            var pwd = $("#passWord").val();
                            console.log("选择记住密码，在cookie中保存7天,username = "+ userName + ",password = " + pwd);
                            setCookie(userName,pwd);
                        }else{
                            deleteCookie(userName);
                        }
                        window.location.href = "../pages/indexPark.html";
                    }
                }else {

                    showfaildialog(result.message)
                    // alert(result.message);
                }

            }
        },
        error: function (XMLHttpRequest) {
            var status = XMLHttpRequest.status;
            if (status == '401') {
                $("#err").show();
                $("#userName").val('');
                $("#passWord").val('');
            }
        }
    });


}

function deleteCookie(parkingCode) {
    //这里若不选择记住，遍历cookie，将这个车场的cookie全部消灭
    var cookieStr = String(document.cookie);
    var rememberArr = cookieStr.split(";");
    var parkingArr = [];var parkingStuff;
    //开始遍历cookie
    for(var i = 0;i<rememberArr.length ; i++){
        parkingArr = rememberArr[i].split("=");
        if(parkingArr[0].indexOf("parking") != "-1"){
            parkingStuff = rememberArr[i].substring(parkingArr[0].length + 1);
            console.log("循环的这个车场的code和密码："+parkingStuff);
            var cookieMsg = parkingStuff.split("%%");
            var thisParkingCode = decrypt(cookieMsg[0]);
            if(thisParkingCode == parkingCode){
                console.log("遍历到已保存的此车场cookie，进行删除");
                var goneTime = new Date();
                goneTime.setTime(goneTime.getTime() + 30 * 1000);
                document.cookie = parkingArr[0] + "=1;expires=" + goneTime.toString();
            }
        }
    }
}

var countRemember = 0;
function setCookie(userName,password) {
    //当前的cookie
    var nowCookie = String(document.cookie);
    var nowCookieArr = nowCookie.split(";");
    var nowMaxCount = 0;
    var thisCookie = [];
    console.log(nowCookieArr);
    for(var i = 0;i < nowCookieArr.length;i++){
        thisCookie = nowCookieArr[i].split("=");
        if(thisCookie[0].indexOf("parking") != "-1"){
            var tmp = thisCookie[0].trim().substring(7);
            nowMaxCount = parseInt(tmp);
            if(nowMaxCount != null){
                if(nowMaxCount > countRemember){
                    countRemember = nowMaxCount;
                    console.log("当前cookie中的num最大值变更为"+countRemember);
                }
            }
        }
    }
    //将之前此车场保存的cookie删除
    deleteCookie(userName);
    //新创建的cookie的count为最大值+1
    if(countRemember >= 999){
        countRemember = 0;
    }else {
        countRemember++;
    }
    var activeTime = new Date();
    activeTime.setTime(activeTime.getTime() + 7 * 24 * 60 * 60 * 1000);
    var AESUserName = encrypt(userName);
    var AESPassword = encrypt(password);
    document.cookie = "parking"+ countRemember + "=" +AESUserName+ "%%" + AESPassword + ";expires="+activeTime.toString();
}

function getCookie() {
    //是否是记住过密码的标志位，为false时，将是否记住改为否，需要用户自己改变
    var remember = false;
    var userName = $("#userName").val();
    var cookieStr = String(document.cookie);
    var rememberArr;
    var parkingArr = [];
    var parkingCode = "";
    var password = "";
    var parkingStuff = "";
    rememberArr = cookieStr.split(";");
    for(var i = 0;i <rememberArr.length;i++){
        parkingArr = rememberArr[i].split("=");
        if(parkingArr[0].indexOf("parking") != "-1"){
            parkingStuff = rememberArr[i].substring(parkingArr[0].length + 1);
            console.log("循环的这个车场的code和密码："+parkingStuff);
            //这里1是用于删除cookie时给原cookie赋的值
            if(parkingStuff != "1"){
                var cookieMsg = parkingStuff.split("%%");
                parkingCode = decrypt(cookieMsg[0]);
                if(parkingCode == userName){
                    //自动填入密码并选中记住密码，并将是否记住置为是
                    password = decrypt(cookieMsg[1]);
                    console.log("输入parkingCode一致，cookie保存的密码："+password);
                    $("#passWord").val(password);
                    $("#isRememberImg").attr("src","../images/remember.png");
                    $("#isRemember").val("1");
                    remember = true;
                    break;
                }
            }
        }
    }
    if(!remember){
        $("#isRememberImg").attr("src","../images/forget.png");
        $("#isRemember").val("0");
    }
}

function changeIsRemember(){
    var isRemember = $("#isRemember").val();
    if(isRemember == "0"){
        //为0表示从不记住选为记住
        $("#isRememberImg").attr("src","../images/remember.png");
        $("#isRemember").val("1");
    }else {
        $("#isRememberImg").attr("src","../images/forget.png");
        $("#isRemember").val("0");
    }
}

document.onkeydown = keyDownSearch;

function keyDownSearch(e) {
    // 兼容FF和IE和Opera
    var theEvent = e || window.event;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        formSubmit();//具体处理函数
    }
}


function encrypt(word){
    //这里传入自己定义的密钥，后端解密时也需要使用一样的密钥
    var key = CryptoJS.enc.Utf8.parse("tingbei2020admin");
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

function decrypt(word){
    var key = CryptoJS.enc.Utf8.parse("tingbei2020admin");
    var decrypted = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}).toString(CryptoJS.enc.Utf8);
    return decrypted;
}



function LoginInformations(){
    var tokenAndResourceJson = sessionStorage.getItem("tokenAndResource");

    var uUid = $.parseJSON(tokenAndResourceJson).userAndResourceVO.userInfo.uiUuid;
    var parkingCode = $.parseJSON(tokenAndResourceJson).userAndResourceVO.userInfo.identityCode;
    var loginName = $.parseJSON(tokenAndResourceJson).userAndResourceVO.userInfo.loginName;
    // var uUid = "123";
    // var parkongCode = "test";
    // var logingName = "test";

    $.ajax({
        url: carLaneBaseUrl + "/api/accounts/LoginInformations",
        type: "GET",
        data: {
            "username": $("#userName").val(),
            "password": $("#passWord").val(),
            "parkingCode":parkingCode,
            "uUid":uUid,
            "loginName":loginName
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("x-auth-token");
            var ip = result.ip;

            if (token && typeof (token) != 'undefined') {
                sessionStorage.setItem("token", token);
                sessionStorage.setItem("ip", ip);
                carLaneBaseUrl = "http://" + result.ip + ":8080";
                window.location.href = "/carLane_v3.2.4/UI/pages/indexPark.html";
            }
        }});
}

