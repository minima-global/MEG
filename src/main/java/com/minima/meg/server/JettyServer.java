package com.minima.meg.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.minima.meg.endpoints.help;
import com.minima.meg.endpoints.login;
import com.minima.meg.endpoints.logoff;
import com.minima.meg.endpoints.logs;
import com.minima.meg.endpoints.testpost;
import com.minima.meg.endpoints.administrator.admin;
import com.minima.meg.endpoints.administrator.newuser;
import com.minima.meg.endpoints.administrator.removeuser;
import com.minima.meg.endpoints.api.apiendpoints;
import com.minima.meg.endpoints.api.newendpoint;
import com.minima.meg.endpoints.api.removeendpoint;
import com.minima.meg.endpoints.api.userapi;
import com.minima.meg.endpoints.node.minimanode;
import com.minima.meg.endpoints.node.setnode;
import com.minima.meg.endpoints.profile.myprofile;
import com.minima.meg.endpoints.profile.updatepassword;
import com.minima.meg.endpoints.trigger.newtrigger;
import com.minima.meg.endpoints.trigger.removetrigger;
import com.minima.meg.endpoints.trigger.triggers;
import com.minima.meg.endpoints.trigger.webhook;
import com.minima.meg.endpoints.wallet.walletapi;
import com.minima.meg.endpoints.wallet.walletpage;

public class JettyServer {
	
	public static final int MEG_PORT = 8080;
	
	private Server server;

    public void start() throws Exception {
    	
    	int maxThreads = 20;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        //Create the Server
        server = new Server(threadPool);
        
        //Session Manager
        DefaultSessionIdManager idmanager = new DefaultSessionIdManager(server);
        server.setSessionIdManager(idmanager);
        
        //Create  Connector on a port
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(MEG_PORT);
        server.addConnector(connector);

        //Create a session handler
        SessionHandler seshhandler = new SessionHandler();
        
        //Servlet Handler
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.setHandler(seshhandler);
        
        //Add all the handlers
        servletHandler.addServletWithMapping(userapi.class, "/api/*");
        servletHandler.addServletWithMapping(walletapi.class, "/wallet/*");
        
        servletHandler.addServletWithMapping(DefaultLoader.class, "/");
        servletHandler.addServletWithMapping(login.class, "/login.html");
        servletHandler.addServletWithMapping(logoff.class, "/logoff.html");
        
        servletHandler.addServletWithMapping(admin.class, "/admin.html");
        servletHandler.addServletWithMapping(newuser.class, "/newuser.html");
        servletHandler.addServletWithMapping(removeuser.class, "/removeuser.html");
        
        servletHandler.addServletWithMapping(apiendpoints.class, "/apiendpoints.html");
        servletHandler.addServletWithMapping(newendpoint.class, "/newendpoint.html");
        servletHandler.addServletWithMapping(removeendpoint.class, "/removeendpoint.html");
        
        servletHandler.addServletWithMapping(triggers.class, "/triggers.html");
        servletHandler.addServletWithMapping(newtrigger.class, "/newtrigger.html");
        servletHandler.addServletWithMapping(removetrigger.class, "/removetrigger.html");
        
        servletHandler.addServletWithMapping(help.class, "/help.html");
        
        servletHandler.addServletWithMapping(minimanode.class, "/minimanode.html");
        servletHandler.addServletWithMapping(setnode.class, "/setnode.html");
        
        servletHandler.addServletWithMapping(myprofile.class, "/myprofile.html");
        servletHandler.addServletWithMapping(updatepassword.class, "/updatepassword.html");
        
        servletHandler.addServletWithMapping(walletpage.class, "/wallet.html");
        
        servletHandler.addServletWithMapping(logs.class, "/logs.html");
        
        servletHandler.addServletWithMapping(webhook.class, "/webhook");
        
        servletHandler.addServletWithMapping(testpost.class, "/testpost");
        
        
        //Set Servlet handler to Server
        server.setHandler(servletHandler);

        //Start the server
        server.start();
        
        servletHandler.initialize();
    }
    
    public void stop() throws Exception {
        server.stop();
    }
}
