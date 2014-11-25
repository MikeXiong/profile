package mx.algorithm.stack_queue;

import java.util.*;

/**
 * Created by hxiong on 7/2/14.
 */
public class Main {

    static void checkExpressionIsMacth(String expression){
        Stack<Character> sepStack = new Stack<>();

        int len = expression.length();
        for(int i = 0; i < len; i++){
            char ch = expression.charAt(i);
            if(SeparaterCharChecker.isSeparater_Left(ch)){
                sepStack.push(ch);
            }else if(SeparaterCharChecker.isSeparatoer_Right(ch)){
                if(!sepStack.isEmpty()){
                    if(!SeparaterCharChecker.isMatch(sepStack.pop(), ch)){
                        throw new RuntimeException("mismatch '" + ch + "' at index=" + i);
                    }
                }else{
                    throw new RuntimeException("mismatch '" + ch + "' at index=" + i);
                }
            }
        }
    }

    static ArithmaticReturn calculateNifixExpressionWrap(String expression){
        ArithmaticReturn rs = calculateNifixExpression(expression + OperatorHelper.expressionEND(), 0);
        return rs;
    }

    /**
     * calculate nifix expression with recursive
     * @param expression nifix expression
     * @param index
     * @return
     */
    static ArithmaticReturn calculateNifixExpression(String expression, int index){
        Stack<Integer> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        OprWrap wrap = null;
        ArithmaticReturn rs = null;
        while(index < expression.length()){
            wrap = getOperandsOrOperatorInNifixExpression(expression, index);
            if(wrap.isOperand){
                operands.push(wrap.getOprand());
                index = wrap.nextPointer;
            }else if(OperatorHelper.isBracket_Left(wrap.getOperator())){
                ArithmaticReturn ar = calculateNifixExpression(expression, wrap.nextPointer);
                operands.push(ar.result);
                index = ar.nextPointer;
            }else if(OperatorHelper.isBracket_Right(wrap.getOperator()) || index == expression.length() - 1){
                while(!operators.isEmpty()){
                    doOperator(operands, operators.pop());
                }
                index = wrap.nextPointer;
                rs = new ArithmaticReturn(index, operands.pop());
                break;
            }else{
                while(!operators.isEmpty() && OperatorHelper.isPriorityNotSmaller(operators.peek(), wrap.getOperator())){
                    doOperator(operands, operators.pop());
                }
                operators.push(wrap.getOperator());
                index = wrap.nextPointer;
            }
        }
        return rs;
    }

    static class ArithmaticReturn{
        int nextPointer = 0;
        int result = 0;

        ArithmaticReturn(int nextPointer, int result) {
            this.nextPointer = nextPointer;
            this.result = result;
        }

        @Override
        public String toString() {
            return "ArithmaticReturn{" +
                    "nextPointer=" + nextPointer +
                    ", result=" + result +
                    '}';
        }
    }


    /**
     * calculate nifix expression via stack
     * @param expression nifix expression
     * @return int
     */
    static int calculateNifixExpression2(String expression){
        Stack<Integer> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        expression += OperatorHelper.expressionEND();
        int index = 0;
        OprWrap wrap = null;
        while(index < expression.length()){
            wrap = getOperandsOrOperatorInNifixExpression(expression, index);
            System.out.println("wrap=" + wrap);
            if(wrap.isOperand){
                operands.push(wrap.getOprand());
            }else{
                String operator = wrap.getOperator();
                if(OperatorHelper.isBracket_Left(operator)){
                    operators.push(operator);
                }else if(OperatorHelper.isBracket_Right(operator)){
                    calculateBracket(operands, operators);
                }else{
                    calculateNormal(operands, operators, operator);
                }
            }

            index = wrap.nextPointer;
        }

        System.out.println(operands);
        System.out.println(operators);

        return operands.pop();
    }

