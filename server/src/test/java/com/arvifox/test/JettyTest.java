package com.arvifox.test;

import Client.JettyWebClient;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;

import java.net.URI;

/**
 * Created by Andrey on 14.12.2016.
 */
public class JettyTest {
    @Test
    public void jtest1() {
        String dest = "ws://localhost:8098/myjetty";
        WebSocketClient client = new WebSocketClient();
        try {
            JettyWebClient jet = new JettyWebClient();
            client.start();
            URI uri = new URI(dest);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(jet, uri, request);
            jet.getLatch().await();
            jet.send("trata");
            jet.send("opaopa");
            Thread.sleep(100001);
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
