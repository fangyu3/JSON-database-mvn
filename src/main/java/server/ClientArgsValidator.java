package server;

import java.util.List;
import java.util.stream.Collectors;

public class ClientArgsValidator {
    private static enum commands {
        GET,
        SET,
        DELETE,
        EXIT
    }

    public static boolean validateIdx(int idx) {
        if (idx<0 || idx>Main.getDbSize()-1)
            return false;

        return true;
    }

    public static boolean validateCommand(String command) {
        if (!List.of(commands.values()).stream().map(e->e.name()).collect(Collectors.toList()).contains(command.toUpperCase()))
            return false;

        return true;
    }
}
