<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
    <!-- local JS -->
    <script type="text/javascript" src="js/app.js"></script>
    <!-- Bootstrap JS or jQuery-->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar fluid-top navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">
				<%-- Logo --%>
				<%--<img src="/docs/4.0/assets/brand/bootstrap-solid.svg" width="30" height="30" alt="">--%>
				Student Store
			</a>
		  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		    	<span class="navbar-toggler-icon"></span>
		  	</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
		    	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
			      	<c:if test="${currUser != null}">
			      		<li class="nav-item">
			      			<a class="nav-link" href="/location">Search My Location</a>
			      		</li>
			      	</c:if>
			      	<c:if test="${currUser == null}">
			      		<li class="nav-item">
			      			<a class="nav-link" href="/register">Search My Location</a>
			      		</li>
			      	</c:if>
			      	<c:if test="${currUser.userName == 'MasterAcct'}">
			      		<li class="nav-item">
			      			<a class="nav-link" href="/product/new">Add New Product</a>
			      		</li>
			      		<li class="nav-item">
			      			<a class="nav-link" href="/category/new">Add New Category</a>
			      		</li>
			      	</c:if>
			      	<c:if test="${currUser != null}">
			      		<li class="nav-item">
			      			<a class="nav-link" href="/product/user">My Cart</a>
			      		</li>
			      	</c:if>
			      	<c:if test="${currUser == null}">
			      		<li class="nav-item">
			      			<a class="nav-link" href="/register">My Cart</a>
			      		</li>
			      	</c:if>
			    </ul>
			    <form class="d-flex">
					<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
				    <button class="btn btn-outline-success" type="submit">Search</button>
				</form>
				<c:if test="${currUser == null}">
					<a href="/login" class="btn btn-outline-secondary ms-2">Login</a>
					<a href="/register" class="btn btn-outline-secondary ms-2">Register</a>
				</c:if>
				<c:if test="${currUser != null}">
					<a href="/logout" class="btn btn-outline-secondary ms-2">Logout</a>
				</c:if>
	  		</div>
	  	</div>
	</nav>
	<form:form action="/category/create" method="post" modelAttribute="newCategory">
		<div class="form-group p-3">
			<h2>Create a New Category:</h2>
		</div>
		<div class="form-group p-3">
	        <label>Category Name:</label>
	        <form:input path="name" class="form-control" />
	        <form:errors path="name" class="text-danger" />
	    </div>
	    <input type="submit" value="Create" class="btn btn-success ms-sm-3" />
	</form:form>
</body>
</html>