package main;
import service.WordProcessor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        WordProcessor processor = new WordProcessor();

        Map<String,Integer> map = processor.processFile(args[0]);
        long end = System.currentTimeMillis();
        System.out.println("Затрачено на обработку файла :"+ (end-start)/1000 + " секунд.");
        Scanner scanner = new Scanner(System.in);
        String command="";
        do {
            System.out.println("Введите слово:");
            command = scanner.nextLine();
            byte[] bytes = command.getBytes();
            command = new String(bytes, StandardCharsets.UTF_8);

            System.out.println(command.equals("\\end")? "Завершение работы программы!!!":("Слово "+ command +" встречается "+(map.get(command)==null?"0":map.get(command))+" раз"));

        }while(!command.equals("\\end"));
    }
}
