package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String getTokenFromFile(Integer line) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("token.txt"));
        return strings.get(line);
    }

    public static List<Integer> getWhiteList() throws IOException {
        List<Integer> result = new ArrayList<Integer>();
        List<String> strings = Files.readAllLines(Paths.get("whitelist.txt"));

        for (String userId : strings)
            result.add(Integer.valueOf(userId));

        return result;
    }

    public static void registerUser(Integer userId) throws IOException {
        Writer out = new BufferedWriter(new FileWriter("whitelist.txt", true));
        out.append("\n" + userId.toString());
        out.close();
    }

    public static boolean isUserRegistered(List<Integer> list, Integer userId) {
        for (Integer id : list) {
            if (id.equals(userId)) {
                return true;
            }
        }
        return false;
    }

}
