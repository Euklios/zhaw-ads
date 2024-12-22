package ch.zhaw.ads;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BracketServer implements CommandExecutor {
    private static final List<String> OPEN_PAREN = Arrays.asList("(", "{", "[", "<", "/*");
    private static final List<String> CLOSE_PAREN = Arrays.asList(")", "}", "]", ">", "*/");

    public boolean checkBrackets(String command) {
        Stack stack = new ListStack();

        while (!command.isEmpty()) {
            Optional<String> openMatch = OPEN_PAREN.stream().filter(command::startsWith).findFirst();
            Optional<String> closeMatch = CLOSE_PAREN.stream().filter(command::startsWith).findFirst();

            if (!openMatch.isPresent() && !closeMatch.isPresent()) {
                command = command.substring(1);
                continue;
            }

            if (openMatch.isPresent()) {
                stack.push(openMatch.get());
                command = command.substring(openMatch.get().length());
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                String open = (String) stack.pop();
                if (OPEN_PAREN.indexOf(open) != CLOSE_PAREN.indexOf(closeMatch.get())) {
                    return false;
                }

                command = command.substring(closeMatch.get().length());
            }
        }

        return stack.isEmpty();
    }

    @Override
    public String execute(String command) throws Exception {
        return Boolean.toString(checkBrackets(command));
    }
}
