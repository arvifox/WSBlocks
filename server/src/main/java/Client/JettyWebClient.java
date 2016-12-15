package Client;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrey on 14.12.2016.
 */
@WebSocket
public class JettyWebClient {
    CountDownLatch latch = new CountDownLatch(1);
    private Session s;

    @OnWebSocketMessage
    public void onText(Session ses, String me) throws IOException {
        System.out.println(me + " reseived from " + ses.getRemoteAddress().getHostName());
    }

    @OnWebSocketConnect
    public void onConnect(Session ses) {
        System.out.println("ses = [" + ses.getRemoteAddress().getHostName() + "]");
        s = ses;
        latch.countDown();
    }

    public void send(String str) throws IOException {
        s.getRemote().sendString(str);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
