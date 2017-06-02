<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="panel panel-default">
	<form:form id="listForm" name="listForm" >
		<input type="hidden" id="id" name="id" value="${old.id }"/>
		<table class="table table-hover table-middle">
			<tr>
				<td  width="25%">姓名：</td><td>${old.name }</td>
			</tr>
			<tr>
				<td>区县：</td>
			<td>${old.household_county.name }</td>
			</tr>
			<tr>
				<td>街道：</td>
			<td>${old.household_street.name }</td>
			</tr>
			<tr>
				<td>社区：</td>
			<td>${old.household_community.name }</td>
			</tr>
			<tr>
				<td>座机号：</td><td>${old.zjnumber }</td>
			</tr>
			<tr>
				<td>手机号：</td><td>${old.phone }</td>
			</tr>
			<tr>
				<td>居住地址：</td><td>${old.address }</td>
			</tr>
			<tr>
				<td>紧急联系人：</td><td>${old.emergencycontact }</td>
			</tr>
			<tr>
				<td>紧急联系人电话：</td><td>${old.emergencycontacttle }</td>
			</tr>
			<tr>
				<td>话机类型：</td>
			<td>${old.phoneType.phoneType }</td>
			</tr>
		</table>

		<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="25%">话机键位</th>
						<th width="25%">服务商名称</th>
						<th >号码</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="old" items="${sp}" varStatus="sta">
						<tr>
							<td>
							   <c:if test="${old.key=='M1'}">养老驿站</c:if>
							   <c:if test="${old.key=='M2'}">咨询投诉</c:if>
							   <c:if test="${old.key=='M3'}">老年用具</c:if>
							   <c:if test="${old.key=='M4'}">居家护理</c:if>
							   <c:if test="${old.key=='M5'}">家电服务</c:if>
							   <c:if test="${old.key=='M6'}">家政服务</c:if>
							   <c:if test="${old.key=='M7'}">社区便利</c:if>
							   <c:if test="${old.key=='M8'}">老年餐桌</c:if>
							   <c:if test="${old.key=='M9'}">卫生站</c:if>
							   <c:if test="${old.key=='M10'}">居委会</c:if>
							   <c:if test="${old.key=='M11'}">社区广播</c:if>
							   <c:if test="${old.key=='M12'}">急救</c:if>
							   <c:if test="${old.key=='M13'}">中心号码1</c:if>
							   <c:if test="${old.key=='M14'}">中心号码2</c:if>
							   <c:if test="${old.key=='M15'}">中心号码3</c:if>
							   <c:if test="${old.key=='M16'}">中心号码4</c:if>
							</td>
							<td>${old.sp.serviceName}</td>
							<td>${old.sp.serviceTell}</td>

						</tr>
					</c:forEach>
				</tbody>

			</table>
	</form:form>
</div>
