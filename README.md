appengine-imaging
=================

Imaging library compatible with Google App Engine. This is mainly a repackaging 
of Apache Harmony AWT classes along with Commons Imaging (formerly Sanselan)
for image I/O. It provides an API similar enough to `java.awt.BufferedImage`
and kin to be a drop-in replacement for common operations. For example, drawing
shapes with `Graphics2D` and executing `ConvolveOp`s. Using the App Engine
[Images Service](https://developers.google.com/appengine/docs/java/images/),
you can read and write PNG, JPEG, and GIF images to and from `BufferedImage`
objects.

Note that there are major gaps. To repeat the disclaimer from 
upstream [appengine-awt](https://code.google.com/p/appengine-awt/):

> PLEASE KEEP IN MIND: THIS SOFTWARE IS EXPERIMENTAL
>
> That means that it will probably not work for you! You are welcome to try, 
> but there will be no support provided at this time. If you would like to help 
> with development, please do!

Why this fork?
--------------

This fork of that code removes a lot of the windowing-related sources and 
fixes some other things in that project so that the sources compile. The code is
kind of schizophrenic in deciding sometimes to copy in Harmony classes, 
sometimes to stub out interface implementations with 
`UnsupportedOperationException`s, and sometimes to just comment out large
blocks of code and standard API functions. 

Gaps and Defects
----------------

The major gaps and defects include:
 * any kind of font support, so you can't use `Graphics.drawString`
 * limited image format support:
   * read/write PNG, JPEG, and GIF
   * read (but not write) WEBP, ICO, BMP (by using the **Images** service)

Quick Start
-----------
Download and build in Eclipse. Create a jar with the `.class` files and put it
in your App Engine project's `WEB-INF/lib` directory. Pattern your image
input/output code after the unit tests or the demo servlet. Example: 

		ServiceImageIO io = new ServiceImageIO(getImagesService());
		BufferedImage image = io.read(inputBytes);
		image = modifyImage(image);
		byte[] outputBytes = io.write(image, ImageInfo.Format.JPEG);
		httpServletResponse.setContentType("image/jpeg");
		httpServletResponse.getOutputStream().write(outputBytes);
