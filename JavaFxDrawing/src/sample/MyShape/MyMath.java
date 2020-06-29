package sample.MyShape;

public class MyMath {
    public boolean isDeleteLine(double x1, double y1, double x2, double y2, double x3, double y3) {

        double distance1 = Math.sqrt(Math.pow(x1 - x2,2) + Math.pow(y1 - y2,2));
        double distance2 = Math.sqrt(Math.pow(x1 - x3,2) + Math.pow(y1 - y3,2));
        double distance3 = Math.sqrt(Math.pow(x2 - x3,2) + Math.pow(y2 - y3,2));

        return distance1 * 1.02 > (distance2 + distance3);
    }
}
