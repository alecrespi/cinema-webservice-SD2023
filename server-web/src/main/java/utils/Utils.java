package utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {

    public static String[] splitRoute(HttpServletRequest req){
        String[] uri = req.getRequestURI().split("/");
        return Arrays.copyOfRange(uri, 1, uri.length + 1); // have to cut the first element 'cause it's always an empty string
    }

    public static String getBody(HttpServletRequest req) throws IOException {
        return req
                .getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
