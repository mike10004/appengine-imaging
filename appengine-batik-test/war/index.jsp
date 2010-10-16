<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Test of Apache Batik on Google AppEngine</title>
  </head>

  <body>
  
	<%
	BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
	%>
    <h1>Test of Apache Batik on Google AppEngine</h1>
	
	<h2>Select an svg file to upload:</h2>
    <form action="<%= bs.createUploadUrl("/svgUpload") %>" method="post" enctype="multipart/form-data">
        <input type="file" name="aFile">
        <input type="submit" value="Submit">
    </form>

	<%
	String imgParam = request.getParameter("img");
	if (imgParam != null) {
	%>
		<h2>Your svg file transcoded to png on Google AppEngine:</h2>
		<img src="/serve?img=<%= imgParam %>" />
	<%
	}
	%>

	<%
	String errorParam = request.getParameter("error");
	if (errorParam != null) {
	%>
		<h2>Well crap... An error occurred while transcoding your svg file</h2>
	<%
	}
	%>

  </body>
</html>
