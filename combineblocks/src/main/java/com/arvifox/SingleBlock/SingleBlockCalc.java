package com.arvifox.SingleBlock;

import com.arvifox.Block.BlockData;
import com.arvifox.Coord;
import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * Created by Andrey on 02.12.2016.
 */
class SingleBlockCalc implements ISingleBlock {

    private SingleBlockResult result;
    private int[][] adjacencyMatrix;
    private int[][] matrixcur;
    private BlockData blockData;
    private int maxpathlength;
    private ReachableVertex rv;
    private OnSingleBlockCalcCompleteListener listener;

    SingleBlockCalc() {
        result = new SingleBlockResult();
    }

    @Override
    public void setOnSingleBlockCalcCompleteListener(OnSingleBlockCalcCompleteListener onListener) {
        listener = onListener;
    }

    @Override
    public void setBlockData(BlockData bd) {
        blockData = bd;
        maxpathlength = bd.getSize().getX() + bd.getSize().getY() + bd.getSize().getZ() - 4;
    }

    private boolean BuildMatrix() {
        int ser = 0;
        int nextser = 0;
        boolean res = false;
        adjacencyMatrix = new int[
                blockData.getSize().getX() * blockData.getSize().getY() * blockData.getSize().getZ()]
                [blockData.getSize().getX() * blockData.getSize().getY() * blockData.getSize().getZ()];
        for (int i = 0; i < blockData.getSize().getX(); i++) {
            for (int j = 0; j < blockData.getSize().getY(); j++) {
                for (int k = 0; k < blockData.getSize().getZ(); k++) {
                    ser = Coord.coordToSerial(i, j, k, blockData.getSize());
                    if (!blockData.getHolesSerial().contains(ser)) {
                        // next 1
                        if (i - 1 >= 0) {
                            nextser = Coord.coordToSerial(i - 1, j, k, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                        // next 2
                        if (j - 1 >= 0) {
                            nextser = Coord.coordToSerial(i, j - 1, k, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                        // next 3
                        if (k - 1 >= 0) {
                            nextser = Coord.coordToSerial(i, j, k - 1, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                        // next 4
                        if (i + 1 < blockData.getSize().getX()) {
                            nextser = Coord.coordToSerial(i + 1, j, k, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                        // next 5
                        if (j + 1 < blockData.getSize().getY()) {
                            nextser = Coord.coordToSerial(i, j + 1, k, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                        // next 6
                        if (k + 1 < blockData.getSize().getZ()) {
                            nextser = Coord.coordToSerial(i, j, k + 1, blockData.getSize());
                            if (!blockData.getHolesSerial().contains(nextser)) {
                                adjacencyMatrix[ser][nextser] = 1;
                                adjacencyMatrix[nextser][ser] = 1;
                                res = true;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void doIt() {
        if (blockData.getHolesSerial().isEmpty()) {
            result.addBlockItemLength(new Coord(0, 0, 0), blockData.getSize());
            result.uid = blockData.getUid();
            listener.onSingleBlockCalcComplete(result);
        }
        mainCalc();
        result.uid = blockData.getUid();
        listener.onSingleBlockCalcComplete(result);
    }

    private boolean checkPairs(ReachableVertex aPairs) {
        // ищем макс подблок
        int max_i = 0;
        int max_j = 0;
        int max = 0;
        for (Integer nn : aPairs.rv.keySet()) {
            for (Integer mm : aPairs.rv.get(nn)) {
                Coord dv = Coord.sub(Coord.serialToCoord(mm, blockData.getSize()), Coord.serialToCoord(nn, blockData.getSize()));
                dv.abs();
                long pathcount = CombinatoricsUtils.factorial(dv.getX() + dv.getY() + dv.getZ()) /
                        (CombinatoricsUtils.factorial(dv.getX()) *
                                CombinatoricsUtils.factorial(dv.getY()) *
                                CombinatoricsUtils.factorial(dv.getZ()));
                if (matrixcur[nn][mm] == pathcount) {
                    if ((dv.getX() + 1) * (dv.getY() + 1) * (dv.getZ() + 1) > max) {
                        max = (dv.getX() + 1) * (dv.getY() + 1) * (dv.getZ() + 1);
                        max_i = nn;
                        max_j = mm;
                    }
                }
            }
        }
        if (max > 0) {
            Coord mi = Coord.serialToCoord(max_i, blockData.getSize());
            Coord ma = Coord.serialToCoord(max_j, blockData.getSize());
            result.addBlockItemEnd(mi, ma);
            for (int ii = mi.getX(); ii <= ma.getX(); ii++) {
                for (int jj = mi.getY(); jj <= ma.getY(); jj++) {
                    for (int kk = mi.getZ(); kk <= ma.getZ(); kk++) {
                        blockData.addHole(ii, jj, kk);
                    }
                }
            }
            return true;
        } else
            return false;
    }

    private void mainCalc() {
        ReachableVertex pairs;
        if (maxpathlength < 1) maxpathlength = 1;
        while (true) {
            if (!BuildMatrix()) {
                // блоки по одному
                for (int ii = 0; ii < adjacencyMatrix.length; ii++) {
                    if (!blockData.getHolesSerial().contains(ii)) {
                        result.addBlockItemLength(Coord.serialToCoord(ii, blockData.getSize()), new Coord(1, 1, 1));
                    }
                }
                break;
            }
            int curlength = maxpathlength;
            while (curlength > 0) {
                rv = new ReachableVertex();
                matrixcur = adjacencyMatrix.clone();
                for (int i = 2; i <= curlength; i++) {
                    // доступность вершин сохраним
                    for (int ii = 0; ii < adjacencyMatrix.length; ii++) {
                        for (int jj = ii + 1; jj < adjacencyMatrix[ii].length; jj++) {
                            if (matrixcur[ii][jj] > 0) {
                                rv.addEdge(ii, jj);
                            }
                        }
                    }
                    matrixcur = MatrixMultiplication.multiStrassenForkJoin(adjacencyMatrix, matrixcur);
                }
                pairs = getPairs(curlength);
                if (checkPairs(pairs)) {
                    break;
                } else {
                    curlength--;
                    rv = null;
                    matrixcur = null;
                }
            }
            if (curlength == 0) {
                // блоки по одному
                for (int ii = 0; ii < adjacencyMatrix.length; ii++) {
                    if (!blockData.getHolesSerial().contains(ii)) {
                        result.addBlockItemLength(Coord.serialToCoord(ii, blockData.getSize()), new Coord(1, 1, 1));
                    }
                }
                break;
            }
        }
    }

    private ReachableVertex getPairs(int curlen) {
        ReachableVertex res = new ReachableVertex();
        for (int i = 0; i < matrixcur.length; i++) {
            for (int j = i + 1; j < matrixcur[i].length; j++) {
                if (matrixcur[i][j] > 0 && !rv.hasEdge(i, j)) {
                    Coord dv = Coord.sub(Coord.serialToCoord(j, blockData.getSize()), Coord.serialToCoord(i, blockData.getSize()));
                    dv.abs();
                    if (curlen == dv.getX() + dv.getY() + dv.getZ())
                        res.addSingleEdge(i, j);
                }
            }
        }
        return res;
    }
}
