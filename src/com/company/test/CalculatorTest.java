package com.company.test;

import com.company.main.Calculator;
import org.junit.jupiter.api.*;

public class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll
    static void init(){
        calculator = new Calculator();

    }

    @Test
    @DisplayName("AddingTwoNumbersWorkCorrectly")
    public void addingWorksCorrectly(){
        Assertions.assertEquals(25, calculator.add(10,15));
        Assertions.assertEquals(5, calculator.add(-5,10));
        Assertions.assertEquals(0, calculator.add(0,0));
    }

    @Test
    @DisplayName("SubtractingTwoNumbersWorkCorrectly")
    public void subtractingWorksCorrectly(){
        Assertions.assertEquals(5,calculator.subtract(10,5));
        Assertions.assertEquals(-15,calculator.subtract(-5,-10));
        Assertions.assertEquals(0,calculator.subtract(0,0));
    }

    @Test
    @DisplayName("DividingTwoNumbersWorkCorrectly")
    public void dividingWorksCorrectly(){
        Assertions.assertEquals(5,calculator.divide(10,2));
        Assertions.assertThrows(ArithmeticException.class,() -> calculator.divide(10,0));
        Assertions.assertEquals(-5,calculator.divide(-10,2));
        Assertions.assertEquals(5,calculator.divide(-10,-2));

    }

    @Test
    @DisplayName("MultiplyingTwoNumbersWorkCorrectly")
    public void multiplyingWorksCorrectly(){
        Assertions.assertEquals(10, calculator.multiply(2,5));
        Assertions.assertEquals(0,calculator.multiply(5,0));
        Assertions.assertEquals(-15,calculator.multiply(-5,3));

    }
}
