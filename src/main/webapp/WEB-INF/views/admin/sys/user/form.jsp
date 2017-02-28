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
							<i class="fa fa-user fa-fw"></i>用户管理&nbsp;<a href='<c:url value="/admin/sys/user/initial.shtml"/>' role="button" class="btn btn-sm btn-default">返回</a>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div id="formDiv">
							<c:url var="save" value="/admin/sys/user/save.shtml" />
							<form:form method="post" action='${save}' class="form-horizontal" role="form">
								<form:hidden path="entity.id" />
								<div class="form-group">
									<label for="entity.name" class="col-md-2 control-label">姓&emsp;&emsp;名: </label>
									<div class="col-md-4">
										<form:input path="entity.name" class="form-control" data-rule-required="true" data-rule-maxlength="100" data-rule-userName="true" placeholder="姓名" />
									</div>
								</div>
								<div class="form-group">
									<label for="entity.logonName" class="col-md-2 control-label">登&ensp;录&ensp;名: </label>
									<div class="col-md-4">
										<form:input path="entity.logonName" class="form-control" data-rule-required="true" data-rule-minlength="3" data-rule-maxlength="20" data-rule-logonName="true" placeholder="登录名为3-20个字符,可由英文、数字组成" />
									</div>
								</div>
								<div class="form-group">
									<label for="entity.password" class="col-md-2 control-label">密&emsp;&emsp;码: </label>
									<div class="col-md-4">
										<form:password path="entity.password" id="password" class="form-control" data-rule-required="true" data-rule-minlength="6" data-rule-maxlength="20" placeholder="密码为6-20个字符,可由英文、数字及符号组成" />
									</div>
								</div>
								<div class="form-group">
									<label for="repassword" class="col-md-2 control-label">确认密码: </label>
									<div class="col-md-4">
										<input type="password" id="repassword" class="form-control" data-rule-required="true" data-rule-equalTo="#password" data-msg-equalTo="两次输入密码不一致" placeholder="确认密码" />
									</div>
								</div>

								<div class="form-group">
									<label for="entity.roles" class="col-md-2 control-label">分配角色: </label>
									<div class="col-md-4">
										<form:select path="entity.roles" multiple="true" class="col-md-4 form-control">
											<form:options items="${roleList}" itemLabel="name" itemValue="id" />
										</form:select>
									</div>
								</div>

								<div class="form-group"><label class="col-md-2 control-label">
									<form:radiobutton path="entity.account_type" value="g-" onchange="addRegion()" data-rule-required="true" data-msg-equalTo="请选择账户类型" />	政府 </label>
								  <label class="col-md-2 control-label">
								  <form:radiobutton path="entity.account_type" value="b-"  data-rule-required="true" />	银行
								  </label>
								</div>
							<div class="form-group" ><label class="checkbox-inline" id="level"></label>
<%-- <form:select path=""></form:select> --%>

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



		function submitForm() {
			$("#command").submit();
		}

		//pre-submit callback
		function showFormRequest(formData, jqForm, options) {
			var suc = $("#command").valid();
			if (suc) {
				$("#displayDiv").mask("正在完成请求，请稍候！");
				return true;
			}
			return false;
		}

		// post-submit callback
		function showFormResponse(responseText, statusText, xhr, $form) {
			$("#displayDiv").unmask();
			suc = responseText.success;
			type = responseText.type;
			$("#formDiv").hide();
			//
			$("#msg-alert").addClass('alert-' + type);
			$("#msg-alert").html(responseText.message);
			$("#msg-alert").show();
		}

		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("m_adm_sys_user");
			initialFormValidate('command');
			initSaveForm();
		});

		//显示分级属性
		function addRegion(){
			var divshow = $("#level");
			divshow.text("");// 清空数据
			divshow.append('市 : <select id="shi" name="shi" class="{required:true}"> <option value="0">--请选择--</option> <option value="2">北京</option> </select> 区域： <select id="county" name="county"> <option value="${county }">--请选择--</option> </select> 街道： <select id="street" name="street"> <option value="${street }">--请选择--</option> </select> 社区： <select id="community" name="community"> <option value="${community }">--请选择--</option> </select>');
			// $.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",function(data){
			// 	for(var i = 0;i<data.length;i++){
			// 		$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			// 	}
			// });
		$("#shi").change(function(){
				$("#street option:not(:first)").remove();
				$("#community option:not(:first)").remove();
				$("#county option:not(:first)").remove();
				var id = $("#shi").val();
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
					for(var i = 0;i<data.length;i++){
						$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				});
			});




			$("#county").change(function(){
				$("#street option:not(:first)").remove();
				$("#community option:not(:first)").remove();
				var id = $("#county").val();
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
					for(var i = 0;i<data.length;i++){
						$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				});
			});

			$("#street").change(function(){
				$("#community option:not(:first)").remove();
				var id = $("#street").val();
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
					for(var i = 0;i<data.length;i++){
						$("#community").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				});
			});







		// 	      $.ajax(
  //       {
  //           url: "send/index",
  //           type: "post",
  //           success: function (data) {
  //               var divshow = $("#showInfo2");
  //               divshow.text("");// 清空数据
  //               divshow.append(data); // 添加Html内容，不能用Text 或 Val
  //               divshow.dialog({
  //                   title: "短信群发系统",
  //                   height: 250,
  //                   width: 580
  //               });

  //           }
  //       }
  //       );
  //       return false;
		}
	</script>


</body>
</html>