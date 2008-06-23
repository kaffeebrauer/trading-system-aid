<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Snapshots for ${param.securityCode}.${param.securityCountry}</title>
	</head>
	<body>
		<p><b>Snapshots for ${param.securityCode}.${param.securityCountry}</b></p>
		
		<table class="displayTable">
			<thead>
				<tr>
					<th>Security</th>
					<th>Quantity</th>
					<th>Price</th>
					<th>Brokerage</th>
					<th>Amount Paid</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${param.securityCode}.${param.securityCountry}</td>
					<td>${buyTransaction.quantity}</td>
					<td><fmt:formatNumber type="currency" currencySymbol="$">${buyTransaction.price}</fmt:formatNumber></td>
					<td><fmt:formatNumber type="currency" currencySymbol="$">${buyTransaction.brokerage}</fmt:formatNumber></td>
					<td><fmt:formatNumber type="currency" currencySymbol="$">${(buyTransaction.quantity*buyTransaction.price)+buyTransaction.brokerage}</fmt:formatNumber></td>
				</tr>
			</tbody>
		</table>
		
		<c:choose>
			<c:when test="${empty snapshots}">
				No data found for this security.
			</c:when>
			<c:otherwise>
				<c:url var="snapshotsUrl" value="/reports/snapshots.htm" />
				<display:table name="snapshots" id="snapshot" sort="list" requestURI="${snapshotsUrl}">
					<display:column title="Date" property="timestamp" sortable="true" decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator" />
					<display:column title="Last Price" >
						<fmt:formatNumber type="currency" currencySymbol="$">${snapshot.lastPrice}</fmt:formatNumber>
					</display:column>
					<display:column title="Market Value" >
						<fmt:formatNumber type="currency" currencySymbol="$">${snapshot.marketValue}</fmt:formatNumber>
					</display:column>
					<display:column title="Profit / Loss" >
						<c:choose>
					    	<c:when test="${snapshot.difference > 0}">
					    		<span style="color: green; font-weight : bold;"><fmt:formatNumber type="currency" currencySymbol="$" >${snapshot.difference}</fmt:formatNumber></span>
					    	</c:when>
					    	<c:otherwise>
					    		<span style="color: red; font-weight : bold;"><fmt:formatNumber type="currency" currencySymbol="$" >${snapshot.difference}</fmt:formatNumber></span>
					    	</c:otherwise>
					    </c:choose>
					</display:column>
					<display:column title="ROC" >
						<fmt:formatNumber type="percent">${snapshot.returnOnCapital/100}</fmt:formatNumber>
					</display:column>
					<display:column title="Trailing StopLoss" >
						<fmt:formatNumber type="currency" currencySymbol="$">${snapshot.trailingStopLoss}</fmt:formatNumber>
					</display:column>
					<display:column title="Risk" >
						<fmt:formatNumber type="percent">${snapshot.risk/100}</fmt:formatNumber>
					</display:column>
					<display:column title="Channel Taken" >
						<fmt:formatNumber type="percent">${snapshot.channelTaken}</fmt:formatNumber>
					</display:column>
					<display:column property="numberOfWeeksSinceBuy" title="Week#" />
				</display:table>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>