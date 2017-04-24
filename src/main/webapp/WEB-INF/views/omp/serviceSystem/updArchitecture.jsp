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
									type="hidden" name="entity.user_falg" id="sid" value="${ss.user_falg }"> <input
									type="hidden" name="entity.county_id" value="${ss.county_id }"> <input
									type="hidden" name="entity.street_id" value="${ss.street_id }"> <input
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
									<a class="btn btn-default" href="#" onclick="serchService()" role="button">搜索</a> <a
										class="btn btn-default" href="#" onclick="addService()" role="button">添加</a>
								</form>
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

        //查询服务体系
        function queryTellType ()
        {
	        //var tellId = $("#telltype").val();
	        var sid = $ ("#sid").val ();
	        var architecture = $ ("#architecture");
	        architecture.empty ();
	        architecture.append ("<tr> <th width='10%' style='text-align: center'>键位</th> <th width='20%' style='text-align: center'>服务类型</th> <th width='70%' style='text-align: center'>服务商</th> </tr>");
	        $.post (
             "${pageContext.request.contextPath }/serviceSystem/serchArchitecture.shtml",
             {
              sid : sid
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
                               + data[i].tname
                               + "</td><td><input type='hidden' class='serviceId' name='"+data[i].key+"'value='"+data[i].sp_id+"' ><span>"
                               + data[i].serviceName + "<span></td></tr>");
              }
              architecture
                      .append ('<tr><td><a class="btn btn-default" href="#" onclick="updateSystem()" role="button">修改</a></td></tr>');
             });
        }
        //providers服务商
        
        //搜索服务商
        function serchService ()
        {
	        
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
	        
	        
	        
	        $.post (
            "${pageContext.request.contextPath }/serviceSystem/serchService.shtml",
            {
             serviceId : serviceId
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
	        var spId = $ ('input[name="providers"]:checked').val ();
	        var spName = $ ('input[name="providers"]:checked').parent ().text ();
	        var td = $ ('input[name="typeId"]:checked').parent ().next ().next ();
	        var sp = td.children ("span");
	        sp.text (spName);
	        td.children ("input.serviceId")[0].value = spId;
	        var serviceList = $("#serviceList");
			serviceList.empty();
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
		            window.location.reload ();
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
	</script>


</body>
</html>
