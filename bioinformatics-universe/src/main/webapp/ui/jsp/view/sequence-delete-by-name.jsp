<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div style="display: none">
	<span id="subnavigation-tab">${subnavigationTab}</span>
</div>

<div class="jumbotron backgroundtheme">
    <div class="container">
			<div></div>
	</div>
</div>

<div class="container-fluid">
	<div class="form-group col-md-12">
		<%@ include file="sequence-navbar.jsp"%>
		<div class="col-md-10">
			<div class="row">
				<div class="col-md-4 my-textarea">
					<div class="row">
						<textarea id="first-file-area" class="form-control" placeholder="Paste names" required rows=8> </textarea>
					</div>
					<div class="row">
						OR <br />
						<label class="btn btn-default">
						Choose File <input id="first-file" type="file" style="display: none">
						</label>
						<span class='label label-info' id="first-file-info"></span>
					</div>
				</div>
				<div class="col-md-4 my-textarea">
					<div class="row">
						<textarea id="second-file-area" class="form-control" placeholder="Paste sequences" required rows=8> </textarea>
					</div>
					<div class="row">
						OR <br />
						<label class="btn btn-default">
						Choose File <input id="second-file" type="file" style="display: none">
						</label> <span class='label label-info' id="second-file-info"></span>
					</div>
					<div class="row">
						<button id="Go" class="btn btn-primary btn-md pull-right" type="button">
							<span>Go!</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
  		<div class="col-md-12 text-center result-container">
			<a id="results-load" class="btn btn-info btn-lg" href="#">
				Download Results <span class="glyphicon glyphicon-download-alt"> </span>
			</a>
		</div>
	</div>



</div>
<script src="${pageContext.request.contextPath}/ui/jsscripts/sequence.js"></script>