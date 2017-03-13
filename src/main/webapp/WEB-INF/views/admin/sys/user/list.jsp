<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${not empty messages }">
	<div class="alert alert-warning alert-dismissable">
		<button type="button" class="close" data-dismiss="alert"
			aria-hidden="true">&times;</button>
		${messages.message}
	</div>
</c:if>
<!-- 模态框（Modal） -->
<div class="modal fade" id="recharge" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog" style="diplay: none;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">充值</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline">
					<input type="text" id="rmb" class="form-control"> &nbsp;条
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button onclick="recharge()" type="button" class="btn btn-primary">
					确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="panel panel-default">
	<form:form id="listForm" name="listForm" method="post" action=''>
		<form:hidden path="entity.id" id="item_entity_id" />
		<c:if test="${DataTotalCount>0}">
			<!-- tools -->
			<!-- 		<div class="panel-heading"></div> -->
			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="20%">登录名</th>
						<th width="20%">账户名</th>
						<th width="20%">用户类型</th>
						<th width="20%">所属区域</th>
						<!-- 						<th width="20%">用户等级</th> -->
						<th width="20%">操作</th>
						<th width="20%">充值操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${dataList}" varStatus="sta">
						<tr>
							<td><a id="viewItem" href="###"
								onclick="showDetails(${item.id});">${item.logonName}</a></td>
							<td>${item.name}</td>
							<td><c:if test="${item.userType==0}">管理员</c:if> <c:if
									test="${item.userType==1}">普通人员</c:if></td>
							<td>${item.regionName}</td>
							<%-- 							<td>${item.leavel}</td> --%>
							<td>
								<div class="btn-group">
									<button type="button"
										class="btn btn-default btn-sm dropdown-toggle"
										data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a
											href='<c:url value="/admin/sys/user/edit.shtml?entity.id=${item.id}"/>'>修改</a></li>
										<c:if test="${item.logonName ne sessionScope.eccomm_admin.logonName}">
											<li><a href="###" onclick="deleteUser(${item.id});">删除</a></li>
											<li><a href="###" onclick="restePass(${item.id});">重置密码</a></li>
										</c:if>
									</ul>
								</div>
							</td>
							<td>
							<a class="btn btn-success" href="#" role="button" onclick="showm(${item.rid})">充值</a>
								<!-- 								data-target="#recharge" style="font-size: 12px; padding: 4px;"> -->
								<!-- 									 </a></td> -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</form:form>
	<div class="panel-footer">
		<table class="table table-pagination">
			<thead>
				<tr>
					<td align="left">共<span class="text-danger"><strong>${DataTotalCount}</strong></span>条记录（每页<span
						class="text-info"><strong>${PerPieceSize}</strong></span>条记录）&emsp;
					</td>
					<td align="right" height="28"><div id="result_page"></div></td>
				</tr>
			</thead>
		</table>
	</div>
</div>



<!-- Script	-->
<SCRIPT type="text/javascript">


/**
* 删除用户
*/
function deleteUser(uid){
	var sure=confirm("删除操作是不可逆的，确认删除该用户吗？");
	if(sure){
		$("#item_entity_id").val(uid);
		$("#listForm").attr("action", '<c:url value="/admin/sys/user/delete.shtml"/>');
		$("#listForm").submit();
	}
}

/**
* 重置密码
*/
function restePass(uid){
	var sure=confirm("确认要重置密码吗？");
	if(sure){
		$("#item_entity_id").val(uid);
		$("#listForm").attr("action", '<c:url value="/admin/sys/user/reset_pass.shtml"/>');
		$("#listForm").submit();
	}
}


$(document).ready(function() {
	initListForm();
	<c:if test="${DataTotalCount!=null&&DataTotalCount>0}">
	initPagination(<c:out value="${DataTotalCount}"/>,<c:out value="${PerPieceSize}"/>,<c:out value="${CurrentPieceNum}"/>);
	</c:if>
});
//充值账户id
var rechargeId;
function showm(id){
	$('#recharge').modal('show');
	rechargeId = id;
}

function recharge(){
	var money = $("#rmb").val();
	var sure=confirm("确认要充"+money+"元吗？");
	if(sure){
		$.post("/admin/sys/user/recharge.shtml",{money:money,id:rechargeId},function(data){
			alert(data);
			location.reload();
		});
	}


}

</SCRIPT>
