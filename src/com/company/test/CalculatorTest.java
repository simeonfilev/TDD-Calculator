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
    @DisplayName("ExpressionOnSingleDigitsWorksCorrectly")
    public void singleDigitsOperationsWorksCorrectly(){
        Assertions.assertEquals(6,calculator.calculate("3*2"));
        Assertions.assertEquals(4,calculator.calculate("8/2"));
        Assertions.assertEquals(5,calculator.calculate("1+4"));
        Assertions.assertEquals(3,calculator.calculate("5-2"));
        Assertions.assertEquals(-7,calculator.calculate("-5-2"));
        Assertions.assertEquals(3,calculator.calculate(" 5  - 2"));
    }

    @Test
    @DisplayName("ExpressionOnTwoDigitsWorksCorrectly")
    public void twoDigitsOperationsWorksCorrectly(){
        Assertions.assertEquals(14,calculator.calculate("10+4"));
        Assertions.assertEquals(5,calculator.calculate("10/2"));
        Assertions.assertEquals(20,calculator.calculate("10*2"));
        Assertions.assertEquals(8,calculator.calculate("10-2"));
        Assertions.assertEquals(8,calculator.calculate("  10 -   2"));
    }

    @Test
    @DisplayName("ExpressionWithPrioritiesWorksCorrectly")
    public void expressionWithPrioritiesWorksCorrectly(){
        Assertions.assertEquals(18,calculator.calculate("()()10+4*2"));
        Assertions.assertEquals(4,calculator.calculate("100/5/5"));
        Assertions.assertEquals(20,calculator.calculate("10+5+5"));
        Assertions.assertEquals(8,calculator.calculate("10-2*1"));
        Assertions.assertEquals(40,calculator.calculate("5*10-2*5"));
        Assertions.assertEquals(14,calculator.calculate("(5+2)*2"));
        Assertions.assertEquals(-20,calculator.calculate("-(2+3)*4"));
        Assertions.assertEquals(2,calculator.calculate("((5+3)/2)/2"));
        Assertions.assertEquals(9,calculator.calculate("(2+3+4)"));
        Assertions.assertEquals(18,calculator.calculate("(2+3*4)+3*2-2"));
        Assertions.assertEquals(2,calculator.calculate("(((-2*3)+5)+5)/2"));
        Assertions.assertEquals(8,calculator.calculate("(((-2*-3)+5)+5)/2"));
    }

    @Test
    @DisplayName("ExpressionWithIllegalArgumentsWorksCorrectly")
    public void expressionWithBadArgumentsWorksCorrectly(){
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("5#2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("(5+2))"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("()5+2))"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("*5+2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("S/2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("3**2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("3S/2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("33//2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("51#2"));
        Assertions.assertThrows(UnsupportedOperationException.class,() -> calculator.calculate("((51#2)*2)+3"));
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
        Assertions.assertEquals(5,calculator.subtract(-5,-10));
        Assertions.assertEquals(-5,calculator.subtract(-10,-5));
        Assertions.assertEquals(-7,calculator.subtract(-5,2));
        Assertions.assertEquals(-3,calculator.subtract(-5,-2));
        Assertions.assertEquals(0,calculator.subtract(0,0));
    }

    @Test
    @DisplayName("DividingTwoNumbersWorkCorrectly")
    public void dividingWorksCorrectly(){
        Assertions.assertEquals(5,calculator.divide(10,2));
        Assertions.assertEquals(-5,calculator.divide(-10,2));
        Assertions.assertEquals(5,calculator.divide(-10,-2));
        Assertions.assertThrows(ArithmeticException.class,() -> calculator.divide(10,0));
    }

    @Test
    @DisplayName("MultiplyingTwoNumbersWorkCorrectly")
    public void multiplyingWorksCorrectly(){
        Assertions.assertEquals(10, calculator.multiply(2,5));
        Assertions.assertEquals(0,calculator.multiply(5,0));
        Assertions.assertEquals(-15,calculator.multiply(-5,3));
        Assertions.assertEquals(15,calculator.multiply(-5,-3));
    }
}
