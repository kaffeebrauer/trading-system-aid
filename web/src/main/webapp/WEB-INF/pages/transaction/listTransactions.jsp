<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>
		    <c:choose>
		    	<c:when test="${transactionType == 'buy'}">
					List Buy Transactions
		    	</c:when>
		    	<c:otherwise>
		    		List Sell Transactions
		    	</c:otherwise>
		    </c:choose>
		</title>
	</head>
	<body>
		<c:choose>
	    	<c:when test="${transactionType == 'buy'}">
				<p><b>List Buy Transactions</b></p>
	    	</c:when>
	    	<c:otherwise>
	    		<p><b>List Sell Transactions</b></p>
	    	</c:otherwise>
	    </c:choose>
		
		<c:url var="updateTransactionUrl" value="/transaction/updateTransaction.htm">
			<c:param name="action" value="update"/>
			<c:param name="transactionType" value="${transactionType}"/>
		</c:url>
		<c:url var="createTransactionUrl" value="/transaction/updateTransaction.htm">
			<c:param name="action" value="create"/>
			<c:param name="transactionType" value="${transactionType}"/>
		</c:url>
		<c:url var="createOppositeTransactionUrl" value="/transaction/updateTransaction.htm">
			<c:param name="action" value="create"/>
			<c:param name="transactionType" value="sell"/>
		</c:url>
		<c:url var="listTransactionsUrl" value="/transaction/listTransactions.htm" />
		
		<display:table id="transaction" name="transactions" sort="list" requestURI="${listTransactionsUrl}" export="true">
			<display:column property="timestamp" title="Date" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
			<display:column title="Security" sortable="true">${transaction.security.code}.${transaction.security.country}</display:column>
			<display:column property="quantity" title="Quantity" />
			<display:column title="Price" >
				<fmt:formatNumber type="currency" currencySymbol="$" >${transaction.price}</fmt:formatNumber>
			</display:column>
			<display:column title="Brokerage" >
				<fmt:formatNumber type="currency" currencySymbol="$" >${transaction.brokerage}</fmt:formatNumber>
			</display:column>
			<display:column title="Net Price" >
				<fmt:formatNumber type="currency" currencySymbol="$" >${(transaction.quantity * transaction.price) + transaction.brokerage}</fmt:formatNumber>
			</display:column>
			<display:column media="html" href="${updateTransactionUrl}" paramId="id" paramProperty="id">
				<img src="${pageContext.request.contextPath}/images/icons/open.png"
							width="18" height="18" title="Update Transaction" border="0" />
			</display:column>
		</display:table>
		<br><a href="${createTransactionUrl}">Create new transaction</a>
	</body>
</html>