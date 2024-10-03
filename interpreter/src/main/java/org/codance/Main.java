package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 23:17
 */
public class Main {
    public static void main(String[] args) {
        Number number = new Number(3);
        Number number1 = new Number(5);
        Number number2 = new Number(2);
        Add add = new Add(number, number1);
        Subtract subtract = new Subtract(add, number2);
        System.out.println(subtract.interpret());
    }
}

interface Expression {
    int interpret();
}

class Number implements Expression {
    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}

class Add implements Expression {
    private Expression left;
    private Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() + right.interpret();
    }
}

class Subtract implements Expression {
    private Expression left;
    private Expression right;

    public Subtract(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() - right.interpret();
    }
}
