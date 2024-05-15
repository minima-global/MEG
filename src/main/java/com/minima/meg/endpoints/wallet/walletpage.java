package com.minima.meg.endpoints.wallet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.server.BasicPage;

public class walletpage extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>Wallet API</h2>\r\n"
				+ "	\r\n"
				+ "	The Wallet API allows you to perform simple Wallet functionality for any number of Users.<br>"
				+ " <br>This does NOT access the main Wallet of the Minima node and does not save any "
				+ "of the private keys or data.<br>\r\n"
				+ "	<br>\r\n"
				+ "	You MUST be running Minima with -megammr for this to work.<br>\r\n"
				+ "	<br>\r\n"
				+ "	Each API Endpoint requires certain parameters. You can send as a POST or GET request.<br>\r\n"
				
				+ "	<h2>Basic Online Wallet</h2>\r\n"
				+ "	<h3>/wallet/create</h3>\r\n"
				+ "	Creates a fresh NEW Wallet with a secure random seed phrase<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : none\r\n"
				+ "	<br><br>\r\n"
				+ "	Node status : online / offline\r\n"
				
				+ "	<h3>/wallet/seedphrase</h3>\r\n"
				+ "	Creates a Wallet from a given seed phrase<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>"
				+ "	&nbsp;&nbsp;&nbsp;address : seedphrase\r\n"
				+ "	<br><br>\r\n"
				+ "	Node status : online / offline\r\n"
				
				+ "	<h3>/wallet/balance</h3>\r\n"
				+ "	Get the balance of an address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;address : Address to check<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	<h3>/wallet/send</h3>\r\n"
				+ "	Send an amount from an address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : TokenID of the token (defaults to Minima)<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;amount : Amount to send<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toaddress : Address to send <b>to</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;fromaddress : Address to send <b>from</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;privatekey : Private Key of the FROM Address<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;script : The script of the FROM Address <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;keyuses : The number of times this address has been used (nonce)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				
				+ "	<h3>/wallet/checktxpow</h3>\r\n"
				+ "	Check status of transaction (only works for 12 hours.. then pruned)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;txpowid : TxPoWID of transaction<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				
				+ "	<h3>/wallet/gettxpow</h3>\r\n"
				+ "	Get TxPoW (only works for 12 hours.. then pruned)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;txpowid : TxPoWID of transaction<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	<h2>Advanced Offline Signing Wallet</h2>\r\n"
				+ "	\r\n"
				+ "	<h3>/wallet/unsignedtxn</h3>\r\n"
				+ "	Create an unsigned transaction from a specific address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : TokenID of the token (defaults to Minima)<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;amount : Amount to send<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toaddress : Address to send <b>to</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;fromaddress : Address to send <b>from</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;script : The script of the FROM Address <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	\r\n"
				+ "	<h3>/wallet/signtxn</h3>\r\n"
				+ "	Sign a transaction created with unsignedtxn<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from unsignedtxn <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;privatekey : Private Key of the FROM Address<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;keyuses : The number of times this address has been used (nonce) <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online / offline \r\n"
				
				+ "	<h3>/wallet/posttxn</h3>\r\n"
				+ "	Post a transaction created with signtxn<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from signtxn <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "");
	}
}
