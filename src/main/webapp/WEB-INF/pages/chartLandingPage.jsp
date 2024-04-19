<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>analysis</title>
</head>
<body>
<div class="container">

<h4 id="matchTitle"></h4>

<!-- style="position: relative; height:100vh;" -->
<div id="cricketOddsChartContainer" data-matchCode=${matchCode}></div>

</div>
<tags:scripts />
</body>
</html>