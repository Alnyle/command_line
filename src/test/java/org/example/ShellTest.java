package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ShellTest {


    // get current system name
    private static final String osName = System.getProperty("os.name");

    // check is system is linux based system
    public static boolean isLinux() {
        return (osName.startsWith("Linux"));
    }


    // test "cd" with valid directory path "directory name is `target`"
    @Test
    void testCd() throws IOException {

        Shell.restPath();
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

    // test "ls" without any arguments
    @Test
    void testLs() throws IOException {

        // rest the path to current programming directory
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        // get list of file and directories in passed path
        ArrayList<File> files;
        if (isLinux()) {
            files = inputHandler.listDirectoriesFiles.LS("/home/user/Documents", true);
        } else {
            files = inputHandler.listDirectoriesFiles.LS(inputHandler.PWD.pwd(), true);
        }
        assertFalse(files.isEmpty(), "This is command should return the list of the files in the directory");
    }


    @Test
    void testTouch() throws IOException {

        // rest the path to current programming directory
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.createFile.touch(("/home/user/Documents/test.txt"));
            assertTrue(isCreated, "This is command should create file in the directory");
        } else {
            // path to create file at
            String CreateFileAt = STR."\{inputHandler.PWD.pwd()}\\test.txt";

            // create file and check is created
            boolean isCreated = inputHandler.createFile.touch(CreateFileAt);
            assertTrue(isCreated, "This is command should create file in the directory");
        }
    }


    // path file name without file extension
    @Test
    void testTouchWithoutFileExtension() throws IOException {

        // rest the path to current programming directory
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.createFile.touch(("/home/user/Documents/test"));
            assertTrue(isCreated, "This is command should create file in the directory");
        } else {
            // path to create file at
            String CreateFileAt = STR."\{inputHandler.PWD.pwd()}\\test";

            // create file and check is created
            boolean isCreated = inputHandler.createFile.touch(CreateFileAt);
            assertTrue(isCreated, "This is command should create file in the directory");
        }
    }

    // path file name without file extension
    @Test
    void testTouchInvalidPath() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        // path to create file at "in this case path is invalid"
        String invalid_Path = "some/path/any/where";

        // create file and check is created
        boolean isCreated = inputHandler.createFile.touch((invalid_Path));
        if (isLinux()) {
            assertTrue(isCreated, "This is command should create file in the directory");
        } else {
            assertFalse(isCreated, "This is command should not create file because path is not a valid path");
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


    // test "mkdir" pass file name with extension instead of file name
    @Test
    void testMkdirWithInvalidPath() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        // path to create directory at "in this case path is invalid"
        String invalid_Path = "some/path/any/where";
        boolean isCreated = inputHandler.createDirectory.mkdir(invalid_Path);
        assertFalse(isCreated, STR."This is command should not \{invalid_Path} is invalid path");
    }

    // test "rmdir" with a valid path
    @Test
    void testRmDir() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.deleteDirectory.rmdir("/home/user/Documents/Dummy");
            assertTrue(isCreated, "This is command should create Directory in the directory");
        } else {
            // path of the directory
            String DeleteDirectoryAt = STR."\{inputHandler.PWD.pwd()}\\Dummy";
            // path create the directory
            inputHandler.createDirectory.mkdir(DeleteDirectoryAt);

            // delete the directory and check if is deleted
            boolean isCreated = inputHandler.deleteDirectory.rmdir(DeleteDirectoryAt);
            assertTrue(isCreated, "This is command should Delete Directory in the directory");
        }
    }

    @Test
    void testRmDirNotExistFile() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        String invalid_Path = "some/path/any/where";
        boolean isCreated = inputHandler.deleteDirectory.rmdir(invalid_Path);
        assertFalse(isCreated, "This is command should not work because it's not valid path");
    }


    // pass invalid path to rmdir function
    @Test
    void testRmDirDeleteFile() throws IOException {
        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        String invalid_Path = "some/path/any/where";
        boolean isDeleted = inputHandler.deleteDirectory.rmdir(invalid_Path);
        assertFalse(isDeleted, STR."This is command should not delete directory at \{invalid_Path} because path is invalid");
    }

    @Test
    void testRm() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {

            boolean isCreated = inputHandler.createFile.touch(("/home/user/Documents/test.txt"));
            if (isCreated) {
               boolean isDeleted = inputHandler.removeFile.rm("/home/user/Documents/test.txt");
                assertTrue(isDeleted, "This is command should create Directory in the directory");
            }
        } else {
            String DeleteFileAt = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            boolean isCreated = inputHandler.createFile.touch(DeleteFileAt);
            if (isCreated) {
                boolean isDelete = inputHandler.removeFile.rm(DeleteFileAt);
                assertTrue(isDelete, "This is command should Delete File in the directory");
            }
        }
    }


    // test "ls -a > path": get ls of directories and files in the current directory and store them in txt file
    @Test
    void testRedirectOutput() throws IOException  {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;
        String pathwithoutFile = "";
        String filePath = "";
        File file;
        if (isLinux()) {
            inputHandler.handleInput("ls \"/home/user/Documents\" > listOfFile.txt");
            pathwithoutFile = "/home/user/Documents";
            filePath = "/home/user/Documents/testDirectorOperator.txt";
            file = new File(filePath);
            boolean isFileCreated = (file.exists() && !file.isDirectory());
            if (isFileCreated) {
                Scanner reader = new Scanner(file);
                ArrayList<File> files = inputHandler.listDirectoriesFiles.LS(pathwithoutFile, true);
                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
                    boolean match = files.stream().anyMatch(f -> f.getName().equals(line));
                    assert  match : STR."file content does not match \{line}";
                }

                reader.close();
            }
            assertTrue(isFileCreated, "This command should create file if not exist and put inside it list file in the current working directory");
        } else {
            inputHandler.handleInput("ls -a > testDirectorOperator.txt");
            filePath = STR."\{inputHandler.PWD.pwd()}\\testDirectorOperator.txt";
            file = new File(filePath);
            boolean isFileCreated = (file.exists() && !file.isDirectory());
            if (isFileCreated) {
                Scanner reader = new Scanner(file);
                ArrayList<File> files = inputHandler.listDirectoriesFiles.LS(inputHandler.PWD.pwd(), true);
                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
                    boolean match = files.stream().anyMatch(f -> f.getName().equals(line));
                    assert  match : STR."file content does not match \{line}";
                }

                reader.close();
            }
            assertTrue(isFileCreated, "This command should create file if not exist and put inside it list file in the current working directory");
        }

    }
    @Test
    void testRedirectOutputWithAppend() throws IOException  {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            inputHandler.handleInput("ls \"/home/user/Documents\" >> listOfFile.txt");
            String filePath = "/home/user/Documents/testDirectorOperator.txt";
            File file = new File(filePath);
            boolean isFileCreated = (file.exists() && !file.isDirectory());
            assertTrue(isFileCreated, "This command should create if note exist and append (if it's already have content) inside it file contain list file in passed directory");
        } else {
            inputHandler.handleInput("ls -a >> testDirectorOperator.txt");
            String filePath = STR."\{inputHandler.PWD.pwd()}\\testDirectorOperator.txt";
            File file = new File(filePath);
            boolean isFileCreated = (file.exists() && !file.isDirectory());
            assertTrue(isFileCreated, "This command should create if note exist and append (if it's already have content) inside it file contain list file in the current working directory");
        }
    }

    // rename a file
    @Test
    void testMove() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;

        if (isLinux()) {
            boolean isCreated = inputHandler.createFile.touch(("/home/user/Documents/test.txt"));
            if (isCreated) {
                inputHandler.move.mv("/home/user/Documents/test.txt", "/home/user/Documents/NameChanged.txt");
                File file = new File("/home/user/Documents/NameChanged.txt");
                boolean isNameChanged = (file.exists() && !file.isDirectory());
                assertTrue(isNameChanged, "This command should change file name");
            }

        } else {
            String filePath = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            String newFileName = STR."\{inputHandler.PWD.pwd()}\\newName.txt";
            boolean isCreated = inputHandler.createFile.touch(filePath);
            inputHandler.move.mv(filePath, newFileName);
            File file = new File(newFileName);
            boolean isNameChanged = (file.exists() && !file.isDirectory());
            assertTrue(isNameChanged, "This command should change file name");
        }

    }

    // try to rename file does not exist
    @Test
    void testMoveInvalidPath() throws IOException {

        Shell.restPath();
        Shell shell = new Shell();
        InputHandler inputHandler = shell.inputHandler;
        String invalid_Path = "some/path/any/where";
        if (isLinux()) {
            boolean isNameChanged = inputHandler.move.mv("/home/user/Documents/NameChanged.txt", "some/path/any/where");
            assertFalse(isNameChanged, "This command should change file name");
        } else {
            String filePath = STR."\{inputHandler.PWD.pwd()}\\test.txt";
            boolean isNameChanged =  inputHandler.move.mv(filePath, invalid_Path);
            assertFalse(isNameChanged, "This command should change file name");
        }

    }


}