package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 19:23
 */
public class LazySingle {
    public static void main(String[] args) {
        Lazy instance = Lazy.getInstance();
    }
}

class Lazy {
    // volatile 保证可见性
    private static volatile Lazy INSTANCE;
    private Lazy() {}

    public static Lazy getInstance() {
        if (INSTANCE == null) {
            // 双重检测保证多线程安全
            synchronized (LazySingle.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Lazy();
                }
            }
        }
        return INSTANCE;
    }
}
