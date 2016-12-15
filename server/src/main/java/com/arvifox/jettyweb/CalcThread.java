package com.arvifox.jettyweb;

import com.arvifox.BlockManager.BlockDataType;
import com.arvifox.BlockManager.BlockManagerFactory;
import com.arvifox.BlockManager.IBlockManager;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

/**
 * Created by Andrey on 15.12.2016.
 */
public class CalcThread implements Runnable {
    private Session s;
    private String str;
    private StringBuffer sbuf;

    public CalcThread() {
        sbuf = new StringBuffer();
    }

    public void setS(Session s) {
        this.s = s;
    }

    public void setStr(String str) {
        this.str = str;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        IBlockManager bm = BlockManagerFactory.getInstance().getManager();
        bm.setInput(str, BlockDataType.XML);
        bm.setOutput(sbuf, BlockDataType.XML);
        bm.calcBlock();
        try {
            s.getRemote().sendString(sbuf.toString());
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
