<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css"/>
<link rel="stylesheet" href="${ctx }/resource/css/style.css"/>


<title>고지 출력</title>
</head>
<body>
	<div id="contents"></div>
</body>


<script>
window.onload = ()=>{
	var errCd =  '${errCode}';
	var errMsg = '${errMsg}';
	var isEmpty = (val)=>{
		if(val==null||val==undefined||val=='')
			return true;

		return false;
	}
	if(!isEmpty(errCd)){
		alert('['+errCd+']'+errMsg);
		return false;
	}



	draw('contents');
	$('.bxslider').bxSlider({
		auto: false, // 자동으로 애니메이션 시작
		speed: 500,  // 애니메이션 속도
		pause: 3000,  // 애니메이션 유지 시간 (1000은 1초)
		mode: 'horizontal', // 슬라이드 모드 ('fade', 'horizontal', 'vertical' 이 있음)
		autoControls: true, // 시작 및 중지버튼 보여짐
		pager: true, // 페이지 표시 보여짐
		captions: true, // 이미지 위에 텍스트를 넣을 수 있음
	});
}


/* ===============
* 데이터 출력
=============== */
var draw = (id) => {
	var Dataset = JSON.parse('${details}');
	Dataset.details.forEach((row)=>{
		switch(row.item_type){
			case "TEXT":
				document.getElementById(id).innerHTML += text(row);
				break;
			case "KEY_VALUE":
				document.getElementById(id).innerHTML += keyvalue(row);
				break;
			case "TABLE":
				document.getElementById(id).innerHTML += table(row);
				break;
			case "IMAGE":
				document.getElementById(id).innerHTML += image(row);
				break;
			case "GRAPH":
				document.getElementById(id).innerHTML += graph(row);
				break;
		}
	});
}

/* ===============
* "Text" Type
=============== */
var text = (item) => {
	//"title" Set
	var subject = document.createElement('h2');
	subject.innerHTML = nvl(item.title, '');

	//"text" Set
	var text = document.createElement('ul');
	item.elements.forEach((row)=>{
		var span = document.createElement('li');
		span.innerHTML = row;
		text.innerHTML += span.outerHTML;
	});

	//<section> Set
	var section = document.createElement('section');
	section.className = 'text';		//섹션 클래스명
	section.innerHTML += isEmpty(subject.innerHTML)?'':subject.outerHTML;
	section.innerHTML += text.outerHTML;


	return section.outerHTML;
}
/* ===============
* "Key-Value" Type
=============== */
var keyvalue = (item) => {
	//"title" Set
	var subject = document.createElement('h2');
	subject.innerHTML = nvl(item.title, '');
	var table = document.createElement('table');

	//<table> Set
	table.innerHTML = createBody(item.elements);

	//<section> Set
	var section = document.createElement('section');
	section.className = 'keyvalue';		//섹션 클래스명
	section.innerHTML += isEmpty(subject.innerHTML)?'':subject.outerHTML;
	section.innerHTML += table.outerHTML;

	//Create <tbody>
	function createBody(arr) {
		var tbody = document.createElement('tbody');
		arr.forEach((row)=>{
			//<td> set - key, value
			var key = document.createElement('td');
			var value = document.createElement('td');
			value.style = 'text-align: right; padding-left: 50px; font-weight: bold;';
			key.innerHTML += row.key;
			value.innerHTML += row.value;
			//<tr> set
			var tr = document.createElement('tr');
			tr.innerHTML += key.outerHTML;
			tr.innerHTML += value.outerHTML;
			//<tbody> append
			tbody.innerHTML += tr.outerHTML;
		});
		return tbody.outerHTML;
	}


	return section.outerHTML;
}
/* ===============
* "Table" Type
=============== */
var table = (item) => {
	//"title" Set
	var subject = document.createElement('h2');
	subject.innerHTML = nvl(item.title, '');

	//<table> Set
	var table = document.createElement('table');
	table.innerHTML += createHead(item.elements.head);
	table.innerHTML += createBody(item.elements.rows);

	//<section> Set
	var section = document.createElement('section');
	section.className = 'table';		//섹션 클래스명
	section.innerHTML += isEmpty(subject.innerHTML)?'':subject.outerHTML;
	section.innerHTML += table.outerHTML;

	//Create <thead>
	function createHead(arr) {
		var arrTxt = [];
		var thead = document.createElement('thead');
		var tr = document.createElement('tr');
		arr.forEach((row)=>{
			var th = document.createElement('th');
			th.style = 'text-align: center;';
			th.innerHTML += row;
			tr.innerHTML += th.outerHTML;
		});
		thead.innerHTML += tr.outerHTML;
		return thead.outerHTML;
	}
	//Create <tbody>
	function createBody(arr) {
		var tbody = document.createElement('tbody');
		arr.forEach((row)=>{
			var tr = document.createElement('tr');
			row.forEach((value)=>{
				var td = document.createElement('td');
				td.style = 'text-align: center;';
				td.innerHTML += value;
				tr.innerHTML += td.outerHTML;
			});
			tbody.innerHTML += tr.outerHTML;
		});
		return tbody.outerHTML;
	}


	return section.outerHTML;
}
/* ===============
* "IMAGE" Type
=============== */
var image = (item) => {
	//"title" Set
	var subject = document.createElement('h2');
	subject.innerHTML = nvl(item.title, '');


	//<img> Set
	var div = document.createElement('div');
	if(item.print_type=='SLIDE')	//print_type이 슬라이드(SLIDE) 이면..
		div.innerHTML += createImgSlide(item.elements);
	else
		div.innerHTML += createImg(item.elements);

	//<section> Set
	var section = document.createElement('section');
	section.className = 'image';	//섹션 클래스명
	section.innerHTML += isEmpty(subject.innerHTML)?'':subject.outerHTML;
	section.innerHTML += div.outerHTML;

	//Create <img> Default
	function createImg(arr) {
		var arrTxt = [];
		arr.forEach((row)=>{
			var img = document.createElement('img');
			img.alt = row.key;
			img.src = row.value;
			arrTxt.push(img.outerHTML);
		});

		return arrTxt.join('<br/>');
	}
	//Create <img> print Slider
	function createImgSlide(arr) {
		var ul = document.createElement('ul');
		ul.className = 'bxslider';
		arr.forEach((row)=>{
			var img = document.createElement('img');
			img.alt = row.key;
			img.src = row.value;
			var a = document.createElement('a');
			a.innerHTML = img.outerHTML;
			var li = document.createElement('li');
			li.innerHTML = a.outerHTML;
			ul.innerHTML += li.outerHTML;
		});

		return ul.outerHTML;
	}

	return section.outerHTML;
}
/* ===============
* "GRAPH" Type(예정)
=============== */
var graph = (item) => {
}




var isEmpty = (val) => {
	if(val==null||val==undefined||val==''||val==[]||val=={})
		return true;
	return false;
}
var nvl = (txt, replaceTxt) => {
	if(txt==null||txt==undefined||txt=='')
		return replaceTxt;
	return txt;
}
</script>








</html>
