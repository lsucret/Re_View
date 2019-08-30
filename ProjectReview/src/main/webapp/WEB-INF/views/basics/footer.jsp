<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<footer class="ftco-footer">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">
				<p>
					Copyright &copy; 2019 All rights reserved | This template is made with by <a href="https://colorlib.com" target="_blank">Colorlib</a>
				</p>
			</div>
		</div>
	</div>
</footer>

<span class="btn-blogmain-sidebar"></span>
<c:if test="${not empty login}"><jsp:include page="sidebar.jsp" /><jsp:include page="../modal/msgList.jsp" /></c:if>
<script src="/js/default/scrollax.min.js"></script>
<script src="/js/default/main.js"></script>
</body>
</html>