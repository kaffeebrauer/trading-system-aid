<%@ include file="/WEB-INF/includes/includes.jsp" %>

<c:url value="/security/updateSecurity.htm" var="updateSecurityUrl" />

<html>
	<head>
		<title>New / Update Security</title>
		<script type="text/javascript">
			var oSubmitButton = new YAHOO.widget.Button("save"); 
		</script>
	</head>
	<body>
		<p><b>New / Update Security</b></p>
		<form:form method="POST" action="${updateSecurityUrl}">
			<table>
				<tr>
					<td>Code</td>
					<td>
						<spring:bind path="command.code">
							<form:input path="code" size="6" maxlength="5" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Country</td>
					<td>
						<spring:bind path="command.country">
							<form:input path="country" size="4" maxlength="3" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Description</td>
					<td>
						<spring:bind path="command.description">
							<form:input path="description" size="51" maxlength="50"/>
						</spring:bind>
					</td>
				</tr>
			</table>
			<input id="save" name="_save" value="Save" type="submit"/>
			<input name="_cancel" value="Cancel" type="submit"/>
		</form:form>
	</body>
</html>