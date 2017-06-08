<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/adm_top.jsp"%>
<style type="text/css">
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
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" role="main">
				<div>
					<div class="page-header">
						<h1>
							<i class="fa fa-user fa-fw"></i>呼叫服务统计
						</h1>
					</div>
					<div class="header-underline"></div>
					<%@ include file="/WEB-INF/views/admin/select_top.jsp"%>
					<div id="resultDiv">
						<%@ include file="/WEB-INF/views/admin/list.jsp"%>
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
		$(document).ready(function() {
			selectMenu("o_wordbooks");
			
		});
		function quety(){
		    var paper = $("#command").serialize() + "&"+ $("#commandR").serialize();
			$.post("<%=request.getContextPath()%>/syslog/ReportFrom/list.shtml",
				paper,
			    function(data) {
					$("#resultDiv").html(data);
			    });
		}
    </script>


</body>
</html>