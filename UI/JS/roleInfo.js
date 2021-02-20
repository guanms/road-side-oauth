var tableOption = {
	url: oauthUrl+"/v1.0/oauth2Server/role/pageQueryAll",
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
		field: 'riRoleName',
		title: '角色名称',
		sortable: true,
		width: '200px'
	}, {
		field: 'riRoleDesc',
		title: '角色描述',
		sortable: true,
		width: '200px'
	}, {
	// 	field: 'riLevel',
	// 	title: '是否系统级别',
	// 	width: '200px'
	// }, {
		field: 'riUuid',
		title: '唯一序列',
		visible: false,
		width: '200px'
	}, {
		field: "operation",
		title: "操作",
		events: {
			'click .update': function(e, value, row, index) {
				$("#riRoleName").val(row.riRoleName);
				$("#riRoleDesc").val(row.riRoleDesc);
				// $("#riLevel").val(row.riLevel);
				$("#riUuid").val(row.riUuid);
				showDialog();
			},
			'click .delete': function(e, value, row, index) {
				showDeleteDialog(row.riUuid);
			},
			'click .fenpei': function(e, value, row, index) {
				$("#resourceTreeRiUuid").val(row.riUuid);
				showAssignPermissionsDialog(row.riUuid);
			},
			'click .fpSr': function(e, value, row, index) {
				$("#srTreeRiUuid").val(row.riUuid);
				showRoleServiceRescourceDialog(row.riUuid);
			}
		}
	}],
	data: []
};

function queryParams(params) {
	if($("#queryDesc").val()) {
		params.riRoleDesc = $("#queryDesc").val();
	}
	params.redisId = sessionStorage.getItem("redisId");
	return params;
}

