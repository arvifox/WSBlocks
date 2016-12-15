package com.arvifox.BlockManager;

/**
 * Created by Andrey on 09.12.2016.
 */
public class BlockManagerFactory {
    private static BlockManagerFactory ourInstance = new BlockManagerFactory();

    public static BlockManagerFactory getInstance() {
        return ourInstance;
    }

    public IBlockManager getManager() {
        return new BlockManager();
    }

    private BlockManagerFactory() {
    }
}
