package com.google.code.batik.test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class SvgUploadHandler extends HttpServlet
{
	private static final Logger log = Logger.getLogger(SvgUploadHandler.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try {
			BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
			Map<String, BlobKey> blobs = bs.getUploadedBlobs(req);
			BlobKey blob = blobs.get("aFile");
	        
	        log.log(Level.INFO, String.format("Got an svg upload: %s", blob));
			
			// Transcode the svg doc
			BlobstoreInputStream blobStream = new BlobstoreInputStream(blob);
	        TranscoderInput input = new TranscoderInput(blobStream);
	        
	        ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
	        TranscoderOutput output = new TranscoderOutput(imgStream);
	        
	        PNGTranscoder t = new PNGTranscoder();
	        t.transcode(input, output);
	        
	        blobStream = null;
	        input = null;
	        output = null;
	        t = null;
	        
	        log.log(Level.INFO, "Image transcoding complete");
	        
	        // Upload the transcoded image
	        String url = bs.createUploadUrl("/imgUpload");
//	        String url = String.format("http://%s:%d%s",
//	        		req.getServerName(),
//	        		req.getServerPort(),
//	        		bs.createUploadUrl("/imgUpload"));
	        
	        log.log(Level.INFO, String.format("Uploading image to: %s\n", url));
	        
	        String redir = upload(url, imgStream);
	        
	        log.log(Level.INFO, String.format("Image uploader returned: %s\n", redir));
	        
	        resp.sendRedirect(redir != null ? redir : "/?error");
		}
		catch (Exception e) {
			log.log(Level.INFO, e.getMessage());
			resp.sendRedirect("/?error");
		}
	}
	
	public String upload(String urlString, ByteArrayOutputStream dataStream)
	{
		HttpURLConnection conn = null;
		DataOutputStream dos = null;

		String exsistingFileName = "blah.png";

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		try {
			// ------------------ CLIENT REQUEST

			// open a URL connection to the Servlet
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"aFile\";"
					+ " filename=\"" + exsistingFileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			dos.write(dataStream.toByteArray());

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			dos.flush();
			dos.close();

			// ------------------ read the SERVER RESPONSE
	        return conn.getHeaderField("location");

		} catch (MalformedURLException ex) {
			log.log(Level.INFO, "From ServletCom CLIENT REQUEST:" + ex);
		}

		catch (IOException ioe) {
			log.log(Level.INFO, "From ServletCom CLIENT REQUEST:" + ioe);
		}
		return null;
	}
}
