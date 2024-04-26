package com.minima.meg.mainsite;

import java.io.PrintWriter;

public class footer {
	public static void writeFooter(PrintWriter zOut) {
		zOut.write("</td>\r\n"
				+ "</tr>\r\n"
				+ "\r\n"
				+ "<tr>\r\n"
				+ "	<td colspan=2 nowrap>\r\n"
				+ "	\r\n"
				+ "		<table class=titletable>\r\n"
				+ "			<tr>\r\n"
				+ "				<td nowrap style=\"text-align:center;\">Visit us at <a target=\"_blank\" href='https://minima.global'>https://minima.global</a></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "	</td>\r\n"
				+ "</tr>\r\n"
				+ "\r\n"
				+ "</table>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>");
	}
}
