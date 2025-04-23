package h04;

import java.util.Arrays;

public abstract class AbstractPolynomialFunction implements MyPolynomialFunction {

    protected final double[] coefficients;

    public AbstractPolynomialFunction(double... coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public int degree() {
        return coefficients.length;
    }

    @Override
    public double[] coefficients() {
        return coefficients;
    }

    @Override
    public double getCoefficient(int exponent) {
        if (exponent < coefficients.length) {
            return coefficients[coefficients.length - 1 - exponent];
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof MyPolynomialFunction that && Arrays.equals(coefficients(), that.coefficients());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefficients);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            int exponent = degree() - i;
            if (coefficients[i] != 0) {
                if (i > 0 && coefficients[i] > 0) {
                    result.append(" + ");
                } else if (i > 0 && coefficients[i] < 0) {
                    result.append(" - ");
                }
                if (Math.abs(coefficients[i]) != 1 || exponent == 0) {
                    result.append(Math.abs(coefficients[i]));
                }
                if (exponent > 0) {
                    result.append("x");
                }
                if (exponent > 1) {
                    result.append("^").append(exponent);
                }
            }
        }
        return result.toString();
    }
}
