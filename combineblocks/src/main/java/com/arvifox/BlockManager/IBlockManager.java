package com.arvifox.BlockManager;

import java.io.File;

/**
 * Created by Andrey on 09.12.2016.
 */
public interface IBlockManager {
    void setInput(File file, BlockDataType bdt);
    void setInput(String s, BlockDataType bdt);
    boolean calcBlock();
    void setOutput(File file, BlockDataType bdt);
    void setOutput(StringBuffer s, BlockDataType bdt);
}
