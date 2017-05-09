<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/easyUIjs/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUIjs/themes/icon.css"
	type="text/css"></link>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/easyUIjs/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/easyUIjs/jquery.easyui.min.js" rel="stylesheet"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/easyUIjs/locale/easyui-lang-zh_CN.js"
	rel="stylesheet"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/sys.js"
	rel="stylesheet"></script>
<script type="text/javascript">
</script>

<div class="panel panel-default">
	<div class="container">
		<form:form name="listForm" method="post"
			action="${pageContext.request.contextPath}/enterprise/serviceMerchants/ServiceAddDo.shtml"
			role="form">
			<form:hidden path="entity.id" />
			<table class="table table-hover table-middle">
				<caption>添加服务商</caption>
				<tr>
					<td width="25%">所属市</td>
					<td><form:select id="city1" path="entity.city_id" onchange="udpCity()" items="${cityS}"
							itemLabel="name" itemValue="id">
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">所属区县</td>
					<td><form:select id="county1" onchange="udpCounty()" path="entity.county_id"
							items="${countyS}" itemLabel="name" itemValue="id"></form:select></td>
				</tr>
				<tr>
					<td width="25%">所属街道</td>
					<td><form:select id="street1" path="entity.street_id" items="${streeS}" itemLabel="name"
							itemValue="id"></form:select></td>
				</tr>
					<tr>
						<td width="25%">服务单位名称</td>
						<td><form:input style="width:300px" path="entity.serviceName" /></td>
					</tr>
				<tr>
					<td width="25%">营业执照名称</td>
					<td><form:input style="width:300px" path="entity.charterName" /></td>
				</tr>
				<tr>
					<td width="25%">营业执照编码</td>
					<td><form:input style="width:300px" path="entity.charterNumber" /></td>
				</tr>
				<tr>
					<td width="25%">服务电话</td>
					<td><form:input path="entity.serviceTell" /></td>
				</tr>
				<tr>
					<td width="25%">服务地址</td>
					<td><form:input style="width:300px" path="entity.serviceAddress" /></td>
				</tr>
				<tr>
					<td width="25%">服务类型</td>
					<td><form:select path="entity.serviceTypeId">
							<form:options items="${typeList }" itemLabel="serviceName" itemValue="id" />
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">服务区域描述</td>
					<td><form:input path="entity.addressDescribe" /></td>
				</tr>
				<tr>
					<td width="25%">联系人</td>
					<td><form:input path="entity.contact" /></td>
				</tr>
				<tr>
					<td width="25%">联系人手机号</td>
					<td><form:input path="entity.contactPhone" /></td>
				</tr>
				<tr>
					<td width="25%">是否能刷养老卡</td>
					<td><form:select path="entity.is_pensionCard" id="is_pensionCard">
							<form:option value="1">是</form:option>
							<form:option value="0">否</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">是否能刷跨年</td>
					<td><form:select path="entity.is_AcrossYears" id="is_AcrossYears">
							<form:option value="1">是</form:option>
							<form:option value="0">否</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">是否能刷失能</td>
					<td><form:select path="entity.is_anergy" id="is_anergy">
							<form:option value="1">是</form:option>
							<form:option value="0">否</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">上级服务单位名称</td>
					<td><form:input style="width: 300px" path="entity.superiorServiceName" /></td>
				</tr>
				<tr>
					<td width="25%">总负责人</td>
					<td><form:input path="entity.principal" /></td>
				</tr>
				<tr>
					<td width="25%">总负责人联系电话</td>
					<td><form:input path="entity.principalPhone" /></td>
				</tr>
				<tr>
					<td width="25%">售后对接人</td>
					<td><form:input path="entity.aftermarketPerson" /></td>
				</tr>
				<tr>
					<td width="25%">售后电话</td>
					<td><form:input path="entity.aftermarketPhone" /></td>
				</tr>
				<tr>
					<td width="25%">服务内容</td>
					<td><form:textarea style="width: 300px; height: 60px;" path="entity.serviceContent" /> <tr>
					<td width="25%">折扣信息</td>
					<td><form:select path="entity.discountInfo" id="discountInfo">
							<form:option value="1">是</form:option>
							<form:option value="0">否</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td width="25%">是否签约</td>
					<td><form:select path="entity.is_signing" id="is_anergy">
							<form:option value="1">是</form:option>
							<form:option value="0">否</form:option>
						</form:select></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td width="25%">服务状态</td> -->
<%-- 					<td><form:input path="entity.serviceState" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td width="25%">邮箱</td>
					<td><form:input path="entity.email" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<a class="btn btn-default" href="#" class="btn btn-success" onclick="submit()">添加</a>
						</td>
					<td colspan="2"><input type="reset" class="btn btn-warning" /></td>
				</tr>


			</table>
		</form:form>
	</div>

</div>
<script>
	var city1 = $("#city1");
	var county1 = $("#county1");
	var street1 = $("#street1");
	var community1 = $("#community1");
	function udpCity(){
		changCity(city1,county1,street1);
	}
	function udpCounty() {
		changCounty(county1, street1, community1);
	}
	function udpStreet() {
		changStreet(street1, community1);
	}
	
	//修改市
	function changCity(city1, county1, street1) {
		county1.empty();
		street1.empty();
		street1.append("<option>请选择</option>");
		var id = city1.val();
		$.post(
				"${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
				{
					id : id
				},
				function(data) {
					county1.empty();
					county1.append("<option>请选择</option>");
					for (var i = 0; i < data.length; i++) {
						county1.append("<option value='"+data[i].id+"'>"
										+ data[i].name + "</option>");
					}

				});
	}
	//修改区县
	function changCounty(county1, street1, community1) {
		var id = county1.val();
		$.post(
				"${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
				{
					id : id
				},
				function(data) {
					street1.empty();
					street1.append("<option>请选择</option>");
					for (var i = 0; i < data.length; i++) {
						street1.append("<option value='"+data[i].id+"'>"
										+ data[i].name + "</option>");
					}

				});
	}

	
	
	function submit() {
		  $.ajax({
				cache : true,
				type : "POST",
				url : '${pageContext.request.contextPath}/enterprise/serviceMerchants/ServiceAddDo.shtml',
				data : $('#command').serialize(),// 你的formid
				async : false,
				error : function(request) {
					alert("Connection error");
				},
				success : function(data) {
					alert(data);
				}
		}); 
	}

</script>