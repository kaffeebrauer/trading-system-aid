<%@ include file="/WEB-INF/includes/includes.jsp" %>
<c:url var="historicalPerformanceUrl" value="/reports/historicalPerformance.htm" />

<html>
	<head>
		<title>Historical Performance</title>
		<script type="text/javascript">
			var fromCalendar;
			var toCalendar;
			function handleFromSelect(type, args, obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[2], day = date[1];
				
				var fromText = document.getElementById("from");
				fromText.value = month + "/" + day + "/" + year;
			}
			function handleToSelect(type, args, obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[2], day = date[1];
				
				var toText = document.getElementById("to");
				toText.value = month + "/" + day + "/" + year;
			}
		</script>
		<style type="text/css">
			#fromCalendarContainer { display:none; position:absolute}
			#toCalendarContainer { display:none; position:absolute}
		</style>
	</head>
	<body>
		<form name="historicalPerformanceForm" action="${historicalPerformanceUrl}" method="POST">
			<b>Historical Performance for </b>
			<select name="portfolioId">
				<option value="0">--- Select a Portfolio ---</option>
				<c:forEach items="${portfolios}" var="portfolio">
					<option value="${portfolio.id}" <c:if test="${portfolio.id == param.portfolioId}">selected</c:if>>${portfolio.name}</option>
				</c:forEach>
			</select>
			
			<b> from </b>
			<input type="text" id="from" name="from" size="10" value="${from}" />
			<img src="${pageContext.request.contextPath}/images/icons/calendar.png" id="showFromCalendar"
				width="20" height="20" title="Select from Calendar" border="0" />
			<div id="fromCalendarContainer"></div>
			
			<b> to </b>
			<input type="text" id="to" name="to" size="10" value="${to}" />
			<img src="${pageContext.request.contextPath}/images/icons/calendar.png" id="showToCalendar"
				width="20" height="20" title="Select from Calendar" border="0" />
			<div id="toCalendarContainer"></div>
			
			<input type="submit" name="submitButton" value="Go" />
		</form>
		<c:choose>
			<c:when test="${not empty profitLossVOs}">
				<table>
					<c:forEach items="${profitLossVOs}" var="profitLossVO">
						<tr>
							<td colspan="4"><b>${profitLossVO.security.completeCode} - ${profitLossVO.security.description}</b></td>
						</tr>
						<!-- Initial valuation -->
						<c:if test="${profitLossVO.initialMarketValuation.quantity > 0}">
							<tr>
								<td>Initial</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy" value="${profitLossVO.initialMarketValuation.valuationDate}" /></td>
								<td>${profitLossVO.initialMarketValuation.quantity}@<fmt:formatNumber type="currency" currencySymbol="$" >${profitLossVO.initialMarketValuation.marketPrice}</fmt:formatNumber></td>
								<td><fmt:formatNumber type="currency" currencySymbol="$" >${profitLossVO.initialMarketValuation.valuation}</fmt:formatNumber></td>
							</tr>
						</c:if>
						<!-- Transactions -->
						<c:forEach items="${profitLossVO.transactions}" var="transaction">
							<tr>
								<td>${transaction.class.simpleName}</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy" value="${transaction.timestamp}" /></td>
								<td>${transaction.quantity}@<fmt:formatNumber type="currency" currencySymbol="$" >${transaction.price}</fmt:formatNumber></td>
								<td><fmt:formatNumber type="currency" currencySymbol="$" >${transaction.quantity * transaction.price}</fmt:formatNumber></td>
							</tr>
						</c:forEach>
						<!-- Final valuation -->
						<c:if test="${profitLossVO.finalMarketValuation.quantity > 0}">
							<tr>
								<td>Final</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy" value="${profitLossVO.finalMarketValuation.valuationDate}" /></td>
								<td>${profitLossVO.finalMarketValuation.quantity}@<fmt:formatNumber type="currency" currencySymbol="$" >${profitLossVO.finalMarketValuation.marketPrice}</fmt:formatNumber></td>
								<td><fmt:formatNumber type="currency" currencySymbol="$" >${profitLossVO.finalMarketValuation.valuation}</fmt:formatNumber></td>
							</tr>
						</c:if>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td><fmt:formatNumber type="currency" currencySymbol="$" >${profitLossVO.profitLoss}</fmt:formatNumber></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="3"><b>Grand total</b></td>
						<td><b><fmt:formatNumber type="currency" currencySymbol="$" >${totalProfitLoss}</fmt:formatNumber></b></td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				No historical performance found.
			</c:otherwise>
		</c:choose>
		<script type="text/javascript">
			fromCalendar = new YAHOO.widget.Calendar("fromCalendar", 
				"fromCalendarContainer", { title: "Choose date", close: true });
			fromCalendar.selectEvent.subscribe(handleFromSelect, fromCalendar, true);
			fromCalendar.cfg.setProperty("DATE_FIELD_DELIMITER", "/");
			fromCalendar.cfg.setProperty("MDY_DAY_POSITION", 1);
			fromCalendar.cfg.setProperty("MDY_MONTH_POSITION", 2);
			fromCalendar.cfg.setProperty("MDY_YEAR_POSITION", 3);
			fromCalendar.cfg.setProperty("MD_DAY_POSITION", 1); 
			fromCalendar.cfg.setProperty("MD_MONTH_POSITION", 2);
			fromCalendar.cfg.setProperty("pagedate", "${fromMonthYear}");
			fromCalendar.cfg.setProperty("selected","${from}", false); 
			fromCalendar.render();
			
			YAHOO.util.Event.addListener("showFromCalendar", "click", 
				fromCalendar.show, fromCalendar, true);
				
			toCalendar = new YAHOO.widget.Calendar("toCalendar", 
				"toCalendarContainer", { title: "Choose date", close: true });
			toCalendar.selectEvent.subscribe(handleToSelect, toCalendar, true);
			toCalendar.cfg.setProperty("DATE_FIELD_DELIMITER", "/");
			toCalendar.cfg.setProperty("MDY_DAY_POSITION", 1);
			toCalendar.cfg.setProperty("MDY_MONTH_POSITION", 2);
			toCalendar.cfg.setProperty("MDY_YEAR_POSITION", 3);
			toCalendar.cfg.setProperty("MD_DAY_POSITION", 1); 
			toCalendar.cfg.setProperty("MD_MONTH_POSITION", 2);
			toCalendar.cfg.setProperty("pagedate", "${toMonthYear}");
			toCalendar.cfg.setProperty("selected","${to}", false); 
			toCalendar.render();
			
			YAHOO.util.Event.addListener("showToCalendar", "click", 
				toCalendar.show, toCalendar, true);
		</script>
	</body>
</html>