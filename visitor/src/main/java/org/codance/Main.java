package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 17:55
 */
public class Main {
    public static void main(String[] args) {
        ObjectStructure list = new ObjectStructure();
        list.addElement(new ElementA());
        list.applyVisitor(new ConcreteVisitor());
    }
}

interface Visitor {
    void visitorElement(Element element);
}

class ConcreteVisitor implements Visitor {

    @Override
    public void visitorElement(Element element) {
        element.operation();
    }
}

interface Element {
    void accept(Visitor visitor);
    void operation();
}

class ElementA implements Element {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitorElement(this);
    }

    @Override
    public void operation() {
        System.out.println("ElementA 的特殊行为");
    }
}

class ObjectStructure {
    private List<Element> elements = new ArrayList<>();

    public void addElement(Element element) {
        elements.add(element);
    }

    public void applyVisitor(Visitor visitor) {
        elements.forEach(e -> e.accept(visitor));
    }
}



