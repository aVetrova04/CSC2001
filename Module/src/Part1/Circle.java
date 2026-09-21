package Part1;

public class Circle {

    private Point p;
    private double r;

    public Circle(Point p, double r) {
        this.p = p;
        this.r = r;

    }

    // getters
    public Point getCenter() {
        return p;
    }
    public double getRadius() {
        return r;
    }

    // setters
    public void setCenter(Point p) {
        this.p = p;
    }
    public void setRadius(double r) {
        this.r = r;
    }

}
