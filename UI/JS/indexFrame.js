var showList = [];

//处理键盘事件
function doKey(e) {
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源
    var t = obj.type || obj.getAttribute('type');//获取事件源类型
    var readonly = obj.getAttribute('readonly');
    //退格键失效
    if (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") {
        return false;
    }
    if (readonly == "readonly") {
        return false;
    }
}

//禁止后退键 作用于Firefox、Opera
document.onkeypress = doKey;
//禁止后退键  作用于IE、Chrome
document.onkeydown = doKey;

function xxx() {

}

// //设为主页
// function doSetHomePage(obj, url) {
//     try {
//         obj.style.behavior = 'url(#default#homepage)';
//         obj.setHomePage(url);
//     } catch (e) {
//         if (window.netscape) {
//             try {
//                 netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
//             } catch (e) {
//                 alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
//             }
//         } else {
//             alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
//         }
//     }
// }
//
// //加入收藏夹
// function saveFavPage() {
//     window.external.AddFavorite('http://localhost:8080/ParkingManageSystem/Pages/indexPark.html', '同利停车场');
// }

function loginOut(){

    $.ajax({
        url :carLaneBaseUrl + "/api/accounts/logout",
        type:"get",
        success: function (result) {
        }
    })

}


$(function () {


    $("#loginname").html(sessionStorage.getItem("loginName"));

    $('.btn-logout').click(function () {
        loginOut();
        sessionStorage.clear();
        var a = document.createElement('a');
        a.href = 'login.html';
        a.click();
    });

    $(document).on('click', '.header_tab__item', function () {
        $(this).addClass('header_tab__active').siblings('.header_tab__item').removeClass('header_tab__active');
        var uuid = $(this).data('id');
        $('#' + uuid).show().siblings('.sidebar-menu').hide();
    });

    $.get(oauthUrl + '/v1.0/oauth2Server/authority/noRole/checkByLoginName/' + sessionStorage.getItem('loginName'), function (res) {

        var menus = res.showSrList;
        var userInfo = res.userInfo;

        console.log("-----------menus-------------");
        console.log(menus);

        // // 增加临时参数，确认菜单权限中是否有支付系统，有则去支付系统实现模拟登录
        // var isHavePay = false;
        //
        // // 增加临时参数，菜单权限中是否有停车系统，有则去停车系统实现模拟登录
        // var isHavePark = false;
        //
        // var isHaveCoupon = false;
        //
        // var isHaveBigData = false;
        menus.forEach(function (menu, index) {

            // if(menu.srProvider === 'chainbi-pay'){
            //     isHavePay = true;
            // }
            // if(menu.srProvider === 'park'){
            //     isHavePark = true;
            // }
            // if(menu.srProvider ==='coupon'){
            // 	isHaveCoupon = true;
            // }
            //
            // if(menu.srProvider ==='bigdata'){
            //     isHaveBigData = true;
            // }

            //拼顶部导航
            // 一级菜单
            var $item = $('<li class="nav-item"></li>');
            $item.append('<div class="first-nav"  data-id="' + menu.srUuid + '">' +
                '<a hrefs="#"><i class="nav-icon fa ' + menu.srDesc + '"></i><span>' + menu.srName + '</span><i class="my-icon nav-more"></i></a>' +
                '</div>');


            //左侧导航
            var roots = menu.sonlist;
            var $menu = $('<ul class="first-drop"></ul>');
            roots.forEach(function (item) {
                // 二级菜单
                var $branch = $('<li class="nav-second-item"></li>');
                $branch.append('<div class="second-nav"><a hrefs="#"><i class="nav-icon fa ' + item.srDesc + '"></i><span>' + item.srName + '</span><i class="my-icon nav-second-more"></i></a></div>').append(parseMenu(item.sonlist))
                $menu.append($branch)
            });
            // $('ul.sidebar-menu').html($menu.html());
            $item.append($menu);
            $('.nav-ul').append($item);
        });

        // ---------- 拥有支付系统权限 -----------
        // if(isHavePay){
        //     moniLoginPayService();
        // }
        //
        // //---拥有停车系统权限
        // if(isHavePark){
        //     moniLoginParkServer(userInfo);
        // }
        //
        // if(isHaveCoupon){
        // 	console.log("优惠券:::::");
        // 	moniLoginCouponServer();
        // }
        //
        // if(isHaveBigData){
        //     console.log("---拥有数据展示权限");
        //     moniLoginBigDataServer();
        // }
    })
});

