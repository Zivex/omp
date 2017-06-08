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
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/syslog/ReportFrom/list.shtml" />
							<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
								<input id="pageNo" name="current" type="hidden" value="1">
								<table class="table">

									<tr>
										<td>开始时间：</td>
										<td><input type="text" value="" id="stime" name="stime"
											onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
										</td>
										<td>结束时间：</td>
										<td><input type="text" value="" id="etime" name="etime"
											onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
										</td>
										<td>座机类型：</td>
										<td><select id="otype" name="otype">
												<option value="">--请选择--</option>
												<option value="1">居家型</option>
												<option value="2">失能型</option>
												<option value="3">农商型</option>
										</select></td>

									</tr>
								</table>
							</form:form>
							<form:form id="commandR" role="form" class="form-inline" action="${queryForm}" method="post">
								<table class="table table-striped">
									<tr>
										<td>市级：</td>
										<td><select id="city" name="city">
												<option value="${city }">--请选择--</option>
										</select></td>
										<td>区县：</td>
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
										<td><button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal"
												onclick="quety()">查询</button></td>
										<td><a class="btn btn-default" href="#" onclick="formReset()" role="button">重置</a></td>
									</tr>
								</table>
							</form:form>
						</div>
						<hr>
						<div id="borad" style="display: none">
							<div id="message" class="alert"></div>
						</div>
						<div id="resultDiv">
							<%@ include file="/WEB-INF/views/admin/list.jsp"%>
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
		
		
		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_wordbooks");
			initQueryForm();
			
			//市
			$.ajax({  
			    type : "post",  
			    url : "<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",  
			    async : false,//取消异步  
			    success : function(data){  
			    	for(var i = 0;i<data.length;i++){
						$("#city").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
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
			var lv = "${eccomm_admin.leave}";
			var rid = "${eccomm_admin.rid}";
			var pid = "${eccomm_admin.parentid}";
			if(lv==2){	//北京
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:rid},function(data){
					$("#city").val(rid); 
					$("#city").attr("disabled", true);
					for(var i = 0;i<data.length;i++){
						$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
				});
			}
			if(lv==3){	//区
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:pid},function(data){
					$("#city").val(pid); 
					$("#city").attr("disabled", true);
					for(var i = 0;i<data.length;i++){
						$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
					$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:rid},function(data){
						$("#county").val(rid); 
						$("#county").attr("disabled", true);
							for(var i = 0;i<data.length;i++){
								$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
							}				
						});
				});
			}
			if(lv==4){	//街道
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionByPid.shtml",{id:pid},function(data){
					var cityid=data[0].PARENTID; //市级
					$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:cityid},function(data){
						$("#city").val(cityid); 
						$("#city").attr("disabled", true);
						for(var i = 0;i<data.length;i++){
							$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}
					});
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
				});
			}
			if(lv==5){	//社区
				$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionByPid.shtml",{id:pid},function(data){
					var cid=data[0].PARENTID; //区县
					var sid=data[0].id;	//街道
					var cityid=data[0].rid;//市级
					$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:cityid},function(data){
						$("#city").val(cityid); 
						$("#city").attr("disabled", true);
						for(var i = 0;i<data.length;i++){
							$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}
					});
					$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:cid},function(data){
 					$("#county").val(cid); 
					$("#county").attr("disabled", true);
							for(var i = 0;i<data.length;i++){
								$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
							}
							$("#street").val(sid); 
							$("#street").attr("disabled", true);
							$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",
				                {
					                id : sid
				                }, function (data)
				                {
					                for (var i = 0; i < data.length; i++)
					                {
						                $ ("#community").append (
						                        "<option value='"+data[i].id+"'>" + data[i].name + "</option>");
					                }
					                $ ("#community").val (rid);
					                $ ("#community").attr ("disabled", true);
					                
				                });
			                });
		                });
	                }
	                </c:if>
			
		});
		
		function quety(){
			var city = $("#city").val();
			var county = $("#county").val();
			var street = $("#street").val();
			var community = $("#community").val();
			var otype = $("#otype").val();
			var stime = $("#stime").val();
			var etime = $("#etime").val();
			$.post("<%=request.getContextPath() %>/syslog/ReportFrom/list.shtml",
	        {
				city:city,
	            county : county,
	            street : street,
	            community : community,
	            otype : otype,
	            stime : stime,
	            etime : etime
	        });
	        
        }
        function formReset ()
        {
	        document.getElementById ("command").reset ();
        }
	</script>


</body>
</html>