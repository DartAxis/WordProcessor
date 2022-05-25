package service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WordProcessor {

    public Map<String, Integer> processFile(String arg) {
        Map<String, Integer> result = new ConcurrentHashMap<>();
        final String text;
        String format = " %s ";

        System.out.println(LocalDateTime.now()+": Читаем файл");
        try(FileInputStream input = new FileInputStream(arg)) {
            text = new String(input.readAllBytes(),StandardCharsets.UTF_8).toLowerCase(Locale.ROOT).replaceAll("[^а-я]"," ").replaceAll("\\s+"," ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Кол-во символов в тексте : " + text.length());
        System.out.println(LocalDateTime.now()+": Запускаем анализатор");

        result=Arrays.stream(text.split("[^а-я]"))
                .parallel()
                .filter(x -> x.length()>2)
                .collect(Collectors.toMap(word-> word, value->1, Integer::sum));
        System.out.println("Кол-во уникальных слов : "+result.keySet().size());

        System.out.println(arg + "env");
        try (FileWriter writer = new FileWriter(arg + "env")) {
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                writer.write(entry.getKey() + " : " + entry.getValue() + "\n");
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now()+": Закончили");
        return result;
    }
}