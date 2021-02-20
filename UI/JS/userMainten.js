var tableoption = {
	url: config.oauthUrl+"/v1.0/dc/userInfo/operate/pageFindAudited",
	method: "post", //请求的方法
	pageList: [5, 10, 25, 50, 100],
	queryParamsType: 'not limit',
	queryParams: queryParams, //传递参数
	responseHandler: responseHandler,
	height: 550, //高度
	striped: true, //是否显示间隔色
	pagination: true, //是否显示分页
	pageSize: 5,
	sidePagination: "server",
	showColumns: true, //是否显示所有列
	minimunCountColumns: 2, //最少允许的列数
	clickToSelect: true, //是否启用点击选中行
	cache: false, //不适用缓存
	columns: [{
		field: 'id',
		title: '序列',
		width: '50px'
	}, {
		field: 'uiUuid',
		title: '用户信息uuid',
		visible: false,
		width: '100px'
	}, {
		field: 'userName',
		title: '用户名',
		width: '100px'
	}, {
		field: 'loginName',
		title: '登录名',
		width: '100px'
	}, {
		field: 'userAttributeFormat', // SP:服务提供者  SU：服务使用者
		title: '用户属性',
		width: '100px'
	}, {
		field: 'userAttribute',
		title: '用户属性',
		visible: false
	}, {
		field: 'userStatus',
		title: '用户状态',
		width: '100px'
	}, {
		field: 'userCreateMan',
		title: '创建人',
		width: '100px'
	}, {
		field: 'userCreateTime',
		title: '创建时间',
		visible: false,
		width: '100px'
	}, {
		field: 'identityCode',
		title: '身份编码',
		visible: false,
		width: '100px'
	}, {
		field: 'areaCode',
		title: '区域编码',
		visible: false,
		width: '100px'
	}, {
		field: 'specialMark',
		title: '特殊标识',
		width: '100px'
	}, {
		field: 'userType',
		title: '用户类型',
		width: '100px'
	}, {
		field: 'password',
		title: '登录密码',
		visible: false
	}, {
		field: 'operation',
		title: '操作',
		width: '180px',
		events: {
			'click .update': function(e, value, row, index) {
				$("#regUserName").val(row.userName);
				$("#loginName").val(row.loginName);
				$("#password").val(row.password);
				var tokenAndResourceJson = sessionStorage.getItem("tokenAndResource");
				var userInfoVo = $.parseJSON(tokenAndResourceJson).userAndResourceVO.userInfo;
				if(null != userInfoVo && "SP" == userInfoVo.userAttribute) {
					// 是服务提供者的给userStatus用户属性select增加一个服务提供者选项
					$("#userAttribute").append("<option value='SP'>服务提供者</option>");
				}
				$("#userAttribute").val(row.userAttribute);
				$("#regUserName").attr("disabled", true);
				$("#loginName").attr("disabled", true);
				$("#uiUuid").val(row.uiUuid);
				showDialog();
			},
			'click .delete': function(e, value, row, index) {
				$("#delUuid").val(row.uiUuid);
				showDialog2();
			}
		}
	}],
	data: []
};

function queryParams(params) {
	if($("#userNameParam").val()) {
		params.userName = $("#userNameParam").val();
	}
	params.redisId = sessionStorage.getItem("redisId");
	console.log(params);
	return params;
}

function responseHandler(resultData) {
	if(resultData != null && resultData != '' && resultData != undefined) {
		var result = resultData["list"];
		var data = [];
		for(var i in result) {
			var userInfo = {};
			if(result.hasOwnProperty(i)) {
				userInfo["id"] = parseInt(i) + 1;
				userInfo["uiUuid"] = result[i]["uiUuid"];
				userInfo["userName"] = result[i]["userName"];
				userInfo["loginName"] = result[i]["loginName"];
				userInfo["userAttribute"] = result[i]["userAttribute"];
				userInfo["userAttributeFormat"] = result[i]["userAttribute"] == "SU" ? "服务使用者" : "服务提供者";
				userInfo["userStatus"] = userStatusFormatter(result[i]["userStatus"]);
				userInfo["userCreateMan"] = result[i]["userCreateMan"];
				userInfo["userCreateTime"] = result[i]["userCreateTime"];
				userInfo["identityCode"] = result[i]["identityCode"];
				userInfo["areaCode"] = result[i]["areaCode"];
				userInfo["specialMark"] = result[i]["specialMark"];
				userInfo["userType"] = userTypeFormatter(result[i]["userType"]);
				userInfo["password"] = result[i]["password"];
				userInfo["operation"] = btnShow(result[i]["userStatus"], result[i]["userType"]);

				data.push(userInfo);
			}
		}
		return {
			"rows": data,
			"total": resultData.total
		};
	} else {
		return {
			"rows": [],
			"total": 0
		};
	}

}

