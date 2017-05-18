<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8" isELIgnored="false"%>
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
							<i class="fa fa-user fa-fw"></i>语音指令批量管理
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/voice/voiceManage/list.shtml" />
							<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
								<input id="pageNo" name="currentPieceNum" type="hidden" value="1">
								<input id="pageSizes" name="perPieceSize" type="hidden" value="10">
								<input name="vid" type="hidden" value="${vid }">
								<table class="table">
									<tr>
										<td>姓名：</td>
										<td><input type="text" value="${name }" id="name" name="name" /></td>
										<td>座机号：</td>
										<td><input type="text" value="${zjNumber }" id="zjNumber" name="zjNumber" /></td>
										<td>身份证号：</td>
										<td><input type="text" value="${idCard }" id="idCard" name="idCard" /></td>

									</tr>
									<tr>
										<td>市级：</td>
										<td><select id="city" name="city">
												<option value="${city }">--请选择--</option>
										</select></td>
										<td>区域：</td>
										<td><select id="county" name="county">
												<option value="${county }">--请选择--</option>
										</select></td>
										<td>街道：</td>
										<td><select id="street" name="street">
												<option value="${street }">--请选择--</option>
										</select></td>
										<td>社区：</td>
										<td><select id="community" name="community">
												<option value="${community }">--请选择--</option>
										</select></td>

									</tr>
									<tr>
										<!-- <td>预约时间：<input type="text" value="" id="executionTime" name="executionTime" onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
											<td>预约结束时间：<input type="text" value="" id="executionEndTime" name="executionEndTime" onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
											 -->
										<!-- 											<td><a class="btn btn-primary btn-lg" style="font-size:12px;padding:4px;" onclick="olorder()">市区发送发送</a></td> -->
										<td><a class="btn btn-primary btn-lg" style="font-size: 12px; padding: 4px;"
											onclick="allsend()">全部发送</a> <!-- <input onclick="ordersend()" type="button" value="指令发送"/> -->



											<a class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"
											style="font-size: 12px; padding: 4px;"> 定时预约发送 </a> <!-- 模态框（Modal） -->
											<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
												aria-labelledby="myModalLabel" aria-hidden="false">
												<div class="modal-dialog" style="diplay: none;">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
															</button>
															<h4 class="modal-title" id="myModalLabel">指令发送</h4>
														</div>
														<div class="modal-body">
															预约时间：<input type="text" value="" id="executionTime" name="executionTime"
																onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
															预约结束时间：<input type="text" value="" id="executionEndTime" name="executionEndTime"
																onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
															<button onclick="ordersend()" type="button" class="btn btn-primary">确认</button>
														</div>
													</div>
													<!-- /.modal-content -->
												</div>
												<!-- /.modal-dialog -->
											</div> <!-- /.modal --></td>
										<td><a class="btn btn-primary btn-lg" style="font-size: 12px; padding: 4px;"
											onclick="quety()">查询</a>
											<button class="btn btn-primary btn-lg" style="font-size: 12px; padding: 4px;"
												type="reset">重置</button></td>

									</tr>
								</table>
							</form:form>
						</div>
						<hr>
						<div id="borad" style="display: none">
							<div id="message" class="alert"></div>
						</div>
						<div id="resultDiv">
							<%@ include file="/WEB-INF/views/omp/voice/list.jsp"%>
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
						$.post("<%=request.getContextPath() %>/voice/voiceManage/list.shtml",
								$("#command").serialize(),	
								function(data){
									$("#resultDiv").html(data);
								});
						
						  

			}
		
		//发送
		 function allsend(){
						$.post("<%=request.getContextPath() %>/voice/voiceManage/allsend.shtml",
								$("#command").serialize(),
								function(data){
									alert(data);
								});
						
						  

			}
		
		
		
		
		
		
		
		
		
		
		
		
		$(document).ready(function() {
			initalizeSiderBar();
			//selectMenu("o_order1");
			selectMenu("o_voice3");
			initQueryForm();
			$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",function(data){
				for(var i = 0;i<data.length;i++){
					$("#city").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
			});
			
			
			
			
			$("#city").change(function(){
				$("#county option:not(:first)").remove();
				$("#street option:not(:first)").remove();
				$("#community option:not(:first)").remove();
				var id = $("#city").val();
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
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id : id},function(data) {
						for (var i = 0; i < data.length; i++) {
							$("#community").append("<option value='"+data[i].id+"'>"+ data[i].name+ "</option>");
						}
					});
				});
			<c:if test="${sessionScope.eccomm_admin.account_type=='g' }">
			flag=1;
			var lv = "${eccomm_admin.leave}";
			var rid = "${eccomm_admin.rid}";
			var pid = "${eccomm_admin.parentid}";
			if(lv==2){	//北京
				flag=0;
			}
			if(lv==3){	//区
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:rid},function(data){
				$("#county").val(rid); 
				$("#county").attr("disabled", true);
					for(var i = 0;i<data.length;i++){
						$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}				
				});
				
			}
			if(lv==4){	//街道
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:pid},function(data){
					$("#county").val(pid); 
					$("#county").attr("disabled", true);
						for(var i = 0;i<data.length;i++){
							$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}
						$("#street").val(rid); 
						$("#street").attr("disabled", true);
						$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:rid},function(data){
							for(var i = 0;i<data.length;i++){
								$("#community").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
							}
							
						});
					});
			}
			if(lv==5){	//社区
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionByPid.shtml",{id:pid},function(data){
					var pid=data[0].PARENTID;
					var sid=data[0].id;
					
					$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:pid},function(data){
					$("#county").val(pid); 
					$("#county").attr("disabled", true);
							for(var i = 0;i<data.length;i++){
								$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
							}
							$("#street").val(sid); 
							$("#street").attr("disabled", true);
							$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:sid},function(data){
								for(var i = 0;i<data.length;i++){
									$("#community").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
								}
								$("#community").val(rid); 
								$("#community").attr("disabled", true);
								
							});
						});
					
					});
			}
		
		</c:if>
		});
		
		/*
		指令发送
		*/
		function ordersend(){
			var stime = $("#executionTime").val();
			var etime = $("#executionEndTime").val();
			var sure=confirm("确认要发送指令吗？");
			var ids = $(".ids:checkbox:checked").map(function(){
				return $(this).val();
			}).get().join();
			if(sure){
				$.post("<%=request.getContextPath() %>/voice/voiceManage/sendOrder.shtml",{ids:ids,stime:stime,etime:etime},function(data){
					alert(data);
					location.reload();
				});
			}
			
		}
		
		
		function olorder(){
			var sure=confirm("确认要发送指令吗？");
			if(sure){
				$.post("<%=request.getContextPath() %>/voice/voiceManage/sendVice.shtml",function(data){
					alert(data);
					location.reload();
				});
			}
		}
		
		
		
		

		
		function ggid(){
			 
			 var objS = document.getElementById("community");
			 var grade = objS.options[objS.selectedIndex].value;
			 
			 if(grade == '') {
				 alert("请选社区");
				  return false;
			 }else{
				 var sure=confirm("确认要发送指令吗？");
				 if(sure){
					 $.post("<%=request.getContextPath() %>/voice/voiceManage/sendCommun.shtml",{grade:grade},function(data){
							alert(data);
							location.reload();
						});
				 }
				 
				 return true;
			 }
			 
		}
		
		function today(){
			var today=new Date();
		    var h=today.getFullYear();
		    var m=today.getMonth()+1;
		    var d=today.getDate();
		    var hs = today.getHours();
		    var ms = today.getMinutes();
		    var s = today.getSeconds();
		    m= m<10?"0"+m:m;
		    s= s<10?"0"+s:s; 
		    return h+"-"+m+"-"+d+" "+hs+":"+ms+":"+s;
		}
		document.getElementById("executionTime").value = today();//获取文本id并且传入当前日期
	
      
		
		
	</script>


</body>
</html>