package Part1;

public class Rectangle {

    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;

    }

    // getters
    public Point getTopLeft() {
        return topLeft;
    }
    public Point getBottomRight() {
        return bottomRight;
    }

    /*
    // setters
    public void setTopLeft(Point topLeft) {this.topLeft = topLeft;}
    public void setBottomRight(Point bottomRight) {this.bottomRight = bottomRight;}

     */
}
