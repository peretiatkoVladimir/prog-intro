import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.*;
public class Wspp3637 {
    public static void main(String[] args) {
    ArrayList<ArrayList<String>> List = new ArrayList<>();
    List.add(new ArrayList<>());
    int num = 0;
    String file = args[0];
    StringBuilder word = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new 
    FileReader(file))) {
        int d;
        while ((d = reader.read()) != -1) {
            char ch = (char) d;
            if ((Character.isLetter(ch))
            || (Character.getType(ch) == Character.DASH_PUNCTUATION)
            || (Character.getType(ch) == Character.CURRENCY_SYMBOL)
            || (ch == '\'')) {
                word.append(Character.toLowerCase(ch));
            } else {
                if (word.length() > 0) {
                    List.get(num).add(word.toString());
                    word.setLength(0);
                }
                if (d == 10) {
                    num++;
                    List.add(new ArrayList<>());
                }
            }
        }
        if (word.length() > 0) {
            List.get(num).add(word.toString());
    }
    } catch (IOException e) {}
    Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
    for (int j = 0; j < List.size(); j++) {
        Map<String, Integer> strWords = new HashMap<>();
        for (int i = 0; i < List.get(j).size(); i++) {
            String str = List.get(j).get(i);
            if (map.containsKey(str)) {
                if (strWords.containsKey(str)) {
                    int a = strWords.get(str);
                    strWords.replace(str, a + 1);
                    if (a % 2 == 0) {
                        map.get(str).add(i+1);
                    }
                } else {
                    strWords.put(str, 1);
                    map.get(str).add(i+1);
                }
                map.get(str).set(0, map.get(str).get(0) + 1);
            } else {
                map.put(str, new ArrayList<>(Arrays.asList(1, i+1)));
                strWords.put(str, 1);
            }
        }
    }
    Map.Entry<String, ArrayList<Integer>>[] newmap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .toArray(Map.Entry[]::new);
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]))) {
        for (Map.Entry<String, ArrayList<Integer>> entry : newmap) {
            bw.write(entry.getKey() + " " + entry.getValue().stream()
            .map(String::valueOf).collect(Collectors.joining(" ")) + "\n");
        }
    } catch (IOException e) {}
    }
}