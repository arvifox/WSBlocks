package com.arvifox.BlockManager;

import com.arvifox.SingleBlock.SingleBlockResult;

import java.io.File;

/**
 * Created by Andrey on 09.12.2016.
 */
public interface IWriter {
    void setString(StringBuffer s);
    void setFile(File f);
    void doWrite(SingleBlockResult sbr);
    void prepareToWrite();
    void finishWrite();
}
