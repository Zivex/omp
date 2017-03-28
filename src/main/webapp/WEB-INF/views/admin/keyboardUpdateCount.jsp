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

  <caption style="text-align:center"><h3>快捷键修改次数统计</h3></caption>
  
  <thead>
    <tr>
      <th>区域</th>
      <th>指令发送总次数</th>
      <th>发送成功</th>
      <th>发送失败</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="c" items="${keyCount}" >
    <tr>
      <td>${c.name }</td>
      <td>${c.count }</td>
      <td>${c.suc }</td>
      <td>${c.fai }</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
	
</div>


<!-- Script	-->
<script>
</script>
