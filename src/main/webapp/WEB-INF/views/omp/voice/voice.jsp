<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>

<script src="<c:url value="/resources/js/jquery/jquery-1.11.1.min.js"/>"></script>

<%@ include file="/WEB-INF/views/layout/adm_top.jsp"%>
<style type="text/css">
</style>

</head>
<body>


	<div class="modal fade" tabindex="-1" id="voiceContent" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="voiceContent">语音内容</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<p class="small" id="vn"></p>
						<p class="small" id="vt"></p>
						<p class="small" id="vc"></p>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button type="button" onclick="openUpload();" class="btn btn-primary">提交更改</button>
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>




	<%@ include file="/WEB-INF/views/layout/adm_head.jsp"%>
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
							<i class="fa fa-user fa-fw"></i>语音信息管理
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/voice/voiceManage/listVoice.shtml" />
							<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
								<input id="pageNo" name="current" type="hidden" value="1">
								<table class="table">
									<tr>
										<td>语音名字：</td>
										<td><input type="text" value="${name }" id="name" name="name" /></td>
									</tr>

									<tr>
										<td><button onclick="quety()">查询</button></td>
										<td><input type="reset" value="重置" /></td>
									</tr>
								</table>
							</form:form>
							<%-- <form id="formf"
								action="<%=request.getContextPath() %>/voice/voiceManage/onUpload.shtml"
								method="post" enctype="multipart/form-data">
								<input type="file" name="muFile" id="finame">
								<!-- <input type="hidden" id="executionTime" name="executionTime" value=""/> -->
								<input type="hidden" id="userid" name="userid" value="" /><input
									type="button" onclick="openUpload();" value="上传" />
							</form> --%>

							<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">上传语音</button>
							<a class="btn btn-primary btn-sm" target="view_window"
								href="<%=request.getContextPath() %>/resources/pdf/help.pdf">语音广播操作教程</a>
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">语音信息添加</h4>
										</div>
										<div class="modal-body">
											<form role="form" id="formf"
												action="<%=request.getContextPath() %>/voice/voiceManage/oneUpload.shtml" method="post"
												enctype="multipart/form-data">
												<div class="form-group">
													<label for="name">语音名称</label> <input type="text" class="form-control" id="voicename"
														name="voicename" placeholder="请输入语音名称">
												</div>
												<div class="form-group">
													<label for="name">语音备注</label> <input type="text" class="form-control" id="remark"
														name="remark" placeholder="请输入备注">
												</div>
												<div class="form-group">
													<label for="name">语音内容</label>
													<textarea class="form-control" id="content" name="content" placeholder="请输入内容">
													</textarea>
												</div>

												<div class="form-group">
													<label for="inputfile">文件输入</label> <input type="file" name="muFile" id="finame"
														onchange="Javascript:validate_excel(this);">
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
													<button type="button" onclick="openUpload();" class="btn btn-primary">提交更改</button>
												</div>
											</form>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>

						</div>
						<hr>
						<div id="borad">
							<div id="message" class="alert"></div>
						</div>
						<div id="resultDiv">
							<%@ include file="/WEB-INF/views/omp/voice/voiceinfo.jsp"%>
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
		/**
		 * 显示FORM对话框
		 **/
		function showFormModal(idstr) {
			var id = idstr;
			if (typeof (id) == "undefined" || null == id) {
				id = "";
			}
			var url = '<c:url value="/admin/sys/user/form.shtml"/>';
			if (id != "") {
				url = '<c:url value="/admin/sys/user/edit.shtml"/>?entity.id='
						+ id;
			}
			showDynamicModal(url);
		}


		/**
		 *指令查询
		 */
		 function quety(){

					var name = $("#name").val();
					$.post("<%=request.getContextPath() %>/voice/voiceManage/listVoice.shtml",
							{
								name:name
							});

		}

		function openUpload(){
			var sure=confirm("确认上传语音吗？");
			if(sure){
				var ids = $(".ids:checkbox:checked").map(function(){
					return $(this).val();
				}).get().join();
				$("#userid").val(ids);
				$("#formf").submit();
			}

		}

		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_voice3");
			initQueryForm();
			$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",function(data){
				for(var i = 0;i<data.length;i++){
					$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
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
		});

		//限制语音大小
		function validate_excel(excel) {
			if (((excel.files[0].size).toFixed(2)) >= (473*1024)) {
				alert("请上传小于473k的语音");
				return false;
			}
		}
		function seeContent(vid){
			 $.ajax({url:"seeContent.shtml?vid="+vid,success:function(result){
			       $('#vc').text("语音内容:"+result.content);
			       $('#vt').text("创建时间:"+result.voiceTime);
			       $('#vn').text("语音名:"+result.voiceName);
				$('#voiceContent').modal("show");
			    }});
		}
	</script>


</body>
</html>