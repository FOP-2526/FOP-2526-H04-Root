package h04;

public interface MyFunction {

    double apply(double x);

    MyFunction derivative();

    default double differentiate(double x) {
        return derivative().apply(x);
    }

    MyFunction integral(double c);

    default double integrate(double a, double b, int steps) {
        double stepSize = (b - a) / steps;
        double sum = 0;
        for (int i = 0; i < steps; i++) {
            double x = a + i * stepSize;
            sum += apply(x) * stepSize;
        }
        return sum;
    }

    String toString();
}
