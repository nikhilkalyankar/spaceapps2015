<%@ include file="header.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<body>
	<section class="main">
		<header>
			<div class="container">
				<h2>SEARCH</h2>
			</div>
		</header>
		<div class="content style4 featured">
			<div class="container">
				<div class="row">
					<form:form action="search" commandName="form" method="POST">
						<div class="form-group">
							<form:input path="search" class="form-control"
								placeholder="Enter text to search" />
						</div>
						<input type="submit" class="button" value="Search" />
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
