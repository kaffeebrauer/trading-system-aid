<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>List Watch Lists</title>
	</head>
	<body>
		<p><b>List Watch Lists</b></p>
		<c:url var="updateWatchListUrl" value="/watchlist/updateWatchlist.htm" />
		<c:url var="listWatchListsUrl" value="/watchlist/listWatchlists.htm" />
		<display:table name="watchLists" id="watchlist" sort="list" requestURI="${listWatchListsUrl}" >
			<display:column title="Name" sortable="true" property="name" />
			<display:column title="Description" property="description" maxLength="50" />
			<display:column media="html" href="${updateWatchListUrl}" paramId="id" paramProperty="id">
				<img src="${pageContext.request.contextPath}/images/icons/open.png"
							width="18" height="18" title="Update ${watchlist.name}" border="0" />
			</display:column>
		</display:table>
		<br><a href="${updateWatchListUrl}">Create new watch list</a>
	</body>
</html>