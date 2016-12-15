package com.arvifox;

/**
 * Created by Andrey on 02.12.2016.
 */
public class Coord {
    private int x;
    private int y;
    private int z;

    public Coord() {}

    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coord(Coord coord) {
        this.x = coord.getX();
        this.y = coord.getY();
        this.z = coord.getZ();
    }

    public void abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
    }

    public static Coord sub(Coord a1, Coord a2) {
        Coord res = new Coord();
        res.setX(a1.getX() - a2.getX());
        res.setY(a1.getY() - a2.getY());
        res.setZ(a1.getZ() - a2.getZ());
        return res;
    }

    public static int coordToSerial(Coord coord, Coord size) {
        return size.getY() * size.getX() * coord.getZ() + size.getX() * coord.getY() + coord.getX();
    }

    public static int coordToSerial(int coordX, int coordY, int coordZ, Coord size) {
        return size.getY() * size.getX() * coordZ + size.getX() * coordY + coordX;
    }

    public static Coord serialToCoord(int serial, Coord size) {
        Coord c= new Coord(0, 0, 0);
        c.setZ(serial / (size.getX() * size.getY()));
        serial = serial - c.getZ() * size.getX() * size.getY();
        c.setY(serial / size.getX());
        c.setX(serial - c.getY() * size.getX());
        return c;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
