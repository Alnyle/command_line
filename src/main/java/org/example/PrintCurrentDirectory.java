package org.example;

public class PrintCurrentDirectory {

    public String pwd() {
        System.out.println(Shell.currentPath);
        return Shell.currentPath;
    }
}
