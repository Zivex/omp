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
	<form:form id="listForm" name="listForm" method="post"
		action='${queryForm }'>
		<input id="item_entity_id" type="hidden" name="id" value="">
		<input id="currentPage" type="hidden" name="current" value="">
		<c:if test="${DataTotalCount>0}">
			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="5%"><input type="checkbox" onclick="check()" /></th>
						<th width="5%">姓名</th>
						<th width="5%">区域</th>
						<th width="5%">街道</th>
						<th width="10%">社区</th>
						<th width="10%">座机号</th>
						<th width="10%">推送状态</th>
						<th width="10%">执行状态</th>
						<th width="10%">执行时间</th>
						<th width="10%">操作</th>
						<!-- 						<th width="10%">居住地址</th> -->
						<!-- 						<th width="10%">录入员</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${voiceOl}" varStatus="sta">
						<c:set value="0" var="i"></c:set>
						<tr>

							<td><input type="checkbox" class="ids" value="${order.old.id}" /></td>
							<td><a id="viewItem" href="###"
								onclick="showDetails(${order.old.id});">${order.old.name}</a></td>
										<td>${order.old.household_county.name}</td>
							<td>${order.old.household_street.name}</td>
							<td>${order.old.household_community.name}</td>
							<td>${order.old.zjnumber}</td>							
							<c:if test="${order.send_flag==1}">
								<td style="color: green;">已发送</td>
							</c:if>
							<c:if test="${order.execute_flag== 3}">
								<td style="color: blue;">未接听</td>
							</c:if>
							<c:if test="${order.execute_flag== 1}">
								<td style="color: green;">执行成功</td>
							</c:if>
							<c:if test="${order.execute_flag== 0}">
								<td style="color: red;">执行失败</td>
							</c:if>
<%-- 							<c:if test="${order.execute_flag== null}"> --%>
<!-- 								<td style="color: red;">未返回</td> -->
<%-- 							</c:if> --%>
							<td>${order.startTime}</td>
							<td>
								<div class="btn-group">
									<button type="button"
										class="btn btn-default btn-sm dropdown-toggle"
										data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a onclick="toupd(${order.old.id})">重新发送</a></li>
										<c:if test="${sys == 'admin'}">
											<li><a href="###" onclick="deleteUser(${order.old.id},this);">删除</a></li>
										</c:if>
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
	
	function check(){
		$(".ids").map(function() {
			$(this).attr("checked", !$(this).attr("checked"));
		});
	}
	
	
	
	function deleteUser(uid,tr){
		var sure=confirm("删除操作是不可逆的，确认删除该指令吗？");
		if(sure){
			location.href = "/order/orderManage/delect.shtml?id="+uid;
	// 		$("#item_entity_id").val(uid);
	// 		$("#listForm").attr("action", '<c:url value="/old/oldMatch/delete.shtml"/>');
	// 		$("#listForm").submit();
		}
	}
	//重新发送语音
	function toupd(oid){
		var sure=confirm("删除操作是不可逆的，确认删除该指令吗？");
		if(sure){
		$.post("<%=request.getContextPath() %>/voice/voiceManage/sendOrder.shtml",{oid:oid,stime:"",etime:"",sata:"repeat"},function(data){
			alert(data);
			location.reload();
		});
		}
		
	}
</SCRIPT>
