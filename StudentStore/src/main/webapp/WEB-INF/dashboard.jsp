<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			    <form action="/search" class="d-flex">
					<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="query">
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
	<c:if test="${query != null}">
		<h2 class="d-flex justify-content-center">${query}</h2>
	</c:if>
	<c:forEach items="${productList}" var="product">
		<div class="card">
		  <div class="card-header">
		    ${product.name}
		  </div>
		  <div class="card-body">
		  	<img width="100" height="100" src="${product.photo}"/>
		    <h5 class="card-title">${product.price} (${product.discount}% off)</h5>
		    <p class="card-text">${product.description}</p>
		    <div class="d-flex justify-content-start">
		    	<c:forEach items="${product.categories}" var="category">
		    		<a href="/${category.name}" class="btn btn-outline-primary mb-2">
		    			<c:out value="${category.name}">
		    			</c:out>
		    		</a>
		    	</c:forEach>
		    </div>
		    <div class="d-flex justify-content-start">
		    	<a href="/product/${product.id}" class="btn btn-outline-info me-2">More Information</a>
			    <c:if test="${currUser.userName == 'MasterAcct'}">
			    	<a href="/product/edit/${product.id}" class="btn btn-outline-danger me-2">Edit</a>
			    	<form action="/product/delete/${product.id}" method="post">
						<input type="hidden" name="_method" value="delete">
						<input type="submit" class="btn btn-outline-danger me-2" value="Delete">
					</form>
			    </c:if>
			    <c:if test="${currUser != null}">
			    	<c:if test="${!currUser.getCart().contains(product)}">
			    		<a href="/cart/add/${product.id}" class="btn btn-outline-secondary me-2">Add to Cart</a>
			    	</c:if>
			    	<c:if test="${currUser.getCart().contains(product)}">
			    		<a href="/cart/remove/${product.id}" class="btn btn-outline-secondary me-2">Remove From Cart</a>
			    	</c:if>
			    </c:if>
			    <c:if test="${currUser == null}">
			    	<a href="/register" class="btn btn-outline-secondary me-2">Add to Cart</a>
			    </c:if>
			</div>
		  </div>
		</div>
	</c:forEach>
</body>
</html>