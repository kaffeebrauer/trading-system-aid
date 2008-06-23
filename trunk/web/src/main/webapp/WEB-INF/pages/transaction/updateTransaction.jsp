<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Update / New Transaction</title>
		<script type="text/javascript">
			var transactionDateCalendar;
			function handleSelect(type, args, obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[2], day = date[1];
				
				var transactionDateText = document.getElementById("timestamp");
				transactionDateText.value = month + "/" + day + "/" + year;
			}
		</script>
		<style type="text/css">
			#transactionDateCalendarContainer { display:none; position:absolute; z-index:1}
		</style>
	</head>
	<body>
		<p><b>Update / New Transaction</b></p>
		
		<c:url var="updateTransactionUrl" value="/transaction/updateTransaction.htm"/>
		<form:form method="POST" action="${updateTransactionUrl}">
			<spring:bind path="command.transactionId">
				<form:hidden path="transactionId" />
			</spring:bind>
			<spring:bind path="command.technicalAnalysisId">
				<form:hidden path="technicalAnalysisId" />
			</spring:bind>
			<b><form:errors path="*" /></b>
			<table>
				<tr>
					<td>Security</td>
					<td>
						<spring:bind path="command.securityId">
							<form:select path="securityId" >
								<c:forEach items="${securities}" var="security">
									<form:option value="${security.id}" label="${security.code}.${security.country}"></form:option>
								</c:forEach>
							</form:select>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Type</td>
					<td>
						<b>${command.transactionType} transaction</b>
						<spring:bind path="command.transactionType">
							<form:hidden path="transactionType" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Quantity</td>
					<td>
						<spring:bind path="command.quantity">
							<form:input path="quantity" size="5" maxlength="5"/>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Price</td>
					<td>
						<spring:bind path="command.price">
							<form:input path="price" size="7" maxlength="7" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Date</td>
					<td>
						<spring:bind path="command.timestamp">
							<form:input path="timestamp" size="10" maxlength="10" />
						</spring:bind>
						<img src="${pageContext.request.contextPath}/images/icons/calendar.png" id="showTransactionCalendar"
							width="20" height="20" title="Select Transaction Date from Calendar" border="0" />
						<div id="transactionDateCalendarContainer"></div>
					</td>
				</tr>
				<tr>
					<td>Reference Number</td>
					<td>
						<spring:bind path="command.referenceNumber">
							<form:input path="referenceNumber" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Brokerage</td>
					<td>
						<spring:bind path="command.brokerage">
							<form:input path="brokerage" size="7" maxlength="7" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Currency</td>
					<td>
						<spring:bind path="command.currencyCode">
							<form:select path="currencyCode" >
								<form:options items="${currencies}" itemLabel="code" itemValue="code" />
							</form:select>
						</spring:bind>
					</td>
				</tr>
				<c:if test="${command.transactionType == 'buy'}">
					<tr>
						<td colspan="2"><b>Technical Analysis Data</b></td>
					</tr>
					<tr>
						<td>Stop loss</td>
						<td>
							<spring:bind path="command.stopLoss">
								<form:input path="stopLoss" size="7" maxlength="7" />
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td>Lower Channel</td>
						<td>
							<spring:bind path="command.lowerChannel">
								<form:input path="lowerChannel" size="7" maxlength="7" />
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td>Upper Channel</td>
						<td>
							<spring:bind path="command.upperChannel">
								<form:input path="upperChannel" size="7" maxlength="7" />
							</spring:bind>
						</td>
					</tr>
				</c:if>
			</table>
			<br>
			<input name="_save" value="Save" type="submit" />
			<input name="_cancel" value="Cancel" type="submit" />
		</form:form>
		<script type="text/javascript">
			transactionDateCalendar = new YAHOO.widget.Calendar("transactionDateCalendar", 
				"transactionDateCalendarContainer", { title: "Choose date", close: true });
			transactionDateCalendar.selectEvent.subscribe(handleSelect, transactionDateCalendar, true);
			transactionDateCalendar.cfg.setProperty("DATE_FIELD_DELIMITER", "/");
			transactionDateCalendar.cfg.setProperty("MDY_DAY_POSITION", 1);
			transactionDateCalendar.cfg.setProperty("MDY_MONTH_POSITION", 2);
			transactionDateCalendar.cfg.setProperty("MDY_YEAR_POSITION", 3);
			transactionDateCalendar.cfg.setProperty("MD_DAY_POSITION", 1); 
			transactionDateCalendar.cfg.setProperty("MD_MONTH_POSITION", 2);
			transactionDateCalendar.cfg.setProperty("pagedate", "${command.timestampMonthYear}");
			transactionDateCalendar.cfg.setProperty("selected","${command.timestamp}", false); 
			transactionDateCalendar.render();
			
			YAHOO.util.Event.addListener("showTransactionCalendar", "click", 
				transactionDateCalendar.show, transactionDateCalendar, true);
		</script>
	</body>
</html>