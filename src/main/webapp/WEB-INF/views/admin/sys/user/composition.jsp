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
			<!-- 模态框（Modal） -->
			<div class="modal fade" id="addC" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog" style="diplay: none;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="myModalLabel">添加机构</h4>
						</div>
						<div class="modal-body">
							<form class="form-inline">
								<input type="text" id="name" class="form-control">
								&nbsp;名称
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button onclick="add()" type="button"
								class="btn btn-primary">确认</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->


			<!-- main -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" role="main">
				<div>
					<div class="page-header">
						<h1>
							<i class="fa fa-user fa-fw"></i>体系管理&nbsp;<a href='<c:url value="/admin/sys/user/initial.shtml"/>' role="button" class="btn btn-sm btn-default">返回</a>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div id="formDiv">
							<c:url var="save" value="/admin/sys/user/saveComposition.shtml" />
							<form:form method="post" action='${save}' class="form-horizontal" role="form">
								<div class="form-group">
									<label for="entity.prient_id" class="col-md-2 control-label">上级: </label>
									<div class="col-md-4">
									<select name="entity.prient_id"   class="col-md-4 form-control" id="up">
										</select>
									</div>
									<a class="btn btn-success" href="#" role="button" onclick="showA()">添加</a>
								</div>
								<div class="form-group">
									<label for="entity.id" class="col-md-2 control-label">下级: </label>
									<div class="col-md-4">
									<select name="entity.id"   class="col-md-4 form-control" id="lower">
										</select>
									</div>
								</div>

							</form:form>
							<div class="form-group">
								<label for="name" class="col-md-2 control-label"></label>
								<div class="col-md-4">
									<button id="btn-submit" class="btn btn-primary" onclick='submitForm()'>提 交</button>
									&emsp; <a role="button" class="btn btn-default" href='<c:url value="/admin/sys/user/initial.shtml"/>'>取消</a>
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

	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">
	$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("omp_composition");
		selectC();
	});
	//提交表单
	function submitForm() {
		$("#command").submit();
	}

	//模态
	function showA(){
		$('#addC').modal('show');
	}
	//下拉追加
	function selectC(){
		var op = $("#up");
		var lo = $("#lower");
		op.text("");
		$.post("<%=request.getContextPath()%>/admin/sys/user/selectComposition.shtml",{name:name},function(data){
				op.append("<option value='0'>--请选择--</option> ");
				lo.append("<option value='0'>--请选择--</option> ");
			for (var int = 0; int < data.length; int++) {
				op.append("<option value='"+data[int].id+"'>"+data[int].name+"</option> ");
				lo.append("<option value='"+data[int].id+"'>"+data[int].name+"</option> ");
			}
		});

	}
	//添加
	function add(){
		var name = $("#name").val();
			$.post("<%=request.getContextPath()%>/admin/sys/user/addComposition.shtml",{name:name},function(data){
				alert(data);
			});

	}


	</script>


</body>
</html>