// 三级菜单
function parseMenu(sonlist) {
    var $treemenu = $('<ul class="second-drop"></ul>');
    sonlist.forEach(function (item) {
        if (item.sonlist && item.sonlist.length) {
            var $thirdmenu = $('<li class="nav-third-item"></li>');
            // $thirdmenu.append('<div class="third-nav"><a hrefs="#"><i class="nav-icon fa ' + item.srDesc + '"></i><span>' + item.srName + '</span><i class="my-icon nav-third-more"></i></a></div>').append(parseMenuThird(item.sonlist))
            $thirdmenu.append('<div class="third-nav"><a hrefs="#"><i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span><i class="my-icon nav-third-more"></i></a></div>').append(parseMenuThird(item.sonlist))
             $treemenu.append($thirdmenu);
        } else {
            var source = item.srCreateMan;
            if(source=="chainbi-pay"){
                $treemenu.append('<li><a hrefs="' + item.srUrl + '.html">' +
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }else if(source!="admin"){
                $treemenu.append('<li><a hrefs="../pages/' + item.srUrl + '.html">' +
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }else{
                $treemenu.append('<li><a hrefs="' + item.srUrl + '.html">' +
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }
        }
    });
    return $treemenu;
}

// 四级菜单
function parseMenuThird(sonlist) {
    var $treemenu = $('<ul class="third-drop"></ul>');
    sonlist.forEach(function (item) {
        if (item.sonlist && item.sonlist.length) {
            // 如有五级菜单需加五级菜单栏的css
        } else {
            var source = item.srCreateMan;
            if(source=="chainbi-pay"){
                $treemenu.append('<li><a hrefs="' + item.srUrl + '.html">' +
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }else if(source!="admin"){
                $treemenu.append('<li><a hrefs="../pages/' + item.srUrl + '.html">' +
                    // '<span>' + item.srName + '</span></a></li>');
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }else{
                $treemenu.append('<li><a hrefs="' + item.srUrl + '.html">' +
                    '<i class="nav-icon nav-icon-third"></i><span>' + item.srName + '</span></a></li>');
            }
        }
    });
    $treemenu.append("</li>")
    return $treemenu;
}

// ---------- 登录用户拥有支付权限，实现模拟登录-----------
// function moniLoginPayService() {
//
//     var paySession = sessionStorage.getItem("chainbi-pay-session");
//
//     if(!paySession) {
//         var tokenAndResource = JSON.parse(sessionStorage.getItem("tokenAndResource"));
//         var accessToken = tokenAndResource.authTokenInfo.access_token;
//         var ciCode = tokenAndResource.userAndResourceVO.userInfo.identityCode;
//         var userAttribute = tokenAndResource.userAndResourceVO.userInfo.userAttribute;
//         var loginName = tokenAndResource.userAndResourceVO.userInfo.loginName;
//
//         if(userAttribute === 'pay_admin') {
//             userAttribute = 'admin';
//         } else if(userAttribute === 'pay_fac' || userAttribute === 'fac') {
//             userAttribute = 'fac';
//         } else {
//             userAttribute = 'chain';
//         }
//
//         var data = {
//             "accessToken": accessToken,
//             "ciCode": ciCode,
//             "userAttribute": userAttribute,
//             "loginName": loginName
//         };
//
//         $.ajax({
//             url: chainbiPayUrl + "/setting/api/prepare/moniLogin",
//             type: "get",
//             data: data,
//             success: function(result) {
//                 if(result && result.code === '0') {
//                     // 存储支付系统的session
//                     sessionStorage.setItem("chainbi-pay-session", result.data);
//                 } else {
//                     console.log(result);
//
//                     showfaildialog("支付系统登录出错","请稍后重试或联系系统管理员！");
//                 }
//             },
//             error: function() {
//
//                 showfaildialog("支付系统登录出错","请稍后重试或联系系统管理员！");
//             }
//         });
//     }
//
// }

// ---------- 登录用户拥有优惠券权限，实现模拟登录-----------
// function moniLoginCouponServer() {
//
//     var couponSession = sessionStorage.getItem("token");
//
//     if(!couponSession) {
//         var tokenAndResource = JSON.parse(sessionStorage.getItem("tokenAndResource"));
//
//         console.log("优惠券:::::");
//         console.log(tokenAndResource);
//
//         var accessToken = tokenAndResource.authTokenInfo.access_token;
//         var loginName = tokenAndResource.userAndResourceVO.userInfo.loginName;
//         var data = {
//             "accessToken": accessToken,
//             "parkingCode": loginName
//         };
//
//         $.ajax({
//             url: couponUrl+"/v1.0/couponServer/moniLogin",
//             type: "get",
//             data: data,
//             xhrFields:{
//                 withCredentials:true
//             },
//             success: function(result) {
//             	console.log("优惠券结果："+result.success);
//                 if(result.success) {
//                     // 存储支付系统的session
//                     sessionStorage.setItem("coupon-session", result.data);
//                     sessionStorage.setItem("couponToken",result.data.ymId);
//                 } else {
//                     console.log(result);
//
//                     showfaildialog("优惠券系统登录出错","请稍后重试或联系系统管理员！");
//                 }
//             },
//             error: function() {
//
//                 showfaildialog("优惠券系统登录出错","请稍后重试或联系系统管理员！");
//             }
//         });
//     }
//
// }


// ---------- 登录用户拥有数据展示权限，实现模拟登录-----------
// function moniLoginBigDataServer() {
//
//     var bigDataSession = sessionStorage.getItem("token");
//
//     if(!bigDataSession) {
//         var tokenAndResource = JSON.parse(sessionStorage.getItem("tokenAndResource"));
//
//         console.log("数据展示:::::");
//         console.log(tokenAndResource);
//
//         var accessToken = tokenAndResource.authTokenInfo.access_token;
//         var userAttribute = tokenAndResource.userAndResourceVO.userInfo.userAttribute;
//         var loginName = tokenAndResource.userAndResourceVO.userInfo.loginName;
//         var userName = tokenAndResource.userAndResourceVO.userInfo.userName;
//
//         var data = {
//             "accessToken": accessToken,
//             "userAttribute": userAttribute,
//             "loginName": loginName,
//             "userName":userName
//         };
//
//         $.ajax({
//             url: carDataUrl+"/bigdata/api/moniLogin",
//             type: "get",
//             data: data,
//             success: function(result) {
//                 console.log("数据展示服务返回结果："+result.success);
//                 if(result.success) {
//                     sessionStorage.setItem("bigdata-session", result.data);
//                 } else {
//                     console.log(result);
//
//                     showfaildialog("数据展示系统登录出错","请稍后重试或联系系统管理员！");
//                 }
//             },
//             error: function() {
//
//                 showfaildialog("数据展示系统登录出错","请稍后重试或联系系统管理员！");
//             }
//         });
//     }
//
// }



// ---------- 登录用户拥有停车权限，实现模拟登录-----------
// function moniLoginParkServer(userInfo) {
//
//     var carLaneSession = sessionStorage.getItem("token");
//
//     if(!carLaneSession) {
//         var tokenAndResource = JSON.parse(sessionStorage.getItem("tokenAndResource"));
//
//         console.log("tokenAndResource:::::");
//         console.log(tokenAndResource);
//
//         var accessToken = tokenAndResource.authTokenInfo.access_token;
//         var parkingCode = tokenAndResource.userAndResourceVO.userInfo.identityCode;
//         var userAttribute = tokenAndResource.userAndResourceVO.userInfo.userAttribute;
//         var loginName = tokenAndResource.userAndResourceVO.userInfo.loginName;
//         var userName = tokenAndResource.userAndResourceVO.userInfo.userName;
//
//         if(userAttribute === 'pay_admin') {
//             userAttribute = 'admin';
//         } else if(userAttribute === 'pay_fac') {
//             userAttribute = 'fac';
//         } else {
//             userAttribute = 'chain';
//         }
//
//         var data = {
//             "accessToken": accessToken,
//             "parkingCode": parkingCode,
//             "userAttribute": userAttribute,
//             "loginName": loginName,
//             "userName":userName
//         };
//
//         $.ajax({
//             url: carLaneBaseUrl + "/api/accounts/moniLogin",
//             type: "get",
//             data: data,
//             success: function(result) {
//                 if(result && result.code === '0') {
//                     console.log(result);
//                     var uservo = result.data;
//
//                     sessionStorage.setItem("token", uservo.token);
//                     sessionStorage.setItem("loginName", uservo.loginname);
//                     sessionStorage.setItem("userName", uservo.username);
//                     sessionStorage.setItem("parkingCode", uservo.parkingcode);
//
//                     if(userInfo&&null!=userInfo&&userInfo.userAttribute!='pay_fac'&&userInfo.userAttribute!='company_manage'){
//                         getFirstLoginFlag();
//                     }
//
//                 } else {
//                     console.log(result);
//                     showfaildialog("停车系统登录失败","请稍后重试或联系系统管理员！");
//                 }
//             },
//             error: function() {
//                 // alert("停车系统模拟登录出错");
//                 showfaildialog("停车系统登录出错","请稍后重试或联系系统管理员！");
//             }
//         });
//     }else{
//         if(userInfo&&null!=userInfo&&userInfo.userAttribute!='pay_fac'){
//             getFirstLoginFlag();
//         }
//     }
//
// }

// function getFirstLoginFlag() {
//
//     $.ajax({
//         url: carLaneBaseUrl + "/api/accounts/getFirstLoginFlag",
//         type: "GET",
//         headers: {"x-auth-token": sessionStorage.getItem("token")},
//         success: function (result) {
//             if (result) {
//                 showBootPage();
//             }
//         }
//     });
// }

// //初次登陆的直接跳车场配置框
// function showBootPage() {
//     $("#bootPage-bg").show();
//     $("#bootPage-main").show();
//     $("#bootPageDiv").load("../carlane-ui-1.0/pages/bootPage.html");
// }


/**
 * 测试用 无业务意义
 */
function testSession(){
	$.ajax({
        url: chainbiPayUrl + "/setting/api/prepare/test",
        type: "get",
        headers: {"x-auth-token": sessionStorage.getItem("chainbi-pay-session")},
        success: function (result) {
        },
        error: function() {
            alert("支付系统模拟登录出错");
        }
    });
}

