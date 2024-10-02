package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 17:36
 */
public class Main {
    public static void main(String[] args) {
        AppleFactory appleFactory = new AppleFactory();
        Phone iphone16 = appleFactory.makePhone("16");
        iphone16.call("乔布斯");
        Phone iphone15 = appleFactory.makePhone("15");
        iphone15.call("乔布斯");

        Phone mateXT = new HuaweiFactory().makePhone("三折屏");
        mateXT.call("余承东");
    }
}

interface Factory {
    Phone makePhone(String name);
}

class AppleFactory implements Factory {

    @Override
    public Phone makePhone(String name) {
        if ("16".equals(name)) {
            return new Iphone16();
        } else if ("15".equals(name)) {
            return new Iphone15();
        }
        return null;
    }
}

class HuaweiFactory implements Factory {

    @Override
    public Phone makePhone(String name) {
        if ("三折屏".equals(name)) {
            return new MateXT();
        } else if ("60".equals(name)) {
            return new Mate60();
        }
        return null;
    }
}

interface Phone {
    void call(String name);
}

class Iphone16 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 iphone 16 打给" + name);
    }
}

class Iphone15 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 iphone 15 打给" + name);
    }
}

class MateXT implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 Mate XT 三折屏打给" + name);
    }
}

class Mate60 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 Mate 60 打给" + name);
    }
}
