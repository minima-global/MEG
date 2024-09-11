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
				+ "	&nbsp;&nbsp;&nbsp;burn : Transaction Burn / Fee (optional)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				
				+ "	<h3>/wallet/checktxpow</h3>\r\n"
				+ "	Check transaction is on-chain (only works for 24 hours.. then pruned)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;txpowid : TxPoWID of transaction<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				
				+ "	<h3>/wallet/gettxpow</h3>\r\n"
				+ "	Get TxPoW (only works for 48 hours.. then pruned)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;txpowid : TxPoWID of transaction<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	<h2>Advanced Offline Signing Wallet</h2>\r\n"
				
				+ "	\r\n"
				+ "	<h3>/wallet/unsignedtxn</h3>\r\n"
				+ "	Create an unsigned transaction from a specific address. Coin selection is done automatically.<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;amount : Amount to send<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toaddress : Address to send <b>to</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;fromaddress : Address to send <b>from</b><br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;script : The script of the FROM Address <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : TokenID of the token (optional - defaults to Minima)<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;burn : Transaction Burn / Fee (optional)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	\r\n"
				+ "	<h3>/wallet/listcoins</h3>\r\n"
				+ "	List all coins belonging to an address<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;address : Address to check for coins<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : TokenID of the token (optional - defaults to Minima)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"

				+ "	\r\n"
				+ "	<h3>/wallet/constructtxn</h3>\r\n"
				+ "	Low level version of 'unsignedtxn'. Construct a transaction by manually selecting the coins used as inputs<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;coinlist : Comma seperated list of CoinID values<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;script : The script of the coins being used as inputs<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toaddress : Address of first output<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;toamount : Amount of first output<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;changeaddress : Address of second output. Normally this is the coin address used as inputs<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;changeamount : Amount of second output. If 0 second output is not added.<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;tokenid : TokenID of the token (optional - defaults to Minima)<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	\r\n"
				+ "	<h3>/wallet/signtxn</h3>\r\n"
				+ "	Sign a transaction created with unsignedtxn or constructtxn<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from unsignedtxn <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;privatekey : Private Key of the FROM Address<br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;keyuses : The number of times this address has been used (nonce) <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online / offline \r\n"
				
				+ "	<h3>/wallet/posttxn</h3>\r\n"
				+ "	Post a complete transaction created with signtxn<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from signtxn <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online \r\n"
				
				+ "	<h3>/wallet/lockcoins</h3>\r\n"
				+ "	Lock CoinID / Coins used when creating multiple transactions without posting to mempool<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online / offline\r\n"

				+ "	<h3>/wallet/unlockcoins</h3>\r\n"
				+ "	Unlock CoinID / Coins. Use after you have posted the transactions.<br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online / offline\r\n"

				+ "	<h3>/wallet/minetxn</h3>\r\n"
				+ "	Mine a complete transaction created with signtxn and get TxPowID without posting<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from signtxn <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online / offline\r\n"
				
				+ "	<h3>/wallet/postminedtxn</h3>\r\n"
				+ "	Post a pre-mined transaction created with minetxn<br>\r\n"
				+ "	<br>\r\n"
				+ "	Params : <br>\r\n"
				+ "	&nbsp;&nbsp;&nbsp;data : The transaction created from minetxn <br>\r\n"
				+ "	<br>\r\n"
				+ "	Node status : online\r\n"

				+ "");
	}
}
