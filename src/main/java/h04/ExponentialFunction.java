package h04;

import java.util.Objects;

public class ExponentialFunction implements MyFunction {

    private final double coefficient;
    private final double exponent;

    public ExponentialFunction(double coefficient, double exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    @Override
    public double apply(double x) {
        return coefficient * Math.exp(exponent * x);
    }

    @Override
    public MyFunction derivative() {
        return new ExponentialFunction(coefficient * exponent, exponent);
    }

    @Override
    public MyFunction integral(double c) {
        return new IntegralExponentialFunction(coefficient / exponent, exponent, c);
    }

    static class IntegralExponentialFunction extends ExponentialFunction {

        private final double constant;

        public IntegralExponentialFunction(double coefficient, double exponent, double constant) {
            super(coefficient, exponent);
            this.constant = constant;
        }

        public double getConstant() {
            return constant;
        }

        @Override
        public double apply(double x) {
            return super.apply(x) + constant;
        }
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof ExponentialFunction that && equals(that) && Double.compare(coefficient, that.coefficient) == 0 && Double.compare(exponent, that.exponent) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, exponent);
    }

    @Override
    public String toString() {
        return coefficient + " * e^(" + exponent + " * x)";
    }
}
