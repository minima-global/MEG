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
				+ "		<table class=titletable width=100%>\r\n"
				+ "			<tr>\r\n"
				+ "				<td nowrap style=\"text-align:center;\">\r\n"
				+ "					<span class=visitustext>Visit us at </span>\r\n"
				+ "					<a class=menu target=\"_blank\" href='https://minima.global'>https://minima.global</a>\r\n"
				+ "				</td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		\r\n"
				+ "	</td>\r\n"
				+ "</tr>\r\n"
				+ "\r\n"
				+ "</table>\r\n"
				+ "\r\n"
				+ "</center>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>");
	}
}
