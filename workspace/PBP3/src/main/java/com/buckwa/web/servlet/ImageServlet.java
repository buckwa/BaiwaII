package com.buckwa.web.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {

	private ServletContext context;

	public ImageServlet() {
	}

	public void init(ServletConfig servletconfig) throws ServletException {
		super.init(servletconfig);
		context = servletconfig.getServletContext();
	}

	public void doGet(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws ServletException,
			IOException {
		doPost(httpservletrequest, httpservletresponse);
	}

	public void doPost(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws ServletException,
			IOException {
		Object obj = null;
		String s = null;
		String s1 = null;
		if ((s = httpservletrequest.getQueryString()) == null) {
			s = getInitParameter("file");
		} else {
			s = decode(s);
		}

		if (s == null) {
			httpservletresponse.setContentType("text/html");
			PrintWriter printwriter = httpservletresponse.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br>Could not get file name ");
			printwriter
					.println("<br><br><br>&copy; <a href=\"mailto:chawean@gmail.com\">Coldbeans</a> ver. 1.5");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
			return;
		}
		if ((s1 = getInitParameter("dir")) != null) {
			String s2 = System.getProperty("file.separator");
			if (!s1.endsWith(s2) && !s1.endsWith("/")) {
				s1 = (new StringBuilder()).append(s1).append(s2).toString();
			}
			if (!s.startsWith(s1)) {
				s = (new StringBuilder()).append(s1).append(s).toString();
			}
		}
		File file = lookupFile(s);
		if (file == null) {
			httpservletresponse.setContentType("text/html");
			PrintWriter printwriter1 = httpservletresponse.getWriter();
			printwriter1.println("<html>");
			printwriter1.println((new StringBuilder())
					.append("<br><br><br>Could not read file ").append(s)
					.toString());
			printwriter1
					.println("<br><br><br>&copy; <a href=\"mailto:chawean@gmail.com\">Coldbeans</a> ver. 1.5");
			printwriter1.println("</html>");
			printwriter1.flush();
			printwriter1.close();
			return;
		}
		if (!file.exists() || !file.canRead()) {
			httpservletresponse.setContentType("text/html");
			PrintWriter printwriter2 = httpservletresponse.getWriter();
			printwriter2.println("<html><font face=\"Arial\">");
			printwriter2.println((new StringBuilder())
					.append("<br><br><br>Could not read file ").append(s)
					.toString());
			printwriter2.print("<br>Reasons are: ");
			if (!file.exists()) {
				printwriter2.println("file does not exist");

			} else {
				printwriter2.println("file is not readable at this moment");
			}
			printwriter2
					.println("<br><br><br>&copy; <a href=\"mailto:chawean@gmail.com\">Coldbeans</a> ver. 1.5");
			printwriter2.println("</font></html>");
			printwriter2.flush();
			printwriter2.close();
			return;
		}
		httpservletresponse.setContentType(getType(s));
		httpservletresponse.setContentLength((int) file.length());
		String s3 = getInitParameter("expires");
		if (s3 != null) {
			long l = -1L;
			try {
				l = Long.parseLong(s3);
			} catch (Exception exception) {
				l = -1L;
			}
			if (l > 0L) {
				Date date = new Date();
				httpservletresponse.setDateHeader("Expires", date.getTime() + l
						* 1000L);
				httpservletresponse.setHeader("Cache-Control",
						(new StringBuilder()).append("max-age=").append(l)
								.toString());
			}
		}
		javax.servlet.ServletOutputStream servletoutputstream = httpservletresponse
				.getOutputStream();
		dumpFile(s, servletoutputstream);
		servletoutputstream.flush();
		servletoutputstream.close();
	}

	private void dumpFile(String s, OutputStream outputstream) {
		String s1 = s;
		byte abyte0[] = new byte[4096];
		try {
			BufferedInputStream bufferedinputstream = new BufferedInputStream(
					new FileInputStream(lookupFile(s1)));
			int i;
			while ((i = bufferedinputstream.read(abyte0, 0, 4096)) != -1)
				outputstream.write(abyte0, 0, i);
			bufferedinputstream.close();
		} catch (Exception exception) {
		}
	}

	private String getType(String s) {
		int i = s.lastIndexOf(".");
		if (i > 0 || i < s.length() - 1) {
			return getMime(s.substring(i + 1));
		} else {
			return "application/octet-stream";
		}
	}

	private String getMime(String s) {
		String s1 = s.toUpperCase();
		if (s1.equals("GIF"))
			return "image/gif";
		if (s1.equals("JPG") || s1.equals("JPEG") || s1.equals("JPE"))
			return "image/jpeg";
		if (s1.startsWith("TIF"))
			return "image/tiff";
		if (s1.startsWith("PNG"))
			return "image/png";
		if (s1.equals("IEF"))
			return "image/ief";
		if (s1.equals("BMP"))
			return "image/bmp";
		if (s1.equals("RAS"))
			return "image/x-cmu-raster";
		if (s1.equals("PNM"))
			return "image/x-portable-anymap";
		if (s1.equals("PBM"))
			return "image/x-portable-bitmap";
		if (s1.equals("PGM"))
			return "image/x-portable-graymap";
		if (s1.equals("PPM"))
			return "image/x-portable-pixmap";
		if (s1.equals("RGB"))
			return "image/x-rgb";
		if (s1.equals("XBM"))
			return "image/x-xbitmap";
		if (s1.equals("XPM"))
			return "image/x-xpixmap";
		if (s1.equals("XWD"))
			return "image/x-xwindowdump";
		else
			return "application/octet-stream";
	}

	private String decode(String s) {
		StringBuffer stringbuffer = new StringBuffer(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '+') {
				stringbuffer.append(' ');
				continue;
			}
			if (c == '%') {
				char c1 = '\0';
				for (int j = 0; j < 2; j++) {
					c1 *= '\020';
					c = s.charAt(++i);
					if (c >= '0' && c <= '9') {
						c1 += c - 48;
						continue;
					}
					if ((c < 'A' || c > 'F') && (c < 'a' || c > 'f'))
						break;
					switch (c) {
					case 65: // 'A'
					case 97: // 'a'
						c1 += '\n';
						break;

					case 66: // 'B'
					case 98: // 'b'
						c1 += '\013';
						break;

					case 67: // 'C'
					case 99: // 'c'
						c1 += '\f';
						break;

					case 68: // 'D'
					case 100: // 'd'
						c1 += '\r';
						break;

					case 69: // 'E'
					case 101: // 'e'
						c1 += '\016';
						break;

					case 70: // 'F'
					case 102: // 'f'
						c1 += '\017';
						break;
					}
				}

				stringbuffer.append(c1);
			} else {
				stringbuffer.append(c);
			}
		}

		return stringbuffer.toString();
	}

	public String getServletInfo() {
		return "Usstreet get Image Servlet";
	}

	private File lookupFile(String s) {
		File file = new File(s);
		return file.isAbsolute() ? file : new File(context.getRealPath("/"), s);
	}

}