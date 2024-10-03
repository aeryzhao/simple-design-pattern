package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 15:46
 */
public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new ConcreteObserver(subject);
        new ConcreteObserver(subject);
        subject.setState(1);
    }
}

class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void setState(int state) {
        this.state = state;
        noticeAll();
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    private void noticeAll() {
        observers.forEach(Observer::update);
    }
}

interface Observer {
    void update();
}

class ConcreteObserver implements Observer{
    private Subject subject;

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        subject.register(this);
    }

    @Override
    public void update() {
        System.out.println("收到主题更新了，开始行动！");
    }
}
