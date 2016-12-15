package com.arvifox.BlockManager;

import com.arvifox.Block.BlockData;

import java.io.File;

/**
 * Created by Andrey on 09.12.2016.
 */
public interface IReader {
    void startRead();
    void setString(String string);
    void setFile(File file);
    void setOnBlockReadListener(OnBlockReadListener onBlockReadListener);
    interface OnBlockReadListener {
        void onBlockRead(BlockData bd);
    }
}
