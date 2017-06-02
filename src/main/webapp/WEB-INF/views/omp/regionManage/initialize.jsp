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
							<i class="fa fa-user fa-fw"></i>区域管理&nbsp;
						</h1>
					</div>
					<div class="header-underline"></div>
					<div id="displayDiv">
						<div class="operatorDiv">
							<c:url var="queryForm" value="/admin/sys/region/query.shtml" />
							<form:form id="command" role="form" class="form-inline" action="${queryForm}" method="post">
								<input id="pageNo" name="currentPieceNum" type="hidden" value="1">
								<input id="pageSizes" name="perPieceSize" type="hidden" value="10">
								<div class="form-group">
									<label for="name">区域名称:</label> <input type="text" class="form-control" id="name"
										name="entity.name" placeholder="按名称查询">
								</div>
								<a class="btn btn-default" href="#" onclick="query()" role="button">查询</a>
								<a class="btn btn-default" href="#" onclick="add()" role="button">添加</a>
							</form:form>
						</div>
						<div class="alert" style="display: none" id="msg-alert"></div>
						<div id="resultDiv" style="margin-top: 10px;">
							<%@ include file="/WEB-INF/views/omp/regionManage/list.jsp"%>
						</div>

					</div>
					<div id="displayDiv1"></div>
				</div>
			</div>
			<!-- ./main -->
		</div>
	</div>
	<!-- /container -->
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="addregion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="false">
		<div class="modal-dialog" style="diplay: none;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h5 class="modal-title" id="voiceContent">添加省或直辖市</h5>
				</div>
				<div class="modal-body">
					<form class="form-inline" id="province">
						<div class="form-group">
							<label for="exampleInputName2">区域名称</label> <input type="text" class="form-control"
								id=name" name="entity.name" placeholder="区域名称">
						</div>
						<a class="btn btn-default" href="#" onclick="addRegion()" role="button">添加</a>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->




	<!--footer-->
	<%@ include file="/WEB-INF/views/layout/adm_foot.jsp"%>
	<!--/footer-->

	<!-- Script	-->
	<script type="text/javascript">
		$ (document).ready (function ()
        {
	        initalizeSiderBar ();
	        selectMenu ("m_adm_sys_region");
	        initQueryForm ();
        });
        
        function add ()
        {
	        $ ('#addregion').modal ('show');
        }

        function query ()
        {
	        $.ajax (
	        {
	            cache : true,
	            type : "POST",
	            url : '${queryForm}',
	            data : $ ('#command').serialize (),
	            async : false,
	            error : function (request)
	            {
		            alert ("Connection error");
	            },
	            success : function (data)
	            {
		            $ ("#resultDiv").html (data);
	            }
	        });
        }
        function addRegion ()
        {
	        $.ajax (
	        {
	            cache : true,
	            type : "POST",
	            url : '<%=request.getContextPath() %>/admin/sys/region/addRegion.shtml',
	            data : $ ('#province').serialize (),
	            async : false,
	            error : function (request)
	            {
		            alert ("Connection error");
	            },
	            success : function (data)
	            {
		            alert(data);
		            window.location.reload ();
	            }
	        });
        }
	</script>


</body>
</html>
