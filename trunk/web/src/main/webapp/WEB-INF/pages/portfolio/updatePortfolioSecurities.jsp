<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Update Portfolio Securities</title>
	</head>
	<body>
		<p><b>Step 2. Update Portfolio Securities</b></p>
		
		<c:url var="updatePortfolioUrl" value="/portfolio/updatePortfolio.htm" />
		<form:form method="POST" action="${updatePortfolioUrl}" >
			<input type="hidden" name="_page1" value="true" />
			
			<p><b>Securities associated with the portfolio</b></p>
			<display:table name="alreadyAssignedSecurities" id="security">
				<display:column property="code" title="Code" />
				<display:column property="country" title="Country"  />
				<display:column property="description" title="Description" />
			</display:table>
			
			<c:if test="${not empty securities}">
				<p><b>Securities to associate to the portfolio</b></p>
				<display:table name="securities" id="security">
					<display:column title="Select">
					    <spring:bind path="command.securityIds">
					    	<form:checkbox path="securityIds" value="${security.id}" />
					    </spring:bind>
				    </display:column>
					<display:column property="code" title="Code" />
					<display:column property="country" title="Country" />
					<display:column property="description" title="Description" />
				</display:table>
			</c:if>
			<c:if test="${empty securities}">
				<p><b>There are no available securities to be associated to this portfolio.</b></p>
			</c:if>
			
			<input name="_finish" value="Finish" type="submit" />
			<input name="_target0" value="Back" type="submit" />
			<input name="_cancel" value="Cancel" type="submit" />
		</form:form>
	</body>
</html>