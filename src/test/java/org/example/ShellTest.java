package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShellTest {


    //
    private static final String osName = System.getProperty("os.name");

    public static boolean isLinux() {
        return (osName.startsWith("Linux"));
    }

    @Test
    void testCd() throws IOException {
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            shell.handleInput("cd \"/home/user/Documents\"");
            assertEquals("/home/user/Documents", inputHandler.PWD.pwd());
        } else {
            shell.handleInput("cd target");
            Path currentPath = Paths.get("");
            String absolutePath = currentPath.toAbsolutePath().toString();
            assertEquals(STR."\{absolutePath}\\target", inputHandler.PWD.pwd());
        }
    }


    @Test
    void testLs() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            ArrayList<File> files =  inputHandler.listDirectoriesFiles.LS("/home/user/Documents", true);
            assertFalse(files.isEmpty(), "This is command should return the list of the files in the directory");
        } else {
            ArrayList<File> files =  inputHandler.listDirectoriesFiles.LS(inputHandler.PWD.pwd(), true);
            assertFalse(files.isEmpty(), "This is command should return the list of the files in the directory");
        }
    }


    @Test
    void testTouch() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.createFile.touch(("/home/user/Documents/test.txt"));
            assertTrue(isCreated, "This is command should create file in the directory");
        } else {
            String CreateFileAt = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            boolean isCreated = inputHandler.createFile.touch(CreateFileAt);
            assertTrue(isCreated, "This is command should create file in the directory");
        }
    }

    @Test
    void testMkdir() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.createDirectory.mkdir("/home/user/Documents/Dummy");
            assertTrue(isCreated, "This is command should create Directory in the directory");
        } else {
            String CreateDirectoryAt = STR."\{inputHandler.PWD.pwd()}\\Dummy";
            boolean isCreated = inputHandler.createDirectory.mkdir(CreateDirectoryAt);
            assertTrue(isCreated, "This is command should create Directory in the directory");
        }
    }
    @Test
    void testRmDir() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.deleteDirectory.rmdir("/home/user/Documents/Dummy");
            assertTrue(isCreated, "This is command should create Directory in the directory");
        } else {
            String DeleteDirectoryAt = STR."\{inputHandler.PWD.pwd()}\\Dummy";
            boolean isCreated = inputHandler.deleteDirectory.rmdir(DeleteDirectoryAt);
            assertTrue(isCreated, "This is command should Delete Directory in the directory");
        }
    }

    @Test
    void testRm() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.removeFile.rm("/home/user/Documents/test.txt");
            assertTrue(isCreated, "This is command should create Directory in the directory");
        } else {
            String DeleteFileAt = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            boolean isCreated = inputHandler.removeFile.rm(DeleteFileAt);
            assertTrue(isCreated, "This is command should Delete File in the directory");
        }
    }

    @Test
    void testRedirectOutput() throws IOException  {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            inputHandler.handleInput("ls \"/home/user/Documents\" > listOfFile.txt");

        } else {
            String DeleteFileAt = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            boolean isCreated = inputHandler.removeFile.rm(DeleteFileAt);
            assertTrue(isCreated, "This is command should Delete File in the directory");
        }

    }



}