package Pattern;

import java.io.IOException;
import java.util.ArrayList;

public class Command implements Executable {
    private Executable command;
    private String commandName, description = null, paramsDescription = null;


    public Command(String commandName, Executable command) {
        setCommandName(commandName);
        setCommand(command);
    }

    public Command(String commandName, String commandDescription, Executable command) {
        this(commandName, command);
        setDescription(commandDescription);
    }

    public Command(String commandName, String commandDescription, String paramsDescription, Executable command) {
        this(commandName, commandDescription, command);
        setParamsDescription(paramsDescription);
    }


    @Override
    public void execute(ArrayList<String> commandArgs) throws IOException {
        this.command.execute(commandArgs);
    }


    public void setCommand(Executable command) {
        if (command == null)
            throw new NullPointerException();
        this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        if (commandName == null)
            throw new NullPointerException();
        this.commandName = commandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException();
        this.description = description;
    }

    public String getParamsDescription() {
        return paramsDescription;
    }

    public void setParamsDescription(String paramsDescription) {
        if (paramsDescription == null)
            throw new NullPointerException();
        this.paramsDescription = paramsDescription;
    }
}
