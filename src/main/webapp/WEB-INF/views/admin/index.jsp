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
							<i class="fa fa-home fa-fw"></i>首页
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="table-responsive">
							<table class="table table-bordered">
								<tr>
									<th colspan="12"><h4 style="text-align: center;">用户信息</h4></th>
								</tr>
								<tr>
									<td colspan="6" width="50%" style="text-align: center;">剩余发送次数:</td>
									<td colspan="6" width="50%">${userINfo.remainCount }</td>
								</tr>
								<tr>
									<td colspan="6" style="text-align: center;">指令键发送次数:</td>
									<td colspan="6">${userINfo.orderCount }</td>
								</tr>
								<tr>
									<td colspan="2" width="20%" style="text-align: right;" class="success">发送成功:</td>
									<td colspan="4" class="success">${userINfo.orderexecuteSuc }</td>
									<td colspan="2" style="text-align: right;" class="success">发送失败:</td>
									<td colspan="4" class="danger"><a href="#" onclick="linkOrder()">${userINfo.orderexecuteFail }</a></td>
								</tr>
								<tr>
									<td colspan="6" style="text-align: center;">语音累计发送次数:</td>
									<td colspan="6">${userINfo.voiceCount }</td>
								</tr>
								<tr>
									<td colspan="1" class="success">执行成功:</td>
									<td colspan="2" class="success">${userINfo.executeSuc }</td>
									<td colspan="1" class="success">执行失败:</td>
									<td colspan="2" class="danger"> <a href="#" onclick="linkVoice()">${userINfo.executeFail }</a>  </td>
									<td colspan="1" class="success">已听取:</td>
									<td colspan="2" class="success">${userINfo.executeSuc - userINfo.notAnswer }</td>
									<td colspan="1" class="success">未听取:</td>
									<td colspan="2" class="danger">${userINfo.notAnswer }</td>
								</tr>
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
		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("m_home");
		});
		function linkOrder(){
			location.href = "<%=request.getContextPath()%>/order/orderManage/initial.shtml?execute_flag=0";
        }
		function linkVoice(){
			location.href = "<%=request.getContextPath()%>/voice/voiceManage/initial2.shtml?entity.execute_flag=0";
        }
	</script>


</body>
</html>