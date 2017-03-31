<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUIjs/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUIjs/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/jquery.easyui.min.js" rel="stylesheet"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/locale/easyui-lang-zh_CN.js" rel="stylesheet"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/sys.js" rel="stylesheet"></script>
<script type="text/javascript">
$(document).ready(function(){
	  expandAll();
	});
</script>

<div class="panel panel-default">
	<div class="container">
		<table class="table table-hover table-middle">
			<caption>服务商详情</caption>
			<tr>
				<td width="25%">所属市</td>
				<td>${serviceProvider.city.name}</td>
			</tr>
			<tr>
				<td width="25%">所属区县</td>
				<td>${serviceProvider.county.name}</td>
			</tr>
			<tr>
				<td width="25%">所属街道</td>
				<td>${serviceProvider.street.name}</td>
			</tr>
			<tr>
				<td width="25%">服务单位名称</td>
				<td>${serviceProvider.serviceName}</td>
			</tr>
			<tr>
				<td width="25%">营业执照名称</td>
				<td>${serviceProvider.charterName}</td>
			</tr>
			<tr>
				<td width="25%">营业执照编码</td>
				<td>${serviceProvider.charterNumber}</td>
			</tr>
			<tr>
				<td width="25%">服务电话</td>
				<td>${serviceProvider.serviceTell}</td>
			</tr>
			<tr>
				<td width="25%">服务地址</td>
				<td>${serviceProvider.serviceAddress}</td>
			</tr>
			<tr>
				<td width="25%">服务类型</td>
				<td>${serviceProvider.serviceType.serviceName}</td>
			</tr>
			<tr>
				<td width="25%">服务区域描述</td>
				<td>${serviceProvider.addressDescribe}</td>
			</tr>
			<tr>
				<td width="25%">服务区域</td>
				<td>
						<ul id="tree" class="easyui-tree" data-options="lines:true">
							<c:forEach items="${countyList }" var="item">
								<li id="${item.id}" state="open"><span>${item.name }</span>
									<ul id="${item.id}">
										<c:forEach items="${streetList }" var="street">
											<c:if test="${item.id==street.parentid }">
												<li id="${street.id}" state="open"><span>${street.name }</span>
													<ul id="${street.id}">
														<c:forEach items="${communityList }" var="community">
															<c:if test="${street.id==community.parentid }">
																<li id="${community.id}" state="open"><span>${community.name }</span>
															</c:if>
														</c:forEach>
													</ul></li>
											</c:if>
										</c:forEach>
									</ul></li>
							</c:forEach>
						</ul>
						<ul id="ss"></ul>
				</td>
			</tr>
			<tr>
				<td width="25%">渠道发展来源</td>
				<td>${serviceProvider.channels}</td>
			</tr>
			<tr>
				<td width="25%">联系人</td>
				<td>${serviceProvider.contact}</td>
			</tr>
			<tr>
				<td width="25%">联系人手机号</td>
				<td>${serviceProvider.contactPhone}</td>
			</tr>
			<tr>
				<td width="25%">是否能刷养老卡</td>
				<td>${serviceProvider.is_pensionCard == 1?"是":"否"}</td>
			</tr>
			<tr>
				<td width="25%">是否能刷跨年</td>
				<td>${serviceProvider.is_AcrossYears == 1?"是":"否"}</td>
			</tr>
			<tr>
				<td width="25%">是否能刷失能</td>
				<td>${serviceProvider.is_anergy  == 1?"是":"否"}</td>
			</tr>
			<tr>
				<td width="25%">上级服务单位名称</td>
				<td>${serviceProvider.superiorServiceName}</td>
			</tr>
			<tr>
				<td width="25%">总负责人</td>
				<td>${serviceProvider.principal}</td>
			</tr>
			<tr>
				<td width="25%">总负责人联系电话</td>
				<td>${serviceProvider.principalPhone}</td>
			</tr>
			<tr>
				<td width="25%">售后对接人</td>
				<td>${serviceProvider.aftermarketPerson}</td>
			</tr>
			<tr>
				<td width="25%">售后电话</td>
				<td>${serviceProvider.aftermarketPhone}</td>
			</tr>
			<tr>
				<td width="25%">服务内容</td>
				<td>${serviceProvider.serviceContent}</td>
			</tr>
			<tr>
				<td width="25%">折扣信息</td>
				<td>${serviceProvider.discountInfo == 1?"是":"否"}</td>
			</tr>
			<tr>
				<td width="25%">核实状态</td>
				<td><c:if test="${serviceProvider.verify == 1}">未审核</c:if> <c:if
						test="${serviceProvider.verify == 2}">无效</c:if> <c:if
						test="${serviceProvider.verify == 3}">有效</c:if></td>
			</tr>
			<tr>
				<td width="25%">是否签约</td>
				<td>${serviceProvider.is_signing == 1?"是":"否"}</td>
			</tr>
			<tr>
				<td width="25%">服务状态</td>
				<td>${serviceProvider.serviceState}</td>
			</tr>
			<tr>
				<td width="25%">邮箱</td>
				<td>${serviceProvider.email}</td>
			</tr>

		</table>
	</div>

</div>
