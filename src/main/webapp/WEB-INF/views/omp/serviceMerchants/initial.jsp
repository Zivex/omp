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
							<c:url var="queryForm"
								value="/enterprise/list.shtml" />
							<form:form  method="post" action='${queryForm}' class="form-horizontal"
								role="form">
								<a role="button" class="btn btn-primary"
									href='<c:url value="/admin/omp/serviceMerchants/list.shtml"/>'>服务商信息展示</a>
								<a role="button" class="btn btn-primary"
									href='<c:url value="/admin/omp/serviceMerchants/export.shtml"/>'>服务商信息导出</a>
								<a role="button" class="btn btn-primary"
									onclick="importInformation()">服务商信息导入</a>
								<table class="table">
									<tr>
										<td>服务商名称：</td>
										<td><form:input path="entity.serviceName"
												class="form-control" /></td>
										<td>服务商电话：</td>
										<td><form:input path="entity.serviceTell"
												class="form-control" /></td>
										<td>服务类型：</td>
										<td><select name="entity.serviceTypeId" id="serviceTypeId">
												<option value="">--请选择--</option>
												<c:forEach items="${typeList }" var="t">
													<option value="${t.id }">${t.serviceName }</option>
												</c:forEach>
										</select></td>

										<td>审核状态：</td>
										<td><select name="entity.verify" id="verify">
												<option value="">--请选择--</option>
												<option value="0">有效</option>
												<option value="1">无效</option>
												<option value="2">未审核</option>

										</select></td>

									</tr>
									<tr>
										<td>联系人：</td>
										<td><input type="text" id="contact" name="entity.contact" /></td>

										<td>联系方式：</td>
										<td><input type="text" id="contactPhone"
											name="entity.contactPhone" /></td>
									</tr>
									<tr>
										<td><button>查询</button></td>
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
		 * 显示FORM对话框
		 **/
// 		function showFormModal(idstr) {
// 			var id = idstr;
// 			if (typeof (id) == "undefined" || null == id) {
// 				id = "";
// 			}
// 			var url = '<c:url value="/admin/sys/user/form.shtml"/>';
// 			if (id != "") {
// 				url = '<c:url value="/admin/sys/user/edit.shtml"/>?entity.id='
// 						+ id;
// 			}
// 			showDynamicModal(url);
// 		}


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
			var url = '<c:url value="/admin/sys/user/detail.shtml"/>?entity.id='
					+ id;
			showDynamicModal(url);
		}

		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_serviceMerchants");
			initQueryForm();
		});


		/**
		 *老人信息查询
		 */
		 function quety(){
			var name = $("#name").val();
			var phone = $("#phone").val();
			var fanwei = $("#fanwei").val();
			var xiangmu = $("#xiangmu").val();
			var sql = "";
			if((name != "") || (phone != "") || (fanwei != "")|| (xiangmu != "") ){
				sql = sql +"WHERE 1 = 1 ";
			}
			if(name != "" && name != undefined){
				sql = sql +"AND t.SERVER_NAME LIKE '%"+ name +"%'  ";
			}
			if(phone != "" && phone != undefined){
				sql = sql +"AND t.SERVER_TEL LIKE '%"+ phone +"%'  ";
			}
			if(fanwei != "" && fanwei != undefined){
				sql = sql +"AND t.SCOPE_DELIVERY LIKE '%"+ fanwei +"%'  ";
			}
			if(xiangmu != "" && xiangmu != undefined){
				sql = sql +"AND t.SERVER_TYPE LIKE '%"+ xiangmu +"%'  ";
			}
			document.getElementById("QSql").value = sql;
			/* alert(sql); */
			$.post("${pageContext.request.contextPath}/admin/omp/ServiceProvider/lists.shtml",
					{current:1,QSql:sql},
					function(data){
				$("#resultDiv").html(data);
			})
		};



		function hxtoServerInfo(id){
			/* alert("asa"); */
			$.post("${pageContext.request.contextPath}/admin/omp/ServiceProvider/serverInfo.shtml",
					{id:id},
					function(data){
						$("#displayDiv1").html(data);
						$("#displayDiv").hide();
						$("#displayDiv1").show();

						$("#backButton").show();
			});
		};
	</script>


</body>
</html>