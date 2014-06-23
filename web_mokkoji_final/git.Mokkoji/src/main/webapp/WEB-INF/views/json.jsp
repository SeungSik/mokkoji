<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Json Test Page</title>
</head>
<body>
<form name="member" id="member" action="${cp }/post" method="post">
	<input type="text" name="id" value="Jung0123"/>
	<input type="text" name="password" value="1q2w3e4r!"/>
	<button type="submit">Post</button>
</form>

<input type="button" id="test" value="JSON"/>

<script src="${cp}/resources/jquery-1.8.2.min.js"></script>
<script src="${cp}/resources/jquery.json.js"></script>
<script>
var cp = '${cp}';

$(document).ready(function(){
	$('#test').bind('click', function(){
		testJson();
	});
});

testJson = function(){
	var url = cp + '/postJson.json',
		json = { 	id : 'kims2014',
					password : '1q2w3e4r!',
					email : 'abcd@naver.com'	};
	
	$.postJSON(url, json, function(member){
		alert(member.id + '\n' + member.password + '\n' + member.email);
	});	
};


</script>

</body>
</html>