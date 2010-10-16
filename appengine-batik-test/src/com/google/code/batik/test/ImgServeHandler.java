package com.google.code.batik.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class ImgServeHandler extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		String imgParam = req.getParameter("img");
		BlobKey blobKey = new BlobKey(imgParam);
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		bs.serve(blobKey, resp);
	}
}
