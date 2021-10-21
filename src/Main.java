import Pattern.Commander;
import Pattern.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void printCommands(ArrayList<Command> commands) {
        for (Command command : commands) {
            System.out.print(command.getCommandName());

            if (!command.getArgsDescription().isEmpty()) {
                for (String arg : command.getArgsDescription())
                    System.out.print(" " + arg);
            }

            System.out.println(" - " + command.getDescription());
        }
    }

    public static void main(String[] args) {
        System.out.print("Введите рабочую директорию: ");
        Scanner scanner = new Scanner(System.in);
        String directory = scanner.nextLine();

        try {
            //класс, откуда берутся методы для создания команд
            WorkingDirectory dir = WorkingDirectory.getInstance(directory);
            //класс, управляющий методами
            Commander commander = new Commander();

            //добавление команд в commander
            //1-ый параметр - название команды
            //2-ой параметр - описание команды
            //3-ий параметр - описание аргументов (необязательный)
            //4-ый параметр - сама команда (лямбда-функция)
            commander.addCommand(new Command("/quit",
                    "выйти из программы",
                    commandArgs -> System.exit(0)));

            commander.addCommand(new Command("/help",
                    "вывести список команд",
                    commandArgs -> printCommands(commander.getCommands())));

            commander.addCommand(new Command("/go_to_parent",
                    "перейти к родительскому каталогу",
                    commandArgs -> dir.goToParentDirectory()));

            commander.addCommand(new Command("/go_to_child",
                    "перейти к дочернему каталогу",
                    new ArrayList<>(List.of("<название каталога>")),
                    commandArgs -> dir.goToChildDirectory(commandArgs.get(0))));

            commander.addCommand(new Command("/parent",
                    "получение имени родительского каталога",
                    commandArgs -> System.out.println(dir.getParentDirectoryName())));

            commander.addCommand(new Command("/child?",
                    "существует ли каталог с данным названием в рабочем каталоге",
                    new ArrayList<>(List.of("<название каталога>")),
                    commandArgs -> System.out.println("Дочерний каталог " +
                            (dir.existsChild(commandArgs.get(0)) ? "существует" : "не существует"))));

            commander.addCommand(new Command("/create",
                    "создать новый каталог в текущем",
                    new ArrayList<>(List.of("<название каталога>")),
                    commandArgs -> dir.createDirectory(commandArgs.get(0))));

            commander.addCommand(new Command("/delete_all",
                    "удаление всех каталогов, вложенных в данный",
                    commandArgs -> dir.deleteSubdirectories()));

            commander.addCommand(new Command("/print_subdirs",
                    "вывод иерархического списка подкаталогов",
                    commandArgs -> dir.printSubdirectories()));

            commander.addCommand(new Command("/exists?",
                    "проверка на наличие вложенного каталога",
                    new ArrayList<>(List.of("<название каталога>")),
                    commandArgs -> System.out.println("Вложенный каталог " +
                            (dir.existsDirectory(commandArgs.get(0)) ? "существует" : "не существует"))));

            commander.addCommand(new Command("/list",
                    "вывести список файлов текущей директории",
                    new ArrayList<>(List.of("<расширение файла>")),
                    commandArgs -> System.out.println(dir.getDirectoryFiles(commandArgs.get(0)))));

            commander.addCommand(new Command("/list",
                    "вывести список файлов текущей директории",
                    commandArgs -> System.out.println(dir.getDirectoryFiles())));

            //вызов команд
            while (true) {
                System.out.print("Введите команду: ");
                String command = scanner.nextLine();
                try {
                    commander.executeCommand(command);
                } catch (Exception exc) {
                    System.out.println(exc.getMessage());
                }
            }
        } catch (Exception exc) {
            System.out.println("Ошибка: " + exc.getMessage());
        }
    }
}
