<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>List Portfolios</title>
	</head>
	<body>
		<p><b>List Portfolios</b></p>
		<c:url var="updatePortfolioUrl" value="/portfolio/updatePortfolio.htm" />
		<c:url var="listPortfoliosUrl" value="/portfolio/listPortfolios.htm" />
		<display:table name="portfolios" id="portfolio" sort="list" requestURI="${listPortfoliosUrl}" >
			<display:column property="name" title="Name" sortable="true"/>
			<display:column property="currency.code" title="Currency" />
			<display:column media="html" href="${updatePortfolioUrl}" paramId="id" paramProperty="id">
				<img src="${pageContext.request.contextPath}/images/icons/open.png"
							width="18" height="18" title="Update ${portfolio.name}" border="0" />
			</display:column>
		</display:table>
		<br>
		<a href="${updatePortfolioUrl}">Create new portfolio</a>
	</body>
</html>