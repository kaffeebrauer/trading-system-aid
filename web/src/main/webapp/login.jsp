<%@ include file="/WEB-INF/includes/includes.jsp" %>

<html>
	<head>
		<title>Trading System Web Interface</title>
	</head>
	<body>
		<form method="post" action="j_acegi_security_check" id="loginForm">
                    <table width="400" valign="center" align="center" bgcolor="#CCFFCC">
                        <tr>
                            <td colspan="2" align="center"><h3>Trading System Login</h3></td>
                        </tr>
                        <tr>
                            <td align="right"><h5>Username</h5></td>
                            <td>
                            	<input type="text" name="j_username" size="10" value="pablo" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><h5>Password</h5></td>
                            <td><input name="j_password" type="password" size="10" /></td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                            	<input name="submit" value="Submit" type="submit" >
                            	<input type="reset" value="Reset">
                            </td>
                        </tr>
                    </table>
		</form>
	</body>
</html>