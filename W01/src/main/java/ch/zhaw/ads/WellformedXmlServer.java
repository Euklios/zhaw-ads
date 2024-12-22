package ch.zhaw.ads;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WellformedXmlServer implements CommandExecutor {
    private static final Pattern XML_TAG = Pattern.compile("<(/)?(\\w+)(?:[^>]*(?<!/))?(/)?>");

    public boolean checkWellformed (String command) {
        Stack stack = new ListStack();
        Matcher matcher = XML_TAG.matcher(command);

        while (matcher.find()) {
            String closingTag = matcher.group(1);
            String tagName = matcher.group(2);
            String directlyClosed = matcher.group(3);

            // </a/> is not valid
            if (closingTag != null && directlyClosed != null) {
                return false;
            }

            // <a/> collapsed tags are valid
            if (directlyClosed != null) {
                continue;
            }

            // <a></a>
            if (closingTag == null) {
                stack.push(tagName);
            } else {
                Object openTag = stack.pop();

                if (openTag == null || !openTag.equals(tagName)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    @Override
    public String execute(String command) throws Exception {
        return Boolean.toString(checkWellformed(command));
    }
}
