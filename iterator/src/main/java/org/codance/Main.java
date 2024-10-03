package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 19:17
 */
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("《设计模式》"));
        library.addBook(new Book("《数据结构》"));
        library.addBook(new Book("《重构：改善既有代码》"));
        Iterator iterator = library.iterator();
        while (iterator.hasNext()) {
            Book book = (Book) iterator.next();
            System.out.println(book.getName());
        }
    }
}

interface Iterator {
    boolean hasNext();
    Object next();
}

class ConcreteIterator implements Iterator {
    private List<Book> books;
    private int index;

    public ConcreteIterator(List<Book> list) {
        this.books = list;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < books.size();
    }

    @Override
    public Book next() {
        return books.get(index++);
    }
}

interface BookCollection {
    Iterator iterator();
}

class Library implements BookCollection {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator(books);
    }
}

class Book {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


