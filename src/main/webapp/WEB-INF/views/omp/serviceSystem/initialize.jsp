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
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">服务商信息</h4>
				</div>
				<div class="modal-body" >
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
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
				role="main">
				<div>
					<div class="page-header">
						<h1>
							<i class="fa fa-user fa-fw"></i>服务体系<span id="backspan"><input
								type="button" id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div class="col-md-4">
						<a role="button" class="btn btn-default" href='#'
							onclick='addsystem()'>公共服务体系管理</a> <a role="button"
							class="btn btn-default" href='#' onclick='addprivate()'>服务体系管理</a>
					</div>


					<div class="page-body">
					<br />
						<table class="table">
						<tr class="active">
						<th width="6%" style="text-align: center;">编号</th>
						<th width="6%" style="text-align: center;">企业名称</th>
						<th width="10%" style="text-align: center;">所属账户</th>
						<th width="6%" style="text-align: center;">话机类型</th>
						<th width="10%" style="text-align: center;">操作</th>
					</tr>
							<c:forEach items="${list }" var="s">
								<tr>
									<td style="text-align: center;">${s.id }</td>
									<td style="text-align: center;">${s.user.enterprise.name }</td>
									<td style="text-align: center;">${s.user.name }</td>
									<td style="text-align: center;">${s.type.phoneType }</td>
									</td>
									<td style="text-align: center;">
										<div class="btn-group">
											<button type="button"
												class="btn btn-default btn-sm dropdown-toggle"
												data-toggle="dropdown">
												操作 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a onclick="see(${s.id})">查看</a></li>
												<li><a onclick="updateSystem(${s.id})">修改</a></li>
												<li><a href="###" onclick="deleteSystem(${s.id})">删除</a></li>
												<%-- 										<c:if test="${sys ==  'admin'}"> --%>
												<%-- 										</c:if> --%>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>

						</table>

						<div class="panel-footer">
		<table class="table table-pagination">
			<thead>
				<tr>
					<td align="left">共<span class="text-danger"><strong>${count}</strong></span>条记录（每页<span class="text-info"><strong>${command.perPieceSize}</strong></span>条记录）&emsp;
					</td>
					<td align="right" height="28"><div id="result_page"></div></td>
				</tr>
			</thead>
		</table>
	</div>

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
			initPagination(<c:out value="${count}"/>,<c:out value="${command.perPieceSize}"/>,<c:out value="${command.currentPieceNum}"/>);
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
			location.href = "udpArchitecture.shtml?id="+sid;
		}
		//查看体系
		function see(sid){
			location.href = "seeArchitecture.shtml?id="+sid;
		}
		//查看体系
		function deleteSystem(id){
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/serviceSystem/delService.shtml",
				async: false,
				data: {
					id : id
				},
				success: function(data) {
					alert(data);
				}
			});
		}



	</script>


</body>
</html>
