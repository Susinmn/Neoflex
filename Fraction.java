package net.susinmn;

import java.lang.*;
import java.math.BigDecimal;

/*
cd C:\Neoflex
javac Fraction.java
java -classpath . Fraction
jar cfv Fraction.jar Fraction.class
*/

public class Fraction {
    private static final String FRACTION_DELIMITER = "/";
    private static final int DECIMAL_MULTIPLIER = 10;
    private static final long DEFAULT_DENOMINATOR = 1L;
    public static final int MAXIMUM_ARGUMENTS_COUNT = 2;
    public static final int MINIMUM_ARGUMENTS_COUNTS = 1;

    private long numerator;
    private long denominator;

    public static void main(String[] args) {
        Fraction fraction1 = Fraction.valueOf("5/10 1/2");
        System.out.println(fraction1);
    }

    public Fraction(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        if (this.denominator == 0){
            throw new IllegalArgumentException(Messages.ZERO_DENOMINATOR.getMessage());
        }
        reduction();
    }

    private Fraction(long longView) {
        this (longView, DEFAULT_DENOMINATOR);
    }

    private Fraction(double doubleView){
        this ((long) (doubleView * Math.pow(10, BigDecimal.valueOf(doubleView).scale())), (long) Math.pow(10, BigDecimal.valueOf(doubleView).scale()));
    }

    public static Fraction valueOf(String s) {
        try {
            s = s.trim();
            String[] arr = s.split(FRACTION_DELIMITER);
            if (arr.length == MAXIMUM_ARGUMENTS_COUNT) {
                return new Fraction(Long.valueOf(arr[0]), Long.valueOf(arr[1]));
            } else if (arr.length == MINIMUM_ARGUMENTS_COUNTS) {
                return new Fraction(Long.valueOf(arr[0]));
            } else {
                throw new IllegalArgumentException(Messages.ILLEGAL_ARGUMENTS_COUNT.getMessage());
            }
        } catch (NumberFormatException ex){
            throw new IllegalArgumentException(Messages.INCORRECT_STRING + " " + ex.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
            throw new IllegalArgumentException(Messages.COMMON_CREATE_ERROR + " " + ex.getMessage());
        }
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
        this.reduction();
    }

    public void subtraction(Fraction fract) {
        this.numerator = this.numerator * fract.denominator - fract.numerator * this.denominator;
        this.denominator = this.denominator * fract.denominator;
        this.reduction();
    }

    public void multiplication(Fraction fract) {
        this.numerator = this.numerator * fract.numerator;
        this.denominator = this.denominator * fract.denominator;
        this.reduction();
    }

    public void division(Fraction fract) {
        this.numerator = this.numerator * fract.denominator;
        this.denominator = this.denominator * fract.numerator;
        this.reduction();
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

    private void reduction() {
        long factor = nod(numerator, denominator);
        numerator = numerator / factor;
        denominator = denominator / factor;
    }

    enum Messages {
        ILLEGAL_ARGUMENTS_COUNT("Некорректное кол-во аргументов"),
        ZERO_DENOMINATOR("Знаменатель не может быть нулем"),
        INCORRECT_STRING("Некорректная строка"),
        COMMON_CREATE_ERROR("Ошибка создания");

        private String message;

        Messages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
