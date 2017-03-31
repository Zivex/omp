<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<<<<<<< HEAD
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUIjs/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUIjs/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/jquery.easyui.min.js" rel="stylesheet"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/locale/easyui-lang-zh_CN.js" rel="stylesheet"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUIjs/sys.js" rel="stylesheet"></script>


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
					<div
						style="float: left; height: 300px; width: 165px; overflow: auto; border: 1px solid black; clear: both;">
						<ul id="tree" class="easyui-tree" data-options="lines:true">
							<c:forEach items="${countyList }" var="item">
								<li id="${item.ID}" state="closed"><span>${item.name }</span>
									<ul id="${item.id}">
										<c:forEach items="${streetList }" var="street">
											<c:if test="${item.id==street.pid }">
												<li id="${street.ID}" state="closed"><span>${street.name }</span>
													<ul id="${street.id}">
														<c:forEach items="${communityList }" var="community">
															<c:if test="${street.id==community.pid }">
																<li id="${community.ID}" state="closed"><span>${community.name }</span>
															</c:if>
														</c:forEach>
													</ul></li>
											</c:if>
										</c:forEach>
									</ul></li>
							</c:forEach>
						</ul>
						<ul id="ss"></ul>
=======
<!-- Ztree -->
<link rel=stylesheet type=text/css href="<c:url value="/resources/zTree/css/demo.css"/>" media="screen">
<link rel=stylesheet type=text/css href="<c:url value="/resources/zTree/css/zTreeStyle/zTreeStyle.css"/>" media="screen">
<script type="text/javascript"	src="<c:url value="/resources/zTree/js/jquery-1.4.4.min.js"/>"></script>
<script type="text/javascript"	src="<c:url value="/resources/zTree/js/jquery.ztree.core-3.5.min.js"/>"></script>
<SCRIPT type="text/javascript">
		<!--
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"父节点1 - 展开", open:true},
			{ id:11, pId:1, name:"父节点11 - 折叠"},
			{ id:111, pId:11, name:"叶子节点111"},
			{ id:112, pId:11, name:"叶子节点112"},
			{ id:113, pId:11, name:"叶子节点113"},
			{ id:114, pId:11, name:"叶子节点114"},
			{ id:12, pId:1, name:"父节点12 - 折叠"},
			{ id:121, pId:12, name:"叶子节点121"},
			{ id:122, pId:12, name:"叶子节点122"},
			{ id:123, pId:12, name:"叶子节点123"},
			{ id:124, pId:12, name:"叶子节点124"},
			{ id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
			{ id:2, pId:0, name:"父节点2 - 折叠"},
			{ id:21, pId:2, name:"父节点21 - 展开", open:true},
			{ id:211, pId:21, name:"叶子节点211"},
			{ id:212, pId:21, name:"叶子节点212"},
			{ id:213, pId:21, name:"叶子节点213"},
			{ id:214, pId:21, name:"叶子节点214"},
			{ id:22, pId:2, name:"父节点22 - 折叠"},
			{ id:221, pId:22, name:"叶子节点221"},
			{ id:222, pId:22, name:"叶子节点222"},
			{ id:223, pId:22, name:"叶子节点223"},
			{ id:224, pId:22, name:"叶子节点224"},
			{ id:23, pId:2, name:"父节点23 - 折叠"},
			{ id:231, pId:23, name:"叶子节点231"},
			{ id:232, pId:23, name:"叶子节点232"},
			{ id:233, pId:23, name:"叶子节点233"},
			{ id:234, pId:23, name:"叶子节点234"},
			{ id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
		];

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
		});
		//-->
	</SCRIPT>


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
				<td><div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
>>>>>>> branch 'master' of https://github.com/Zivex/omp.git
					</div>
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
