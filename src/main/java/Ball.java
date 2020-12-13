public class Ball {
    private int id;
    private int x;
    private int y;
    private int z;
    private int mid;

    public Ball(int id, int x, int y, int z, int mid) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mid = mid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", mid=" + mid +
                '}';
    }
}
