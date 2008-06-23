<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>List Securities</title>
	</head>
	<body>
		<p><b>List Securities</b></p>
		<c:url var="updateSecurityUrl" value="/security/updateSecurity.htm" />
		<c:url var="listSecurityUrl" value="/security/listSecurities.htm" />
		<display:table name="securities" sort="list" requestURI="${listSecurityUrl}" export="true" id="security">
			<display:column property="code" title="Code" sortable="true"/>
			<display:column property="country" title="Country" sortable="true" />
			<display:column property="description" title="Description" />
			<display:column media="html">
				<a href="${updateSecurityUrl}?code=${security.code}&country=${security.country}">
					<img title="Update" src="${pageContext.request.contextPath}/images/icons/open.png"
							width="18" height="18" title="Update Security" border="0" />
				</a>
			</display:column>
		</display:table>
		<br><a href="${updateSecurityUrl}">Create new security</a>
	</body>
</html>