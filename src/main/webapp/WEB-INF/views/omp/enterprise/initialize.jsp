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
							<i class="fa fa-user fa-fw"></i>企业管理&nbsp;
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div id="formDiv">
							<c:url var="save" value="/enterprise/addEnterprise.shtml" />
							<form:form  method="post" action='${save}' class="form-horizontal"
								role="form">
								<input type="hidden" value="${msg}" id="msg">
								<div class="form-group">
									<label for="entity.name" class="col-md-2 control-label">企业名称:
									</label>
									<div class="col-md-4">
										<form:input path="entity.name" class="form-control"
											data-rule-required="true" data-rule-maxlength="100"
											data-rule-userName="true" placeholder="名称" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"> <form:radiobutton
											path="entity.type" value="b" /> 银行
									</label> <label class="col-md-2 control-label"> <form:radiobutton
											path="entity.type" value="m" /> 商户
									</label>
								</div>
							</form:form>
							<div class="form-group">
								<label for="name" class="col-md-2 control-label"></label>
								<div class="col-md-4">
									<a role="button" class="btn btn-default" href='#'
										onclick='submitForm()'>添加</a>
								</div>
							</div>
						</div>
						<div class="alert" style="display: none" id="msg-alert"></div>
					</div>
				</div>
			</div>
			<!-- ./main -->
		</div>
	</div>
	<!-- /container -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="callBack" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog" style="diplay: none;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
			</div>
			<div class="modal-body">
				<h5>添加成功!</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->




	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">
		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_enterprise");
			//弹窗
			var msg = $("#msg").val();
			if(msg>0){
				$('#callBack').modal('show');
			}
		});

		function submitForm() {
			$("#command").submit();

		}
	</script>


</body>
</html>
