<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="panel panel-default">
	<form:form id="listForm" name="listForm" method="post" action='${queryForm }'>
		<input id="pageNo" name="currentPieceNum" type="hidden" value="1">
		<input id="pageSizes" name="perPieceSize" type="hidden" value="10">
		<table class="table table-hover table-middle" role="grid" id="chidTable">
			<tr class="active">
				<th width="20%" style="text-align: center;">序号</th>
				<th width="40%" style="text-align: center;">区域名称</th>
				<th width="40%" style="text-align: center;">操作</th>
			</tr>
		</table>
		<div class="panel-footer">
			<table class="table table-pagination">
				<thead>
					<tr>

<%-- 						<td align="left">共<span class="text-danger"><strong>${count}</strong></span>条记录（每页<span --%>
<%-- 							class="text-info"><strong>${command.perPieceSize}</strong></span>条记录）&emsp; --%>
<!-- 						</td> -->
<!-- 						<td align="right" height="28"><div id="result_page"></div></td> -->
					</tr>
				</thead>
			</table>
		</div>
	</form:form>
</div>



<!-- Script	-->
<script type="text/javascript">

	/**
	* 删除用户
	*/
	function deleteUser(uid,tr){
		var sure=confirm("删除操作是不可逆的，确认删除该信息吗？");
		if(sure){
			location.href = "delete.shtml?id="+uid;
			alert("删除成功！");
			window.location.href="${pageContext.request.contextPath}/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&isindividuation=";
		}
	}

	$(document).ready(function() {
		initListForm();
// 		<c:if test="${count!=null&&count>0}">
// 		 initPagination(<c:out value="${count}"/>,<c:out value="${command.perPieceSize}"/>,<c:out value="${command.currentPieceNum}"/>);		</c:if>
	});

</script>
