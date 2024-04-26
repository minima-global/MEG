package com.minima.meg.mainsite;

import java.io.PrintWriter;

public class header {

	public static void writeHeader(PrintWriter zOut) {
		zOut.write("<html>"
				+ "<head>"
				+ "	<title>MEG</title>"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "	<link rel=\"stylesheet\" href=\"style.css\">"
				+ "</head>"
				+ ""
				+ "<body>"
				+ ""
				+ "<table class=maintable>"
				+ ""
				+ "<tr>"
				+ "	<td colspan=2>"
				+ "		<table class=titletable>"
				+ "			<tr>"
				+ "				<td width=100% nowrap>&nbsp;<b>Minima Enterprise Gateway</b></td>"
				+ "				<td nowrap><a class=menu href='logoff.html'>Log Off</a>&nbsp;&nbsp;</td>"
				+ "			</tr>"
				+ "		</table>"
				+ "	</td>"
				+ "</tr>"
				+ ""
				+ "<tr>"
				+ "	<td height=100% style=\"vertical-align:top;\">"
				+ "		<br>"
				+ "		<table class=menutable>"
				+ "			<tr><td nowrap>&nbsp;<b>MAIN MENU</b>&nbsp;</td></tr>"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='triggers.html'>Triggers</a>&nbsp;</td></tr>"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='apiendpoints.html'>API Endpoints</a>&nbsp;</td></tr>"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='minimanode.html'>Minima Node</td>&nbsp;</a></tr>"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='admin.html'>Admin</td></a>&nbsp;</tr>"
				+ "		</table>"
				+ "		"
				+ "	</td>"
				+ "	"
				+ "	<td width=100% style=\"vertical-align:top;\">"
				+ "");
	}
}
