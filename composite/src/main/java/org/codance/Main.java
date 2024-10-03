package org.codance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxg
 * @date 2024/10/3 11:39
 */
public class Main {
    public static void main(String[] args) {
        File fileA = new File("fileA");
        File fileB = new File("fileB");
        File fileC = new File("fileC");
        File fileD = new File("fileD");
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.add(fileA);
        folder1.add(fileB);
        folder2.add(fileC);
        folder2.add(fileD);
        folder1.add(folder2);
        folder1.display();
    }
}

interface FileComposite {
    default void add(FileComposite fileComposite) { throw new UnsupportedOperationException();}
    default void remove(FileComposite fileComposite) { throw new UnsupportedOperationException();}
    default FileComposite getChildren(int i) { throw new UnsupportedOperationException();}
    String getName();
    void display();
}

class File implements FileComposite {
    private String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public void display() {
        System.out.println("File: " + fileName);
    }
}

class Folder implements FileComposite {
    private List<FileComposite> children;
    private String fileName;

    public Folder(String fileName) {
        this.fileName = fileName;
        children = new ArrayList<>();
    }

    @Override
    public void add(FileComposite fileComposite) {
        children.add(fileComposite);
    }

    @Override
    public void remove(FileComposite fileComposite) {
        children.remove(fileComposite);
    }

    @Override
    public FileComposite getChildren(int i) {
        return children.get(i);
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public void display() {
        System.out.println("Folder: " + fileName);
        if (!children.isEmpty()) {
            children.forEach(FileComposite::display);
        }
    }
}
