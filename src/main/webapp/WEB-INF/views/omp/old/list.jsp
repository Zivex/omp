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
		<%-- 		<form:hidden path="id" id="item_entity_id" /> --%>
		<%-- 		<form:hidden path="current" id="current" /> --%>
		<input id="item_entity_id" type="hidden" name="id" value="">
		<input id="currentPage" type="hidden" name="current" value="">
		<c:if test="${count>0}">
			<!-- tools -->
			<!-- 		<div class="panel-heading"></div> -->
			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="5%"><input type="checkbox" onclick="check()" /></th>
						<th width="5%" style="text-align: center;">姓名</th>
						<th width="10%" style="text-align: center;">座机号</th>
						<th width="10%" style="text-align: center;">身份证号</th>
						<th width="5%" style="text-align: center;">区县</th>
						<th width="5%" style="text-align: center;">街道</th>
						<th width="12%" style="text-align: center;">社区</th>
						<th width="10%" style="text-align: center;">编号</th>
						<th width="10%" style="text-align: center;">话机类型</th>
						<th width="10%" style="text-align: center;">所属账户</th>
						<th width="10%" style="text-align: center;">来电显示</th>
						<th width="8%" style="text-align: center;">个性化</th>
						<th width="10%" style="text-align: center;">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="old" items="${dataList}" varStatus="sta">
						<tr>
							<td><input type="checkbox" class="ids" value="${old.id}" /></td>
							<td><a id="viewItem" onclick="hxtoOldInfo(${old.id});">${old.name}</a></td>
							<td style="text-align: center;">${old.zjnumber}</td>
							<td style="text-align: center;">${old.certificates_number}</td>
							<td style="text-align: center;">${old.household_county.name}</td>
							<td style="text-align: center;">${old.household_street.name}</td>
							<td style="text-align: center;">${old.household_community.name}</td>
							<td style="text-align: center;">${old.account_type}</td>
							<td style="text-align: center;">${old.phoneType.phoneType}</td>
							<td style="text-align: center;">${old.user.name}</td>
							<c:if test="${old.call_id == 1}">
								<td style="text-align: center; color: green">是</td>
							</c:if>
							<c:if test="${old.call_id == 0}">
								<td style="text-align: center; color: red">否</td>
							</c:if>

							<td style="text-align: center;"><c:if test="${old.isindividuation==1}">
									<span style="color: green">有</span>
								</c:if> <c:if test="${old.isindividuation==0}">
									<span style="color: red">无</span>
								</c:if></td>

							<%-- 							<td>${old.workername}</td> --%>
							<td style="text-align: center;">
								<div class="btn-group">
									<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<c:if
											test="${old.agent_id == sessionScope.eccomm_admin.id || sessionScope.eccomm_admin.leave == 0 }">
											<li><a onclick="toupd(${old.id})">修改</a></li>
											<li><a href="###" onclick="deleteUser(${old.id},this);">删除</a></li>
										</c:if>
										<li><a onclick="see('${old.certificates_number}',${old.id})">查看</a></li>
										<li><a onclick="ompKeyModify(${old.id} )">指令个性化</a></li>
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
<SCRIPT type="text/javascript">

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
		<c:if test="${count!=null&&count>0}">
		 initPagination(<c:out value="${count}"/>,<c:out value="${command.perPieceSize}"/>,<c:out value="${command.currentPieceNum}"/>);		</c:if>
	});

	function check(){
		$(".ids").map(function() {
			$(this).attr("checked", !$(this).attr("checked"));
		});
	}

	function createOrder(){
		var ids = $(".ids:checkbox:checked").map(function(){
			return $(this).val();
		}).get().join();
		$.post("<%=request.getContextPath()%>/old/oldMatch/createOrder.shtml",{ids:ids},function(data){
			alert(data);
		});
	}
	function pageS(num){
		$("#pageSizes").val(num.value);
	}

</SCRIPT>
