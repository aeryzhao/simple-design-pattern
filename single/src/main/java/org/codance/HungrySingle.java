package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 19:19
 */
public class HungrySingle {
    public static void main(String[] args) {
        Hungry instance = Hungry.getInstance();
    }
}

class Hungry {
    private static Hungry INSTANCE = new Hungry();
    private Hungry() {}

    public static Hungry getInstance() {
        return INSTANCE;
    }
}

