package Pattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Commander {
    private final ArrayList<Command> commands;


    public Commander(ArrayList<Command> commands) {
        if (commands == null)
            throw new NullPointerException();
        this.commands = (ArrayList<Command>)commands.clone();
    }

    public Commander() {
        commands = new ArrayList<>();
    }


    public void executeCommand(String commandName) throws IOException {
        commandName += " ";
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(commandName.split(" ")));
        removeEmptyEntries(arguments);
        if (arguments.size() == 0)
            throw new IllegalArgumentException("Неверно введена команда");

        Command command = getByName(arguments.get(0));
        arguments.remove(0);
        command.execute(arguments);
    }

    private Command getByName(String commandName) {
        for (Command command : commands) {
            if (command.getCommandName().equals(commandName))
                return command;
        }

        throw new IllegalArgumentException("Команды " + commandName + " не существует");
    }

    private void removeEmptyEntries(ArrayList<String> list) {
        list.removeIf(str -> str.isEmpty() || str.isBlank());
    }

    public void addCommand(Command command) {
        if (command == null)
            throw new NullPointerException();
        commands.add(command);
    }

    public String getCommands() {
        String commandNames = "";

        for (Command command : commands) {
            commandNames = commandNames.concat(command.getCommandName());

            if (command.getParamsDescription() != null)
                commandNames = commandNames.concat(" " + command.getParamsDescription());

            if (command.getDescription() != null)
                commandNames = commandNames.concat(" - " + command.getDescription());

            commandNames = commandNames.concat("\n");
        }

        return commandNames;
    }
}
