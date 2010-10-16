package com.google.code.batik.test;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class ImgUploadHandler extends HttpServlet
{
	private static final Logger log = Logger.getLogger(ImgUploadHandler.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, BlobKey> blobs = bs.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("aFile");
		
		log.log(Level.INFO, String.format("Got image blob: %s\n", blobKey));
        
		resp.sendRedirect("/?img=" + blobKey.getKeyString());
	}
}
