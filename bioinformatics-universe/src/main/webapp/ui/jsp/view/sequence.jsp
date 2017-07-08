<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="jumbotron backgroundtheme">
    <div class="container">
			<div>Description</div>
	</div>
</div>

<div class="container-fluid">
	<div>
		<c:if test="${message} != ''">
			<h2>
				<c:out value="${message}"></c:out>
			</h2>
		</c:if>
	</div>
		<div class="form-group col-md-12">
			<div class="col-md-2">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul class="nav nav-pills nav-stacked">
							<li><a href="#">Make Unique</a></li>
							<li class="active"><a href="#">Get By Name</a></li>
							<li><a href="#">Extract</a></li>
						</ul>
					</div>	
				</div>

			</div>
			<div class="col-md-10">
				<div class="row">
					<div class="col-md-4 my-textarea">
						<div class="row">
							<textarea class="form-control" placeholder="test" required rows=8
								id="query-field"> </textarea>
						</div>
						<div class="row">
							OR <br /> <label class="btn btn-default"> Choose File <input type="file" style="display: none"
								onchange="$('#upload-file-info').html(this.files[0].name)">
							</label> <span class='label label-info' id="upload-file-info"></span>
						</div>
					</div>
					<div class="col-md-4 my-textarea">
						<div class="row">
							<textarea class="form-control" placeholder="test" required rows=8 id="query-field"> </textarea>
						</div>
						<div class="row">
							OR <br /> <label class="btn btn-default"> Choose File <input type="file" style="display: none"
								onchange="$('#upload-file-info').html(this.files[0].name)">
							</label> <span class='label label-info' id="upload-file-info"></span>
						</div>
						<div class="row">
							<button class="btn btn-primary btn-md pull-right" type="button" id="Go">
								<span >Go!</span>
							</button>
						</div>
					</div>
					<div class="col-md-2 panel panel-default">
						<div class="panel-body">
							<p class="text-primary">Set options: </p>
							<ul class="nav nav-pills nav-stacked">
								<li> 
									<div class="form-group">
										<small>First file delimiter:</small>
										<select class="form-control input-sm" id="sel1">
											<option>tab</option>
											<option>comma</option>
											<option>semicolon</option>
											<option>vertical bar</option>
										</select>
										<small>First file column:</small>
										<input type="text" class="form-control input-sm" id="usr">
									</div>
								</li>
								<li>
									<div class="form-group">
										<small>Second file delimiter:</small> 
										<select class="form-control input-sm" id="sel2">
											<option>tab</option>
											<option>comma</option>
											<option>semicolon</option>
											<option>vertical bar</option>
										</select>
										<small>Second file column:</small>
										<input type="text" class="form-control input-sm" id="usr">
									</div>
								</li>
							</ul>
						</div>	
					</div>
				</div>

			</div>
		</div>


	<div>
		<form method="POST" enctype="multipart/form-data" action="/sequence">
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