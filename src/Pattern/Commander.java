package Pattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Commander {
    private ArrayList<Command> commands;


    public Commander(ArrayList<Command> commands) {
        if (commands == null)
            throw new NullPointerException();
        for (Command command : commands)
            addCommand(command);
    }

    public Commander() {
        commands = new ArrayList<>();
    }


    public void executeCommand(String command) throws IOException {
        command += " ";
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(command.split(" ")));
        removeEmptyEntries(arguments);
        if (arguments.size() == 0)
            throw new IllegalArgumentException("Неверно введена команда");

        String commandName = arguments.get(0);
        arguments.remove(0);
        getBy(commandName, arguments.size()).execute(arguments);
    }

    public Command getBy(String commandName, int argsCount) {
        for (Command command : commands) {
            if (command.getCommandName().equals(commandName) && argsCount == command.getArgsDescription().size())
                return command;
        }

        throw new IllegalArgumentException("Команды " + commandName + " не существует");
    }

    public boolean exists(String commandName, int argsCount) {
        for (Command command : commands) {
            if (command.getCommandName().equals(commandName) && argsCount == command.getArgsDescription().size())
                return true;
        }

        return false;
    }

    private void removeEmptyEntries(ArrayList<String> list) {
        list.removeIf(str -> str.isEmpty() || str.isBlank());
    }

    public void addCommand(Command command) {
        if (command == null)
            throw new NullPointerException();
        if (exists(command.getCommandName(), command.getArgsDescription().size()))
            throw new IllegalArgumentException("Команда с такими параметрами уже существует");
        commands.add(command);
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(commands);
    }
}
