<%@ include file="/WEB-INF/includes/includes.jsp" %>
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
    <head>
        <title>
            <decorator:title default="Trading System Web Interface" />
        </title>
        <decorator:head />
        <c:url var="styleSheetUrl" value="/styles/layout.css"/>
        <LINK REL="stylesheet" TYPE="text/css" HREF="${styleSheetUrl}"/>
        
        <!-- Core + Skin CSS -->
		<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.3.1/build/menu/assets/skins/sam/menu.css">
		<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.3.1/build/calendar/assets/skins/sam/calendar.css"> 
		
		<!-- Dependencies --> 
		<script type="text/javascript" src="http://yui.yahooapis.com/2.3.1/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="http://yui.yahooapis.com/2.3.1/build/container/container_core-min.js"></script>
		
		<!-- Source Files -->
		<script type="text/javascript" src="http://yui.yahooapis.com/2.3.1/build/menu/menu-min.js"></script>
		<script type="text/javascript" src="http://yui.yahooapis.com/2.3.1/build/calendar/calendar-min.js"></script>
		
    </head>
    <body class="yui-skin-sam">
        <table id="mainTable" width="800" bgcolor="#CCFFCC" align="center">
        	<tr>
        		<td id="menuCell" colspan="2"></td>
        	</tr>
            <tr>
            	<td colspan="2">
            		<!-- Menu -->
					<c:url value="/security/listSecurities.htm" var="listSecuritiesUrl"/>
					<c:url value="/reports/positionSummary.htm" var="positionSummaryUrl"/>
					<c:url value="/transaction/listTransactions.htm" var="listBuyTransactionsUrl">
						<c:param name="transactionType">buy</c:param>
					</c:url>
					<c:url value="/transaction/listTransactions.htm" var="listSellTransactionsUrl">
						<c:param name="transactionType">sell</c:param>
					</c:url>
					<c:url var="updateBuySellTransactionsUrl" value="/transaction/updateBuySellTransaction.htm"/>
					<c:url var="listPortfoliosUrl" value="/portfolio/listPortfolios.htm" />
					<c:url var="listDividendsUrl" value="/dividend/listDividends.htm" />
					<c:url var="listWatchListsUrl" value="/watchlist/listWatchlists.htm" />
					<c:url var="historicalPerformanceUrl" value="/reports/historicalPerformance.htm" />
					<c:url var="closedTransactionsUrl" value="/reports/closedTransactions.htm" />
                    <authz:authorize ifAnyGranted="ROLE_USER">
                    	<script type="text/javascript">
                    	    var aItemData = [
                    	    	{text: "Portfolios", url: "${listPortfoliosUrl}"},
                    	    	{text: "Securities", url: "${listSecuritiesUrl}"},
                    	    	{text: "Transactions", submenu: { id:"transactionsSubmenu", itemData: [
                    	    		{text: "Buy", url: "${listBuyTransactionsUrl}"},
                    	    		{text: "Sell", url: "${listSellTransactionsUrl}"},
                    	    		{text: "Association", url: "${updateBuySellTransactionsUrl}"}
                    	    	]}},
                    	    	{text: "Dividends", url: "${listDividendsUrl}"},
                    	    	{text: "Watch Lists", url: "${listWatchListsUrl}"},
                    	    	{text: "Reports", submenu: { id:"reportsSubmenu", itemData: [
                    	    		{text: "Position Summary", url: "${positionSummaryUrl}"},
                    	    		{text: "Historical Performance", url: "${historicalPerformanceUrl}"},
                    	    		{text: "Closed Transactions", url: "${closedTransactionsUrl}"}
                    	    	]}}
                    	    ];
							var oMenu = new YAHOO.widget.MenuBar("basicmenu", { visible: true, autosubmenudisplay: true, itemData: aItemData });
							oMenu.render(document.getElementById("menuCell"));
						</script>
                    </authz:authorize>
            	</td>
            </tr>
            <tr valign="top">
                <td colspan="2">
                	<c:if test="${not empty message}">
                		<b>${message}</b>
                		<c:remove var="message" />
                	</c:if>
                	<decorator:body />
                </td>
            </tr>
            <tr>
            	<td colspan="2">
	            	<hr color="#FFFFFF">
            	</td>
            </tr>
            <tr>
            	<td>
            		<c:url var="logoutUrl" value="/j_acegi_logout" />
                    Logged in as <b><%= SecurityContextHolder.getContext().getAuthentication().getName() %></b> - <a href="${logoutUrl}">
                    <img src="${pageContext.request.contextPath}/images/icons/logout.png"
							width="18" height="18" title="Logout" border="0" /></a>
            	</td>
                <td align="right" class="footerClass">openBiz Solutions 2007 - enquires@openbiz.com.au</td>
            </tr>
        </table>
    </body>
</html>
