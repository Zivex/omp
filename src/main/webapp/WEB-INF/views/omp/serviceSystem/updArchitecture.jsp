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
<form>
<input type="hidden" id ="account" value="${sessionScope.eccomm_admin.account_type }">
</form>
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
							<i class="fa fa-user fa-fw"></i>修改服务体系<span id="backspan"><input type="button"
								id="backButton" onclick="hxBackClick()" value="返回" /></span>
						</h1>
					</div>
					<div class="header-underline"></div>
					<div>
						<br>
						<div style="float: left; width: 50%;">
							<form id="architectureForm">
								<input type="hidden" name="entity.id" id="sid" value="${ss.id }"> <input
									type="hidden" name="entity.user_falg" value="${ss.user_falg }"> <input
									type="hidden" name="entity.county_id" value="${ss.county_id }"> <input
									type="hidden" name="entity.street_id" id="street_id" value="${ss.street_id }"> <input
									type="hidden" name="entity.community_id" value="${ss.community_id }"> <input
									type="hidden" name="entity.uid" value="${ss.uid }"> <input type="hidden"
									name="entity.createTime" value="${ss.createTime }"> <input type="hidden"
									id="telltype" name="entity.telltype_id" value="${ss.telltype_id }">
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
							</div>
							<c:if test="${sessionScope.eccomm_admin.account_type !='g' }">
								<div class="form-group" style="margin-top: 10px;">
									<form class="form-inline" id="conditions">
										<div class="form-group">
											<label for="city">市</label> <select name="city" id="city" class="form-control"
												onchange="udpCity()">
												<option value="0">--请选择--</option>
												<c:forEach items="${cityS }" var="city">
													<option value="${city.id }">${city.name }</option>
												</c:forEach>
											</select>
										</div>

										<div class="form-group">
											<label for="county">区县</label> <select name="county" id="county" class="form-control"
												onchange="udpCounty()">
												<option value="0">--请选择--</option>

											</select>
										</div>


										<div class="form-group">
											<label for="street">街道</label> <select name="street" class="form-control" id="street">
												<option value="0">--请选择--</option>
											</select>
										</div>
									</form>
								</div>
							</c:if>
							<a class="btn btn-default" href="#" onclick="serchService()" role="button">搜索</a> <a
								class="btn btn-default" href="#" onclick="addService()" role="button">添加</a> <br>
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

        //修改市
        function changCity (city1, county1, street1)
        {
	        county1.empty ();
	        street1.empty ();
	        community1.empty ();
	        street1.append ("<option value='0'>--请选择--</option>");
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


        //查询服务体系
        function queryTellType ()
        {
	        //var tellId = $("#telltype").val();
	        var sid = $ ("#sid").val ();
	        var architecture = $ ("#architecture");
	        architecture.empty ();
	        architecture.append ("<tr> <th width='10%' style='text-align: center'>键位</th> <th width='20%' style='text-align: center'>服务类型</th> <th width='40%' style='text-align: center'>服务商</th><th width='30%' style='text-align: center'>服务商电话</th> </tr>");
	        $.post (
             "${pageContext.request.contextPath }/serviceSystem/serchArchitecture.shtml",
             {
              sid : sid
             },
             function (data)
             {
              var account = $("#account").val();
              for (var i = 0; i < data.length; i++)
              {
//             	  if(account=='g'){
//             	  if(i==11 || i==12 || i==13 || i==14 || i==15 ){
//             		  architecture.append("<input type='hidden' class='serviceId' name='"+data[i].key+"'value='"+data[i].sp_id+"' >");
//             		  continue;
//             	  }
//               }
               architecture.append ("<tr><td><input type='radio' name='typeId' value='"+data[i].id+"'> "
                               + data[i].key
                               + "</td><td>"
                               + data[i].tname
                               + "</td><td><input type='hidden' class='serviceId' name='"+data[i].key+"'value='"+data[i].sp_id+"' ><span>"
                               + data[i].serviceName + "<span></td><td>"+data[i].serviceTell+"</td></tr>");
              }
              architecture.append ('<tr><td><a class="btn btn-default" href="#" onclick="updateSystem()" role="button">修改</a></td></tr>');
             });
        }
        //providers服务商
        
        //搜索服务商
        function serchService ()
        {
        	//var streetId = $ ("#street_id").val ();
	        var serviceList = $ ("#serviceList");
	        //服务区域
	        serviceList.empty();
	        //话机类型
	        var serviceId = $ ('input[name="typeId"]:checked').val ();
	        
	        //	var serviceId = $("#serviceType").val();
	        
	        if(typeof(serviceId)==="undefined"){
				alert("请选择键位");
				return ; 
			}
	        var street = $("#street").val();
	        var serviceName = $("#serviceName").val();
	        var city = $("#city").val();
	        if(city != 0 && street == 0){
	        	alert("请选择街道");
	        	retun;
	        }
	        $.post (
            "${pageContext.request.contextPath }/serviceSystem/serchService.shtml",
            {
            	serviceId : serviceId,
				serviceName : serviceName,
				streetId : street
            },
            function (data)
            {
             serviceList.empty ();
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
        	
        	
	        var serviceList = $("#serviceList");
        	if ($ ('input[name="providers"]:checked').length > 0)
	        {
        	
	        var spId = $ ('input[name="providers"]:checked').val ();
	        var spName = $ ('input[name="providers"]:checked').parent ().text ();
	        var sptell = $ ('input[name="providers"]:checked').parent ().next ().text();
	        var td = $ ('input[name="typeId"]:checked').parent ().next ().next ();
	        var sp = td.children ("span");
	        sp.text (spName);
	        td.next().text(sptell);
	        td.children ("input.serviceId")[0].value = spId;
	        }
	        else
	        {
		        alert ("请勾选服务商");
	        }
	        serviceList.empty ();
        }

        function updateSystem ()
        {
	        var addForm = $ ("#architectureForm").serialize () + "&" + $ ("#conditions").serialize ();
	        $.ajax (
	        {
	            type : "POST",
	            url : "${pageContext.request.contextPath }/serviceSystem/updateServiceSystem.shtml",
	            data : addForm,
	            success : function (result)
	            {
		            alert (result);
		            hxBackClick();
		           // window.location.reload ();
	            }
	        });
	        
        }

        $ (function ()
        {
	        queryTellType ();
	        initalizeSiderBar ();
	        selectMenu ("o_sys");
	        initQueryForm ();
        });
        function hxBackClick(){
        	location.href = "<%=request.getContextPath() %>/serviceSystem/initialize.shtml";
        }
	</script>


</body>
</html>
