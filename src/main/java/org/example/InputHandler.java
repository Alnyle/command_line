package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {


    private final ChangeDirectory changeDirectory;
    private final LSDF listDirectoriesFiles;
    private final CreateDirectory createDirectory;
    private final DeleteDirectory deleteDirectory;

    private final CreateFile createFile;


    public InputHandler() {
        changeDirectory = new ChangeDirectory();
        listDirectoriesFiles = new LSDF();
        createDirectory = new CreateDirectory();
        deleteDirectory = new DeleteDirectory();
        createFile = new CreateFile();
    }


    public void handleInput(String Input) throws IOException {

        System.out.println();

        String[] arguments = commandSplit(Input);

        // responsible for change changing directory

        //        System.out.print("result = ");
        for (String str : arguments) {
            System.out.print(str + ", ");
        }


        switch (arguments[0]) {
            case "cd" -> changeDirectory.cd(arguments[1]);
            case "ls" -> {


                if (arguments.length == 1) {
                    listDirectoriesFiles.ls(Shell.currentPath);
                    return;
                } else if (arguments.length == 2) {

                    if (arguments[1].equals("-a")) {
                        listDirectoriesFiles.ls(Shell.currentPath, 'a');
                    } else if (arguments[1].equals("-r")) {
                        listDirectoriesFiles.ls(Shell.currentPath, 'r');
                    } else {


                        listDirectoriesFiles.ls(arguments[1]);

                    }

                } else if (arguments.length == 3) {


                    if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                        listDirectoriesFiles.ls("", 'a', 'r');
                    }
                    // check on char
                    else if (arguments[1].equals("-a")) {
                        listDirectoriesFiles.ls(arguments[2], 'a');
                    } else if (arguments[1].equals("-r")) {
                        listDirectoriesFiles.ls(arguments[2], 'r');
                    }
                } else if (arguments.length == 4) {
                    listDirectoriesFiles.ls(arguments[3], 'r', 'a');
                }
            }
            case "mkdir" -> createDirectory.mkdir(arguments[1]);
            case "rmdir" -> deleteDirectory.rmdir(arguments[1]);
            case "touch" -> createFile.touch(arguments[1]);
        }
    }

    private String[] commandSplit(String command) {

        List<String> arguments = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"([^\"]*)\"|\\S+");
        Matcher matcher = pattern.matcher(command);

        while (matcher.find()) {

            if (matcher.group(1) != null) {
                arguments.add(matcher.group(1));
            } else {
                arguments.add(matcher.group());
            }
        }

        return arguments.toArray(new String[0]);
    }
}
