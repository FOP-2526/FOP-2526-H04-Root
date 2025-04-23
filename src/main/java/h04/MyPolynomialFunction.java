package h04;

public interface MyPolynomialFunction extends MyFunction {

    int degree();

    double[] coefficients();

    double getCoefficient(int exponent);

    @Override
    MyPolynomialFunction derivative();

    @Override
    MyPolynomialFunction integral(double c);

    String toString();
}
