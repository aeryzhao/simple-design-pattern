package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 21:53
 */
public class Main {
    public static void main(String[] args) {
        PhoneProxy phoneProxy = new PhoneProxy();
        phoneProxy.makePhone();
    }
}

interface PhoneService {
    void makePhone();
}

class PhoneServiceImpl implements PhoneService {

    @Override
    public void makePhone() {
        System.out.println("制作手机");
    }
}

class PhoneProxy implements PhoneService {
    // 被代理的真实对象
    PhoneService phoneService = new PhoneServiceImpl();

    @Override
    public void makePhone() {
        // 代理前处理
        System.out.println("以旧换新");
        phoneService.makePhone();
        // 代理后处理
        System.out.println("给手机赠送个贴膜");
    }
}
