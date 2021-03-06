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
							<i class="fa fa-user fa-fw"></i>服务商管理 <span id="backspan"><input
								type="button" id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/enterprise/list.shtml" />
							<form:form id="parameter" name="parameter" method="post"  action='${queryForm}' >
								<a role="button" class="btn btn-primary"
									onclick="importInformation()">服务商信息导入</a>
								<a role="button" class="btn btn-primary"
									onclick="addService()">添加服务商</a>
								<table class="table">
									<tr>
										<td>服务商名称：</td>
										<td><form:input path="entity.serviceName"  /></td>
										<td>服务商电话：</td>
										<td><form:input path="entity.serviceTell"  /></td>
										<td>服务类型：</td>
										<td><form:select path="entity.serviceTypeId">
												<form:option value="0">--请选择--</form:option>
												<form:options items="${typeList }" itemLabel="serviceName"
													itemValue="id" />
											</form:select></td>
										<td>审核状态：</td>
										<td><form:select path="entity.verify" id="verify">
												<form:option value="0">--请选择--</form:option>
												<form:option value="1">未审核</form:option>
												<form:option value="2">无效</form:option>
												<form:option value="3">有效</form:option>
										</form:select></td>

									</tr>
									<tr>
										<td>联系人：</td>
										<td><form:input path="entity.contact"  /></td>

										<td>联系方式：</td>
										<td><form:input path="entity.contactPhone"  /></td>
										<td>服务区域：</td>
											<td>
												<select id="countyinit" name="county">
													<option value="${county }">--请选择--</option>		
												</select>
											</td>
											<td>服务街道：</td>
											<td>
												<select id="streetinit" name="street">
													<option value="${street }">--请选择--</option>		
												</select>
											</td>
											<td>服务社区：</td>
											<td>
												<select id="communityinit" name="community">
													<option value="${community }">--请选择--</option>		
												</select>
											</td>
									</tr>
									<tr>
										<td><input  type="button" onclick="query()" value="查询"></td>
										<td><input type="reset" /></td>
									</tr>
								</table>
							</form:form>
						</div>
						<hr>
						<div id="borad" style="display: none">
							<div id="message" class="alert"></div>
						</div>
						<div id="resultDiv">
							<%@ include file="/WEB-INF/views/omp/serviceMerchants/list.jsp"%>
						</div>
					</div>
					<div id="displayDiv1"></div>

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

		/**
		* 导入信息
		**/
		function importInformation(){
			var url = '<c:url value="serviceMerchants/toImport.shtml"/>';
			showDynamicModal(url);
		}

		$(function(){
				$("#displayDiv1").hide();
				$("#displayDiv").show();
				$("#backButton").hide();
			});

		function hxBackClick(){

			$("#displayDiv1").hide();
			$("#displayDiv").show();
			$("#backButton").hide();
		};
		/**
		 *显示用户详细信息
		 */
		function showDetails(id) {
			var url = '<c:url value="/admin/sys/user/detail.shtml"/>?entity.id='+ id;
			showDynamicModal(url);
		}

		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_serviceMerchants");
			initQueryForm();
			initialFormValidate('command');
			initSaveForm();
			
			
			
			$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",function(data){
				for(var i = 0;i<data.length;i++){
					$("#countyinit").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}				
			});
			$("#countyinit").change(function(){
			//	$("#county").find("option:not(:first)").remove();
				$("#streetinit").find("option:not(:first)").remove();
				$("#communityinit").find("option:not(:first)").remove();
				
				
				
				var id = $("#countyinit").val();
				
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
					for(var i = 0;i<data.length;i++){
						$("#streetinit").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}				
				});
			});
			
			$("#streetinit").change(function(){
				//$("#street").find("option:not(:first)").remove();
				$("#communityinit").find("option:not(:first)").remove();
				var id = $("#streetinit").val();
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
					for(var i = 0;i<data.length;i++){
						$("#communityinit").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}				
				});
			});
		});

		function see(id){
			$.post("${pageContext.request.contextPath}/enterprise/serviceMerchants/ServiceInfo.shtml",
					{id:id},
					function(data){
				$("#resultDiv").html(data);
			});
		}
		function addService(){
			$.post("${pageContext.request.contextPath}/enterprise/serviceMerchants/ServiceAdd.shtml",
					{},
					function(data){
				$("#resultDiv").html(data);
			});
		}
		function update(id){

			$.post("${pageContext.request.contextPath}/enterprise/serviceMerchants/Serviceupdate.shtml",
					{id:id},
					function(data){
				$("#resultDiv").html(data);
			});
		}


		function hxtoServerInfo(id){
			$.post("${pageContext.request.contextPath}/enterprise/ServiceInfo.shtml",
					{id:id},
					function(data){
						$("#displayDiv1").html(data);
						$("#displayDiv").hide();
						$("#displayDiv1").show();
						$("#backButton").show();
			});
		};
		
		function query() {
			  $.ajax({
					cache : true,
					type : "POST",
					url : '${queryForm}',
					data : $('#parameter').serialize(),// 你的formid
					async : false,
					error : function(request) {
						alert("Connection error");
					},
					success : function(data) {
						$("#resultDiv").html(data);
					}
			});
		}
		
		
		
	</script>


</body>
</html>