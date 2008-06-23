<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Update Watch List</title>
	</head>
	<body>
		<p><b>Update Watch List</b></p>
		
		<c:url var="updateWatchlistUrl" value="/watchlist/updateWatchlist.htm" />
		<form:form method="POST" action="${updateWatchlistUrl}" >
			<form:errors path="*" />
			<spring:bind path="command.watchListId">
				<form:hidden path="watchListId" />
			</spring:bind>
			<table>
				<tr>
					<td>Name</td>
					<td>
						<spring:bind path="command.name">
							<form:input path="name" size="25" maxlength="50" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Description</td>
					<td>
						<spring:bind path="command.description">
							<form:input path="description" size="40" maxlength="250"/>
						</spring:bind>
					</td>
				</tr>
			</table>
			
			<p><b>Securities associated with the Watch List</b></p>
			<display:table name="alreadyAssignedSecurities" id="security">
				<display:column property="code" title="Code" />
				<display:column property="country" title="Country"  />
				<display:column property="description" title="Description" />
			</display:table>
			
			<c:if test="${not empty securities}">
				<p><b>Securities to associate to the Watch List</b></p>
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
				<p><b>There are no available securities to be associated to this Watch List.</b></p>
			</c:if>
			
			<input name="_save" value="Save" type="submit" />
			<input name="_cancel" value="Cancel" type="submit" />
		</form:form>
	</body>
</html>