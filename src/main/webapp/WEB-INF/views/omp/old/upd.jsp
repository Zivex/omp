<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="panel panel-default">
	<form:form id="listForm" name="listForm" method="post" action="${pageContext.request.contextPath }/old/oldMatch/updete.shtml">
		<input type="hidden" id="id" name="entity.id" value="${detaMap.id }"/>
		<table>
			<tr>
				<td>姓名：</td><td><input type="text" id="name" name="entity.name" value="${detaMap.name }"/></td>
			</tr>
			<tr>
				<td>区县：</td><td><select name=county id="county1" onchange="udpCounty1()" >
					<c:forEach items="${Region.county }" var="county" >
						<option class="county" value="${county.id }"<c:if test="${county.id==detaMap.household_county_id }">selected="selected"</c:if>>${county.name}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>街道：</td><td><select name="street" id="street1" onchange="udpStreet1()">
						<c:forEach items="${Region.street }" var="street">
							<option value="${street.id }"<c:if test="${street.id==detaMap.household_street_id }">selected="selected"</c:if>>${street.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>社区：</td><td><select name="community" id="community1">
						<c:forEach items="${Region.community }" var="community">
							<option value="${community.id }"<c:if test="${community.id==detaMap.household_community_id }">selected="selected"</c:if>>${community.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>座机号：</td><td><input type="text" id="zjnumber" name="entity.zjnumber" value="${detaMap.zjnumber }"/></td>
			</tr>
			<tr>
				<td>手机号：</td><td><input type="text" id="phone" name="entity.phone" value="${detaMap.phone }"/></td>
			</tr>
			<tr>
				<td>居住地址：</td><td><input type="text" id="address" name="entity.address" value="${detaMap.address }"/></td>
			</tr>
			<tr>
				<td>紧急联系人：</td><td><input type="text" id="emergencycontact" name="entity.emergencycontact" value="${detaMap.emergencycontact }"/></td>
			</tr>
			<tr>
				<td>紧急联系人电话：</td><td><input type="text" id="emergencycontacttle" name="entity.emergencycontacttle" value="${detaMap.emergencycontacttle }"/></td>
			</tr>
			<tr>
				<td>来电显示：</td><td><input type="radio"  name="entity.call_id" value="1" <c:if test="${detaMap.call_id=='1' }">checked="checked"</c:if>/>是

				<input type="radio"  name="call_id" value="0" <c:if test="${detaMap.call_id=='0' }">checked="checked"</c:if>/> 否

				</td>
			</tr>
			<tr>
				<td>话机类型：</td>
				<td><c:if test="${sys == 'admin'}">
						<select id="teltype" name="entity.teltype">
							<option value="1"
								<c:if test="${detaMap.teltype=='1' }">selected="selected"</c:if>>居家型</option>
							<option value="3"
								<c:if test="${detaMap.teltype=='3' }">selected="selected"</c:if>>农商型</option>
							<option value="2"
								<c:if test="${detaMap.teltype=='2' }">selected="selected"</c:if>>失能型</option>
						</select>
					</c:if></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" onclick="submit()" value="修改"/></td>
			</tr>
		</table>
	</form:form>
</div>
<!-- Script	-->
<script type="text/javascript">
var county1 = $("#county1");
var street1 = $("#street1");
var community1 = $("#community1");

function udpCounty1(){
	changCounty(county1,street1,community1);
}
function udpStreet1(){
	changStreet(street1,community1);
}


//修改街道
function changCounty(county1,street1,community1){
	community1.empty();
	var id = county1.val();
	$.post("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
			{id:id},
			function(data){
				street1.empty();
				for(var i = 0;i<data.length;i++){
					street1.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>")
				}

	});
}
//修改社区
function changStreet(street1,community1){
	var id = street1.val();
	$.post("${pageContext.request.contextPath }/old/oldMatch/getRegionById.shtml",
			{id:id},
			function(data){
				community1.empty();
				for(var i = 0;i<data.length;i++){
					community1.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>")
				}
	});
}

function changCommunity(community1){

}



	function submit(){
		$("#listForm").submit(function(){
		});
 	}
// 			var street = $("#street").val();
// 			var community = $("#community").val();
// 			if(street=="" || street==undefined){
// 				return;
// 			}
// 			if(community=="" || community==undefined){
// 				return;
// 			}
	</script>
