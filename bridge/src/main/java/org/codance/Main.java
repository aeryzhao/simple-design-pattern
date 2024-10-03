package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 10:03
 */
public class Main {
    public static void main(String[] args) {
        RedPen redPen = new RedPen();
        Circle circle = new Circle(redPen, 10);
        circle.draw();
    }
}

interface Draw {
    void draw(int radius, int x, int y);
}

class RedPen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用红色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}

class GreenPen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用绿色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}

class BluePen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用蓝色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}

abstract class Shape {
    protected Draw draw;

    public Shape(Draw draw) {
        this.draw = draw;
    }

    public abstract void draw();
}

class Circle extends Shape {
    private int radius;

    public Circle(Draw draw, int radius) {
        super(draw);
        this.radius = radius;
    }

    @Override
    public void draw() {
        draw.draw(radius, 0, 0);
    }
}

class Ractangle extends Shape {
    private int x, y;

    public Ractangle(Draw draw, int x, int y) {
        super(draw);
    }

    @Override
    public void draw() {
        draw.draw(0, x, y);
    }
}