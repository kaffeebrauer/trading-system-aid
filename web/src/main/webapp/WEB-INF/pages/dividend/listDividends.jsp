<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>List Dividends</title>
	</head>
	<body>
		<p><b>List Dividends</b></p>
		<c:url var="updateDividendUrl" value="/dividend/updateDividend.htm" />
		<c:url var="listDividendsUrl" value="/dividend/listDividends.htm" />
		<display:table name="dividends" id="dividend" sort="list" requestURI="${listDividendsUrl}" varTotals="totals" >
			<display:column title="Security" sortable="true" >
				${dividend.shareHolding.security.code}
			</display:column>
			<display:column property="paymentDate" title="Payment Date" sortable="true" 
				decorator="au.com.openbiz.trading.presentation.view.decorator.DateColumnDecorator"/>
			<display:column property="paymentAmountPerShare" title="Amount" >
				<fmt:formatNumber type="currency" currencySymbol="$">${dividend.paymentAmountPerShare}</fmt:formatNumber>
			</display:column>
			<display:column property="numberOfShares" title="No Shares" />
			<display:column title="Franked Percentage" >
				<fmt:formatNumber type="percent">${dividend.frankedPercentage/100}</fmt:formatNumber>
			</display:column>
			<display:column title="Unfranked" property="unfrankedAmount" total="true" >
				<fmt:formatNumber type="currency" currencySymbol="$">${dividend.unfrankedAmount}</fmt:formatNumber>
			</display:column>
			<display:column title="Franked" property="frankedAmount" total="true" >
				<fmt:formatNumber type="currency" currencySymbol="$">${dividend.frankedAmount}</fmt:formatNumber>
			</display:column>
			<display:column title="Franking Credit" property="frankingCredit" total="true" >
				<fmt:formatNumber type="currency" currencySymbol="$">${dividend.frankingCredit}</fmt:formatNumber>
			</display:column>
			<display:column title="Gross Payment" property="totalDividends" total="true">
				<fmt:formatNumber type="currency" currencySymbol="$">${dividend.totalDividends}</fmt:formatNumber>
			</display:column>
			<display:column media="html" href="${updateDividendUrl}" paramId="dividendId" paramProperty="id">
				<img src="${pageContext.request.contextPath}/images/icons/open.png"
							width="18" height="18" title="Update Dividend" border="0" />
			</display:column>
			<display:footer>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${totals.column6}</fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${totals.column7}</fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${totals.column8}</fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${totals.column9}</fmt:formatNumber></b></td>
					<td></td>
				</tr>
			</display:footer>
		</display:table>
		<br><a href="${updateDividendUrl}">Create new dividend</a>
	</body>
</html>