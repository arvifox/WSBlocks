package com.arvifox;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrey on 15.12.2016.
 */
public class StartClientFile {
    public static CountDownLatch connectEstablished;
    public static CountDownLatch messageSent;

    public static void main(String[] args) {
        connectEstablished = new CountDownLatch(1);
        messageSent = new CountDownLatch(1);
        String dest = args[0];
        String input = "";
        try {
            input = new String(Files.readAllBytes(Paths.get(args[1])), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebSocketClient client = new WebSocketClient();
        try {
            JettyWebClient jet = new JettyWebClient();
            jet.setFilepath(args[2]);
            client.start();
            URI uri = new URI(dest);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(jet, uri, request);
            connectEstablished.await();
            jet.send(input);
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
