<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Update Buy Sell Transaction</title>
	</head>
	<body>
		<p><b>Update Buy Sell Transaction</b></p>
		<c:url var="updateBuySellTransactionUrl" value="/transaction/updateBuySellTransaction.htm" />
		<form:form method="POST" action="${updateBuySellTransactionUrl}" >
			<p><b>Buy Sell Transactions Associated</b></p>
			<display:table name="buySellTransactions" id="buySellTransaction" sort="list" requestURI="${updateBuySellTransactionUrl}">
				<display:column title="Buy Date" property="buyTransaction.timestamp" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Buy Security">${buySellTransaction.buyTransaction.security.code}.${buySellTransaction.buyTransaction.security.country}</display:column>
				<display:column title="Buy Quantity">${buySellTransaction.buyTransaction.quantity}</display:column>
				<display:column title="Buy Price">
					<fmt:formatNumber type="currency" currencySymbol="$">${buySellTransaction.buyTransaction.price}</fmt:formatNumber>
				</display:column>
				
				<display:column title="Sell Date" property="sellTransaction.timestamp" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Sell Security">${buySellTransaction.sellTransaction.security.code}.${buySellTransaction.sellTransaction.security.country}</display:column>
				<display:column title="Sell Quantity">${buySellTransaction.sellTransaction.quantity}</display:column>
				<display:column title="Sell Price">
					<fmt:formatNumber type="currency" currencySymbol="$">${buySellTransaction.sellTransaction.price}</fmt:formatNumber>
				</display:column>
			</display:table>
		
			<p><b>Buy Transactions</b></p>
			<display:table name="buyTransactions" id="buyTransaction" sort="list" requestURI="${updateBuySellTransactionUrl}">
			    <display:column title="Select">
				    <spring:bind path="command.buyTransactionIds">
				    	<form:checkbox path="buyTransactionIds" value="${buyTransaction.id}" />
				    </spring:bind>
			    </display:column>
				<display:column property="timestamp" title="Date" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Security" sortable="true">${buyTransaction.security.code}.${buyTransaction.security.country}</display:column>
				<display:column property="quantity" title="Quantity" />
				<display:column title="Price" >
					<fmt:formatNumber type="currency" currencySymbol="$" >${buyTransaction.price}</fmt:formatNumber>
				</display:column>
				<display:column title="Brokerage" >
					<fmt:formatNumber type="currency" currencySymbol="$" >${buyTransaction.brokerage}</fmt:formatNumber>
				</display:column>
				<display:column title="Net Price" >
					<fmt:formatNumber type="currency" currencySymbol="$" >${(buyTransaction.quantity * buyTransaction.price) + buyTransaction.brokerage}</fmt:formatNumber>
				</display:column>
			</display:table>
			
			<p><b>Sell Transactions</b></p>
			<display:table name="sellTransactions" id="sellTransaction" sort="list" requestURI="${updateBuySellTransactionUrl}">
				<display:column title="Select">
					<spring:bind path="command.sellTransactionIds">
				    	<form:checkbox path="sellTransactionIds" value="${sellTransaction.id}" />
				    </spring:bind>
			    </display:column>
				<display:column property="timestamp" title="Date" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
				<display:column title="Security" sortable="true">${sellTransaction.security.code}.${sellTransaction.security.country}</display:column>
				<display:column property="quantity" title="Quantity" />
				<display:column title="Price">
					<fmt:formatNumber type="currency" currencySymbol="$" >${sellTransaction.price}</fmt:formatNumber>
				</display:column>
				<display:column title="Brokerage" >
					<fmt:formatNumber type="currency" currencySymbol="$" >${sellTransaction.brokerage}</fmt:formatNumber>
				</display:column>
				<display:column title="Net Price" >
					<fmt:formatNumber type="currency" currencySymbol="$" >${(sellTransaction.quantity * sellTransaction.price) + sellTransaction.brokerage}</fmt:formatNumber>
				</display:column>
			</display:table>
			<br>
			<input name="_save" value="Save" type="submit"/>
			<input name="_cancel" value="Cancel" type="submit"/>
		</form:form>
	</body>
</html>