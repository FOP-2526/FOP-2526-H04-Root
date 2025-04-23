package h04;

public class LinearFunction extends AbstractPolynomialFunction implements MyPolynomialFunction {

    public LinearFunction(double m, double b) {
        super(m, b);
    }

    public double getM() {
        return coefficients[0];
    }

    public double getB() {
        return coefficients[1];
    }

    @Override
    public double apply(double x) {
        return getM() * x + getB();
    }

    @Override
    public MyPolynomialFunction derivative() {
        return new ConstantFunction(getM());
    }

    @Override
    public MyPolynomialFunction integral(double c) {
        return new SimplePolynomialFunction(c, getB(), getM() / 2);
    }
}
