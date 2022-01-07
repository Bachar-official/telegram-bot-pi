package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

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

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    /**
     * Формирование имени пользователя. Если заполнен никнейм, используем его. Если нет - используем фамилию и имя
     * @param user пользователь
     */
    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }

}
