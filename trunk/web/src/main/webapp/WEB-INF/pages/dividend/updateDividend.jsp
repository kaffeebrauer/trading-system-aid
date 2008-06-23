<%@ include file="/WEB-INF/includes/includes.jsp" %>

<c:url value="/dividend/updateDividend.htm" var="updateDividendUrl" />

<html>
	<head>
		<title>New / Update Dividend</title>
		<script type="text/javascript">
			var paymentDateCalendar;
			function handleSelect(type, args, obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[2], day = date[1];
				
				var paymentDateText = document.getElementById("paymentDate");
				paymentDateText.value = month + "/" + day + "/" + year;
			}
			function modifyIPD(changeTo) {
				var allotedShares = document.getElementById('allotedShares');
				allotedShares.disabled = changeTo;
				var allotmentSharePrice = document.getElementById('allotmentSharePrice');
				allotmentSharePrice.disabled = changeTo;
			}
		</script>
		<style type="text/css">
			#paymentDateCalendarContainer { display:none; position:absolute; z-index:1}
		</style>
	</head>
	<body>
		<p><b>New / Update Dividend</b></p>
		<form:form method="POST" action="${updateDividendUrl}">
			<form:errors path="*" />
			<spring:bind path="command.id">
				<form:hidden path="id" />
			</spring:bind>
			<table>
				<tr>
					<td>Payment Date</td>
					<td>
						<spring:bind path="command.paymentDate">
							<form:input path="paymentDate" size="10" />
						</spring:bind>
						<img src="${pageContext.request.contextPath}/images/icons/calendar.png" id="showPaymentCalendar"
							width="20" height="20" title="Select Payment Date from Calendar" border="0" />
						<div id="paymentDateCalendarContainer"></div>
					</td>
					<td>Payment Amount Per Share</td>
					<td>
						<spring:bind path="command.paymentAmountPerShare">
							<form:input path="paymentAmountPerShare" size="7" maxlength="6" title="e.g. 0.57 means 57 cents" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Number Of Shares</td>
					<td>
						<spring:bind path="command.numberOfShares">
							<form:input path="numberOfShares" size="5" maxlength="5"/>
						</spring:bind>
					</td>
					<td>Franked Percentage</td>
					<td>
						<spring:bind path="command.frankedPercentage">
							<form:input path="frankedPercentage" size="4" maxlength="3" title="e.g. 60 means 60% franked"/>
						</spring:bind>
					</td>
				</tr>
			</table>
			
			</br><b>Assign the dividend to a security</b>
			<display:table name="securities" id="security">
				<display:column title="Select">
				    <spring:bind path="command.securityId">
				    	<form:radiobutton path="securityId" value="${security.id}" />
				    </spring:bind>
			    </display:column>
				<display:column property="code" title="Code" />
				<display:column property="country" title="Country" />
				<display:column property="description" title="Description" />
			</display:table>
			
			<br><b>Investment Plan Dividend</b>
			<table>
				<tr>
					<td>Is an investment plan dividend?</td>
					<td>
						<spring:bind path="command.isInvestmentPlanDividend">
							Yes <form:radiobutton path="isInvestmentPlanDividend" value="true" onclick="modifyIPD(false)" />
							No <form:radiobutton path="isInvestmentPlanDividend" value="false" onclick="modifyIPD(true)" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td>Alloted Shares</td>
					<td>
						<spring:bind path="command.allotedShares">
							<form:input path="allotedShares" size="5" maxlength="5" disabled="${command.isInvestmentPlanDividend?'false':'true'}" />
						</spring:bind>
					</td>
					<td>Allotment Share Price</td>
					<td>
						<spring:bind path="command.allotmentSharePrice">
							<form:input path="allotmentSharePrice" size="9" maxlength="8" title="e.g. 2.7222" disabled="${command.isInvestmentPlanDividend?'false':'true'}" />
						</spring:bind>
					</td>
				</tr>
			</table>

			<br>
			<input name="_save" value="Save" type="submit"/>
			<input name="_cancel" value="Cancel" type="submit"/>
		</form:form>
		<script type="text/javascript">
			paymentDateCalendar = new YAHOO.widget.Calendar("paymentDateCalendar", 
				"paymentDateCalendarContainer", { title: "Choose date", close: true });
			paymentDateCalendar.selectEvent.subscribe(handleSelect, paymentDateCalendar, true);
			paymentDateCalendar.cfg.setProperty("DATE_FIELD_DELIMITER", "/");
			paymentDateCalendar.cfg.setProperty("MDY_DAY_POSITION", 1);
			paymentDateCalendar.cfg.setProperty("MDY_MONTH_POSITION", 2);
			paymentDateCalendar.cfg.setProperty("MDY_YEAR_POSITION", 3);
			paymentDateCalendar.cfg.setProperty("MD_DAY_POSITION", 1); 
			paymentDateCalendar.cfg.setProperty("MD_MONTH_POSITION", 2);
			paymentDateCalendar.cfg.setProperty("pagedate", "${command.paymentMonthYear}");
			paymentDateCalendar.cfg.setProperty("selected","${command.paymentDate}", false); 
			paymentDateCalendar.render();
			
			YAHOO.util.Event.addListener("showPaymentCalendar", "click", 
				paymentDateCalendar.show, paymentDateCalendar, true);
		</script>
	</body>
</html>