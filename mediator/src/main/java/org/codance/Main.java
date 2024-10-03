package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 18:57
 */
public class Main {
    public static void main(String[] args) {
        ConcreteChatMediator mediator = new ConcreteChatMediator();
        User jack = new User(mediator, "Jack");
        mediator.addUser(jack);
        mediator.addUser(new User(mediator, "Lucy"));
        mediator.addUser(new User(mediator, "Tom"));
        jack.send("Cp 滴滴");
    }
}

interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

class ConcreteChatMediator implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void sendMessage(String message, User user) {
        // 通过中介发送消息，中介再把消息转发给其他人
        users.forEach(e -> {
            if (e != user) {
                e.receive(message);
            }
        });
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}

class User {
    protected ChatMediator chatMediator;
    private String name;

    public User(ChatMediator chatMediator, String name) {
        this.chatMediator = chatMediator;
        this.name = name;
    }

    public void send(String message) {
        System.out.println(this.name + "发送了消息：" + message);
        chatMediator.sendMessage(message, this);
    }
    public void receive(String message) {
        System.out.println(name + "收到消息：" + message);
    }
}

