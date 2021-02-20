/*$(document).ready(function () {
    initMenuOperation();
})

$(window).resize(function () {
    initMenuOperation();
})

var initMenuOperation = function () {
    $('.menu-pull,.menu-push').css({top: $(window).height() / 2});
}*/

$('.menu-push').on('click', function () {
    $('.main-sidebar').animate({left: -250}, 500);
    $('.htmleaf-header').animate({paddingLeft: 40}, 500);
    $('#index_content').animate({left: 0}, 500);
    // $('.menu-push').animate({left: -30}, 200);
    // try {
    //     setTimeout(function () {
    //         $('.menu-pull').animate({left: 0}, 200);
    //         $('.table-demand-reset').bootstrapTable('resetWidth');
    //     }, 520);
    // } catch (e) {
    // }

})

$('.menu-pull').on('click', function () {
    $('.main-sidebar').animate({left: 0}, 500);
    $('.htmleaf-header').animate({paddingLeft: 260}, 500);
    $('#index_content').animate({left: 230}, 500);
    $('.menu-pull').animate({left: -30}, 10);
    // try {
    //     setTimeout(function () {
    //         $('.menu-push').animate({left: 205}, 200);
    //         $('.table-demand-reset').bootstrapTable('resetWidth');
    //     }, 520);
    // } catch (e) {
    // }
})

// 点击一级菜单
$('.nav-div').on('click','.first-nav',function(){
    $('.first-nav').css("background","#253443");
    $('.first-nav').find("a").css("color","#a9b6d9");
    $(this).css("background","#131B24");
    $(this).find("a").css("color","#ffffff");
    if (!$('.nav-div').hasClass('nav-mini')) {
        // 点击没缩小的菜单
        if ($(this).next().css('display') == "none") {
            //显示二级菜单
            $('.nav-item').children('ul').slideUp(300);
            $(this).next('ul').slideDown(300);
            $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
        }else{
            // 隐藏二级菜单
            $(this).next('ul').slideUp(300);
            $('.nav-item.nav-show').removeClass('nav-show');
        }
    } else {
        // 点击缩小了的菜单
        if($(this).next('ul').css('display') == 'block'){
            // 隐藏二级菜单
            $(".first-drop").hide();
            $('.nav-menu-operation').css('left','15px');
            $('.nav-menu-operation').find('img').attr('src','../images/right.png');
            $('.nav-right').css('width','0').removeClass('nav-right-div');
            $('#index_content').css("left", "46px");
        } else {
            //显示二级菜单
            $(".first-drop").hide();
            $(this).next(".first-drop").css("display","block");
            $('#index_content').css("left", "230px");
            $('.nav-right').css("width", "199px").addClass('nav-right-div');
            $('.nav-menu-operation').css('left', '199px').find('img').attr('src', '../images/left.png');
            var title = $(this).children('a').children('span').html();

            $('.nav-right-title').html(title);
        }
    }
    // try {
    //     setTimeout(function () {
    //         $('.table-demand-reset').bootstrapTable('resetWidth');
    //     }, 320);
    // } catch (e) {
    // }
});
// 点击二级菜单
$('.nav-div').on('click','.second-nav',function(){
    if ($(this).next().css('display') == "none") {
        // 显示三级菜单
        $('.nav-second-item').removeClass('nav-second-show').children('ul').slideUp(300);
        $(this).next('ul').slideDown(300);
        $(this).parent('li').addClass('nav-second-show').siblings('li').removeClass('nav-second-show');
    }else{
        // 隐藏三级菜单
        $(this).next('ul').slideUp(300);
        if($(this).find('.my-icon').length != 0) {
            $('.nav-second-item').removeClass('nav-second-show');
        }
    }
});

// 点击三级级菜单
$('.nav-div').on('click','.third-nav',function(){
    if ($(this).next().css('display') == "none") {
        // 显示四级菜单
        $('.nav-third-item').removeClass('nav-third-show').children('ul').slideUp(300);
        $(this).next('ul').slideDown(300);
        $(this).parent('li').addClass('nav-third-show').siblings('li').removeClass('nav-third-show');
    }else{
        // 隐藏三级菜单
        $(this).next('ul').slideUp(300);
        if($(this).find('.my-icon').length != 0) {
            $('.nav-third-item').removeClass('nav-third-show');
        }
    }
});
// 伸缩菜单栏
$('.nav-menu-operation').on('click',function(){
    if (!$('.nav-div').hasClass('nav-mini')) {
        $(this).css('left','15px');
        $(this).find('img').attr('src','../images/right.png');
        $('.nav-item.nav-show').removeClass('nav-show');
        $('.nav-item').children('ul').removeAttr('style');
        $('.htmleaf-header').css("transition","all .3s").css("left","50px");
        $('.nav-div').addClass('nav-mini');
        $('#index_content').css("left","46px");
    }else{
        if(!$('.nav-right').hasClass('nav-right-div')) {
            $(this).css('left','199px');
            $(this).find('img').attr('src', '../images/left.png');
            $('.nav-div').removeClass('nav-mini');
            $('.htmleaf-header').css("transition", "all .3s").css("left", "220px");
            $('#index_content').css("left", "230px");
        } else {
            $(this).css('left','15px');
            $(this).find('img').attr('src','../images/right.png');
            $(".first-drop").hide();
            $('.nav-right').css('width','0').removeClass('nav-right-div');
            $('#index_content').css("left", "46px");
        }
    }
    // try {
    //     setTimeout(function () {
    //         $('.table-demand-reset').bootstrapTable('resetWidth');
    //     }, 320);
    // } catch (e) {
    // }
});



$('.nav-div').on('click','a',function(){
    if($(this).find('.my-icon').length == 0){
        $('.nav-div a').removeClass('selected-a');
        $('.nav-div a').css('color','#a9b6d9');
        $(this).addClass('selected-a');
        $(this).css('color','#ffffff');
    }
    var href = $(this).attr("hrefs");
    if(href != "#") {
        $("#index_content").html("");
        $("#index_content").load(href);
    }
});