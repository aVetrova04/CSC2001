package Part1;

import static java.lang.Math.pow;

public class Utility {
    public static double perimeter(Circle c) {
        return 2*3.1416*c.getRadius();
    }

    public static double area(Circle c) {
        return 3.1416*c.getRadius()*c.getRadius();
    }

    private static double dist(Point p1, Point p2) {
        return pow((pow(p1.getX() - p2.getX(), 2) + pow((p1.getY() - p2.getY()), 2)), 0.5);
    }

    public static double perimeter(Rectangle r) {
        return 0;
    }
}
