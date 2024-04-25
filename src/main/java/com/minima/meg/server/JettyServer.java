package com.minima.meg.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.minima.meg.endpoints.basic;
import com.minima.meg.endpoints.home;
import com.minima.meg.endpoints.login;

public class JettyServer {
	
	private Server server;

    public void start() throws Exception {
    	
    	int maxThreads = 20;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        //Add all the handlers
        servletHandler.addServletWithMapping(basic.class, "/");
        servletHandler.addServletWithMapping(login.class, "/login.html");
        servletHandler.addServletWithMapping(home.class, "/home.html");
        
        server.start();
        
        servletHandler.initialize();
    }
    
    public void stop() throws Exception {
        server.stop();
    }
}
