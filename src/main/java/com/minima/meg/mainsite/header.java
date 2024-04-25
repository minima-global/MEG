package com.minima.meg.mainsite;

import java.io.PrintWriter;

public class header {

	public static void writeHeader(PrintWriter zOut) {
		zOut.write("<html>"
				+ "<head>"
				+ "<title>MEG</title>\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<link rel=\"stylesheet\" href=\"style.css\">"
				+ "</head>"
				+ "<body>");
	}
}
