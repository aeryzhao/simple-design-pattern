package org.codance;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zhaoxg
 * @date 2024/10/3 12:51
 */
public class Main {
    public static void main(String[] args) {
        String[] strings = new String[]{"Red", "Green", "Black"};
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Pen pen = PenFactory.getPen(strings[Math.abs(random.nextInt()) % strings.length]);
            System.out.println(pen.hashCode());
            pen.draw();
        }
    }
}

class Pen {
    private String color;

    public Pen(String color) {
        this.color = color;
    }

    public void draw() {
        System.out.println("使用" + color);
    }
}

class PenFactory {
    private static final Map<String, Pen> pens = new HashMap<>();
    private PenFactory() {}

    public static Pen getPen(String color) {
        if (pens.containsKey(color)) {
            return pens.get(color);
        }
        Pen p = new Pen(color);
        pens.put(color, p);
        return p;
    }
}
