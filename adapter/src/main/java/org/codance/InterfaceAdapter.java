package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 22:07
 */
public class InterfaceAdapter {
    public static void main(String[] args) {
        CatAdapter catAdapter = new CatAdapter(new HelloKitty());
        catAdapter.bark();
    }
}

interface Cat {
    void meow();
}

interface Dog {
    void bark();
}

class HelloKitty implements Cat {

    @Override
    public void meow() {
        System.out.println("喵喵喵");
    }
}

class Husky implements Dog {

    @Override
    public void bark() {
        System.out.println("汪汪汪");
    }
}

class CatAdapter implements Dog {
    Cat cat;

    public CatAdapter(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void bark() {
        cat.meow();
    }
}
