<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${not empty messages }">
	<div class="alert alert-warning alert-dismissable">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		${messages.message}
	</div>
</c:if>
<div class="panel panel-default">
	<div style="float: left; width: 50%;">
		<form:form id="sService" name="listForm" method="post" action='${queryForm }'>
		<input type="hidden" name="oid" id="oid" value="${hxUserID }">

			<table class="table table-hover table-middle" role="grid">
				<thead>
					<tr class="active">
						<th width="15%">话机键位</th>
						<th width="25%">服务商名称</th>
						<th width="15%">服务商号码</th>
					</tr>
				</thead>
				<c:forEach items="${sp }" var="sp">
					<tr>
						<td><input type='radio' name='typeId' value='${sp.id }'> ${sp.serviceName }</td>
						<td><input type='hidden' class='serviceId' name='${sp.key }' value='${sp.service.id }'><span>${sp.service.serviceName }</span></td>
						<td>${sp.service.serviceTell }</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="2"><input type="button" onclick="hxsubmit()" value="修改" /></td>
				</tr>
			</table>

		</form:form>
	</div>
	<div style="float: right; width: 40%;" id="providers">
		<div>
			<form id="providersForm" role="form">
				<div class="form-group">
					<label for="serviceName">名称</label> <input type="text" name="serviceName" class="form-control"
						id="serviceName" placeholder="请输入服务商名称">
				</div>
				<a class="btn btn-default" href="#" onclick="serchService()" role="button">搜索</a> <a
					class="btn btn-default" href="#" onclick="addService()" role="button">添加</a>
			</form>
		</div>
		<br>
		<div>
			<form id="serviceListForm">
				<table class="table table-bordered" id="serviceList">

				</table>
			</form>
		</div>
	</div>
</div>
<div>

	<SCRIPT type="text/javascript">
		function hxsubmit ()
        {
	        var addForm = $ ("#sService").serialize ();
	        $.post ("${pageContext.request.contextPath }/old/oldMatch/uploadOldIndividuation.shtml", addForm, 
       		function (data)
	        {
		        alert (data);
	        });
        }

        //搜索服务商
        function serchService ()
        {
	        var streetId = $ ("#street_id").val ();
	        var serviceList = $ ("#serviceList");
	        //服务区域
	        serviceList.empty ();
	        //话机类型
	        var serviceId = $ ('input[name="typeId"]:checked').val ();
	        
	        //	var serviceId = $("#serviceType").val();
	        
	        if (typeof (serviceId) === "undefined")
	        {
		        alert ("请选择键位");
		        return;
	        }
	        
	        $
	                .post (
	                        "${pageContext.request.contextPath }/serviceSystem/serchService.shtml",
	                        {
	                            serviceId : serviceId,
	                            streetId : streetId
	                        },
	                        function (data)
	                        {
		                        serviceList.empty ();
		                        var idNum = 0;
		                        serviceList
		                                .append ("<tr> <th width='70%' style='text-align: center'>服务商名称</th> <th width='30%' style='text-align: center'>服务电话</th> </tr>");
		                        
		                        for (var i = 0; i < data.length; i++)
		                        {
			                        idNum++;
			                        serviceList
			                                .append ("<tr><td><input type='radio' name='providers' value='"+data[i].id+"'> "
			                                        + data[i].serviceName
			                                        + "</td><td>"
			                                        + data[i].serviceTell
			                                        + "</td></tr>");
		                        }
	                        });
        }
        //页面修改服务体系
        function addService ()
        {
	        var serviceList = $ ("#serviceList");
	        if ($ ('input[name="providers"]:checked').length > 0)
	        {
		        var spId = $ ('input[name="providers"]:checked').val ();
		        var spName = $ ('input[name="providers"]:checked').parent ().text ();
		        var sptell = $ ('input[name="providers"]:checked').parent ().next ().text ();
		        var td = $ ('input[name="typeId"]:checked').parent ().next ()
		        var sp = td.children ("span");
		        sp.text (spName);
		        td.next ().text (sptell);
		        td.children ("input.serviceId")[0].value = spId;
	        }
	        else
	        {
		        alert ("请勾选务商");
	        }
	        serviceList.empty ();
	        
        }
	</SCRIPT>
</div>


