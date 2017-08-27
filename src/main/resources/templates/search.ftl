<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>IT之家-热评搜索</title>
<link rel="stylesheet" href="/css/search.css"/>
</head>

<body>
<div class="center-wrapper">
	<div class="center-block">
        <div class="center-up">
            <div class="logo-div"><img alt="IT之家logo" src="/img/ithome.logo.png"/></div>
            <span class="app-desc">热评</span>
        </div>
        <div class="center-down">
        	<input type="text" class="keyword-input"/><a class="search-btn" href="javascript:void(0);">搜索</a>
        </div>
        <ul class="rank-list">
            <#list comments as comment>
                <li>
                    <div class="comment-top"><span class="name">${comment.user}</span><span class="mobile">${comment.mobile}</span><span class="posandtime">${comment.posandtime}</span><a class="article-url" href="${comment.articleUrl}">查看原文</a>
                    <p class="comment">${comment.comment}</p>
                    <div class="comment-bottom"><span class="up">支持(${comment.up})</span><span class="down">反对(${comment.down})</span></div>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
</div>

<script src="/js/jquery-3.2.1.min.js"></script>
<script>
	$(".search-btn").click(function(){
	    var keyword = $(".keyword-input").val();
	    if(keyword.trim() === ""){
	        alert("搜索框现在为空呢");
	        return;
        }
	    var url = "/ithome/search/"+keyword;
	    location.href = url;
    });
</script>
</body>
</html>
