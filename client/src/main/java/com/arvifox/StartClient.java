package com.arvifox;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrey on 15.12.2016.
 */
public class StartClient {
    public static CountDownLatch connectEstablished;
    public static CountDownLatch messageSent;

    public static void main(String[] args) {
        connectEstablished = new CountDownLatch(1);
        messageSent = new CountDownLatch(1);
        String dest = "ws://localhost:8098/myjetty";
        WebSocketClient client = new WebSocketClient();
        try {
            JettyWebClient jet = new JettyWebClient();
            client.start();
            URI uri = new URI(dest);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(jet, uri, request);
            connectEstablished.await();
            jet.send("<Blocks><Block id=\"1\" sizex=\"2\" sizey=\"2\" sizez=\"1\"><Hole x=\"1\" y=\"0\" z=\"0\"/></Block></Blocks>");
            messageSent.await();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
