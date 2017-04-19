<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${not empty messages }">
	<div class="alert alert-warning alert-dismissable">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		${messages.message}
	</div>

</c:if>
<div class="panel panel-default">
	${messageCount }
	<form:form id="listForm" name="listForm" method="post" action='${queryForm }'>
		<c:if test="${DataTotalCount>0}">
			<table class="table table-hover table-middle " width="100%">
				<thead>
					<tr class="active">
						<th width="10%">ID</th>
						<th width="20%">店名</th>
						<th width="10%">服务地区</th>
						<th width="15%">服务类型</th>
						<th width="15%">服务电话</th>
						<th width="10%">是否有效</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${command}" varStatus="sta">
						<tr>
							<td>${item.id}</td>
							<td>${item.SERVER_NAME}</td>
							<td>${item.SCOPE_DELIVERY}</td>
							<td>${item.SERVER_TYPE}</td>
							<td>${item.SERVER_TEL}</td>
							<c:if test="${item.IS_VALID == 1 }">
								<td style="color: green;">是</td>
							</c:if>
							<c:if test="${item.IS_VALID == 0 }">
								<td style="color: red;">否</td>
							</c:if>
							<%-- <td><a onclick="hxtoServerInfo(${item.id})">详情</a></td> --%>
							<td style="text-align: center;">
								<div class="btn-group">
									<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a onclick="hxtoServerInfo(${item.id})">详情</a></li>
										<c:if test="${sys == 'admin'}">
											<li><a href="###" onclick="deleteService(${item.id},this);">删除</a></li>
										</c:if>
										<%-- <li><a onclick="tocreOrder(${old.id})">生成指令</a></li> --%>
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
<script type="text/javascript">

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
	}
}


</script>
