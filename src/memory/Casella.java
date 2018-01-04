package memory;

import java.util.Objects;

public class Casella {
    private int x;
    private int y;
    private int z;

    public Casella(int x, int y,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casella)) return false;
        Casella casella = (Casella) o;
        return x == casella.x &&
                y == casella.y &&
                z == casella.z;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Casella{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

