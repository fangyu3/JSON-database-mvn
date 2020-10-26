package server;

import client.ClientInput;

import java.util.List;
import java.util.stream.Collectors;

public class ClientInputValidator {
    private enum commands {
        GET,
        SET,
        DELETE,
        EXIT
    }

    public boolean validateCommand(ClientInput clientInput) {
        String command = clientInput.getType();
        String key = clientInput.getKey();
        String value = clientInput.getValue();
        boolean result = true;

        if (!List.of(commands.values()).stream().map(e->e.name()).collect(Collectors.toList()).contains(command.toUpperCase()))
            result = false;

        if (command.equals("exit") && (key!=null || value!=null))
            result = false;

        if (!command.equals("exit") && key == null)
            result = false;

        if (command.equals("set") && value == null)
            result = false;

        return result;
    }
}
