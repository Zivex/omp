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
	<form:form id="listForm" name="listForm" method="post" action='${queryForm }'>
		<%-- 		<form:hidden path="id" id="item_entity_id" /> --%>
		<%-- 		<form:hidden path="current" id="current" /> --%>
		<input id="item_entity_id" type="hidden" name="id" value="">
		<input id="currentPage" type="hidden" name="current" value="">
		<%-- 		<c:if test="${count>0}"> --%>
		<!-- tools -->
		<!-- 		<div class="panel-heading"></div> -->
		<table class="table table-hover table-middle" role="grid">
			<thead>
				<tr class="active">
					<!-- 					<th width="3%"><input type="checkbox" onclick="check()" /></th> -->
					<th width="5%" style="text-align: center;">区域名称</th>
					<th width="10%" style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="r" items="${list}" varStatus="sta">
					<tr>
						<%-- 						<td><input type="checkbox" class="ids" value="${r.id}" /></td> --%>

						<td style="text-align: center;"><a id="viewItem" onclick="hxtoOldInfo(${r.id});">${r.name}</a></td>
						<td style="text-align: center;">
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
									操作 <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a onclick="toupd(${r.id},'${r.name }')">修改</a></li>
									<li><a href="###" onclick="deleteRegion(${r.id})">删除</a></li>
									<li><a href="###" onclick="addchild(${r.id},'${r.name }')">添加</a></li>
									<li><a onclick="see('${old.certificates_number}',${old.id})">查看</a></li>
								</ul>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%-- 		</c:if> --%>
		<div class="panel-footer">
			<table class="table table-pagination">
				<thead>
					<tr>

						<td align="left">共<span class="text-danger"><strong>${count}</strong></span>条记录（每页<span
							class="text-info"><strong>${command.perPieceSize}</strong></span>条记录）&emsp;
						</td>
						<td align="right" height="28"><div id="result_page"></div></td>
					</tr>
				</thead>
			</table>
		</div>
	</form:form>
</div>
<div>
	<table class="table" id="table1">
	</table>
</div>

<div class="modal fade" id="updateregionName" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog" style="diplay: none;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h5 class="modal-title" id="voiceContent">修改的区域名称</h5>
			</div>
			<div class="modal-body">
				<form class="form-inline" id="province">
					<div class="form-group">
						<label for="exampleInputName2">区域名称</label> <input type="text" class="form-control"
							id="updatename" name="entity.name" placeholder="区域名称"> <input type="hidden"
							class="form-control" id="updateid" name="entity.id">
					</div>
					<a class="btn btn-default" href="#" onclick="updateRegion()" role="button">修改</a>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- 	追加子区域 -->
<div class="modal fade" id="addChildRegion" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog" style="diplay: none;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h5 class="modal-title" id="voiceContent">修改的区域名称</h5>
			</div>
			<div class="modal-body">
				<form class="form-inline" id="childprovince">
					<div class="form-group">
						<label for="exampleInputName2">区域名称</label> <input type="text" class="form-control"
							id="addchilname" name="entity.name" placeholder="区域名称"> <input type="hidden"
							class="form-control" id="parentid" name="entity.parentid">
					</div>
					<div id="resultDiv" style="margin-top: 10px;">
						<%@ include file="/WEB-INF/views/omp/regionManage/childList.jsp"%>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default" href="#" onclick="addChildRegion()" role="button" id="addText"></a>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<!-- Script	-->
<script type="text/javascript">

	/**
	* 删除用户
	*/
	function deleteUser(uid,tr){
		var sure=confirm("删除操作是不可逆的，确认删除该信息吗？");
		if(sure){
			location.href = "delete.shtml?id="+uid;
			alert("删除成功！");
			window.location.href="${pageContext.request.contextPath}/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&isindividuation=";
		}
	}

	$(document).ready(function() {
		initListForm();
		<c:if test="${count!=null&&count>0}">
		 initPagination(<c:out value="${count}"/>,<c:out value="${command.perPieceSize}"/>,<c:out value="${command.currentPieceNum}"/>);		</c:if>
	});

	function check(){
		$(".ids").map(function() {
			$(this).attr("checked", !$(this).attr("checked"));
		});
	}

	

	
	 function updatemodle ()
     {
	        $ ('#updateregionName').modal ('show');
     }
	 
	 function toupd(rid,rname){
		 $("#updatename").val(rname);
		 $("#updateid").val(rid);
		 updatemodle();
	 }
	 
	 
	  function updateRegion ()
      {
	        $.ajax (
	        {
	            cache : true,
	            type : "POST",
	            url : '<%=request.getContextPath() %>/admin/sys/region/updateRegion.shtml',
	            data : $ ('#province').serialize (),
	            async : false,
	            error : function (request)
	            {
		            alert ("Connection error");
	            },
	            success : function (data)
	            {
		            alert(data);
		            window.location.reload ();
	            }
	        });
      }
	  
	  function addChildRegion ()
      {
	        $.ajax (
	        {
	            cache : true,
	            type : "POST",
	            url : '<%=request.getContextPath() %>/admin/sys/region/addChildRegion.shtml',
	            data : $ ('#childprovince').serialize (),
	            async : false,
	            error : function (request)
	            {
		            alert ("Connection error");
	            },
	            success : function (data)
	            {
		            alert(data);
		            window.location.reload ();
	            }
	        });
      }
	  
	  
	  function addchild(rid,rname){
		  $("#addText").text("添加到"+rname);
		  $(".addR").remove();
			 $("#parentid").val(rid);
				$.ajax({ 
					cache : false,
				    type : "post",  
				    url : "<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",  
				    data: {id:rid},
				    async : false,//取消异步  
				    success : function(data){  
				    	for(var i = 0;i<data.length;i++){
				    		var j=i+1;
				    		$("#chidTable").append("<tr class='addR'><td>"+j+"</td><td style='text-align: center;' >"+data[i].name+"<td><td><a class='btn btn-default' href='#' onclick='deleteRegion("+data[i].id+")' role='button' >删除</a></td></tr>");
						}
				    }  
				}); 
		  $ ('#addChildRegion').modal ('show');
	  }
	  
	  function deleteRegion(rid){
		   $.ajax (
			        {
			            cache : true,
			            type : "POST",
			            url : '<%=request.getContextPath() %>/admin/sys/region/deleteRegion.shtml',
			            data : {rid:rid},
			            async : false,
			            error : function (request)
			            {
				            alert ("Connection error");
			            },
			            success : function (data)
			            {
				            alert(data);
				            window.location.reload ();
			            }
			        });
	  }
	  
</script>
