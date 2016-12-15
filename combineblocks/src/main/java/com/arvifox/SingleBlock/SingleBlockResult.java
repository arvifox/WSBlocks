package com.arvifox.SingleBlock;

import com.arvifox.Coord;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Andrey on 02.12.2016.
 */
public class SingleBlockResult {

    public Collection<BlockItem> resultBlocks;
    public int uid;

    SingleBlockResult() {
        resultBlocks = new ArrayList<>();
    }

    void addBlockItemLength(Coord start, Coord length) {
        resultBlocks.add(new BlockItem(start, length));
    }

    void addBlockItemEnd(Coord start, Coord end) {
        BlockItem blockItem = new BlockItem(start, end);
        blockItem.length.setX(end.getX() - start.getX() + 1);
        blockItem.length.setY(end.getY() - start.getY() + 1);
        blockItem.length.setZ(end.getZ() - start.getZ() + 1);
        resultBlocks.add(blockItem);
    }

    public class BlockItem {
        public Coord start;
        public Coord length;

        private BlockItem(Coord st, Coord le) {
            start = new Coord(st);
            length = new Coord(le);
        }
    }
}
