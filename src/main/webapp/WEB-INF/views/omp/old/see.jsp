<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="panel panel-default">
	<table class="table table-striped">
		<tr>
			<td>姓名：</td>
			<td>${detaMap.name }</td>
		</tr>
		<tr>
			<td>区县：</td>
			<td><select name="county" id="county">
					<c:forEach items="${Region.county }" var="county">
						<option value="${county.id }"
							<c:if test="${county.id==detaMap.household_county_id }">selected="selected"</c:if>>${county.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>街道：</td>
			<td><select name="street" id="street">
					<c:forEach items="${Region.street }" var="street">
						<option value="${street.id }"
							<c:if test="${street.id==detaMap.household_street_id }">selected="selected"</c:if>>${street.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>社区：</td>
			<td><select name="community" id="community">
					<c:forEach items="${Region.community }" var="community">
						<option value="${community.id }"
							<c:if test="${community.id==detaMap.household_community_id }">selected="selected"</c:if>>${community.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>座机号：</td>
			<td>${detaMap.zjnumber }</td>
		</tr>
		<tr>
			<td>手机号：</td>
			<td>${detaMap.phone }</td>
		</tr>
		<tr>
			<td>居住地址：</td>
			<td>${detaMap.address }</td>
		</tr>
		<tr>
			<td>紧急联系人：</td>
			<td><${detaMap.emergencycontact }</td>
		</tr>
		<tr>
			<td>紧急联系人电话：</td>
			<td>${detaMap.emergencycontacttle }</td>
		</tr>
		<tr>
			<td>话机类型：</td>
			<td>${detaMap.teltype }</td>
		</tr>
	</table>
</div>
<div class="container">
	<table class="table table-hover">
		<caption>老人原始数据</caption>
		<tr>
			<td>姓名</td>
			<td>${mapPreson.name }</td>
		</tr>
		<tr>
			<td>性别</td>
			<td>${mapPreson.sex }</td>
		</tr>
		<tr>
			<td>身份证号码</td>
			<td>${mapPreson.certificatesNumber }</td>
		</tr>
		<tr>
			<td>证件有郊期</td>
			<td>${mapPreson.disabilityCardDate }</td>
		</tr>
		<tr>
			<td>出生日期</td>
			<td>${mapPreson.birthdate }</td>
		</tr>
		<tr>
			<td>居住地所在区县</td>
			<td>${mapPreson.residenceCounty }</td>
		</tr>
		<tr>
			<td>居住地所在街道</td>
			<td>${mapPreson.residenceStreet }</td>
		</tr>
		<tr>
			<td>居住地所在社区</td>
			<td>${mapPreson.residenceCommunity }</td>
		</tr>
		<tr>
			<td>居住地地址</td>
			<td>${mapPreson.residenceAddress }</td>
		</tr>
		<tr>
			<td>银行卡号</td>
			<td>${mapPreson.bankCard }</td>
		</tr>
		<tr>
			<td>一本通号</td>
			<td>${mapPreson.bookId }</td>
		</tr>
		<tr>
			<td>北京通号</td>
			<td>${mapPreson.bjtNumber }</td>
		</tr>
		<tr>
			<td>一卡通号</td>
			<td>${mapPreson.yktNumber }</td>
		</tr>
		<tr>
			<td>民族</td>
			<td>${mapPreson.nation }</td>
		</tr>
		<tr>
			<td>紧急联系人</td>
			<td>${mapPreson.contacter }</td>
		</tr>
		<tr>
			<td>紧急联系人证件号码</td>
			<td>${mapPreson.contacterIdcardNumber }</td>
		</tr>
		<tr>
			<td>紧急联系人手机号</td>
			<td>${mapPreson.contacterMobile }</td>
		</tr>
		<tr>
			<td>与联系人关系</td>
			<td>${mapPreson.contacterRelation }</td>
		</tr>
		<tr>
			<td>居住情况</td>
			<td>${mapPreson.resideType }</td>
		</tr>
		<tr>
			<td>文化程度</td>
			<td>${mapPreson.degreeType }</td>
		</tr>
		<tr>
			<td>医疗保障类型</td>
			<td>${mapPreson.healthCareType }</td>
		</tr>
		<tr>
			<td>月收入</td>
			<td>${mapPreson.revenueType }</td>
		</tr>
		<tr>
			<td>享受金额</td>
			<td>${mapPreson.treatmentOney }</td>
		</tr>
		<tr>
			<td>主要经济来源</td>
			<td>${mapPreson.mainSourceIncomeType }</td>
		</tr>
		<tr>
			<td>生活自理情况</td>
			<td>${mapPreson.selfCareAbilityType }</td>
		</tr>
		<tr>
			<td>婚姻状况</td>
			<td>${mapPreson.marryStateType }</td>
		</tr>
		<tr>
			<td>居住小区类型</td>
			<td>${mapPreson.biotope }</td>
		</tr>
		

	</table>
</div>
