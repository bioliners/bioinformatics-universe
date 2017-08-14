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
		<%@ include file="evolution-navbar.jsp"%>
		<div class="col-md-10">
			<div class="row">
				<div class="col-md-8 my-textarea">
					<div class="row">
						<label class="btn btn-default btn-lg">
						Choose Files <input id="first-file" type="file" style="display: none" multiple>
						</label>
						<span class='label label-info' id="first-file-info"></span>
					</div>
					<div class="row">
						<button id="Go" class="btn btn-primary btn-md pull-right" type="button">
							<span>Go!</span>
						</button>
					</div>
				</div>
				<div class="col-md-2 panel panel-default">
					<div class="panel-body">
						<p class="text-primary">Set options:</p>
						<div class="form-group">
							<form id="first">
								<small>File delimiter:</small> 
								<select id="first-delim" class="form-control input-sm">
									<option selected='selected' disabled>select</option>
									<option>tab</option>
									<option>comma</option>
									<option>semicolon</option>
									<option>vertical bar</option>
								</select>
								<small>File column:</small>
								<input id="first-col" name="first-col" type="text" class="form-control input-sm">
								<small>Identity threshold:</small>
								<input id="identity-threshold" name="identity-threshold" type="text" class="form-control input-sm">
								<small>Coverage threshold:</small>
								<input id="coverage-threshold" name="coverage-threshold" type="text" class="form-control input-sm">
								<small>E-value threshold:</small>
								<input id="evalue-threshold" name="evalue-threshold" type="text" class="form-control input-sm">
							</form>
						</div>
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



