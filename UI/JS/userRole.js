var tableOption = {
	url: oauthUrl+"/v1.0/signUp/userRole/pageQueryAll",
	method: "post",
	pageList: [5, 10, 25, 50, 100],
	queryParamsType: 'not limit',
	queryParams: queryParams,
	responseHandler: responseHandler,
	height: 500,
	striped: true,
	pagination: true,
	pageSize: 5,
	// search: true,
	sidePagination: "server",
	showColumns: true,
	minimunCountColumns: 2,
	clickToSelect: true,
	columns: [{
		field: 'id',
		title: '序列',
		width: '100px'
	}, {
		field: 'userName',
		title: '用户名称',
		visible: false,
		width: '200px'
	}, {
		field: 'loginName',
		title: '登录名称',
		width: '200px'
	}, {
		field: 'userAttribute',
		title: '用户属性',
		width: '300px'
	}, {
		field: 'identityCode',
		title: '身份编码',
		width: '300px'
	}, {
		field: 'userType',
		title: '用户类型',
		visible: false,
		width: '200px'
	}, {
		field: 'specialMark',
		title: '特殊标识',
		visible: false,
		width: '200px'
	}, {
		field: 'uiUuid',
		title: '用户uuid',
		visible: false,
		width: '200px'
	}, {
		field: "operation",
		title: "操作",
		events: {
			'click .fenpei': function(e, value, row, index) {
				$("#uiUuid").val(row.uiUuid);
				showAssignRoleDialog(row.uiUuid);
			}
		}
	}],
	data: []
};

function queryParams(params) {
	if($("#queryUserName").val()) {
		params.userName = $("#queryUserName").val();
	}
	params.redisId = sessionStorage.getItem("redisId");
	return params;
}

function responseHandler(resultData) {
	if(resultData != null && resultData != '' && resultData != undefined) {
		console.log(resultData);
		var result = resultData["list"];
		var data = [];
		for(var i in result) {
			var roles = {};
			if(result.hasOwnProperty(i)) {
				roles["id"] = parseInt(i) + 1;
				roles["userName"] = result[i]["userName"];
				roles["loginName"] = result[i]["loginName"];
				roles["userAttribute"] = result[i]["userAttribute"];
				roles["identityCode"] = initIdentityCode(result[i]["identityCode"]);
				roles["userType"] = result[i]["userType"];
				roles["specialMark"] = result[i]["specialMark"];
				roles["uiUuid"] = result[i]["uiUuid"];
				roles["operation"] = btnShow(result[i]["riUuid"]);

				data.push(roles);
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

/**
 * 初始化身份编码
 * 主要手机端身份编码需要对应公众号
 * @param {Object} identityCode
 */
function initIdentityCode(identityCode) {
	var showCode = "";
	switch(identityCode) {
		case "gh_e5e94ba7d006":
			showCode = "威闪付";
			break;
		case "gh_583b2bd55eae":
			showCode = "e车e生活";
			break;
		default:
			showCode = identityCode;
			break;
	}
	return showCode;
}

$(function() {
	hideDialog();
	$('#userRoleInfoTable').bootstrapTable(tableOption);
});

var btnShow = function(id) {
	return [
		'<a class="fenpei" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="分配角色"><i class="glyphicon glyphicon-list-alt" style="font-size: 18px;"></i></a>'
	].join('');

};

function hideDialog() {
	$("#uiUuid").val("");
	$("#riRoleDesc").val("");
	$('.dialog-wrap').hide();
	$('.content-wrap').hide();
}

function showDialog() {
	$('.dialog-wrap').show();
	$('.content-wrap').show();
}

function showAssignRoleDialog(uiUuid) {
	showDialog();

	$.ajax({
		url: oauthUrl+"/v1.0/signUp/userRole/findUserRoleByUiUuid",
		type: "post",
		data: {
			"uiUuid": uiUuid,
			"riRoleDesc": $("#riRoleDesc").val(),
			"redisId":sessionStorage.getItem("redisId")
		},
		async: false,
		success: function(result) {
			var html = "";
			$.each(result, function(index, value) {
				if(value.isOwner == "1") {
					html += "<input style='margin: 0 0 10px 20px;' name='roleCheck' type='checkbox' value='" + value.riUuid + "' checked>" + value.riRoleName + "(" + value.riRoleDesc + ")" + "</br>";
				} else {
					html += "<input style='margin: 0 0 10px 20px;' name='roleCheck' type='checkbox' value='" + value.riUuid + "'>" + value.riRoleName + "(" + value.riRoleDesc + ")" + "</br>";
				}
			});
			$("#roleDiv").html(html);
		}
	});

}

function refreshRole() {
	$.ajax({
		url: oauthUrl+ "/v1.0/signUp/userRole/findUserRoleByUiUuid",
		type: "post",
		data: {
			"uiUuid": $("#uiUuid").val(),
			"riRoleDesc": $("#riRoleDesc").val(),
			"redisId": sessionStorage.getItem("redisId")
		},
		async: false,
		success: function(result) {
			var html = "";
			$.each(result, function(index, value) {
				if(value.isOwner == "1") {
					html += "<input style='margin: 0 0 10px 20px;' name='roleCheck' type='checkbox' value='" + value.riUuid + "' checked>" + value.riRoleName + "(" + value.riRoleDesc + ")" + "</br>";
				} else {
					html += "<input style='margin: 0 0 10px 20px;' name='roleCheck' type='checkbox' value='" + value.riUuid + "'>" + value.riRoleName + "(" + value.riRoleDesc + ")" + "</br>";
				}
			});
			$("#roleDiv").html(html);
		}
	});
}

function saveUserRoleInfo() {
	var list = [];
	$("input[name='roleCheck']:checked").each(function(index, value) {
		list.push($(this).val());
	});
	var data = {
		uiUuid: $("#uiUuid").val(),
		ownerRoleList: list
	};
	$.ajax({
		type: "post",
		url: oauthUrl+"/v1.0/signUp/userRole/saveUserRole",
		headers: {
			'Content-Type': 'application/json'
		},
		async: true,
		data: JSON.stringify(data),
		success: function(result) {
			if(result == "success") {
				hideDialog();
			} else {
				alert("分配角色失败");
			}
		},
		error: function() {
			alert("请求后台失败！");
		}
	});
}


function query() {
	$('#userRoleInfoTable').bootstrapTable("destroy");
	$('#userRoleInfoTable').bootstrapTable(tableOption);
}