    /**
     * convert nifix expression to Reserve Polish Notation(postfix/suffix expression)
     * @param expression nifix expression
     * @return
     */
    static String convertNifixExpression2RPN(String expression){
        expression = expression + OperatorHelper.expressionEND();
        String rpn = "";
        Stack<String> operators = new Stack<>();
        int index = 0;
        OprWrap wrap = null;
        while(index < expression.length()){
            wrap = getOperandsOrOperatorInNifixExpression(expression, index);
//            System.out.println("wrap:" + wrap);
            if(wrap.isOperand){
                rpn += wrap.getOprand();
            }else if(OperatorHelper.isBracket_Left(wrap.getOperator())){
                operators.push(wrap.getOperator());
            }else if(OperatorHelper.isBracket_Right(wrap.getOperator())){
                while(!OperatorHelper.isBracket_Left(operators.peek())){
                    rpn += operators.pop();
                }
                //pop left bracket
                operators.pop();
            }else{
                while(!operators.isEmpty() && !OperatorHelper.isBracket_Left(operators.peek())
                        && OperatorHelper.isPriorityNotSmaller(operators.peek(), wrap.getOperator())){
                    rpn += operators.pop();
                }
                operators.push(wrap.getOperator());
            }
            index = wrap.nextPointer;
        }
        return rpn;
    }


    static int calculatePostfixExpression(String expression){
        Stack<Integer> operands = new Stack<>();
        int index = 0;
        OprWrap wrap = null;
        while(index < expression.length()){
            wrap = getOprandOrOperatorInPostfixExpression(expression, index);
            if(wrap.isOperand){
                operands.push(wrap.getOprand());
            }else{
                doOperator(operands, String.valueOf(wrap.getOperator()));
            }
            index = wrap.nextPointer;
        }
        return operands.pop();
    }


    private static void calculateNormal(Stack<Integer> operands, Stack<String> operators, String operator) {
        System.out.println("calculateNormal:operands=" + operands + ", operators=" + operators + ", operator is:" + operator);

        while(!operators.isEmpty() && !OperatorHelper.isBracket_Left(operators.peek())
                && OperatorHelper.isPriorityNotSmaller(operators.peek(), operator)){
            doOperator(operands, operators.pop());
        }
        operators.push(operator);
    }

    private static void calculateBracket(Stack<Integer> operands, Stack<String> operators) {
        System.out.println("calculateBracket:operands=" + operands + ", operators=" + operators);
        String last = null;
        while(!OperatorHelper.isBracket_Left(last = operators.pop())){
            doOperator(operands, last);
        }
    }

    private static void doOperator(Stack<Integer> operands, String operator){
        int b = operands.pop();
        int a = operands.pop();
        operands.push(OperatorHelper.doOperator(a, b, operator));
    }

    /**
     *
     * @param expression postfix expression
     * @param index
     * @return
     */
    private static OprWrap getOprandOrOperatorInPostfixExpression(String expression, int index){
        final char NUM_WRAP_LEFT = '(';
        final char NUM_WRAP_RIGHT = ')';
        OprWrap wrap = null;
        char c = expression.charAt(index);
        if(NUM_WRAP_LEFT == c){
            String operand = "";
             while(NUM_WRAP_RIGHT != (c = expression.charAt(++index))){
                operand += c;
             }
            wrap = new OprWrap(true, operand, index);
        }else{
           wrap = new OprWrap(OperatorHelper.isOperand(c), String.valueOf(c), ++index);
        }
        return wrap;
    }

    /**
     * @param expression nifix expressioni
     * @param index
     * @return
     */
    static OprWrap getOperandsOrOperatorInNifixExpression(String expression, int index){
        char first = expression.charAt(index);
        final String NUM = "0123456789";
        OprWrap wrap = null;
        if(-1 < NUM.indexOf(first)){
            String operands = "";
            while(index < expression.length()){
                if(-1 < NUM.indexOf(first = expression.charAt(index))){
                    operands += first;
                    index++;
                }else break;
            }
            wrap = new OprWrap(true, operands, index);
        }else{
            wrap = new OprWrap(false, String.valueOf(first), ++index);
        }
        return wrap;
    }

    static class OperatorHelper{

        private final static Map<String, Integer> OPERATOR_LEVEL = new HashMap<>();
        private final static String END = "=";
        static {
            OPERATOR_LEVEL.put(expressionEND(), -1);//set the end tag to lowest priority
            OPERATOR_LEVEL.put("+", 1);
            OPERATOR_LEVEL.put("-", 1);
            OPERATOR_LEVEL.put("*", 2);
            OPERATOR_LEVEL.put("/", 2);
        }



