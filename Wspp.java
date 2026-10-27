import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Arrays;
public class Wspp {
    public static void main(String[] args) {
    ArrayList<String> List = new ArrayList<>();
    // ArrayList<String> Words = new ArrayList<>();
    String file = args[0];
    StringBuilder word = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new 
    FileReader(file))) {
        int d;
        while ((d = reader.read()) != -1) {
            char ch = (char) d;
            if ((Character.isLetter(ch))
            || (Character.getType(ch) == Character.DASH_PUNCTUATION)
            || (ch == '\'')) {
                word.append(Character.toLowerCase(ch));
            } else {
                if (word.length() > 0) {
                    List.add(word.toString());
                    word.setLength(0);
                }
            }
        }
        if (word.length() > 0) {
            List.add(word.toString());
    }
    } catch (IOException e) {}
    Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
    for (int i = 0; i < List.size(); i++) {
        String str = List.get(i);
        if (map.containsKey(str)) {
            map.get(str).set(0, map.get(str).get(0) + 1);
            map.get(str).add(i+1);
        } else {
            map.put(str, new ArrayList<>(Arrays.asList(1, i+1)));
        }
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]))) {
        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
            bw.write(entry.getKey() + " " + entry.getValue().stream()
            .map(String::valueOf).collect(Collectors.joining(" ")) + "\n");
        }
    } catch (IOException e) {}
    }
}