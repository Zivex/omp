<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="panel panel-default">
	<div class="container">
		<c:url var="save" value="/old/oldMatch/oldAdd.shtml" />
		<form:form id="command1" method="post" action='${save}' class="form-horizontal" role="form">
			<div class="form-group">
				<label for="city_id" class="col-md-2 control-label">所属市: </label>
				<div class="col-md-4">
					<form:select id="city1" data-rule-required="true" path="entity.city_id" onchange="udpCity()">
						<form:option value="">--请选择--</form:option>
						<form:options items="${cityS}" itemLabel="name" itemValue="id" />
					</form:select>
				</div>
				<span style="color: red">*</span>
			</div>

			<div class="form-group">
				<label for="entity.household_county_id" class="col-md-2 control-label">所属区县: </label>
				<div class="col-md-4">
					<form:select id="county1" onchange="udpCounty()" data-rule-required="true"
						path="entity.county_id" items="${countyS}" itemLabel="name" itemValue="id"></form:select>
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.household_street_id" class="col-md-2 control-label">所属街道: </label>
				<div class="col-md-4">
					<form:select id="street1" path="entity.street_id" data-rule-required="true" items="${streeS}"
						itemLabel="name" itemValue="id"></form:select>
				</div>
				<span style="color: red">*</span>
			</div>

			<div class="form-group">
				<label for="entity.name" class="col-md-2 control-label">服务单位名称: </label>
				<div class="col-md-4">
					<form:input style="width:300px" data-rule-required="true" class="form-control"
						path="entity.serviceName" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.certificates_number" class="col-md-2 control-label">营业执照名称: </label>
				<div class="col-md-4">
					<form:input style="width:300px" path="entity.charterName" class="form-control"
						data-rule-required="true" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.charterNumber" class="col-md-2 control-label">营业执照编码: </label>
				<div class="col-md-4">
					<form:input style="width:300px" path="entity.charterNumber" class="form-control"
						data-rule-required="true" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.phone" class="col-md-2 control-label">服务电话: </label>
				<div class="col-md-4">
					<form:input path="entity.serviceTell" class="form-control" data-rule-required="true" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.emergencycontact" class="col-md-2 control-label">服务地址: </label>
				<div class="col-md-4">
					<form:input style="width:300px" path="entity.serviceAddress" class="form-control"
						data-rule-required="true" />
				</div>
				<span style="color: red">*</span>
			</div>

			<div class="form-group">
				<label for="entity.emergencycontacttle" class="col-md-2 control-label">服务类型: </label>
				<div class="col-md-4">
					<form:select path="entity.serviceTypeId">
						<form:options items="${typeList }" itemLabel="serviceName" itemValue="id"
							data-rule-required="true" />
					</form:select>
				</div>
				<span style="color: red">*</span>
			</div>


			<div class="form-group">
				<label for="entity.teltype" class="col-md-2 control-label">服务区域描述: </label>
				<div class="col-md-4">
					<form:input path="entity.addressDescribe" class="form-control" data-rule-required="true" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">联系人: </label>
				<div class="col-md-4">
					<form:input path="entity.contact" data-rule-required="true" class="form-control" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">联系人手机号: </label>
				<div class="col-md-4">
					<form:input path="entity.contactPhone" data-rule-required="true" class="form-control"  />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">是否能刷养老卡: </label>
				<div class="col-md-4">
					<form:select path="entity.is_pensionCard" id="is_pensionCard">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">是否能刷跨年卡: </label>
				<div class="col-md-4">
					<form:select path="entity.is_AcrossYears" id="is_AcrossYears">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">是否能刷失能卡: </label>
				<div class="col-md-4">
					<form:select path="entity.is_anergy" id="is_anergy">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">上级服务单位名称: </label>
				<div class="col-md-4">
					<form:input style="width: 300px" path="entity.superiorServiceName" />
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">总负责人: </label>
				<div class="col-md-4">
					<form:input path="entity.principal" />
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">总负责人联系电话: </label>
				<div class="col-md-4">
					<form:input path="entity.principalPhone" />
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">售后对接人: </label>
				<div class="col-md-4">
					<form:input path="entity.aftermarketPerson" data-rule-required="true" class="form-control" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">售后电话: </label>
				<div class="col-md-4">
					<form:input path="entity.aftermarketPhone" data-rule-required="true" class="form-control" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">服务内容: </label>
				<div class="col-md-4">
					<form:textarea style="width: 300px; height: 60px;" path="entity.serviceContent" data-rule-required="true" class="form-control" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">折扣信息: </label>
				<div class="col-md-4">
					<form:select path="entity.discountInfo" id="discountInfo">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">是否签约: </label>
				<div class="col-md-4">
					<form:select path="entity.is_signing" id="is_anergy">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">邮箱: </label>
				<div class="col-md-4">
					<form:input path="entity.email" data-rule-required="true" class="form-control" />
				</div>
				<span style="color: red">*</span>
			</div>

		</form:form>
		<a class="btn btn-default" href="#" class="btn btn-success" onclick="submit()">添加</a>
	</div>

</div>































<script>
	var city1 = $ ("#city1");
    var county1 = $ ("#county1");
    var street1 = $ ("#street1");
    var community1 = $ ("#community1");
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
	    street1.append ("<option>请选择</option>");
	    var id = city1.val ();
	    $.post ("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
	    {
		    id : id
	    }, function (data)
	    {
		    county1.empty ();
		    county1.append ("<option>请选择</option>");
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
	    $.post ("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
	    {
		    id : id
	    }, function (data)
	    {
		    street1.empty ();
		    street1.append ("<option>请选择</option>");
		    for (var i = 0; i < data.length; i++)
		    {
			    street1.append ("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
		    }
		    
	    });
    }

    function submit ()
    {
    	if (!$("#command1").valid()) {
            return;
         }
	    $.ajax (
	    {
	        cache : true,
	        type : "POST",
	        url : '${pageContext.request.contextPath}/enterprise/serviceMerchants/ServiceAddDo.shtml',
	        data : $ ('#command').serialize (),// 你的formid
	        async : false,
	        error : function (request)
	        {
		        alert ("Connection error");
	        },
	        success : function (data)
	        {
		        alert (data);
	        }
	    });
    }
</script>