package com.arvifox.jettyweb;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by Andrey on 14.12.2016.
 */
@WebSocket
public class MySocket {
    private Session session;

    @OnWebSocketConnect
    public void onConnect(Session ses) {
        System.out.println("Connect:" + ses.getRemoteAddress().getAddress());
//        System.out.println("Connect:" + ses.toString());
//        System.out.println("Connect:" + ses.getRemoteAddress().getHostName());
//        System.out.println("Connect:" + ses.getRemoteAddress().toString());
        System.out.println("Connect:" + ses.getRemoteAddress().getPort());
//        System.out.println("Connect:" + ses.getRemoteAddress().getAddress().getCanonicalHostName());
        session = ses;
//            session.getRemote().sendString("Got your");
    }

    @OnWebSocketMessage
    public void onText(Session se, String message) {
        System.out.println("text: " + message);
        try {
//            this.session.getRemote().sendString(message);
            CalcThread calcThread = new CalcThread();
            calcThread.setS(se);
            calcThread.setStr(message);
            Thread thr = new Thread(calcThread);
            thr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(Session se, int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }
}
