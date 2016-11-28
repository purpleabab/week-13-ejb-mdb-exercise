<%--
  Created by IntelliJ IDEA.
  User: paulawaite
  Date: 11/16/15
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--I was running into an issue in which EL was not evaluated for some reason,
the following line resolved the issue--%>

<%@ page isELIgnored="false" %>
<html>
<head>
    <title>MDB Exercise</title>
</head>
<body>
<h2>Send a message to the TestMessageBean<h2>
    <form action="testMessageBean" method="get">
        <input type="text" id="message" name="message">
        <button type="submit">Send</button>
    </form>
    <br />
    ${status}
</body>
</html>