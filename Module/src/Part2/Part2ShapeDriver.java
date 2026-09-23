package Part2;

import Part1.Point;

import java.util.ArrayList;

public class Part2ShapeDriver {

    public static void main(String[] args) {

        Circle[] circles = {
                new Circle(new Point(0, 0), 5),
                new Circle(new Point(2, 3), 3),
                new Circle(new Point(-1, 4), 7)
        };

        Rectangle[] rectangles = {
                new Rectangle(new Point(0, 5), new Point(4, 0)),
                new Rectangle(new Point(-2, 6), new Point(3, 1)),
                new Rectangle(new Point(1, 10), new Point(8, 4))
        };

        ArrayList<Double> computedList = new ArrayList<>();

        for (Circle circle : circles) {
            computedList.add(circle.perimeter());
            computedList.add(circle.area());
        }

        for (Rectangle rectangle : rectangles) {
            computedList.add(rectangle.perimeter());
            computedList.add(rectangle.area());
        }

        double[] values = smallLarge(computedList);

        System.out.println("Smallest computed value: " + values[0]);
        System.out.println("Largest computed value: " + values[1]);
    }

    public static double[] smallLarge(ArrayList<Double> computedList) {

        double smallest = computedList.get(0);
        double largest = computedList.get(0);

        for (double value : computedList) {

            if (value < smallest) {
                smallest = value;
            }

            if (value > largest) {
                largest = value;
            }
        }

        return new double[]{smallest, largest};
    }
}
