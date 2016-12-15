package com.arvifox.Block;

import com.arvifox.Coord;

import java.util.ArrayList;

/**
 * Created by Andrey on 09.12.2016.
 */
public class BlockData {
    private Coord size;
    private int uid;
    private ArrayList<Integer> holes;

    public BlockData() {
        holes = new ArrayList<>();
    }

    public void setSize(int x, int y, int z) {
        size = new Coord(x, y, z);
    }

    public Coord getSize() {
        return size;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public ArrayList<Coord> getHolesCoord() {
        ArrayList<Coord> alc = new ArrayList<>();
        for (Integer i : holes) {
            alc.add(Coord.serialToCoord(i, size));
        }
        return alc;
    }

    public ArrayList<Integer> getHolesSerial() {
        return holes;
    }

    public void addHole(int x, int y, int z) {
        int s = Coord.coordToSerial(x, y, z, size);
        if (!holes.contains(s))
            holes.add(s);
    }
}