function responseHandler(resultData) {
	if(resultData != null && resultData != '' && resultData != undefined) {
		var result = resultData["list"];
		var data = [];
		for(var i in result) {
			var roles = {};
			if(result.hasOwnProperty(i)) {
				roles["id"] = parseInt(i) + 1;
				roles["riRoleName"] = result[i]["riRoleName"];
				roles["riRoleDesc"] = result[i]["riRoleDesc"];
				// roles["riLevel"] = result[i]["riLevel"];
				roles["riUuid"] = result[i]["riUuid"];
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

$(function() {
	hideDialog();
	hideDialog2();
	hideDialog3();
	hideDialog4();

	$('#roleInfoTable').bootstrapTable(tableOption);
});

var btnShow = function(id) {
	//  return "<button type='button' onClick='showEditDialog(" + id + ")' class='btn btn-success member-op-btn' style='display:inline-block'><span class='glyphicon glyphicon-edit'></span>&nbsp;修改</button>" +
	//      "&nbsp;&nbsp;&nbsp;&nbsp;" +
	//      "<button type='button' onClick='showDeleteDialog(" + id + ")' class='btn btn-success member-op-btn' style='display:inline-block'><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>" +
	//      "&nbsp;&nbsp;&nbsp;&nbsp;" +
	//      "<button type='button' onClick='showAssignPermissionsDialog(" + id + ")' class='btn btn-success member-op-btn' style='display:inline-block'><span class='glyphicon glyphicon-list-alt'></span>&nbsp;分配权限</button>";

	return [
		'<a class="update" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="修改"><i class="glyphicon glyphicon-edit" style="font-size: 18px;"></i></a>',
		'<a class="delete" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="删除"><i class="	glyphicon glyphicon-trash" style="font-size: 18px;"></i></a>',
		'<a class="fenpei" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="分配使用范围权限"><i class="glyphicon glyphicon-list-alt" style="font-size: 18px;"></i></a>',
		'<a class="fpSr" style="margin-right: 10px;cursor:pointer;text-decoration: none;color:black" data-toggle="tooltip" data-placement="bottom" title="分配服务资源权限"><i class="glyphicon glyphicon-list-alt" style="font-size: 18px;"></i></a>'
	].join('');

};

function hideDialog() {
	$(':input', '#saveRoleInfoForm')

		.not(':button,:submit,:reset,:hidden,:checkbox') //将表单中input元素type为button、submit、reset、hidden排除

		.val(''); //将input元素的value设为空值

	var checkArr = $('input[name=checkbox]'); // 如果任何radio/checkbox/select inputs有checked or selected 属性，将其移除
	$.each(checkArr, function(i, u) {
		$(u).prop("checked", false); // 用attr无效
	});
	$('.dialog-wrap').hide();
	$('.content-wrap').hide();
}

function showDialog() {
	$('.dialog-wrap').show();
	$('.content-wrap').show();
}

function showDialog3() {
	$('.dialog-wrap3').show();
	$('.content-wrap3').show();
}

function hideDialog3() {
	$('.dialog-wrap3').hide();
	$('.content-wrap3').hide();
}

function showDialog2() {
	$('.dialog-wrap2').show();
	$('.content-wrap2').show();
}

function hideDialog2() {
	$('.dialog-wrap2').hide();
	$('.content-wrap2').hide();
}

function showDialog4() {
	$('.dialog-wrap4').show();
	$('.content-wrap4').show();
}

function hideDialog4() {
	$('.dialog-wrap4').hide();
	$('.content-wrap4').hide();
}

function query() {
	$('#roleInfoTable').bootstrapTable("destroy");
	$('#roleInfoTable').bootstrapTable(tableOption);
}


function showDeleteDialog(id) {
	showDialog3();
	$("#deleteRoleInfoBtn").click(function() {
		$.ajax({
			url: oauthUrl+ "/v1.0/oauth2Server/role/delete",
			type: "POST",
			data: {
				"riUuid": id
			},
			success: function(result) {
				if(result == "success") {
					hideDialog3();
					$('#roleInfoTable').bootstrapTable('refresh');
				} else {
					alert("删除失败");
				}
			},
			error: function() {
				alert("删除失败");
			}
		});
	});
}

function saveRoleInfo() {
	if(checkRoleInfoForm()) {
		console.log("进入save请求");
		var data = {
			riRoleName: $.trim($("#riRoleName").val()),
			riRoleDesc: $.trim($("#riRoleDesc").val()),
			riLevel: $.trim($("#riLevel").val()),
            redisId: sessionStorage.getItem("redisId")
		};
		var riUuid = $("#riUuid").val();
		if(riUuid == null || riUuid == '' || riUuid == undefined) {
			$.ajax({
				url: oauthUrl+ "/v1.0/oauth2Server/role/save",
				headers: {
					"content-Type": "application/json", // important
				},
				type: "POST",
				data: JSON.stringify(data),
				success: function(result) {
					if(result == "success") {
						hideDialog();
						$('#roleInfoTable').bootstrapTable('refresh');
					} else {
						alert("添加失败");
					}

				},
				error: function() {
					alert("网络请求失败！");
				}
			});
		} else {
			data.riUuid = $("#riUuid").val();
			$.ajax({
				url: oauthUrl+"/v1.0/oauth2Server/role/modify",
				headers: {
					"content-Type": "application/json", // important
				},
				type: "POST",
				data: JSON.stringify(data),
				success: function(result) {
					if(result == "success") {
						hideDialog();
						$('#roleInfoTable').bootstrapTable('refresh');
					} else {
						alert("修改失败");
					}
				},
				error: function() {
					alert("网络请求失败！");
				}
			});
		}
	}
}

/*
 * 检测角色名称
 */
function checkRoleInfoForm() {
	var riUuid = $.trim($("#riUuid").val());
	var riRoleName = $.trim($("#riRoleName").val());
	var riLevel = $("#riLevel").val();

	if(!riRoleName || typeof(riRoleName) == 'undefined') {
		$("#riRoleName").next().html("请输入内容");
		$("#riRoleName").parent().parent().addClass("has-error");
		return false;
	} else if(!riLevel || typeof(riLevel) == 'undefined') {
		$("#riLevel").next().html("请选择级别");
		$("#riLevel").parent().parent().addClass("has-error");
		//  } else if (!checkRiNameCanUse(riUuid, riRoleName)) {
		//      $("#riRoleName").next().html("该角色名称已存在,请重新输入");
		//      $("#riRoleName").parent().parent().addClass("has-error");
		//      return false;
	} else {
		$("#riRoleName").next().html("");
		$("#riRoleName").parent().parent().removeClass("has-error");
		return true;
	}
}

/**
 * 检测角色名称是否重复，是否可用 （暂时不检测，超级用户进来）
 * @param {Object} riUuid 角色信息的唯一序列
 * @param {Object} riRoleName  角色信息的名称
 */
function checkRiNameCanUse(riUuid, riRoleName) {
	var flag = false;
	var data = {
		riUuid: riUuid,
		riRoleName: riRoleName,
		redisId: sessionStorage.getItem("redisId")
	};
	$.ajax({
		url: oauthUrl + "/v1.0/oauth2Server/role/canUse",
		type: "post",
		data: JSON.stringify(data),
		contentType: "application/json",
		dataType: "json",
		async: false,
		success: function(result) {
			flag = result;
		},
		error: function() {

		}
	});
	return flag;
}

/**
 * 用户角色关系 (使用范围资源)
 * @param {Object} riId  角色id
 */
function showAssignPermissionsDialog(riUuid) {
	refreshzNodes(riUuid);
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	showDialog2();

}

/**
 * 用户角色关系 (服务资源)
 * @param {Object} riId  角色id
 */
function showRoleServiceRescourceDialog(riUuid) {
	refreshServiceTreeNodes(riUuid);
	$.fn.zTree.init($("#srTree"), setting, srTreeNodes);
	var zTree = $.fn.zTree.getZTreeObj("srTree");
	zTree.setting.check.chkboxType = {
		"Y": "ps",
		"N": "ps"
	};
	showDialog4();

}

/**
 * 使用范围资源刷新
 */
function refreshTree() {
	refreshzNodes($("#resourceTreeRiUuid").val());
	$.fn.zTree.destroy("treeDemo");
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
}

/**
 * 服务资源刷新
 */
function refreshSrTree() {
	refreshServiceTreeNodes($("#srTreeRiUuid").val());
	$.fn.zTree.destroy("srTree");
	$.fn.zTree.init($("#srTree"), setting, srTreeNodes);
	var zTree = $.fn.zTree.getZTreeObj("srTree");
	zTree.setting.check.chkboxType = {
		"Y": "ps",
		"N": "ps"
	};
}

function saveAuthority(type) {
	var useScopeList = [];
	var serviceList = [];
	var riUuid = "";
	var operateLevel = "";
	if(type == "scope") {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var allNodes = treeObj.getNodes(); // allNodes[0] 使用范围 
		var scopeCheckedNodes = treeObj.getNodesByParam("checked", true, allNodes[0]);
		$.each(scopeCheckedNodes, function(i, item) {
			useScopeList.push(item.uuid);
		});
		riUuid = $("#resourceTreeRiUuid").val();
		operateLevel = $("input[name='usOperateLevelRadio']:checked").val();
	} else {
		var srTreeObj = $.fn.zTree.getZTreeObj("srTree");
		var srAllNodes = srTreeObj.getNodes(); // 服务资源
		var serviceCheckedNodes = srTreeObj.getNodesByParam("checked", true, srAllNodes[0]);
		$.each(serviceCheckedNodes, function(i, item) {
			if(item.name == "服务资源") {
				return true;
			}
			serviceList.push(item.uuid);
		});
		riUuid = $("#srTreeRiUuid").val();
		operateLevel = $("input[name='srOperateLevelRadio']:checked").val();
	}

	var data = {
		riUuid: riUuid,
		useScopeList: useScopeList,
		serviceList: serviceList,
		resourceType: type,
		operateLevel:operateLevel
	};

	$.ajax({
		type: "post",
		url: oauthUrl+"/v1.0/oauth2Server/role/saveAuthority",
		headers: {
			'Content-Type': 'application/json'
		},
		async: true,
		data: JSON.stringify(data),
		success: function(result) {
			if(result == "success") {
				hideDialog2();
				hideDialog4();
			} else {
				alert("分配资源失败");
			}
		},
		error: function() {
			alert("请求后台失败！");
		}
	});
}

/**
 * ======== zTree 初始化的setting 参数==================
 */
var setting = {
	check: {
		autoCheckTrigger: false,
		chkboxType: {
			"Y": "s",
			"N": "s"
		},
		chkStyle: "checkbox",
		enable: true,
		nocheckInherit: false,
		chkDisabledInherit: false,
		radioType: "level"
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "uuid",
			pIdKey: "puuid",
			rootPId: "0"
		}
	},

	async: {
		enable: true
		//			url: "http://localhost:12000/v1.0/dc/role/resourceTree",
		//			otherParam: {
		//				"riUuid": "d129e49059fd410a9fe8893ac4191a3a",
		//				"usName": usName
		//			}
	}
};

var zNodes = []; // 使用范围资源nodes
var srTreeNodes = []; // 服务资源nodes

/**
 * 使用范围资源
 * @param {Object} riUuid
 */
function refreshzNodes(riUuid) {
	$.ajax({
		type: "post",
		url: oauthUrl+"/v1.0/oauth2Server/role/resourceTree/useScope",
		async: false,
		data: {
			riUuid: riUuid,
			usName: $("#usName").val(),
			redisId: sessionStorage.getItem("redisId"),
			operateLevel:$("input[name='usOperateLevelRadio']:checked").val()
		},
		success: function(result) {
			zNodes = result;
		},
		error: function() {
			alert("请求后台失败！");
		}
	});
}

/**
 * 服务资源
 * @param {Object} riUuid
 */
function refreshServiceTreeNodes(riUuid) {
	$.ajax({
		type: "post",
		url: oauthUrl+"/v1.0/oauth2Server/role/resourceTree/serviceResource",
		async: false,
		data: {
			riUuid: riUuid,
			usName: $("#srName").val(),
			redisId: sessionStorage.getItem("redisId"),
			operateLevel:$("input[name='srOperateLevelRadio']:checked").val()
		},
		success: function(result) {
			console.log(result);
			srTreeNodes = result;
		},
		error: function() {
			alert("请求后台失败！");
		}
	});
}

