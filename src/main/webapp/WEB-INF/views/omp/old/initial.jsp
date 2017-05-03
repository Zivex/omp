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
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" role="main">
				<div>
					<div class="page-header">
						<h1>
							<i class="fa fa-user fa-fw"></i>老人管理 <span id="backspan"><input type="button"
								id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/old/oldMatch/listtoo.shtml" />
							<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
								<%-- 								action="${queryForm}" method="post"> --%>
								<input id="pageNo" name="currentPieceNum" type="hidden" value="1">
								<input id="pageSizes" name="perPieceSize" type="hidden" value="10">
								<table class="table">
									<tr>
										<td>姓名：</td>
										<td><input type="text" id="name" name="name" /></td>
										<td>身份证号码：</td>
										<td><input type="text" value="${idCard }" id="idCard" name="idCard" /></td>
										<td>座机号：</td>
										<td><input type="text" value="${zjNumber }" id="zjNumber" name="zjNumber" /></td>
										<!-- 										<td>是否生成指令</td> -->
										<!-- 										<td><select id="isGenerationOrder" name="isGenerationOrder"> -->
										<%-- 												<option value="${isGenerationOrder }">--请选择--</option> --%>
										<!-- 												<option value="0">未生成指令</option> -->
										<!-- 												<option value="1">已生成指令</option> -->
										<!-- 										</select></td> -->
										<td>是否有来电显示：</td>
										<td><select id="call_id" name="call_id">
												<option value="${call_id }">--请选择--</option>
												<option value="1">是</option>
												<option value="0">否</option>
										</select></td>
									</tr>
									<tr>
										<td>个性类型：</td>
										<td><select id="isindividuation" name="isindividuation">
												<option value="${isindividuation }">--请选择--</option>
												<option value="0">有个性化</option>
												<option value="1">无个性化</option>
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
										<td><input class="btn btn-default" type="button" onclick="quety()" value="查询">
										<td><input class="btn btn-danger" type="reset" value="重置">
										<td><a class="btn btn-default" href="#" onclick="importInformation()" role="button">导入</a>
										<td><a class="btn btn-default" href="#" onclick="exportExcel()" role="button">导出</a>
									</tr>
								</table>
							</form:form>
						</div>
						<hr>
						<div id="borad" style="display: none">
							<div id="message" class="alert"></div>
						</div>
						<div id="resultDiv">
							<%@ include file="/WEB-INF/views/omp/old/list.jsp"%>
						</div>
					</div>
					<div id="displayDiv1"></div>
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

	$(function(){
		$("#displayDiv1").hide();
		$("#displayDiv").show();
		$("#backButton").hide();
	});


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
		* 导入信息
		**/
		function importInformation(){
			var url = '<c:url value="/old/Import/toImport.shtml"/>';
			showDynamicModal(url);
		}


		/**
		 *老人信息查询
		 */
		 function quety(){
			var isindividuation = $("#isindividuation").val();
			var name = $("#name").val();
			var creationTime = $("#creationTime").val();
			var idCard = $("#idCard").val();
			var zjNumber = $("#zjNumber").val();
			var county = $("#county").val();
			var street = $("#street").val();
			var community = $("#community").val();
			var isGenerationOrder = $("#isGenerationOrder").val();
			var call_id = $("#call_id").val();
			$.post("<%=request.getContextPath() %>/old/oldMatch/listtoo.shtml",
					{
						creationTime:creationTime,
						name:name,
						idCard:idCard,
						zjNumber:zjNumber,
						county:county,
						street:street,
						community:community,
						isGenerationOrder:isGenerationOrder,
						isindividuation:isindividuation,
						call_id:call_id
					},	
					function(data){
				$("#resultDiv").html(data);
			});
		}

		//导出
		function exportExcel(){
			var f = $("#command").serialize();
			location.href = "<%=request.getContextPath() %>/old/oldMatch/exportExcel.shtml?"+f;
		}

		/**
		 *未审核信息展示
		 */
		 function DeAuditInformation(){
			var current=1;
			$.post("${pageContext.request.contextPath}/old/oldMatch/listForDeGenerate.shtml",
					{current:current},
					function(data){
				$("#resultDiv").html(data);
			})
		}

		function toupd(id){
			$.post("${pageContext.request.contextPath}/old/oldMatch/upd.shtml",
					{id:id},
					function(data){
				$("#resultDiv").html(data);
			});
		}
		function see(cardId,id){
			$.post("${pageContext.request.contextPath}/old/oldMatch/see.shtml",
					{cardId:cardId,id:id},
					function(data){
				$("#resultDiv").html(data);
			});
		}
		function hxtoOldInfo(id){
			$.post("${pageContext.request.contextPath}/old/oldMatch/oldinfo.shtml",
					{oid:id},
					function(data){
						$("#displayDiv1").html(data);
						$("#backButton").show();
						$("#displayDiv").hide();
						$("#displayDiv1").show();
			});
		}
		function ompKeyModify(id,typeid){
			$.post("${pageContext.request.contextPath}/old/oldMatch/ompKeyModify.shtml",
					{id:id, typeid:typeid},
					function(data){
				$("#displayDiv1").html(data);
				$("#backButton").show();
				$("#displayDiv").hide();
				$("#displayDiv1").show();
			});
		}
		function tocreOrder(ids){
			$.post("<%=request.getContextPath() %>/old/oldMatch/createOrder.shtml",{ids:ids},function(data){
				alert(data);
			});
		}



		$(document).ready(function() {
			initalizeSiderBar();
			selectMenu("o_old");
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
        
        function hxBackClick ()
        {
	        $ ("#displayDiv1").hide ();
	        $ ("#displayDiv").show ();
	        $ ("#backButton").hide ();
        };
	</script>


</body>
</html>
