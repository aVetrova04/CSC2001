package Part2;

import Part1.Point;

public class Rectangle {

    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double perimeter() {
        double width = Math.abs(
                bottomRight.getX() - topLeft.getX()
        );

        double height = Math.abs(
                bottomRight.getY() - topLeft.getY()
        );

        return 2 * (width + height);
    }

    public double area() {
        double width = Math.abs(
                bottomRight.getX() - topLeft.getX()
        );

        double height = Math.abs(
                bottomRight.getY() - topLeft.getY()
        );

        return width * height;
    }
}
