package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 16:16
 */
public class Main {
    public static void main(String[] args) {
        Manager approver = new Manager();
        CEO ceo = new CEO();
        // 1.建立责任链
        approver.setNextApprover(ceo);
        // 2.进行审批处理
        approver.request(1000);
        approver.request(5000);
    }
}

abstract class Approver {
    protected Approver nextApprover;

    public void setNextApprover(Approver approver) {
       this.nextApprover = approver;
    }

    public abstract void request(int amount);
}

class Manager extends Approver {

    @Override
    public void request(int amount) {
        if (amount <= 1000) {
            System.out.println("经理批准了 " + amount + " 元");
        } else if (nextApprover != null) {
            nextApprover.request(amount);
        }
    }
}

class CEO extends Approver {

    @Override
    public void request(int amount) {
        if (amount > 1000) {
            System.out.println("CEO批准了 " + amount + " 元");
        }
    }
}
