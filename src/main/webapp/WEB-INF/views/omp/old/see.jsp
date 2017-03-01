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
			<td><c:forEach items="${Region.county }" var="county">
					<c:if test="${county.id==detaMap.household_county_id }">${county.name}</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>街道：</td>
			<td><c:forEach items="${Region.street }" var="street">
					<c:if test="${street.id==detaMap.household_street_id }">${street.name}</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>社区：</td>
			<td><c:forEach items="${Region.community }" var="community">
					<c:if test="${community.id==detaMap.household_community_id }">${community.name}</c:if>
				</c:forEach></td>
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
			<td>${detaMap.emergencycontact }</td>
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
<c:if test="${sessionScope.eccomm_admin.display_all == 1}">
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
				<td>证件类型</td>
				<td>${mapPreson.certificatesType }</td>
			</tr>
			<tr>
				<td>身份证号码</td>
				<td>${mapPreson.certificatesNumber }</td>
			</tr>
			<tr>
				<td>发证机关</td>
				<td>${mapPreson.idcradDept }</td>
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
				<td>制卡推送时间</td>
				<td>${mapPreson.creatCardPushDate }</td>
			</tr>
			<tr>
				<td>制卡回盘时间</td>
				<td>${mapPreson.createCardInDate }</td>
			</tr>
			<tr>
				<td>制卡是否成功</td>
				<td>${mapPreson.createCardSuccess }</td>
			</tr>
			<tr>
				<td>制卡失败原因</td>
				<td>${mapPreson.createCardFailInfo }</td>
			</tr>
			<tr>
				<td>区县是否推送</td>
				<td>${mapPreson.cityPushed }</td>
			</tr>
			<tr>
				<td>区县推送时间</td>
				<td>${mapPreson.cityPushedDate }</td>
			</tr>
			<tr>
				<td>核查推送时间</td>
				<td>${mapPreson.hcPushedDate }</td>
			</tr>
			<tr>
				<td>核查回盘时间</td>
				<td>${mapPreson.hcInTime }</td>
			</tr>
			<tr>
				<td>核查是否成功</td>
				<td>${mapPreson.hcSuccess }</td>
			</tr>
			<tr>
				<td>核查失败原因</td>
				<td>${mapPreson.hcFailInfo }</td>
			</tr>
			<tr>
				<td>卡类型</td>
				<td>${mapPreson.cardType }</td>
			</tr>
			<tr>
				<td>数据采集状态</td>
				<td>${mapPreson.gatherStatus }</td>
			</tr>
			<tr>
				<td>系统审核状态</td>
				<td>${mapPreson.auditStatus }</td>
			</tr>
			<tr>
				<td>制卡</td>
				<td>${mapPreson.creatCardStatus }</td>
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
				<td>制卡时间</td>
				<td>${mapPreson.creatCardDate }</td>
			</tr>
			<tr>
				<td>民族</td>
				<td>${mapPreson.nation }</td>
			</tr>
			<tr>
				<td>邮编</td>
				<td>${mapPreson.postalCode }</td>
			</tr>
			<tr>
				<td>户口在区县</td>
				<td>${mapPreson.householdCounty }</td>
			</tr>
			<tr>
				<td>户口所在街道</td>
				<td>${mapPreson.householdStreet }</td>
			</tr>
			<tr>
				<td>户口所在社区</td>
				<td>${mapPreson.householdCommunity }</td>
			</tr>
			<tr>
				<td>户口地址</td>
				<td>${mapPreson.householdAddress }</td>
			</tr>
			<tr>
				<td>报纸收取方式</td>
				<td>${mapPreson.newspaperGetWayType }</td>
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
				<td>紧急联系人证件类型</td>
				<td>${mapPreson.contacterIdcardType }</td>
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
				<td>人员类型/td>
				<td>${mapPreson.personType }</td>
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
</c:if>