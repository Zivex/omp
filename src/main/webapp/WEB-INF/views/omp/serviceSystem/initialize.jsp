<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="/WEB-INF/views/layout/adm_top.jsp"%>
<style type="text/css">
#backButton {
	font-size: 20px;
	float: right;
	margin-right: 2cm;
}
</style>

</head>
<body>
	<!-- header -->
	<%@ include file="/WEB-INF/views/layout/adm_head.jsp"%>
	<!-- /header -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">服务商信息</h4>
				</div>
				<div class="modal-body">
					<table class="table" id="modalBody">
						<tr>
							<th>服务商名称</th>
							<th>服务商电话</th>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- container -->
	<div class="container-fluid">
		<div class="row">
			<!-- menu -->
			<%@ include file="/WEB-INF/views/menu/menu_adm.jsp"%>
			<!-- ./menu -->
			<!-- main -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" role="main">
				<div>
					<div class="page-header">
						<h1>
							<i class="fa fa-user fa-fw"></i>服务体系<span id="backspan"><input type="button"
								id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div class="col-md-4">
						<c:if test="${sessionScope.eccomm_admin.id==1 }">
							<a role="button" class="btn btn-default" href='#' onclick='addsystem()'>默认服务体系管理</a>
						</c:if>
						<c:if test="${sessionScope.eccomm_admin.id!=1 }">
							<a role="button" class="btn btn-default" href='#' onclick='addprivate()'>服务体系确认</a>
						</c:if>
						<a class="btn btn-primary btn-sm" href="<%=request.getContextPath() %>/resources/pdf/服务体系管理-教程.pdf">
							帮助</a>
					</div>
					<div class="header-underline"></div>

					<c:url var="queryForm" value="/serviceSystem/list.shtml" />
					<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
						<input id="pageNo" name="currentPieceNum" type="hidden" value="1">
						<input id="pageSizes" name="perPieceSize" type="hidden" value="10">
					</form:form>


					<div id="resultDiv">
						<%@ include file="/WEB-INF/views/omp/serviceSystem/list.jsp"%>
					</div>
				</div>
			</div>
			<!-- ./main -->
		</div>

	</div>
	<!-- /container -->

	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">

		$(function() {
			initalizeSiderBar();
			selectMenu("o_sys");
			initQueryForm();
		});

		function addsystem() {
			location.href = "addArchitecture.shtml";
		}
		function addprivate() {
			location.href = "addPrivateArchitecture.shtml";
		}
		//模态
		function mod(id){
			$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath }/serviceSystem/serchService.shtml",
					async: false,
					data: {
						id : id
					},
					success: function(data) {
						$("#modalBody").append('<tr><td>'+data[0].serviceName+'<td> <td>'+data[0].serviceTell+'<td></tr>');
						$('#myModal').modal({
					        keyboard: false
					    })
					}
				});

		}
		//修改体系
		function updateSystem(sid){
			location.href = "udpArchitecture.shtml?sid="+sid;
		}
		//查看体系
		function see(sid){
			location.href = "seeArchitecture.shtml?entity.id="+sid;
		}
		//删除
		function deleteSystem(sid){
			var sure=confirm("删除操作是不可逆的，确认删除该体系吗？");
			if(sure){
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/serviceSystem/delService.shtml",
				async: false,
				data: {
					sid : sid
				},
				success: function(data) {
					alert(data);
					window.location.reload ();
				}
			});
		}}



	</script>


</body>
</html>
