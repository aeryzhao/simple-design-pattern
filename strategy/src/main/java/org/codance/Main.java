package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 15:29
 */
public class Main {
    public static void main(String[] args) {
        Context context = new Context(new RedPen());
        context.executeDraw();
    }
}

interface Strategy {
    void draw();
}

class RedPen implements Strategy {

    @Override
    public void draw() {
        System.out.println("使用红色笔绘画");
    }
}

class GreenPen implements Strategy {

    @Override
    public void draw() {
        System.out.println("使用绿色笔绘画");
    }
}

class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeDraw() {
        strategy.draw();
    }
}