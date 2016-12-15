package com.arvifox.BlockManager;

import com.arvifox.Block.BlockData;
import com.arvifox.SingleBlock.ISingleBlock;
import com.arvifox.SingleBlock.SingleBlockFactory;
import com.arvifox.SingleBlock.SingleBlockResult;

import java.io.File;

/**
 * Created by Andrey on 09.12.2016.
 */
class BlockManager implements IBlockManager, IReader.OnBlockReadListener, ISingleBlock.OnSingleBlockCalcCompleteListener {

    private IReader reader;
    private IWriter writer;

    BlockManager() {
    }

    @Override
    public void setInput(File file, BlockDataType bdt) {
        if (bdt.equals(BlockDataType.XML)) {
            reader = new XmlDomReader();
            reader.setFile(file);
            reader.setOnBlockReadListener(this);
        }
    }

    @Override
    public void setInput(String s, BlockDataType bdt) {
        if (bdt.equals(BlockDataType.XML)) {
            reader = new XmlDomReader();
            reader.setString(s);
            reader.setOnBlockReadListener(this);
        }
    }

    @Override
    public boolean calcBlock() {
        writer.prepareToWrite();
        reader.startRead();
        // ждать завершения

        writer.finishWrite();
        return false;
    }

    @Override
    public void setOutput(File file, BlockDataType bdt) {
        if (bdt == BlockDataType.XML) {
            writer = new XmlDomWriter();
            writer.setFile(file);
        }
    }

    @Override
    public void setOutput(StringBuffer s, BlockDataType bdt) {
        if (bdt == BlockDataType.XML) {
            writer = new XmlDomWriter();
            writer.setString(s);
        }
    }

    @Override
    public void onBlockRead(BlockData bd) {
        ISingleBlock sb = SingleBlockFactory.getInstance().createCalc();
        sb.setBlockData(bd);
        sb.setOnSingleBlockCalcCompleteListener(this);
        sb.doIt();
    }

    @Override
    public void onSingleBlockCalcComplete(SingleBlockResult sbresult) {
        writer.doWrite(sbresult);
    }
}
