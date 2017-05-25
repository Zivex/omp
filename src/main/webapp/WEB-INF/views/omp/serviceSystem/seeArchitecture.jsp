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
<form>
<input type="hidden" id ="account" value="${sessionScope.eccomm_admin.account_type }">
</form>
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
							<i class="fa fa-user fa-fw"></i>查看服务体系<span id="backspan"><input
								type="button" id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div>
						<br>
						<div style="float: left; width: 50%;">
							<form id="architectureForm">
							<input type="hidden" name="entity.id" id="sid" value="${ss.id }">
								<table class="table table-bordered" id="architecture">

								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- ./main -->
	</div>
	<!-- /container -->

	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">

		//查询服务体系
		function queryTellType() {
			//var tellId = $("#telltype").val();
			var sid = $("#sid").val();
			var architecture = $("#architecture");
			architecture.empty();
			architecture
					.append("<tr> <th width='10%' style='text-align: center'>键位</th> <th width='20%' style='text-align: center'>服务类型</th> <th width='40%' style='text-align: center'>服务商</th><th width='30%' style='text-align: center'>服务商电话</th> </tr>");
			$.post(
					"${pageContext.request.contextPath }/serviceSystem/serchArchitecture.shtml",
					{
						sid : sid
					},
					function(data) {
						var idNum = 0;
						  var account = $("#account").val();
						for (var i = 0; i < data.length; i++) {
							if(account=='g'){
				             	  if(i==10 || i==12 || i==13 || i==14 || i==15 ){
				            		  continue;
				             	  }
				               }else if(account=='b' || account=='m'){
				            	   if(i==8 || i==9 ||i==10 || i==12 || i==13 || i==14 || i==15 ){
				             		  continue;
				              	  }
				          	  }
							idNum++;
							architecture
									.append("<tr><td>"
											+ data[i].key
											+ "</td><td>"
											+ data[i].tname
											+ "</td><td><input type='hidden' class='serviceId' name='"+data[i].key+"'value='"+data[i].sp_id+"' ><span>"+data[i].serviceName+"<span></td><td>"+data[i].serviceTell+"</td></tr>");
						}
					});
		}


	    $(function(){
			initalizeSiderBar();
			selectMenu("o_sys");
			initQueryForm();
	    	queryTellType();
	    });
	    function hxBackClick(){
        	location.href = "<%=request.getContextPath() %>/serviceSystem/initialize.shtml";
        }

	</script>


</body>
</html>
