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
<div class="panel panel-default">
	${messageCount }
	<form:form id="listForm" name="listForm" method="post"
		action='${queryForm }'>
		<c:if test="${DataTotalCount>0}">
			<!-- tools -->
			<!-- 		<div class="panel-heading"></div> -->
			<table class="table table-hover table-middle " width="100%">
				<thead>
					<tr class="active">
						<th width="10%">ID</th>
						<th width="20%">服务商名称</th>
						<th width="10%">服务电话</th>
						<th width="15%">服务类型</th>
<!-- 						<th width="15%">服务区域</th> -->
						<th width="10%">联系人</th>
						<th width="15%">联系方式</th>
						<th width="15%">审核状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="m" items="${mList}" >
						<tr>
							<td>${m.id}</td>
							<td>${m.serviceName}</td>
							<td>${m.serviceTell}</td>
							<td>${m.serviceType.serviceName}</td>
							<td>${m.contact}</td>
							<td>${m.contactPhone}</td>
							<c:if test="${m.verify == 2 }"><td style="color: red;">无效</td></c:if>
							<c:if test="${m.verify == 3 }"><td style="color: green;">有效</td></c:if>
							<c:if test="${m.verify == 1 }"><td style="color: gray;">未审核</td></c:if>
							<c:if test="${m.verify == 4 }"><td style="color: gray;">待审核</td></c:if>
							<td style="text-align: center;">
								<div class="btn-group">
									<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a onclick="see(${m.id})">查看详情 </a></li>
<%-- 											<li><a href="###" onclick="deleteService(${item.id},this);">删除</a></li> --%>
									<c:choose>
										<c:when test="${sessionScope.eccomm_admin.id==1 }" >	
											<li><a href="###" onclick="update(${m.id});">修改</a></li>
										</c:when>
										<c:when test="${sessionScope.eccomm_admin.account_type=='g' && (m.user.account_type =='g' || m.user.account_type == 'admin')   }" >
											<li><a href="###" onclick="update(${m.id});">修改</a></li>
										</c:when>
										<c:when test="${sessionScope.eccomm_admin.account_type=='b' && m.user.account_type=='b'  }" >
											<li><a href="###" onclick="update(${m.id});">修改</a></li>
										</c:when>
									</c:choose>
									</ul>
								</div>
							</td>
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

$(document).ready(function() {

	initListForm();
	<c:if test="${DataTotalCount!=null&&DataTotalCount>0}">
	   initPagination(<c:out value="${DataTotalCount}"/>,<c:out value="${PerPieceSize}"/>,<c:out value="${CurrentPieceNum}"/>);
	</c:if>
	
	
	
});

function serverPro(id){
	$.post(
			"${pageContext.request.contextPath}/admin/omp/ServiceProvider/serverPro.shtml",
			{id:id},
			function(data){
		$("#resultDiv").html(data);
	});
}

/**
* 删除服务商
*/
function deleteService(uid,tr){
	var sure=confirm("删除操作是不可逆的，确认删除该信息吗？");
	if(sure){

		location.href = "deleteService.shtml?id="+uid;
		alert("删除成功！");
		location.replace()
		//window.location.href="${pageContext.request.contextPath}/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&isindividuation=";
// 		$("#item_entity_id").val(uid);
// 		$("#listForm").attr("action", '<c:url value="/old/oldMatch/delete.shtml"/>');
// 		$("#listForm").submit();
	}
}


</SCRIPT>
