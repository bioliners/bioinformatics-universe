<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
      	<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/sequence-manipulation">Sequence</a></li>
        <li><a href="${pageContext.request.contextPath}/domain">Domain</a></li>
        <li><a href="${pageContext.request.contextPath}/phylogeny">Phylogeny</a></li>
        <li><a href="${pageContext.request.contextPath}/blast">Blast </a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Help</a></li>
        <li><a href="#">About</a></li>
      </ul>
    </div>
  </div>
  <div class="bottom-nav-bar"></div>
</nav>

<div class="jumbotron backgroundtheme">
    <div class="container">
		<div>
			<div th:if="${message}">
				<h2 th:text="${message}" />
			</div>
			<div>
			    <div class="form-group col-md-5">
	            	<textarea class="form-control" placeholder ="test" required rows=8 id="query-field"> </textarea> 
		        	<button class="btn btn-primary btn-md" type="button" id="make-unique">
		            	<span class="make-unique">Make Unique</span>
		        	</button>
		        	<button class="btn btn-primary btn-md" type="button" id="get-by-name">
		            	<span class="get-by-name">Get by name</span>
		        	</button>
		        	<button class="btn btn-primary btn-md" type="button" id="extract">
		            	<span class="extract">Extract</span>
		        	</button>
            	</div>
			</div>
			
			<div class="row">
				<div class="col-md-5">
				OR
					<label class="btn btn-default">
    				Choose File <input type="file" style="display:none" onchange="$('#upload-file-info').html(this.files[0].name)">
					</label>
					<span class='label label-info' id="upload-file-info"></span>
				</div>
			</div>

			<div>
				<form method="POST" enctype="multipart/form-data" action="/sequence-manipulation">
					<table>
						<tr>
							<td>File to upload:</td>
							<td><input type="file" name="file" /></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Upload" /></td>
						</tr>
					</table>
				</form>
			</div>

			<div>
				<ul>
					<c:forEach items="${files}" var="file">
						<li><a href="${file}">${file}</a></li>
					</c:forEach>
				</ul>
			</div>

		</div>
	</div>
</div>

