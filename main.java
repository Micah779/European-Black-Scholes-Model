/*
 * credit to Robert Sedgewick and Kevin Wayne from Princeton for the Java PDF, CDF Formulas
 * S = equity price, K = strike price, r = interest rate, volatility, time
 */

// importing the math library
import java.lang.Math;

class BlackScholesModel {
    // returns the standard gaussian pdf (normal distribution)
    public static double PDF(double z) {
        return Math.exp(-z*z / 2) / Math.sqrt(2 * Math.PI);
    }

    // returns cumulative density function of x using Taylor approximation for integration
    public static double CDF(double x) {
        if (x < -8.0) {
            return 0.0;
        }
        if (x > 8.0) {
            return 1.0;
        }

        double sum = 0.0;
        double term = x;

        for (int i = 3; sum + term != sum; i += 2) {
            sum += term;
            term = term * x * x / i;
        }
        
        double w = PDF(x);

        // calling the normal distribution
        return 0.5 + sum * w;
    }

    public static String CallPrice(double S, double K, double r, double volatility, int time) {
        // evaluating the CDF inputs d1, d2
        double d1 = (Math.log(S/K) + (time * (r + (Math.pow(volatility,2)/2)))) / volatility * Math.sqrt(time);
        System.out.println("d1 is: " + d1);
        double d2 = (Math.log(S/K) + (time * (r - (Math.pow(volatility,2)/2)))) / volatility * Math.sqrt(time);
        System.out.println("d2 is: " + d2);

        // number e for discounted interest rate calculation
        double e = Math.exp(1);

        // evaluating the call price using the normal (Gaussian) cumulitive probability distribution function of the return
        double price = S*CDF(d1) - ((K*Math.pow(e, (-1*r*time))) * CDF(d2));
        
        String call = "call price is: $" + price + " ";
        return call;

    }

    public static void main(String[] args) {
        System.out.print(CallPrice(1330.0, 1580.0, 0.1, 1.0, 1));
    }
}
