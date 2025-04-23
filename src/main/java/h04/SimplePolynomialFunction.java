package h04;

public class SimplePolynomialFunction extends AbstractPolynomialFunction implements MyPolynomialFunction {

    public SimplePolynomialFunction(double... coefficients) {
        super(coefficients);
    }

    @Override
    public double apply(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
        }
        return result;
    }

    @Override
    public MyPolynomialFunction derivative() {
        double[] derivativeCoefficients = new double[coefficients.length - 1];
        for (int i = 0; i < coefficients.length - 1; i++) {
            derivativeCoefficients[i] = coefficients[i] * (coefficients.length - 1 - i);
        }
        return new SimplePolynomialFunction(derivativeCoefficients);
    }

    @Override
    public MyPolynomialFunction integral(double c) {
        double[] integralCoefficients = new double[coefficients.length + 1];
        integralCoefficients[0] = c;
        for (int i = 0; i < coefficients.length; i++) {
            integralCoefficients[i + 1] = coefficients[i] / (coefficients.length - i);
        }
        return new SimplePolynomialFunction(integralCoefficients);
    }
}