var btnShow = function(status, type) {
	//	var btnArr = [];
	//	if(status == "unaudited"){
	//		btnArr.push('<a class="auditing" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="审核"><i class="lnr lnr-checkmark-circle" style="font-size: 18px;"></i></a>');
	//	}
	//	btnArr.push('<a class="delete" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="删除"><i class="lnr lnr-trash" style="font-size: 18px;"></i></a>');
	//	return btnArr.join('');
	var btnArr = [];
	if(status == "normal" && type == "web") {
		btnArr.push('<a class="update" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="修改"><i class="glyphicon glyphicon-edit" style="font-size: 18px;"></i></a>');
	}
	btnArr.push('<a class="delete" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="删除"><i class="	glyphicon glyphicon-trash" style="font-size: 18px;"></i></a>');
	return btnArr.join('');
};

function userStatusFormatter(userStatus) {
	var statusStr = "";
	switch(userStatus) {
		case "unaudited":
			statusStr = "未审核";
			break;
		case "auditing":
			statusStr = "审核中";
			break;
		case "notpass":
			statusStr = "审核未通过";
			break;
		case "normal":
			statusStr = "审核通过";
			break;
		case "deleted":
			statusStr = "已删除";
			break;
	}
	return statusStr;
}

function userTypeFormatter(userType) {
	var typeStr = "";
	switch(userType) {
		case "androidToll":
			typeStr = "安卓收费端";
			break;
		case "ppBox":
			typeStr = "派派盒子";
			break;
		case "handPc":
			typeStr = "手持设备";
			break;
		case "web":
			typeStr = "网页";
			break;
		case "weixin":
			typeStr = "微信";
			break;
		case "alipay":
			typeStr = "支付宝";
			break;
		case "ccb":
			typeStr = "建行卡";
			break;
		case "mobile":
			typeStr = "手机端";
			break;
	}
	return typeStr;
}

$(function() {
	hideDialog();
	$('#userInfoTable').bootstrapTable(tableoption);
});

/**
 * 更新用户信息的状态
 * @param {Object} status 状态
 */
function updateUserInfoStatus(uiUuid, status) {
	$.ajax({
		type: "post",
		url: config.oauthUrl+"/v1.0/dc/userInfo/operate/updateStatus",
		async: true,
		data: {
			"uiUuid": uiUuid,
			"status": status
		},
		success: function(result) {
			if(result != null && result != '' && result != undefined) {
				if("success" == result) {
					$('#userInfoTable').bootstrapTable("refresh");
				} else {
					Modal.alert({
						msg: "操作失败",
						title: '请求结果',
						btnok: '确定'
					});
				}
			}
		},
		error: function() {
			Modal.alert({
				msg: "请求后台失败",
				title: "请求结果",
				btnok: "确定"
			});
		}
	});
}

function hideDialog() {
	$("#regUserName").val();
	$("#loginName").val();
	$("#password").val();
	$("#userAttribute").html("<option value='SU' selected='selected'>服务使用者</option>");
	$("#uiUuid").val();
	$("#regUserName").attr("disabled", false);
	$("#loginName").attr("disabled", false);
	$('.dialog-wrap').hide();
	$('.content-wrap').hide();
}

function showDialog() {
	$('.dialog-wrap').show();
	$('.content-wrap').show();
}
function hideAddDialog() {
    $('#add_dialog-wrap').hide();
    $('#add_content-wrap').hide();
}

function showAddDialog() {
    $('#add_dialog-wrap').show();
    $('#add_content-wrap').show();
}

function hideDialog2() {
	$('.dialog-wrap2').hide();
	$('.content-wrap2').hide();
}

function showDialog2() {
	$('.dialog-wrap2').show();
	$('.content-wrap2').show();
}

function findByUserNameFuzz() {
	$('#userInfoTable').bootstrapTable("destroy");
	$('#userInfoTable').bootstrapTable(tableoption);
}

function btnClickaddWebUser() {
	var tokenAndResourceJson = sessionStorage.getItem("tokenAndResource");
	var userInfoVo = $.parseJSON(tokenAndResourceJson).userAndResourceVO.userInfo;
	if(null != userInfoVo && "SP" == userInfoVo.userAttribute) {
		// 是服务提供者的给userStatus用户属性select增加一个服务提供者选项
		$("#userAttribute").append("<option value='SP'>服务提供者</option>");
	}
    showAddDialog();
}

/*
 * 检测用户名称和登录名
 * 检查用户form表
 */
