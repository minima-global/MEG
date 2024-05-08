package com.minima.meg.endpoints.wallet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.server.BasicPage;

public class walletpage extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h1>WALLET API</h1>\r\n"
				+ "	\r\n"
				+ "	The Wallet API allows you to perform simple Wallet functionality for any number of Users. This does \r\n"
				+ "	NOT access the main Wallet of the Minima node.<br>\r\n"
				+ "	<br>\r\n"
				+ "	You MUST be running Minima with -megammr for this to work.<br>\r\n"
				+ "	<br>\r\n"
				+ "	Each API Endpoint requires certain parameters. You can send as a POST or GET request.<br>\r\n"
				+ "	<br>\r\n"
				+ "	\r\n"
				+ "	<h3>/wallet/create</h3>\r\n"
				+ "	Creates a fresh NEW Wallet with random seed phrase<br>\r\n"
				+ "	<br>\r\n"
				+ "	Required Params : none\r\n"
				+ "	\r\n"
				+ "	<h3>/wallet/seedphrase</h3>\r\n"
				+ "	Creates a Wallet from a given seed phrase<br>\r\n"
				+ "	<br>\r\n"
				+ "	Required Params : seedphrase\r\n"
				+ "	\r\n"
				+ "	<h3>/wallet/balance</h3>\r\n"
				+ "	Get the balance of an address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;address : Address to check\r\n"
				+ "	\r\n"
				+ "	<h3>/wallet/send</h3>\r\n"
				+ "	Send an amount from an address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : The TokenID of the token (defaults to Minima)<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toaddress : Address to send to<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;fromaddress : Address to send from<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;privatekey : Private Key of the FROM Address<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;script : The script of the FROM Address <br>\r\n"
				+ "	");
	}
}
