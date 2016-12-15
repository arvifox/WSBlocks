package com.arvifox;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Andrey on 14.12.2016.
 */
@WebSocket
public class JettyWebClient {
    private Session s;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String filepath;

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @OnWebSocketMessage
    public void onText(Session ses, String me) throws IOException {
//        System.out.println(me + " reseived from " + ses.getRemoteAddress().getHostName());
//        File res = new File(URI.create(filepath));
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.write(me.getBytes("UTF-16"));
        fos.flush();
        fos.close();
    }

    @OnWebSocketConnect
    public void onConnect(Session ses) {
        System.out.println("ses = [" + ses.getRemoteAddress().getHostName() + "]");
        s = ses;
        logger.info("connect");
        StartClientFile.connectEstablished.countDown();
    }

    @OnWebSocketClose
    public void onClose(Session se, int statusCode, String reason) {
        logger.info(String.format("Close protocol=%s status=%s reason=%s", se.getProtocolVersion(), statusCode, reason));
        StartClientFile.messageSent.countDown();
    }

    public void send(String str) throws IOException {
        logger.info("sending...");
        s.getRemote().sendString(str);
    }
}
