package com.company.main;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public static final List<Character> validSymbols = List.of('*', '/', '+', '-', '(', ')');
    public static final List<Character> mathOperators = List.of('*', '/', '+', '-');
    public static final List<Character> operatorWithPriority = List.of('*', '/');


    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        if (a < 0 && b < 0) {
            return a - b;
        } else if (b < 0) {
            return a + b;
        } else {
            return a - b;
        }
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException();
        }
        return a / b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double calculate(String expression) {
        expression = removeWhiteSpaces(expression);
        if (containsIllegalArguments(expression)) {
            throw new UnsupportedOperationException();
        }
        double calculatedAnswer = calculateExpression(expression);
        return calculatedAnswer;
    }

    private Double calculateExpression(String expression){
        expression = calculateParenthesis(expression);
        expression = calculatePriorityExpressions(expression);
        expression = calculateNonPriorityExpressions(expression);
        return Double.valueOf(expression);
    }

    private String calculateMatchedExpression(Matcher matcher){
        double firstNumber = Double.parseDouble(matcher.group(1));
        char mathOperator = matcher.group(2).charAt(0);
        double secondNumber = Double.parseDouble(matcher.group(3));
        String expressionToCalculate = "" + firstNumber + mathOperator + secondNumber;
        return String.valueOf(calculateSingleExpression(expressionToCalculate));
    }

    private String calculateNonPriorityExpressions(String expression) {
        String priorityRegex = "(-?[0-9.]+)(["  +Pattern.quote(mathOperators.toString()) +"])([\\d.]+)";
        Pattern pattern = Pattern.compile(priorityRegex);
        while (getExpressionsCount(expression) > 0) {
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
               String calculatedExpression = calculateMatchedExpression(matcher);
               expression = expression.replaceAll(Pattern.quote(matcher.group(0)), calculatedExpression);
            }
        }
        return expression;
    }

    private String calculatePriorityExpressions(String expression) {
        String priorityRegex = "(-?[\\d.]+)(["+ Pattern.quote(operatorWithPriority.toString()) +"])(-?[\\d.]+)";
        Pattern pattern = Pattern.compile(priorityRegex);
        while (getPriorityOperatorsCount(expression) > 0) {
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
                String calculatedExpression = calculateMatchedExpression(matcher);
                expression = expression.replaceAll(Pattern.quote(matcher.group(0)), calculatedExpression);
            }
        }
        return expression;
    }

    private Integer getPriorityOperatorsCount(String expression) {
        int priorityOperatorsCount = 0;

        for (int index = 0; index < expression.length(); index++) {
            if (operatorWithPriority.contains(expression.charAt(index)))
                priorityOperatorsCount++;
        }
        return priorityOperatorsCount;
    }

    private boolean startsWithMathSignOtherThanMinus(String expression) {
        char firstChar = expression.charAt(0);
        return isMathOperator(firstChar) && firstChar != '-';
    }

    private String removeParenthesisFromExpression(String expression){
        if (hasParentheses(expression)) {
            expression = expression.replace("(", "").replace(")", "");
        }
        return expression;
    }

    private double calculateSingleExpression(String expression) {
        expression = removeParenthesisFromExpression(expression);
        char mathOperator = getOperatorSingleExpression(expression);
        List<Double> numbers = getNumbersFromSingleExpression(expression);
        Double firstNumber = numbers.get(0);
        Double secondNumber = numbers.get(1);

        switch (mathOperator) {
            case '*':
                return multiply(firstNumber, secondNumber);
            case '/':
                return divide(firstNumber, secondNumber);
            case '+':
                return add(firstNumber, secondNumber);
            case '-':
                return subtract(firstNumber, secondNumber);
        }
        return 0;
    }

    private List<Double> getNumbersFromSingleExpression(String expression) {
        List<Double> numbers = new ArrayList<>();
        int mathOperator = getOperatorIndex(expression);
        Double firstNumber = Double.parseDouble(expression.substring(0, mathOperator));
        Double secondNumber = Double.parseDouble(expression.substring(mathOperator + 1));
        numbers.add(firstNumber);
        numbers.add(secondNumber);
        return numbers;
    }

    private boolean isMathOperator(char symbol) {
        return mathOperators.contains(symbol);
    }

    private int getOperatorIndex(String expression) {
        for (int index = 1; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (isMathOperator(currentChar)) {
                return index;
            }
        }
        return 0;
    }

    private char getOperatorSingleExpression(String expression) {
        for (int index = 1; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (isMathOperator(currentChar)) {
                return currentChar;
            }
        }
        return 0;
    }

    private String calculateParenthesis(String expression) {
        while (hasParentheses(expression)) {
            String nestedExpression = getNestedParenthesesExpression(expression);
            String calculateExpression = nestedExpression;
            calculateExpression = calculatePriorityExpressions(calculateExpression);
            calculateExpression = calculateNonPriorityExpressions(calculateExpression);
            String toReplaceExpression = "(" + nestedExpression + ")";
            expression = expression.replaceAll(Pattern.quote(toReplaceExpression),calculateExpression);
        }
        return expression;
    }

    private int getExpressionsCount(String expression) {
        int countExpressions = 0;
        for (int index = 1; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (isMathOperator(currentChar))
                countExpressions++;
        }
        return countExpressions;
    }

    private String getNestedParenthesesExpression(String expression) {
        while (hasParentheses(expression)) {
            int indexOfClosingParentheses = expression.indexOf(')');
            for (int index = indexOfClosingParentheses - 1; index >= 0; index--) {
                if (expression.charAt(index) == '(') {
                    expression = expression.substring(index + 1, indexOfClosingParentheses);
                    break;
                }
            }
        }
        return expression;
    }

    private boolean hasParentheses(String expression) {
        return expression.contains("(");
    }

    private boolean containsMultipleMathOperators(String expression) {
        for (int index = 1; index < expression.length() - 1; index++) {
            char currentChar = expression.charAt(index);
            char nextChar = expression.charAt(index + 1);
            if ((isMathOperator(currentChar) && nextChar==currentChar)) {
                return true;
            }
        }
        return false;
    }

    private boolean parenthesesAreMatching(String expression) {
        Stack<Character> parenthesesStack = new Stack<>();
        for (int index = 0; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (currentChar == '(') {
                parenthesesStack.push(currentChar);
            }
            if (currentChar == ')') {
                if (parenthesesStack.isEmpty() || parenthesesStack.peek() != '(') {
                    return false;
                }
                parenthesesStack.pop();
            }
        }
        return parenthesesStack.isEmpty();
    }

    private String removeWhiteSpaces(String expression) {
        return expression.replaceAll("\\s+", "");
    }

    private boolean containsIllegalSymbols(String expression) {
        for (int index = 0; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (!(Character.isDigit(currentChar) || validSymbols.contains(currentChar))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsIllegalArguments(String expression) {
        return containsIllegalSymbols(expression) || !parenthesesAreMatching(expression)
                || containsMultipleMathOperators(expression) || expression.equals("")
                || startsWithMathSignOtherThanMinus(expression);
    }
}
