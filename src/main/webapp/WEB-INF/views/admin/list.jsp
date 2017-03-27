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
	
<table class="table table-hover">

  <caption style="text-align:center"><h3>呼叫服务统计</h3></caption>
  
  <thead>
    <tr>
      <th>服务类型</th>
      <th>次数</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="c" items="${showcout}" >
    <tr>
      <td>${c.sname }</td>
      <td>${c.count }</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
	
</div>


<!-- Script	-->
<script>
</script>
