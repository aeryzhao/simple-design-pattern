package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 19:45
 */
public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();
        Caretaker caretaker = new Caretaker();
        editor.saveContent("版本一");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.saveContent("版本二");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.saveContent("版本三");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.restore(caretaker.getMemento(0));
        System.out.println(editor.getContent());
    }
}

class Editor {
    private String content;

    public void saveContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Memento save() {
        return new Memento(content);
    }

    public void restore(Memento memento) {
        this.content = memento.getContent();
    }
}

class Memento {
    private String content;

    public Memento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();

    public void saveMemento(Memento memento) {
        mementoList.add(memento);
    }

    public Memento getMemento(int index) {
        return mementoList.get(index);
    }
}