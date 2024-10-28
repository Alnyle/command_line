package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {


    final ChangeDirectory changeDirectory;
    final LSDF listDirectoriesFiles;
    final CreateDirectory createDirectory;
    final DeleteDirectory deleteDirectory;

    final CreateFile createFile;
    final RemoveFile removeFile;

    final Cat readFile;

    final PrintCurrentDirectory PWD;

    final RedirectOutputWrite wirteToFile;

    public InputHandler() {
        changeDirectory = new ChangeDirectory();
        listDirectoriesFiles = new LSDF();
        createDirectory = new CreateDirectory(listDirectoriesFiles);
        deleteDirectory = new DeleteDirectory(listDirectoriesFiles);
        createFile = new CreateFile(listDirectoriesFiles);
        removeFile = new RemoveFile(listDirectoriesFiles);
        readFile = new Cat(listDirectoriesFiles);
        PWD = new PrintCurrentDirectory();
        wirteToFile = new RedirectOutputWrite(listDirectoriesFiles, PWD);
    }


    public void handleInput(String Input) throws IOException {

        System.out.println();

        String[] arguments = commandSplit(Input);

        // responsible for change changing directory

        //        System.out.print("result = ");
//        for (String str : arguments) {
//            System.out.print(str + ", ");
//        }


        switch (arguments[0]) {
            case "cd" -> changeDirectory.cd(arguments[1]);

            case "pwd" -> {
                if (arguments.length == 1) {
                    PWD.pwd();
                }

                if (arguments.length == 3) {
                    if (arguments[1].equals(">")) {
                        wirteToFile.redirectOutPut(arguments[2], ">");
                    } else if (arguments[1].equals(">>")) {
                        wirteToFile.redirectOutPut(arguments[2], ">>");
                    }
                }
            }

            case "ls" -> {


                if (arguments.length == 1) {
                    listDirectoriesFiles.ls(Shell.currentPath);
                    return;
                } else if (arguments.length == 2) {

                    if (arguments[1].equals("-a")) {
                        listDirectoriesFiles.ls(Shell.currentPath, 'a');
                    } else if (arguments[1].equals("-r")) {
                        listDirectoriesFiles.ls(Shell.currentPath, 'r');
                    }
                    else {
                        listDirectoriesFiles.ls(arguments[1]);
                    }

                } else if (arguments.length == 3) {


                    if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                        listDirectoriesFiles.ls(Shell.currentPath, 'a', 'r');
                    }
                    // check on char
                    else if (arguments[1].equals("-a")) {
                        listDirectoriesFiles.ls(arguments[2], 'a');
                    } else if (arguments[1].equals("-r")) {
                        listDirectoriesFiles.ls(arguments[2], 'r');
                    } else if (arguments[1].equals(">")) {
                        wirteToFile.listRedirectOutPut(Shell.currentPath ,arguments[2], ">", false, false);
                    } else if (arguments[1].equals(">>")) {
                        wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[2], ">>", false, false);
                    }
                } else if (arguments.length == 4) {

                    // handle this cases
                    // ls .. >> path
                    // ls -a -r path
                    // ls -a > path
                    if (arguments[2].equals(">") && (arguments[1].equals("-r") || arguments[1].equals("-a")))  {
                        if (arguments[1].equals("-a")) {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[3], ">", false, true);
                            return;
                        } else {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[3], ">", true, false);
                            return;
                        }

                    } else if (arguments[2].equals(">>") && (arguments[1].equals("-r") || arguments[1].equals("-a"))) {
                        if (arguments[1].equals("-a")) {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[3], ">>", false, true);
                            return;
                        } else {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[3], ">>", true, false);
                            return;
                        }

                    }
                    else if (arguments[2].equals(">")) {
                        wirteToFile.listRedirectOutPut(arguments[1],arguments[3], ">", false, false);
                    } else if (arguments[2].equals(">>")) {
                        wirteToFile.listRedirectOutPut(arguments[1],arguments[3], ">>", false, false);
                    } else if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                        listDirectoriesFiles.ls(arguments[3], 'r', 'a');
                    } else {
                        System.out.println("Invalid command");
                    }
                } else if (arguments.length == 5) {

                    // handle this cases
                    // ls -a -r > path
                    // ls -a .. > path

                    if (arguments[3].equals(">")) {
                        if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[4], ">", true, true);
                        } else if (arguments[1].equals("-a") || arguments[1].equals("-r")) {
                            wirteToFile.listRedirectOutPut(arguments[2],arguments[4], ">>", true, true);
                        } else {
                            System.out.println("Invalid command");
                        }
                    } else if (arguments[3].equals(">>")) {
                        if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                            wirteToFile.listRedirectOutPut(Shell.currentPath,arguments[4], ">>", true, true);
                        }  else if (arguments[1].equals("-a") || arguments[1].equals("-r")) {
                            wirteToFile.listRedirectOutPut(arguments[2],arguments[4], ">>", true, true);
                        }
                        else {
                            System.out.println("Invalid command");
                        }
                    }
                    else {
                        System.out.println("Invalid command");
                    }
                }
                else if (arguments.length == 6) {
                    // ls -a -r ..  > path
                    // ls -r -a .. > path
                    if ((arguments[1].equals("-a") && arguments[2].equals("-r")) || (arguments[1].equals("-r") && arguments[2].equals("-a"))) {
                        if (arguments[4].equals(">")) {
                            wirteToFile.listRedirectOutPut(arguments[3],arguments[5], ">", true, true);
                        } else if (arguments[4].equals(">>")) {
                            wirteToFile.listRedirectOutPut(arguments[3],arguments[5], ">>", true, true);
                        }
                    } else {
                        System.out.println("Invalid command");
                    }
                }
            }
            case "mkdir" -> createDirectory.mkdir(arguments[1]);
            case "rmdir" -> deleteDirectory.rmdir(arguments[1]);
            case "touch" -> createFile.touch(arguments[1]);
            case "rm" -> removeFile.rm(arguments[1]);

            // cat path
            // cat path > path
            // cat path >> path
            case "cat" -> {

                if (arguments.length == 2) {
                    readFile.cat(arguments[1]);
                } else if (arguments.length == 4) {
                    if (arguments[2].equals(">")) {
                        wirteToFile.catRedirect(arguments[1], arguments[3], ">");
                    } else if (arguments[2].equals(">>")) {
                        wirteToFile.catRedirect(arguments[1], arguments[3], ">>");
                    } else {
                        System.out.println("Invalid command");
                    }
                }

            }

            default -> System.out.println("Invalid Input");
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
