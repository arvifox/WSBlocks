package com.arvifox.SingleBlock;

import com.arvifox.Block.BlockData;

/**
 * Created by Andrey on 02.12.2016.
 */
public interface ISingleBlock {
    void setBlockData(BlockData bd);

    void doIt();

    void setOnSingleBlockCalcCompleteListener(OnSingleBlockCalcCompleteListener onListener);

    interface OnSingleBlockCalcCompleteListener {
        void onSingleBlockCalcComplete(SingleBlockResult sbresult);
    }
}
