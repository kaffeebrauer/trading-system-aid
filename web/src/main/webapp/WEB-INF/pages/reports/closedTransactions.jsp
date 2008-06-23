<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Closed Transactions</title>
	</head>
	<body>
		<p><b>Closed Transactions</b></p>
		<c:url var="closedTransactionsUrl" value="/reports/closedTransactions.htm" />
		<form:form method="POST" action="${closedTransactionsUrl}" >
			<display:table name="closedTransactions" id="closedTransaction" sort="list" requestURI="${closedTransactionsUrl}">
				<display:column title="Security" property="security" sortable="true" />
				<display:column title="Buy Date" property="buyDate" sortable="true" 
					decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Buy Quantity" property="buyQuantity" />
				<display:column title="Buy Price">
					<fmt:formatNumber type="currency" currencySymbol="$">${closedTransaction.buyPrice}</fmt:formatNumber>
				</display:column>
				<display:column title="Paid">
					<fmt:formatNumber type="currency" currencySymbol="$">${closedTransaction.amountPaid}</fmt:formatNumber>
				</display:column>
				
				<display:column title="Sell Date" property="sellDate" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Sell Quantity" property="sellQuantity" />
				<display:column title="Sell Price">
					<fmt:formatNumber type="currency" currencySymbol="$">${closedTransaction.sellPrice}</fmt:formatNumber>
				</display:column>
				<display:column title="Received">
					<fmt:formatNumber type="currency" currencySymbol="$">${closedTransaction.amountReceived}</fmt:formatNumber>
				</display:column>
				
				<display:column title="Profit / Loss" media="html" sortable="true">
				    <c:choose>
				    	<c:when test="${closedTransaction.profitLoss > 0}">
				    		<span style="color: green; font-weight : bold;">
				    			<fmt:formatNumber type="currency" currencySymbol="$" >${closedTransaction.profitLoss}</fmt:formatNumber>
				    		</span>
				    	</c:when>
				    	<c:otherwise>
				    		<span style="color: red; font-weight : bold;">
				    			<fmt:formatNumber type="currency" currencySymbol="$" >${closedTransaction.profitLoss}</fmt:formatNumber>
				    		</span>
				    	</c:otherwise>
				    </c:choose>
				</display:column>
				<display:column title="Profit / Loss" media="csv pdf">
					<fmt:formatNumber type="currency" currencySymbol="$" >${closedTransaction.profitLoss}</fmt:formatNumber>
				</display:column>
				<display:column title="ROC"  sortable="true">
					<fmt:formatNumber type="percent">${closedTransaction.returnOnCapital/100}</fmt:formatNumber>
				</display:column>
				<display:column title="Weeks" sortable="true" property="numberOfWeeks"></display:column>
			</display:table>
		</form:form>
	</body>
</html>