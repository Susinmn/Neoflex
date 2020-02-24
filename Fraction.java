import java.lang.*;
import java.math.BigDecimal;

/*
cd C:\Neoflex
javac Fraction.java
java -classpath . Fraction
jar cfv Fraction.jar Fraction.class
*/

public class Fraction {
    //TODO: реализовать атрибут intPart, plusMinus
    private long numerator;
    private long denominator;

    public static void main(String[] args) {
        Fraction fraction1 = new Fraction(0.25);
        System.out.println(fraction1);
    }

    public Fraction(String s) {
        s = s.trim();
        String[] arr = s.split("/");
        if (arr.length > 2) {
            throw new IllegalArgumentException("Некорректная строка");
        } else if (arr.length == 2) {
            try {
                this.numerator = Long.valueOf(arr[0]);
                this.denominator = Long.valueOf(arr[1]);
            } catch (Exception e) {
                throw new IllegalArgumentException("Некорректная строка");
            }
        } else {
            try {
                this.numerator = Long.valueOf(arr[0]);
                this.denominator = 1L;
            } catch (Exception e) {
                throw new IllegalArgumentException("Некорректная строка");
            }

        }
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public Fraction(long intView) {
        this.numerator = intView;
        this.denominator = 1L;
    }

    public Fraction(double doubleView){
        this.numerator = (long) (doubleView * Math.pow(10, BigDecimal.valueOf(doubleView).scale()));
        this.denominator = (long) Math.pow(10, BigDecimal.valueOf(doubleView).scale());
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public String toString() {
        if (Math.abs(this.numerator) > Math.abs(this.denominator)) {
            if (this.numerator % this.denominator == 0) {
                return "" + this.numerator / this.denominator;
            } else {
                return this.numerator / this.denominator + " " + Math.abs(this.numerator % this.denominator) + "/" + this.denominator;
            }
        } else {
            return this.numerator + "/" + this.denominator;
        }
    }

    public void add(Fraction fract) {
        //TODO: здесь нужно предусмотреть переполение long
        this.numerator = this.numerator * fract.denominator + fract.numerator * this.denominator;
        this.denominator = this.denominator * fract.denominator;
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public void subtraction(Fraction fract) {
        this.numerator = this.numerator * fract.denominator - fract.numerator * this.denominator;
        this.denominator = this.denominator * fract.denominator;
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public void multiplication(Fraction fract) {
        this.numerator = this.numerator * fract.numerator;
        this.denominator = this.denominator * fract.denominator;
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public void division(Fraction fract) {
        this.numerator = this.numerator * fract.denominator;
        this.denominator = this.denominator * fract.numerator;
        long factor = nod(this.numerator, this.denominator);
        this.numerator /= factor;
        this.denominator /= factor;
    }

    public long nod(long num1, long num2) {
        num1 = Math.abs(num1);
        num2 = Math.abs(num2);
        if (num1 > num2) {
            return (num1 % num2 == 0) ? num2 : nod(num2, num1 % num2);
        } else {
            return (num2 % num1 == 0) ? num1 : nod(num1, num2 % num1);
        }

    }

}