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
	<form:form id="listForm" name="listForm" method="post" action='${queryForm }'>
		<c:if test="${count>0}">
			<!-- tools -->
			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">

						<c:choose>
							<c:when test="${sessionScope.eccomm_admin.id==1 }">
								<th width="6%" style="text-align: center;">编号</th>
								<th width="6%" style="text-align: center;">企业名称</th>
							</c:when>
							<c:when test="${sessionScope.eccomm_admin.account_type=='g'}">
							</c:when>
							<c:when test="${sessionScope.eccomm_admin.account_type=='b'}">
								<th width="6%" style="text-align: center;">企业名称</th>
							</c:when>
						</c:choose>

						<th width="10%" style="text-align: center;">所属账户</th>
						<th width="6%" style="text-align: center;">话机类型</th>
						<th width="10%" style="text-align: center;">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="s">
						<tr>
							<c:choose>
								<c:when test="${sessionScope.eccomm_admin.id==1 }">
									<td style="text-align: center;">${s.id }</td>
									<td style="text-align: center;">${s.user.enterprise.name }</td>
								</c:when>
								<c:when test="${sessionScope.eccomm_admin.account_type=='g'}">
								</c:when>
								<c:when test="${sessionScope.eccomm_admin.account_type=='b'}">
									<td style="text-align: center;">${s.user.enterprise.name }</td>
								</c:when>
							</c:choose>
							<td style="text-align: center;">${s.user.name }</td>
							<td style="text-align: center;">${s.type.phoneType }</td>
							<td style="text-align: center;">
								<div class="btn-group">
									<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a onclick="see(${s.id})">查看</a></li>
										<li><a onclick="updateSystem(${s.id})">修改</a></li>
										<c:if test="${sessionScope.eccomm_admin.id==1 }">
											<li><a href="###" onclick="deleteSystem(${s.id})">删除</a></li>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<div class="panel-footer">
			<table class="table table-pagination">
				<thead>
					<tr>
						<td align="left">共<span class="text-danger"><strong>${count}</strong></span>条记录（每页<span
							class="text-info"><strong>${command.perPieceSize}</strong></span>条记录）&emsp;
						</td>
						<td align="right" height="28"><div id="result_page"></div></td>
					</tr>
				</thead>
			</table>
		</div>
	</form:form>
</div>
<div>
	<table class="table" id="table1">
	</table>
</div>



<!-- Script	-->
<script type="text/javascript">
$(document).ready(function() {
	initListForm();
	<c:if test="${count!=null&&count>0}">
	  initPagination(<c:out value="${count}"/>,<c:out value="${command.perPieceSize}"/>,<c:out value="${command.currentPieceNum}"/>);
	</c:if>
});

</script>
