<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body {background-color:#66CDAA}
h1 {
    font-size: 40px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page import= "com.project.*"  %>
<%@ page import= "java.io.*"  %>
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<div class="row" align="middle" font size="5"><div class="col-lg-12" font size="10"><h1 align="center"> TRUE OR FAKE : AMAZON REVIEW ANALYSIS </h1></div></div>
<br/>
<br/>
<br/>

<center><form action="Sentiment.jsp">
<input type="submit" value="Sentimental Anaylsis"  style="height:50px; width:200px">
</form><br><br><br><br>
</center>
<center><form action="Text1.jsp">
<input type="submit" value="Text Analysis Part 1" style="height:50px; width:200px">
</form></center>
<br><br><br><br><br>
<center><form action="Text2.jsp">
<input type="submit" value="Text Analysis Part 2" style="height:50px; width:200px">
</form></center>
<br><br><br><br><br>

<center><form action="User.jsp" method = post>
<input type="submit" value="User Review Anaylsis" style="height:50px; width:200px">
</form></center>
<br><br><br><br><br><br>

<center><form action="Final.jsp" method = post>
<input type="submit" value="Final Anaylsis" style="height:50px; width:200px">
</form></center>
<br><br><br><br><br><br>


<p style="color:blue;">Designed by : Aditi Aggarwal , Pooja Sethia , Malvika Singh</p>
</body>
</html>