package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 22:16
 */
public class ClassAdapter {
    public static void main(String[] args) {
        ServiceAdapter adapter = new ServiceAdapter();
        adapter.methodA();
        adapter.methodB();
    }
}

class Service {
    public void methodA() {
        System.out.println("method A");
    }
}

interface Target {
    void methodA();
    void methodB();
}

class ServiceAdapter extends Service implements Target {

    @Override
    public void methodB() {
        System.out.println("实现方法 B");
    }
}
