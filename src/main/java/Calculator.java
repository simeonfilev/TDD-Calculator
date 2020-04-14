import java.util.*;

public class Calculator {
    public static final List<Character> validSymbols = List.of('*', '/', '+', '-', '(', ')');
    public static final List<Character> mathOperators = List.of('*', '/', '+', '-');

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

    public double calculate(String expression){
        expression = removeWhiteSpaces(expression);

        if (containsIllegalArguments(expression)) {
            throw new UnsupportedOperationException();
        }

        List<String> tokenizedExpression = tokenizeMathExpression(expression);
        List<String> expressionToRPN = convertExpressionToRPN(tokenizedExpression);

        return calculateRPNExpression(expressionToRPN);
    }

    private static boolean isANumber(String input){
        try{
            Double.parseDouble(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private double calculateRPNExpression(List<String> tokens) {
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (isANumber(token)) {
                stack.push(token);
            }
            else {
                if(stack.size()<2){
                    if(token.equals("-")){
                        stack.push(String.valueOf(Double.parseDouble(stack.pop()) * -1));
                    }
                    continue;
                }
                double d2 = Double.parseDouble(stack.pop());
                double d1 = Double.parseDouble(stack.pop());

                double result = 0.0;
                switch (token){
                    case "+":
                        result = add(d1,d2);
                        break;
                    case "*":
                        result = multiply(d1,d2);
                        break;
                    case "-":
                        result = subtract(d1,d2);
                        break;
                    case "/":
                        result = divide(d1,d2);
                }
                stack.push( String.valueOf( result ));
            }
        }
        return Double.parseDouble(stack.pop());
    }

    private boolean isMathOperator(char c){
        return mathOperators.contains(c);
    }

    public List<String> tokenizeMathExpression(String expression){
        List<String> tokenizedExpression = new ArrayList<>();
        StringBuilder acc = new StringBuilder();
        for(int i=0;i<expression.length();i++){
            char currentChar = expression.charAt(i);
            if(Character.isDigit(currentChar)){
                acc.append(currentChar);
            }
           else if(currentChar =='-' && i==0){
                acc.append('-');
            }
           else if(isMathOperator(currentChar) && expression.charAt(i+1)=='-'){
                acc.append('-');
            }
            else if(validSymbols.contains(currentChar)){
                if(acc.length()!=0){
                    tokenizedExpression.add(acc.toString());
                    acc = new StringBuilder();
                }
                tokenizedExpression.add(String.valueOf(expression.charAt(i)));
            }
        }
        if(acc.length()!=0)
            tokenizedExpression.add(String.valueOf(acc));
        return tokenizedExpression;
    }

    public List<String> convertExpressionToRPN(List<String> inputTokens)
    {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : inputTokens) {
            if(isANumber(token)){
                output.add(token);
            }
            else if(token.equals("(")){
                stack.push(token);
            }
            else if(token.equals(")")){
                while (!stack.empty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            }
            else if(validSymbols.contains(token.charAt(0))){
                while (!stack.empty() && (operatorPriorityCount(token)<=operatorPriorityCount(stack.peek()))) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
            else{
                throw new UnsupportedOperationException();
            }
        }
        while (!stack.empty()) {
            output.add(stack.pop());
        }
        return output;
    }

    private int operatorPriorityCount(String operator){
        Map<String,Integer> priorityOperatorsMap = new HashMap<>();
        priorityOperatorsMap.put("-",1);
        priorityOperatorsMap.put("+",1);
        priorityOperatorsMap.put("*",10);
        priorityOperatorsMap.put("/",10);
        priorityOperatorsMap.put("(",0);
        return priorityOperatorsMap.get(operator);
    }

    private boolean parenthesesAreNotMatching(String expression) {
        Stack<Character> parenthesesStack = new Stack<>();
        for (int index = 0; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            if (currentChar == '(') {
                parenthesesStack.push(currentChar);
            }
            if (currentChar == ')') {
                if (parenthesesStack.isEmpty() || parenthesesStack.peek() != '(') {
                    return true;
                }
                parenthesesStack.pop();
            }
        }
        return !parenthesesStack.isEmpty();
    }

    private String removeWhiteSpaces(String expression) {
        return expression.replaceAll("\\s+", "");
    }

    private boolean containsMultipleMathOperators(String expression){
        for(int index =0; index<expression.length()-1;index++){
            char currentChar = expression.charAt(index);
            char nextChar = expression.charAt(index+1);
            if(isMathOperator(currentChar) && currentChar == nextChar){
                return true;
            }
        }
        return false;
    }

    private boolean startsWithASignOtherThanMinus(String expression){
        return isMathOperator(expression.charAt(0)) && expression.charAt(0) != '-';
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
        return containsIllegalSymbols(expression) || parenthesesAreNotMatching(expression)
                || containsMultipleMathOperators(expression) || expression.equals("")
                || startsWithASignOtherThanMinus(expression);
    }
}
