package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 17:18
 */
public class Main {
    public static void main(String[] args) {
        Phone iphone = PhoneFactory.makePhone("苹果");
        iphone.call("乔布斯");
        Phone phone = PhoneFactory.makePhone("华为");
        phone.call("余承东");
    }
}

class PhoneFactory {
    public static Phone makePhone(String name) {
        if ("苹果".equals(name)) {
            return new ApplePhone();
        } else if ("华为".equals(name)) {
            return new HuaweiPhone();
        } else {
            return null;
        }
    }
}

interface Phone {
    void call(String name);
}

class ApplePhone implements Phone {
    @Override
    public void call(String name) {
        System.out.println("正在使用苹果手机打给" + name);
    }
}

class HuaweiPhone implements Phone {
    @Override
    public void call(String name) {
        System.out.println("正在使用华为手机打给" + name);
    }
}
