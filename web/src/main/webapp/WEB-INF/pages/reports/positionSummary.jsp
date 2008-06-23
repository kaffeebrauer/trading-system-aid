<%@ include file="/WEB-INF/includes/includes.jsp" %>
<c:url var="positionSummaryUrl" value="/reports/positionSummary.htm" />

<html>
	<head>
		<title>Position Statement</title>
		<!-- <meta content="121" http-equiv="refresh">  -->
	</head>
	<body>
		<form name="portfolioForm" action="${positionSummaryUrl}" method="POST">
			<b>Position Statement for </b>
			<select name="portfolioId" onchange="javascript: portfolioForm.submit();">
				<option value="0">--- Select a Portfolio ---</option>
				<c:forEach items="${portfolios}" var="portfolio">
					<option value="${portfolio.id}" <c:if test="${portfolio.id == param.portfolioId}">selected</c:if>>${portfolio.name}</option>
				</c:forEach>
			</select>
			<a href="${chartUrl}">View chart</a>
		</form>
		
		<display:table name="positionSummaryContainerList" id="rowId" requestURI="${positionSummaryUrl}" sort="list" export="true">
			<display:column title="Code" media="html" sortable="true">
				<a href="http://finance.yahoo.com/charts#chart1:symbol=${rowId.security.code}.${rowId.security.country};range=2y;charttype=line;crosshair=on;logscale=on;source=undefined;indicator=bollinger+macd">
					${rowId.security.code}.${rowId.security.country}
				</a>
			</display:column>
			<display:column title="Code" media="csv pdf">${rowId.security.code}.${rowId.security.country}</display:column>
			<display:column title="Quantity" property="buyTransaction.quantity" />
			<display:column title="Price">
				<fmt:formatNumber type="currency" currencySymbol="$">${rowId.buyTransaction.price}</fmt:formatNumber>
			</display:column>
			<display:column title="Brokerage">
				<fmt:formatNumber type="currency" currencySymbol="$">${rowId.buyTransaction.brokerage}</fmt:formatNumber>
			</display:column>
			<display:column title="Amount Paid">
				<fmt:formatNumber type="currency" currencySymbol="$">${(rowId.buyTransaction.quantity * rowId.buyTransaction.price) + rowId.buyTransaction.brokerage}</fmt:formatNumber>
			</display:column>
			<display:column title="Last Price">
				<fmt:formatNumber type="currency" currencySymbol="$">${rowId.securityPrice.closePrice}</fmt:formatNumber>
			</display:column>
			<display:column title="Market Value" >
				<fmt:formatNumber type="currency" currencySymbol="$">${rowId.snapshot.marketValue}</fmt:formatNumber>
			</display:column>
			<display:column title="Profit / Loss" media="html" sortable="true">
			    <c:choose>
			    	<c:when test="${rowId.snapshot.difference > 0}">
			    		<span style="color: green; font-weight : bold;"><fmt:formatNumber type="currency" currencySymbol="$" >${rowId.snapshot.difference}</fmt:formatNumber></span>
			    	</c:when>
			    	<c:otherwise>
			    		<span style="color: red; font-weight : bold;"><fmt:formatNumber type="currency" currencySymbol="$" >${rowId.snapshot.difference}</fmt:formatNumber></span>
			    	</c:otherwise>
			    </c:choose>
			</display:column>
			<display:column title="Profit / Loss" media="csv pdf">
				<fmt:formatNumber type="currency" currencySymbol="$" >${rowId.snapshot.difference}</fmt:formatNumber>
			</display:column>
			<display:column title="ROC"  sortable="true">
				<fmt:formatNumber type="percent">${rowId.snapshot.returnOnCapital/100}</fmt:formatNumber>
			</display:column>
			<display:column title="Weeks" sortable="true">
				${rowId.snapshot.numberOfWeeksSinceBuy}
			</display:column>
			<display:column title="Stop Loss">
				<c:choose>
					<c:when test="${rowId.securityPrice.closePrice <= rowId.buyTransaction.technicalAnalysis.stopLoss}">
						<span style="color: red; font-weight : bold;">
							<fmt:formatNumber type="currency" currencySymbol="$" >${rowId.buyTransaction.technicalAnalysis.stopLoss}</fmt:formatNumber>
						</span>
					</c:when>
					<c:otherwise>
						<span style="color: green; font-weight : bold;">
							<fmt:formatNumber type="currency" currencySymbol="$" >${rowId.buyTransaction.technicalAnalysis.stopLoss}</fmt:formatNumber>
						</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column media="html">
				<c:url var="snapshotsUrl" value="/reports/snapshots.htm">
					<c:param name="securityCode" value="${rowId.security.code}" />
					<c:param name="securityCountry" value="${rowId.security.country}" />
					<c:param name="buyTransactionId" value="${rowId.buyTransaction.id}" />
				</c:url>
				<a href="${snapshotsUrl}" >
					<img src="${pageContext.request.contextPath}/images/icons/digital_camera.png"
							width="18" height="18" title="View Snapshots" border="0" />
				</a>
			</display:column>
			<display:footer>
				<tr >
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${boughtAmountTotal}</fmt:formatNumber></b></td>
					<td></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${marketValueTotal}</fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber type="currency" currencySymbol="$">${differenceTotal}</fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber type="percent">${rocAverage/100}</fmt:formatNumber></b></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</display:footer>
		</display:table>
	</body>
</html>