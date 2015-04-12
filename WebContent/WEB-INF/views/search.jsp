<%@include file="header.jsp"%>
<body>
	<section class="main">
		<header>
			<div class="container">
				<h2>SEARCH RESULT</h2>
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
		<div class="content style4 featured">
			<div class="container">
				<div class="row">
					<ul class="list-group">

						<c:forEach items="${results}" var="result">
							<li class="list-group-item"><b>${result.title}</b><br />
								${result.description} <br /> <a target="_blank"
								href="${result.url}">${result.url}</a></li>
						</c:forEach>

					</ul>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
