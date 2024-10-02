package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 19:32
 */
public class Single {
    private Single() {}

    private static class Holder {
        private static final Single single = new Single();
    }

    public static Single getInstance() {
        return Holder.single;
    }
}