        static String expressionEND(){
            return END;
        }

        static boolean isPrioritySmaller(String opr1, String  opr2){
            if(OPERATOR_LEVEL.get(opr1) < OPERATOR_LEVEL.get(opr2)){
                return true;
            }
            return false;
        }

        static boolean isPriorityNotSmaller(String opr1, String opr2){
            return !isPrioritySmaller(opr1, opr2);
        }

        static boolean isBracket_Left(String opr){
            return "(".equals(opr);
        }

        static boolean isBracket_Right(String opr){
            return ")".equals(opr);
        }

        static boolean isEnd(String opr){
            return OperatorHelper.expressionEND().equals(opr);
        }

        static boolean isOperand(char s){
            return -1 < "0123456789".indexOf(s);
        }

        static int doOperator(int a, int b, String operator){
            System.out.println("do:[" + a + operator + b + "]");
            switch (operator){
                case "+": return a + b;
                case "-": return a - b;
                case "*": return a * b;
                case "/": return a / b;
                default: throw new RuntimeException("Unsupported operator:" + operator);
            }
        }
    }

    static class OprWrap{
        boolean isOperand = false;
        String opr = null;
        int nextPointer = -1;

        OprWrap(boolean isOperand, String opr, int nextPointer) {
            this.isOperand = isOperand;
            this.opr = opr;
            this.nextPointer = nextPointer;
        }

        int getOprand(){
            if(!this.isOperand)throw new RuntimeException("The opr is not operand");
            return Integer.valueOf(this.opr);
        }

        String getOperator(){
            if(this.isOperand)throw new RuntimeException("The opr is not operator");
            return this.opr;
        }

        @Override
        public String toString() {
            return "OprWrap{" +
                    "isOperand=" + isOperand +
                    ", opr='" + opr + '\'' +
                    ", nextPointer=" + nextPointer +
                    '}';
        }
    }

    static void test_ExpressionCheck(){
        String expression = "a{b[c<(d>)e]f}g}";
        checkExpressionIsMacth(expression);
    }

    static void test_PriorityQueue(){
        System.out.println("test_PriorityQueue BEGIN !");
        PriorityQuery<Integer> q = new PriorityQuery<>(6);
        q.put(-1);
        q.put(10);
        q.put(100);
        q.put(-100);
        q.put(9);
        q.put(9);
        try{
            q.put(8);
        }catch (Exception e){
            System.out.println("Must throw exception here. error=" + e.toString());
        }

        System.out.println("q=" + q);
        System.out.println("get()=" + q.get());
        System.out.println("q=" + q);
        q.put(-1000);
        System.out.println("q=" + q);

        System.out.println("test_PriorityQueue END !");
    }

    static void test_calculateNifixExpression2(){
        System.out.println("test_calculateNifixExpression2 BEGIN !");
        String orig = "1+2*3*(3+4-(5*1))+1*(2+3)+1+100*0-100";
        System.out.println("orig expression=" + orig);
        System.out.println("expression result=" + calculateNifixExpression2(orig));
        System.out.println("test_calculateNifixExpression2 END !");
    }

    static void test_calculateNifixExpressionWrap(){
        System.out.println("test_calculateNifixExpressionWrap BEGIN !");
        String orig = "1+2+3*2*(1+1)*(2+(1*3))";
        System.out.println("orig expression=" + orig);
        System.out.println("expression result=" + calculateNifixExpressionWrap(orig));
        System.out.println("test_calculateNifixExpressionWrap END !");
    }

    static void test_convertNifixExpression2RPN(){
        System.out.println("test_convertNifixExpression2RPN BEGIN !");
        String orig = "3*(4+5)-6/(1+2)";
        System.out.println("orig expression=" + orig);
        System.out.println("RPN=" + convertNifixExpression2RPN(orig));
        System.out.println("test_convertNifixExpression2RPN END !");
    }

    static void test_calculatePostfixExpression(){
        System.out.println("test_calculatePostfixExpression BEGIN !");
        String orig = "345+*612+/-";
        System.out.println("orig expression=" + orig);
        System.out.println("expression result=" + calculatePostfixExpression(orig));
        System.out.println("test_calculatePostfixExpression END !");
    }


