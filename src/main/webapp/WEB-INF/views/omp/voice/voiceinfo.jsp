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
		<input id="item_entity_id" type="hidden" name="id" value="">
		<input id="currentPage" type="hidden" name="current" value="">
		<c:if test="${DataTotalCount>0}">
			<c:if test="${old.logonName ne 'admin'}">
				<a class="btn btn-primary btn-sm" href="#" onclick="deleteAll('')">删除</a>
			</c:if>
			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="3%"><input type="checkbox" onclick="check()" /></th>
						<th width="10%">语音名</th>
						<th width="10%">语音创建时间</th>
						<th width="10%">语音备注</th>
						<th width="10%">操作</th>
					</tr>
				</thead>


				<tbody>
					<c:forEach items="${entities}" var="vo" varStatus="ig">
						<c:set value="0" var="i"></c:set>
						<tr>
							<td><input type="checkbox" class="ids" value="${vo.id}" /></td>
							<td>${vo.n}</td>
							<td>${vo.t}</td>
							<td>${vo.r}</td>
							<td>

								<div class="btn-group">
									<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a onclick="seeContent(${vo.id})">查看内容</a></li>
										<li><a onclick="ordersend(${vo.id})">发送</a></li>
										<c:if test="${old.logonName ne 'admin'}">
											<li><a onclick="deleteVoice(${vo.id});">删除</a></li>
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


<!-- /container -->

<!--footer-->
<%-- <%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%> --%>
<!--/footer-->

<!-- Script	-->
<script type="text/javascript">
	$(document).ready(function() {
		<c:if test="${DataTotalCount!=null&&DataTotalCount>0}">
		initPagination(<c:out value="${DataTotalCount}"/>,<c:out value="${PerPieceSize}"/>,<c:out value="${CurrentPieceNum}"/>);
		</c:if>
	});
	
		function deleteVoice(uid){
			var sure=confirm("删除操作是不可逆的，确认删除该指令吗？");
			if(sure){
				    $.ajax({url:"deleteAll.shtml?vids="+uid,success:function(result){
				       alert(result);
				       window.location.reload();
				    }});
			}
		}
	
	
	function check(){
		$(".ids").map(function() {
			$(this).attr("checked", !$(this).attr("checked"));
		});
	}
	
	function ordersend(vid){
			$.post("<%=request.getContextPath()%>/voice/voiceManage/goVoiceIds.shtml",
					{vid:vid},function(vid){
						window.location="<%=request.getContextPath()%>/voice/voiceManage/initial.shtml?vid="+vid; 
					}
					);	
	}
	
		/* 批量删除 */
	function deleteAll(vid){
			
		if(vid==''){
			// 判断是否至少选择一项
			var checkedNum = $(".ids:checked").length;
			if (checkedNum == 0) {
				alert("请选择至少一项！");
				return;
			}
			// 批量选择
			if (confirm("删除操作是不可逆的,确定要删除所选指令吗？")) {
				var checkedList = new Array();
				$(".ids:checked").each(function() {
					checkedList.push($(this).val());
					vid=checkedList.toString();
					
				});
		}
			
				$.ajax({
					type: "POST",
					url: "deleteAll.shtml",
					data: {
						'vids': vid
					},
					success: function(result) {
						$("[name ='subChk']:checkbox").attr("checked", false);
						window.location.reload();
					}
				}); 
			}
			}
	
	
	//selectMenu("o_voice1");
		
	</script>