<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="panel panel-default">
	<div class="container">
		<c:url var="save" value="/old/oldMatch/oldAdd.shtml" />
		<form:form id="command1" method="post" action='${save}' class="form-horizontal" role="form">
			<div class="form-group">
				<label for="city_id" class="col-md-2 control-label">市: </label>
				<div class="col-md-4">
					<form:select path="city_id" data-rule-required="true" class="form-control" id="cityOld">
						<form:option value="">--请选择--</form:option>
						<form:options items="${citys }" itemLabel="name" itemValue="id" />
					</form:select>
				</div>
				<span style="color: red">*</span>
			</div>

			<div class="form-group">
				<label for="entity.household_county_id" class="col-md-2 control-label">区县: </label>
				<div class="col-md-4">
					<select name="entity.household_county_id" data-rule-required="true" class="form-control"
						id="countyOld">
						<option value="">--请选择--</option>
					</select>
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.household_street_id" class="col-md-2 control-label">街道: </label>
				<div class="col-md-4">
					<form:select path="entity.household_street_id" data-rule-required="true" class="form-control"
						id="streetOld">
						<form:option value="">--请选择--</form:option>
					</form:select>
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.household_community_id" class="col-md-2 control-label">社区: </label>
				<div class="col-md-4">
					<form:select path="entity.household_community_id" data-rule-required="true"
						class="form-control" id="communityOld">
						<form:option value="">--请选择--</form:option>
					</form:select>
				</div>
				<span style="color: red">*</span>
			</div>

			<div class="form-group">
				<label for="entity.name" class="col-md-2 control-label">姓名: </label>
				<div class="col-md-4">
					<form:input path="entity.name" class="form-control" data-rule-required="true" placeholder="姓名" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.certificates_number" class="col-md-2 control-label">身份证号: </label>
				<div class="col-md-4">
					<form:input path="entity.certificates_number" class="form-control" data-rule-required="true"
						placeholder="身份证号" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.zjnumber" class="col-md-2 control-label">座机号: </label>
				<div class="col-md-4">
					<form:input path="entity.zjnumber" class="form-control" data-rule-required="true"
						placeholder="座机号" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.phone" class="col-md-2 control-label">联系电话: </label>
				<div class="col-md-4">
					<form:input path="entity.phone" class="form-control" data-rule-required="true"
						placeholder="联系电话" />
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.emergencycontact" class="col-md-2 control-label">紧急联系人: </label>
				<div class="col-md-4">
					<form:input path="entity.emergencycontact" class="form-control" data-rule-required="true"
						placeholder="紧急联系人" />
				</div>
				<span style="color: red">*</span>
			</div>
			
			<div class="form-group">
				<label for="entity.emergencycontacttle" class="col-md-2 control-label">紧急联系人电话: </label>
				<div class="col-md-4">
					<form:input path="entity.emergencycontacttle" class="form-control" data-rule-required="true"
						placeholder="紧急联系人" />
				</div>
				<span style="color: red">*</span>
			</div>


			<div class="form-group">
				<label for="entity.teltype" class="col-md-2 control-label">话机类型: </label>
				<div class="col-md-4">
					<select name="entity.teltype" data-rule-required="true" class="form-control" id="countyOld">
						<option value="">--请选择--</option>
						<option value="1">居家型</option>
						<option value="3">农商型</option>
						<option value="2">失能型</option>
					</select>
				</div>
				<span style="color: red">*</span>
			</div>
			<div class="form-group">
				<label for="entity.address" class="col-md-2 control-label">居住地址: </label>
				<div class="col-md-4">
					<form:input path="entity.address" class="form-control" placeholder="居住地址" />
				</div>
			</div>

			<div class="form-group">
				<label for=entity.display_all class="col-md-2 control-label">是否有来电显示: </label>
				<div class="col-md-4">
					<label> <form:radiobutton path="entity.call_id" value="1" data-rule-required="true" />是
						<form:radiobutton path="entity.call_id" value="0" /> 否
					</label> <span></span> <span style="color: red">*</span>
				</div>
			</div>
		</form:form>
				<a class="btn btn-default" href="#" class="btn btn-success" onclick="submit()">添加</a>
	</div>

</div>
<script>

$(document).ready(function() {
	initialFormValidate('command1');
	
	$("#cityOld").change(function(){
		$("#countyOld option:not(:first)").remove();
		$("#streetOld option:not(:first)").remove();
		$("#communityOld option:not(:first)").remove();
		var id = $("#cityOld").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
			for(var i = 0;i<data.length;i++){
				$("#countyOld").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		});
	});
	$("#countyOld").change(function(){
		$("#streetOld option:not(:first)").remove();
		$("#communityOld option:not(:first)").remove();
		var id = $("#countyOld").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
			for(var i = 0;i<data.length;i++){
				$("#streetOld").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		});
	});
	$("#streetOld").change(function(){
		$("#communityOld option:not(:first)").remove();
		var id = $("#streetOld").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
			for(var i = 0;i<data.length;i++){
				$("#communityOld").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		});
	});
});

function submit(){
	if (!$("#command1").valid()) {
        return;
     }
	  var data = $("#command1").serializeArray();
	    $.ajax({
	      url: "<%=request.getContextPath() %>/old/oldMatch/oldAdd.shtml",
	      data: data,
	      success: function(result){
	       alert(result);
	       location.href = "<%=request.getContextPath() %>/old/oldMatch/list.shtml";
	      }
	    });
}
</script>