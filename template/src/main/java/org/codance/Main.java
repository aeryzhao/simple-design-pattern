package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 16:34
 */
public class Main {
    public static void main(String[] args) {
        Template template = new Template();
        template.templateMethod();
    }
}

abstract class AbstractTemplate {
    public void templateMethod() {
        init();
        apply();
        end();
    }

    public void init() {
        System.out.println("上班打卡");
    }

    public abstract void apply();

    public void end() {
        System.out.println("下班回家");
    }
}

class Template extends AbstractTemplate{

    @Override
    public void apply() {
        System.out.println("工作");
        System.out.println("吃午饭");
        System.out.println("工作");
        System.out.println("健身");
    }
}
