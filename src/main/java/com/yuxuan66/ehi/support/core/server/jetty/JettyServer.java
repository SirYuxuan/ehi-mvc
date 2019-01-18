package com.yuxuan66.ehi.support.core.server.jetty;

import com.yuxuan66.ehi.support.core.AppContext;
import com.yuxuan66.ehi.support.core.config.Configuration;
import com.yuxuan66.ehi.support.core.handler.ActionHandler;
import com.yuxuan66.ehi.support.core.server.IServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 11:05
 */
@Slf4j
public class JettyServer implements IServer {
    @Override
    public void run() {
        Configuration configuration = AppContext.getInstance().getConfiguration();
        Server server = new Server();
        //JVM Stop And Jetty Stop
        server.setStopAtShutdown(true);
        // HTTP connector
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(configuration.getProt());
        http.setIdleTimeout(30000);
        // 设置 connector
        server.addConnector(http);
        // 静态资源处理的handler
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(configuration.getStaticPath());
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[]{resourceHandler,new ActionHandler() });
        server.setHandler(list);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        log.info("JettyServer Start Port:" + configuration.getProt());
    }
}