function checkUserInfoForm() {


	debugger;

	var userName = $.trim($("#regUserName").val());
	var loginName = $.trim($("#loginName").val());
	var uiUuid = $.trim($("#uiUuid").val());

	if(!userName || typeof(userName) == 'undefined') {
		$("#regUserName").parent().next().children("span").html("请输入内容");
		$("#regUserName").parent().addClass("has-error");
		return false;
	} else if(!loginName || typeof(loginName) == 'undefined') {
		$("#loginName").parent().next().children("span").html("请输入内容");
		$("#loginName").parent().addClass("has-error");
		return false;
	} else if(!checkUserNameCanUse(uiUuid, userName)) {
		$("#regUserName").parent().next().children("span").html("该用户名称已存在,请重新输入");
		$("#regUserName").parent().addClass("has-error");
		return false;
	} else if(!checkLoginNameCanUse(uiUuid, loginName)) {
		$("#loginName").parent().next().children("span").html("该登录名称已存在,请重新输入");
		$("#loginName").parent().addClass("has-error");
		return false;
	} else {
		$("#regUserName").parent().next().children("span").html("");
		$("#regUserName").parent().removeClass("has-error");
		$("#loginName").parent().next().children("span").html("");
		$("#loginName").parent().removeClass("has-error");
		return true;
	}
}

/**
 * 检查用户名是否存在
 * @param {Object} userName 用户名
 */
function checkUserNameCanUse(uiUuid, userName) {
	var flag = false;
	$.ajax({
		type: "get",
		url: config.oauthUrl+"/v1.0/dc/userInfo/operate/checkUserNameCanUse",
		async: false,
		data: {
			"userName": userName,
			"uiUuid": uiUuid
		},
		success: function(result) {
			flag = result;
		},
		error: function() {
			Modal.alert({
				msg: "请求后台失败",
				title: "请求结果",
				btnok: "确定"
			});
		}
	});
	return flag;
}

function checkLoginNameCanUse(uiUuid, loginName) {
	var flag = false;
	$.ajax({
		type: "get",
		url: config.oauthUrl+"/v1.0/dc/userInfo/operate/checkLoginNameCanUse",
		async: false,
		data: {
			"loginName": loginName,
			"uiUuid": uiUuid
		},
		success: function(result) {
			flag = result;
		},
		error: function() {
			Modal.alert({
				msg: "请求后台失败",
				title: "请求结果",
				btnok: "确定"
			});
		}
	});
	return flag;
}

function saveUserInfo() {
	if(checkUserInfoForm()) {
		var data = {
			userName: $.trim($("#regUserName").val()),
			loginName: $.trim($("#loginName").val()),
			password: $.trim($("#password").val()),
			userType: $("#userType").val(),
			userAttribute: $("#userAttribute").val(),
			redisId: sessionStorage.getItem("redisId")
		};

		var uiUuid = $("#uiUuid").val();
		if(uiUuid == null || uiUuid == '' || uiUuid == undefined) {
			$.ajax({
				url: config.oauthUrl+ "/v1.0/dc/userInfo/operate/saveUser/fromPage",
				headers: {
					"content-Type": "application/json", // important
				},
				type: "POST",
				data: JSON.stringify(data),
				success: function(result) {
					if(result && result.success) {
						$('#userInfoTable').bootstrapTable("destroy");
						$('#userInfoTable').bootstrapTable(tableoption);
						hideDialog();
					} else {
						Modal.alert({
							msg: "操作失败！",
							title: "请求结果",
							btnok: "确定"
						});
					}

				},
				error: function() {
					Modal.alert({
						msg: "网络请求失败！",
						title: "请求结果",
						btnok: "确定"
					});
				}
			});
		} else {
			data.uiUuid = uiUuid;
			$.ajax({
				url: config.oauthUrl+ "/v1.0/dc/userInfo/operate/saveUser/fromPage",
				headers: {
					"content-Type": "application/json", // important
				},
				type: "POST",
				data: JSON.stringify(data),
				success: function(result) {
					if(result && result.success) {
						$('#userInfoTable').bootstrapTable("destroy");
						$('#userInfoTable').bootstrapTable(tableoption);
						hideDialog();
					} else {
						Modal.alert({
							msg: "操作失败！",
							title: "请求结果",
							btnok: "确定"
						});
					}
				},
				error: function() {
					Modal.alert({
						msg: "网络请求失败！",
						title: "请求结果",
						btnok: "确定"
					});
				}
			});
		}
	}
}

function deleteUserInfo() {
	var uiUuid = $("#delUuid").val();
	$.ajax({
		type: "post",
		url: config.oauthUrl+"/v1.0/dc/userInfo/operate/delete",
		async: true,
		data: {"uiUuid":uiUuid},
		success: function(result) {
			if(result && "success" == result) {
				$('#userInfoTable').bootstrapTable("destroy");
				$('#userInfoTable').bootstrapTable(tableoption);
				hideDialog2();
			} else {
				Modal.alert({
					msg: "操作失败！",
					title: "请求结果",
					btnok: "确定"
				});
			}
		},
		error: function() {
			Modal.alert({
				msg: "网络请求失败！",
				title: "请求结果",
				btnok: "确定"
			});
		}
	});
}