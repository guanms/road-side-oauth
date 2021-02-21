
setTimeout(function(){
    loadScript();
},1000);

function loadScript(){

    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-table/dist/bootstrap-table.min.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-datetimepicker/js/moment-with-locales.min.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/layui/layui.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/layui_ext/dtree/dtree.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>');
    $("head").prepend('<script type="text/javascript" src="../lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>');



    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/bootstrap/css/bootstrap.min.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/bootstrap-table/dist/bootstrap-table.min.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/bootstrap-select/css/bootstrap-select.min.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/layui/css/layui.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/layui_ext/dtree/dtree.css">');
    $("head").prepend('<link rel="stylesheet" type="text/css" href="../lib/layui_ext/dtree/font/iconfont.css">');

    layui.config({
        base: '../lib/layui_ext/' //假设这是你存放拓展模块的根目录
    }).extend({ //设定模块别名
        dtree: 'dtree/dtree',
        treeSelect: 'treeSelect'
    });

}
