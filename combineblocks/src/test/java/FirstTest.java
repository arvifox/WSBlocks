import com.arvifox.Block.BlockData;
import com.arvifox.SingleBlock.ISingleBlock;
import com.arvifox.SingleBlock.SingleBlockFactory;
import com.arvifox.SingleBlock.SingleBlockResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 06.12.2016.
 */
public class FirstTest {

    private boolean simpleblocktest(SingleBlockResult sbr) {
        if (sbr.resultBlocks.size() != 1) return false;
        for (SingleBlockResult.BlockItem bi : sbr.resultBlocks) {
            if (bi.start.getX() != 0) return false;
            if (bi.start.getY() != 0) return false;
            if (bi.start.getZ() != 0) return false;
            if (bi.length.getX() != 1) return false;
            if (bi.length.getY() != 1) return false;
            if (bi.length.getZ() != 1) return false;
        }
        return true;
    }

    @Test
    public void singleblock() {
        ISingleBlock singleBlock = SingleBlockFactory.getInstance().createCalc();
        BlockData bd = new BlockData();
        bd.setUid(1);
        bd.setSize(1, 1, 1);
        singleBlock.setBlockData(bd);
//        SingleBlockResult sbr = singleBlock.doIt();
//        Assert.assertTrue("simpleblock", simpleblocktest(sbr));
    }
}
