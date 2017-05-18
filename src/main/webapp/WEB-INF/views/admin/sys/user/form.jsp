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
							<i class="fa fa-user fa-fw"></i>用户管理&nbsp;<a
								href='<c:url value="/admin/sys/user/initial.shtml"/>' role="button"
								class="btn btn-sm btn-default">返回</a>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div id="formDiv">
							<c:url var="save" value="/admin/sys/user/save.shtml" />
							<form:form method="post" action='${save}' class="form-horizontal" role="form">
								<form:hidden path="entity.id" />
								<div class="form-group">
									<label for="entity.name" class="col-md-2 control-label">名&emsp;&emsp;称: </label>
									<div class="col-md-4">
										<form:input path="entity.name" class="form-control" data-rule-required="true"
											data-rule-maxlength="100" data-rule-userName="true" placeholder="名称" />
									</div>
								</div>
								<div class="form-group">
									<label for="entity.logonName" class="col-md-2 control-label">登&ensp;录&ensp;名: </label>
									<div class="col-md-4">
										<form:input path="entity.logonName" class="form-control" data-rule-required="true"
											data-rule-minlength="3" data-rule-maxlength="20" data-rule-logonName="true"
											placeholder="登录名为3-20个字符,可由英文、数字组成" />
									</div>
								</div>
								<div class="form-group">
									<label for="entity.password" class="col-md-2 control-label">密&emsp;&emsp;码: </label>
									<div class="col-md-4">
										<form:password path="entity.password" id="password" class="form-control"
											data-rule-required="true" data-rule-minlength="6" data-rule-maxlength="20"
											placeholder="密码为6-20个字符,可由英文、数字及符号组成" />
									</div>
								</div>
								<div class="form-group">
									<label for="repassword" class="col-md-2 control-label">确认密码: </label>
									<div class="col-md-4">
										<input type="password" id="repassword" class="form-control" data-rule-required="true"
											data-rule-equalTo="#password" data-msg-equalTo="两次输入密码不一致" placeholder="确认密码" />
									</div>
								</div>

								<div class="form-group">
									<label for="entity.roles" class="col-md-2 control-label">分配角色: </label>
									<div class="col-md-4">
										<form:select path="entity.roles" multiple="true" class="col-md-4 form-control"
											data-rule-required="true">
											<form:options items="${roleList}" itemLabel="name" itemValue="id" />
										</form:select>
									</div>
								</div>
									<label for=entity.display_all class="col-md-2 control-label">是否显示全部信息: </label>
								<div class="form-group">
									<div class="col-md-4">
										<label> <form:radiobutton path="entity.display_all" value="1"
												data-rule-required="true" /> 是
										</label> 
										<label> <form:radiobutton path="entity.display_all" value="0" /> 否
										</label>

									</div>
								</div>

								<div class="form-group">
									<c:if test="${sessionScope.eccomm_admin.account_type ==  'b'}">
										<label class="col-md-2 control-label"> <form:radiobutton
												path="entity.account_type" value="b"
												onclick="addSystem(${sessionScope.eccomm_admin.id})" data-rule-required="true" /> 银行
										</label>
									</c:if>
									<c:if test="${sessionScope.eccomm_admin.account_type ==  'm'}">
										<label class="col-md-2 control-label"> <form:radiobutton
												path="entity.account_type" value="m"
												onclick="addSystem(${sessionScope.eccomm_admin.id})" data-rule-required="true" /> 商户
										</label>
									</c:if>
									<c:if test="${sessionScope.eccomm_admin.logonName ==  'admin'}">
										<label class="col-md-2 control-label"> <form:radiobutton
												path="entity.account_type" value="g" onchange="addRegion()" data-rule-required="true" />
											政府
										</label>
										<label class="col-md-2 control-label"> <form:radiobutton
												path="entity.account_type" value="b" onclick="addb('b')" /> 银行
										</label>
										<label class="col-md-2 control-label"> <form:radiobutton
												path="entity.account_type" value="m" onclick="addm('m')" /> 商户
										</label>
									</c:if>
								</div>
								<div class="form-group">
									<label class="selectt-inline" id="level"></label>

								</div>
							</form:form>
							<div class="form-group">
								<label for="name" class="col-md-2 control-label"></label>
								<div class="col-md-4">
									<button id="btn-submit" class="btn btn-primary" onclick='submitForm()'>提 交</button>
									&emsp; <a role="button" class="btn btn-default"
										href='<c:url value="/admin/sys/user/initial.shtml"/>'>取消</a>
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
	
	var divshow = $("#level");
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
		
		//政府
		//显示分级属性
		function addRegion(){
			divshow.text("");// 清空数据
			divshow.append('市 : <select id="shi" name="shi" class="{required:true}"> <option value="0">--请选择--</option>  </select> 区域： <select id="county" name="county"> <option value="${county }">--请选择--</option> </select> 街道： <select id="street" name="street"> <option value="${street }">--请选择--</option> </select> 社区： <select id="community" name="community"> <option value="${community }">--请选择--</option> </select>');
				$.post("<%=request.getContextPath() %>/admin/sys/user/queryCity.shtml",{rid:0},function(data){
					for(var i = 0;i<data.length;i++){
						$("#shi").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				});
			
				
			
		
			
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
			
		}
		
		//企业
		//银行		
		function addb(type){
			purge();
			divshow.append('企业名称: <select  id="addEnterprise" name="entity.type_id" class="{required:true}"> <option value="0">--请选择--</option> </select> ');
			$.post("<%=request.getContextPath() %>/enterprise/queryEnterprise.shtml",{type:type},function(data){
				for(var i = 0;i<data.length;i++){
					$("#addEnterprise").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
			});
		}
		//商户
		function addm(){
			addb("m");
		}
		
		//清空数据
		function purge(){
			var divshow = $("#level");
			divshow.text("");// 清空数据
		}
		
		function addSystem(uid){
			var lv = 1;
			var flg = 0;
			divshow.text("");
			divshow.append('&nbsp;<select  id="yiji" name="yiji" > <option value="0">--请选择--</option>  </select>');
			for(;lv<10;lv++){
				$.post("<%=request.getContextPath() %>/enterprise/ajaxEnterprise.shtml",{uid:uid,lv:lv},function(data){
					if(data != null){
						var flg = 1;
						for(var i = 0;i<data.length;i++){
							$("#yiji").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}
					}
				
				});
				if(flg=1){
					break;
				}
			}
			
			$("#yiji").change(function(){
				$("#erji").remove();
				$("#sjji").remove();
				$("#siji").remove();
				if(this.value==0){
					return ;
				}
				lv=0;
				divshow.append('&nbsp;<select id="erji" name="erji" > <option value="0">--请选择--</option> </select>');
				$.post("<%=request.getContextPath() %>/enterprise/ajaxEnterprise.shtml",{uid:uid,lv:lv,upId:this.value},function(data){
					if(data.length >0){
						for(var i = 0;i<data.length;i++){
							$("#erji").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}
					}
				});
				
				$("#erji").change(function(){
					$("#sjji").remove();
					$("#siji").remove();
					if(this.value==0){
						return ;
					}
					lv=0;
					$.post("<%=request.getContextPath() %>/enterprise/ajaxEnterprise.shtml",{uid:uid,lv:lv,upId:this.value},function(data){
						if(data.length >0){
							divshow.append('&nbsp;<select id="sjji"   name="sjji" onchange="sjjis(this,'+uid+')" > <option value="0">--请选择--</option> </select>');
							for(var i = 0;i<data.length;i++){
								$("#sjji").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
							}
						} 
					});
				});
			});
		};		
		
		
		function sjjis(upId,uid){
			$("#siji").remove();
			if(upId.value==0){
				return ;
			}
			lv=0;
			$.post("<%=request.getContextPath() %>/enterprise/ajaxEnterprise.shtml",{uid:uid,lv:lv,upId:upId.value},function(data){
				if(data.length >0){
				divshow.append('&nbsp;<select  id="siji" name="siji" > <option value="0">--请选择--</option>  </select>');
					for(var i = 0;i<data.length;i++){
						$("#siji").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				} 
			});
		}
	</script>


</body>
</html>