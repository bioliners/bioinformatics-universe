<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bu-navbar">
        <span class="sr-only">Toggle Dropdown</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>     
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/home" style="font-size:120%; color:#D8F0E3">Bioinformatics Universe</a>
    </div>
    
    <div class="navbar-collapse" id="bu-navbar">
      <ul class="nav navbar-nav">
      	<li id="home"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li id="sequence"><a href="${pageContext.request.contextPath}/sequence">Sequence</a></li>
        <li id="domain"><a href="${pageContext.request.contextPath}/domain">Domain</a></li>
        <li id="evolution"><a href="${pageContext.request.contextPath}/evolution">Evolution</a></li>
        <li id="blast"><a href="${pageContext.request.contextPath}/blast">Blast </a></li>
        <li id="protocols"><a href="${pageContext.request.contextPath}/protocols">Protocols </a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li id="help"><a href="${pageContext.request.contextPath}/help">Help</a></li>
        <li id="about"><a href="${pageContext.request.contextPath}/about">About</a></li>
      </ul>
    </div>
  </div>
  <div class="bottom-nav-bar"></div>
</nav>

<div style="display: none">
	<span id="tab">${tab}</span>
</div>