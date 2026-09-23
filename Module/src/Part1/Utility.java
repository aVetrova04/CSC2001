package Part1;

public class Utility {

    public static double perimeter(Circle c) {
        return 2 * Math.PI * c.getRadius();
    }

    public static double perimeter(Rectangle r) {
        double width = Math.abs(
                r.getBottomRight().getX() - r.getTopLeft().getX()
        );

        double height = Math.abs(
                r.getBottomRight().getY() - r.getTopLeft().getY()
        );

        return 2 * (width + height);
    }

    public static double area(Circle c) {
        return Math.PI * c.getRadius() * c.getRadius();
    }

    public static double area(Rectangle r) {
        double width = Math.abs(
                r.getBottomRight().getX() - r.getTopLeft().getX()
        );

        double height = Math.abs(
                r.getBottomRight().getY() - r.getTopLeft().getY()
        );

        return width * height;
    }
}
