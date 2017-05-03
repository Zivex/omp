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
								<th colspan="6" ><h4 style="text-align: center;">用户信息</h4></th>
							</tr>
							<tr>
								<td colspan="3" width="50%" style="text-align: center;" >剩余发送次数:</td>
								<td colspan="3" width="50%">${userINfo.remainCount }</td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center;" >指令键发送次数:</td>
								<td colspan="3">${userINfo.orderCount }</td>
							</tr>
							<tr>
								<td colspan="1" width="20%" style="text-align: right;" class="success">成功:</td>
								<td colspan="2" class="success">${userINfo.orderSuc }</td>
								<td colspan="1" style="text-align: right;" class="danger">失败:</td>
								<td colspan="2" class="danger">${userINfo.orderFail }</td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center;">语音累计发送次数:</td>
								<td colspan="3">${userINfo.voiceCount }</td>
							</tr>
							<tr>
								<td colspan="1" width="20%" style="text-align: right;" class="success">成功:</td>
								<td colspan="2" class="success">${userINfo.voiceSendSuc }</td>
								<td colspan="1" style="text-align: right;" class="danger">失败:</td>
								<td colspan="2" class="danger">${userINfo.voiceSendFail }</td>
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
	</script>


</body>
</html>