    public static void main(String[] args) {
//        test_ExpressionCheck();
//        test_PriorityQueue();
//        test_calculateNifixExpression2();
//        test_calculateNifixExpressionWrap();
//        test_convertNifixExpression2RPN();
        test_calculatePostfixExpression();
    }
}

class SeparaterCharChecker{

    private final static List<Sep> SEP_LIST = new ArrayList<>();

    private final static Map<Character, Character> RIGHT_SEP_MAP = new HashMap<>();
    private final static Map<Character, Character> LEFT_SEP_MAP = new HashMap<>();
    static {
        SEP_LIST.add(new Sep('{', '}'));
        SEP_LIST.add(new Sep('(', ')'));
        SEP_LIST.add(new Sep('[', ']'));
        SEP_LIST.add(new Sep('<', '>'));

        for(Sep sep: SEP_LIST){
            RIGHT_SEP_MAP.put(sep.right, sep.left);
            LEFT_SEP_MAP.put(sep.left, sep.right);
        }
    }


    static boolean isMatch(char left, char right){
        Character _right = LEFT_SEP_MAP.get(left);
        return null != _right && _right.charValue() == right;
    };

    static boolean isSeparatoer_Right(char ch){
        return null != RIGHT_SEP_MAP.get(ch);
    }

    static boolean isSeparater_Left(char ch){
        return null != LEFT_SEP_MAP.get(ch);
    }

    static class Sep{
        Character left = null;
        Character right = null;

        Sep(Character left, Character right) {
            this.left = left;
            this.right = right;
        }

    }
}

class Queue<T>{

    private Object[] store = null;
    private int front = 0;
    private int rear = -1;
    private int maxSize = 0;

    public Queue(int n){
        maxSize = n + 1;
        store = new Object[maxSize];
        front = 0;
        rear = -1;
    }

    public boolean isFull(){
        return (rear + 2 == front) || (front + maxSize - 2 == rear);
    }

    public boolean isEmpty(){
        return (rear + 1 == front) ||(front + maxSize - 1 == rear);
    }

    public void put(T t){
        if(this.isFull()){
            throw new RuntimeException("queue has full, front=" + this.front + ", rear=" + this.rear);
        }
        this.store[++rear] = t;
    }

    public T get(){
        if(this.isFull()){
            throw new RuntimeException("queue has empty, front=" + this.front + ", rear=" + this.rear);
        }
        return (T) this.store[front++];
    }

    public T peek(){
        if(this.isFull()){
            throw new RuntimeException("queue has empty, front=" + this.front + ", rear=" + this.rear);
        }
        return (T) this.store[front];
    }

    public int size(){
        if(this.isEmpty())return 0;
        if(front > rear){
            return (this.maxSize - front) + (rear + 1);
        }else{
            return this.rear - this.front + 1;
        }
    }

}

class PriorityQuery<T extends Comparable<? super T>>{

//    private final static int FRONT = 0;
//    private int rear = 0;

    private Object[] store = null;

    private int size = 0;

    public PriorityQuery(int capacity){
        this.store = new Object[capacity];
        this.size = 0;
    }

    public boolean isFull(){
        return this.size == this.store.length;
    }

    public boolean isEmpty(){
        return 0 == this.size;
    }

    public void put(T t){
        if(this.isFull()){
            throw new RuntimeException("The queue is full, size=" + this.size);
        }
        if(this.isEmpty()){
            this.store[0] = t;
        }else{
            int p = size - 1;
            while(-1 < p){
                if(0 < ((T)this.store[p]).compareTo(t)){
                    this.store[p + 1] = this.store[p];
                }else{
                    break;
                }
                p--;
            }
            this.store[++p] = t;
        }
        this.size++;
    }

    public T get(){
        return (T) this.store[--this.size];
    }

    public T peek(){
        return (T) this.store[this.size - 1];
    }

    @Override
    public String toString() {
        return "PriorityQuery{" +
                "store=" + Arrays.toString(store) +
                ", size=" + size +
                '}';
    }
}