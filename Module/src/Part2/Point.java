package Part2;

public class Point {

    private double x;
    private double y;

    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    // setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    // rotates
    public Point rotate() {
        return new Point(-y, x);
    }
}
