package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 17:25
 */
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.processOrder();
        context.processOrder();
        context.processOrder();
    }
}

interface OrderState {
    void handleOrder(Context context);
}

class NewOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("新增订单");
        context.setState(new PaidOrderState());
    }
}

class PaidOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("支付完成");
        context.setState(new ShipedOrderState());
    }
}

class ShipedOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("订单完成购买，没有其他需要执行");
    }
}

class Context {
    private OrderState state;

    public Context() {
        this.state = new NewOrderState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void processOrder() {
        state.handleOrder(this);
    }
}
