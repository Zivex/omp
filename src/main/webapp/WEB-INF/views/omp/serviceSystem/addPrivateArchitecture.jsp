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
							<i class="fa fa-user fa-fw"></i>添加服务体系<span id="backspan"> <a class="btn btn-default"
								id="backButton" href="${pageContext.request.contextPath }/serviceSystem/initialize.shtml"
								role="button">返回</a>
							</span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div>

						<br>
						<div style="float: left; width: 50%;">
							<form id="architectureForm">
								<div class="form-group">
									<label for="telltype">话机类型</label> <select name="telltype" id="telltype"
										onchange="queryTellType()">
										<option value="0">--请选择--</option>
										<c:forEach items="${tellList }" var="tell">
											<option value="${tell.id }">${tell.phoneType }</option>
										</c:forEach>
									</select>
									<p>如果您想使用自己指定的服务商，您需先在“服务商管理”中添加服务商，审核通过后即可匹配使用。</p>
								</div>
								<table class="table table-bordered" id="architecture">

								</table>
							</form>
						</div>

						<div style="float: right; width: 40%;" id="providers">
							<div>
								<form id="providersForm" role="form">
									<div class="form-group">
										<label for="serviceName">名称</label> <input type="text" name="serviceName"
											class="form-control" id="serviceName" placeholder="请输入服务商名称">
									</div>
								</form>
								<c:if test="${sessionScope.eccomm_admin.account_type !='g' }">
									<div class="form-group">
										<form class="form-inline" id="conditions">
											<div class="form-group">
												<label for="city">市</label> <select name="city" id="city" onchange="udpCity()">
													<option value="0">--请选择--</option>
													<c:forEach items="${cityS }" var="city">
														<option value="${city.id }">${city.name }</option>
													</c:forEach>
												</select>
											</div>

											<div class="form-group">
												<label for="county">区县</label> <select name="county" id="county" onchange="udpCounty()">
													<option value="0">--请选择--</option>

												</select>
											</div>


											<div class="form-group">
												<label for="street">街道</label> <select name="street" id="street" onchange="udpStreet()">
													<option value="0">--请选择--</option>

												</select>
											</div>


											<div class="form-group">
												<label for="community">社区</label> <select name="community" id="community"
													onchange="udpCommunity()">
													<option value="0">--请选择--</option>

												</select>
											</div>


										</form>
									</div>
								</c:if>
								<a class="btn btn-default" href="#" onclick="serchService()" role="button">搜索</a> <a
									class="btn btn-default" href="#" onclick="addService()" role="button">添加</a>
							</div>
							<br>
							<div>
								<form id="serviceListForm">
									<table class="table table-bordered" id="serviceList">

									</table>
								</form>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
		<!-- ./main -->
	</div>
	<!-- /container -->

	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">
		$ (function ()
        {
	        initalizeSiderBar ();
	        selectMenu ("o_sys");
	        initQueryForm ();
        });
        
        function addsystem ()
        {
	        $ ('#addsystem').modal (
	        {
		        backdrop : false
	        });
        }
        var city1 = $ ("#city");
        var county1 = $ ("#county");
        var street1 = $ ("#street");
        var community1 = $ ("#community");
        function udpCity ()
        {
	        changCity (city1, county1, street1);
        }
        function udpCounty ()
        {
	        changCounty (county1, street1, community1);
        }
        function udpStreet ()
        {
	        changStreet (street1, community1);
        }

        //修改市
        function changCity (city1, county1, street1)
        {
	        county1.empty ();
	        street1.empty ();
	        community1.empty ();
	        street1.append ("<option value='0'>--请选择--</option>");
	        community1.append ("<option value='0'>--请选择--</option>");
	        var id = city1.val ();
	        $.post ("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
	        {
		        id : id
	        }, function (data)
	        {
		        county1.empty ();
		        county1.append ("<option value='0'>--请选择--</option>");
		        for (var i = 0; i < data.length; i++)
		        {
			        county1.append ("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
		        }
		        
	        });
        }
        //修改区县
        function changCounty (county1, street1, community1)
        {
	        var id = county1.val ();
	        community1.empty ();
	        community1.append ("<option value='0'>--请选择--</option>");
	        $.post ("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
	        {
		        id : id
	        }, function (data)
	        {
		        street1.empty ();
		        street1.append ("<option value='0'>--请选择--</option>");
		        for (var i = 0; i < data.length; i++)
		        {
			        street1.append ("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
		        }
		        
	        });
        }

        //修改街道
        function changStreet (street1, community1)
        {
	        var id = street1.val ();
	        $.post ("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
	        {
		        id : id
	        }, function (data)
	        {
		        community1.empty ();
		        community1.append ("<option value='0'>--请选择--</option>");
		        for (var i = 0; i < data.length; i++)
		        {
			        community1.append ("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
		        }
		        
	        });
        }

        //查询话机类型
        function queryTellType ()
        {
	        var tellId = $ ("#telltype").val ();
	        var architecture = $ ("#architecture");
	        architecture.empty ();
	        architecture
	                .append ("<tr> <th width='10%' style='text-align: center'>键位</th> <th width='20%' style='text-align: center'>服务类型</th> <th width='70%' style='text-align: center'>服务商</th> </tr>");
	        $
	                .post (
	                        "${pageContext.request.contextPath }/serviceSystem/queryarchitecture.shtml",
	                        {
		                        stId : tellId
	                        },
	                        function (data)
	                        {
		                        var idNum = 0;
		                        for (var i = 0; i < data.length; i++)
		                        {
			                        idNum++;
			                        architecture
			                                .append ("<tr><td><input type='radio' name='typeId' value='"+data[i].id+"'> "
			                                        + data[i].key
			                                        + "</td><td>"
			                                        + data[i].serviceName
			                                        + "</td><td><input type='hidden' class='serviceId' name='"+data[i].key+"'value='0' ><span><span></td></tr>");
		                        }
		                        architecture
		                                .append ('<tr><td><a class="btn btn-default" href="#" onclick="addSystem()" role="button">确认</a></td><td colspan="2">您可直接点击确认，使用默认的服务体系。</td></tr>');
	                        });
        }
        //providers服务商
        
        //搜索服务商
        function serchService ()
        {
	        
	        var serviceList = $ ("#serviceList");
	        serviceList.empty ();
	        //服务区域
	        
	        //话机类型
	        var serviceId = $ ('input[name="typeId"]:checked').val ();
	        
	        if (typeof (serviceId) === "undefined")
	        {
		        alert ("请选择键位");
		        return;
	        }
	        var street = $ ("#street").val ();
	        var serviceName = $ ("#serviceName").val ();
	        $
	                .post (
	                        "${pageContext.request.contextPath }/serviceSystem/serchService.shtml",
	                        {
	                            serviceId : serviceId,
	                            serviceName : serviceName,
	                            streetId : street,
	                        },
	                        function (data)
	                        {
		                        var idNum = 0;
		                        serviceList
		                                .append ("<tr> <th width='70%' style='text-align: center'>服务商名称</th> <th width='30%' style='text-align: center'>服务电话</th> </tr>");
		                        
		                        for (var i = 0; i < data.length; i++)
		                        {
			                        idNum++;
			                        serviceList
			                                .append ("<tr><td><input type='radio' name='providers' value='"+data[i].id+"'> "
			                                        + data[i].serviceName
			                                        + "</td><td>"
			                                        + data[i].serviceTell
			                                        + "</td></tr>");
		                        }
	                        });
        }
        function addService ()
        {
	        var spId = $ ('input[name="providers"]:checked').val ();
	        var spName = $ ('input[name="providers"]:checked').parent ().text ();
	        var td = $ ('input[name="typeId"]:checked').parent ().next ().next ();
	        var sp = td.children ("span");
	        sp.text (spName);
	        td.children ("input.serviceId")[0].value = spId;
	        var serviceList = $ ("#serviceList");
	        serviceList.empty ();
        }

        function addSystem ()
        {
	        var addForm = $ ("#architectureForm").serialize () + "&" + $ ("#conditions").serialize ();
	        $.ajax (
	        {
	            type : "POST",
	            url : "${pageContext.request.contextPath }/serviceSystem/addServiceSystem.shtml",
	            data : addForm,
	            success : function (result)
	            {
		            alert (result);
		            window.location.href="${pageContext.request.contextPath }/serviceSystem/initialize.shtml";
	            }
	        });
	        
        }
	</script>


</body>
</html>
