package h04;

public class ConstantFunction extends AbstractPolynomialFunction implements MyPolynomialFunction {

    public ConstantFunction(double value) {
        super(value);
    }

    public double getValue() {
        return getCoefficient(0);
    }

    @Override
    public double apply(double x) {
        return getValue();
    }

    @Override
    public MyPolynomialFunction derivative() {
        return new ConstantFunction(0);
    }

    @Override
    public MyPolynomialFunction integral(double c) {
        return new SimplePolynomialFunction(c, getValue());
    }

    @Override
    public double integrate(double a, double b, int steps) {
        return getValue() * (b - a);
    }
}
