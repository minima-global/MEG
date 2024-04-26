package com.minima.meg.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.minima.meg.endpoints.admin;
import com.minima.meg.endpoints.login;
import com.minima.meg.endpoints.logoff;

public class JettyServer {
	
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
        connector.setPort(8080);
        server.addConnector(connector);

        //Create a session handler
        SessionHandler seshhandler = new SessionHandler();
        
        //Servlet Handler
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.setHandler(seshhandler);
        
        //Add all the handlers
        servletHandler.addServletWithMapping(DefaultLoader.class, "/");
        servletHandler.addServletWithMapping(login.class, "/login.html");
        servletHandler.addServletWithMapping(logoff.class, "/logoff.html");
        
        servletHandler.addServletWithMapping(admin.class, "/admin.html");
        
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
