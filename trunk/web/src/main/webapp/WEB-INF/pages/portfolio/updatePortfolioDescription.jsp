<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Update Portfolio</title>
	</head>
	<body>
		<p><b>Update Portfolio</b></p>
		
		<c:url var="updatePortfolioUrl" value="/portfolio/updatePortfolio.htm" />
		<form:form method="POST" action="">
			<form:errors path="*" />
			<input type="hidden" name="_page0" value="true" />
			<input type="hidden" name="_target1" value="true" />
			
			<spring:bind path="command.portfolioId">
				<form:hidden path="portfolioId" />
			</spring:bind>
			
			<table>
				<tr>
					<td>Name</td>
					<td>
						<spring:bind path="command.name">
							<form:input path="name" size="30" maxlength="50" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Description</td>
					<td>
						<spring:bind path="command.description">
							<form:input path="description" size="50" maxlength="250"/>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Currency</td>
					<td>
						<spring:bind path="command.currencyCode">
							<form:select path="currencyCode">
								<form:options items="${currencies}" itemLabel="code" itemValue="code" />
							</form:select>
						</spring:bind>
					</td>
				</tr>
			</table>
			<input name="_next" value="Next" type="submit"/>
			<input name="_cancel" value="Cancel" type="submit"/>
		</form:form>
		
	</body>
</html>