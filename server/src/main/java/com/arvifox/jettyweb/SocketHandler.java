package com.arvifox.jettyweb;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Andrey on 14.12.2016.
 */
@WebServlet(urlPatterns = "/myjetty")
public class SocketHandler extends WebSocketHandler {
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.register(MySocket.class);
    }
}
