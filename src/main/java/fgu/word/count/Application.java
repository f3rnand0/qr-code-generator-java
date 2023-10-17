package fgu.word.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            System.out.println("Usage: [-clwm] <filename>");
            System.exit(1);
        }
        Set<String> options = new HashSet<>(Arrays.asList("-c", "-l", "-w", "-m"));
        String option = args[0];
        String filename = null;
        Stream.Builder<String> builder = Stream.builder();
        BufferedReader bufferedReader;
        String line;
        if (options.contains(option)) {
            if (args.length > 1) {
                filename = args[1];
                bufferedReader = new BufferedReader(new FileReader(filename));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            }
        } else {
            filename = option;
            bufferedReader = new BufferedReader(new FileReader(filename));
        }

        while ((line = bufferedReader.readLine()) != null) {
            builder.add(line);
        }

        long total;
        Stream<String> stream = builder.build();
        String results;

        try {
            switch (args[0]) {
                case "-c":
                    total = countBytes(stream);
                    printCount(filename, total);
                    break;
                case "-l":
                    total = countLines(stream);
                    printCount(filename, total);
                    break;
                case "-w":
                    total = countWords(stream);
                    printCount(filename, total);
                    break;
                case "-m":
                    total = countCharacters(stream);
                    printCount(filename, total);
                    break;
                default:
                    results = countAll(stream.collect(Collectors.toList()));
                    printCount(filename, results);
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static long countBytes(Stream<String> stream) throws IOException {
        return stream.map(s -> s.getBytes().length).mapToInt(i -> i).sum();
    }

    private static long countLines(Stream<String> stream) throws IOException {
        return stream.count();
    }

    private static int countWords(Stream<String> stream) throws IOException {
        return stream.map(s -> s.split("\\s+").length).mapToInt(i -> i).sum();
    }

    private static int countCharacters(Stream<String> stream) throws IOException {
        boolean multiByte = Charset.defaultCharset().newEncoder().maxBytesPerChar() > 1.0f;
        if (multiByte) {
            return stream.map(s -> s.codePointCount(0, s.length())).mapToInt(i -> i).sum();
        }
        return countWords(stream);
    }

    private static String countAll(List<String> content) throws IOException {
        String results = String.valueOf(countLines(content.stream()));
        results += " " + countWords(content.stream());
        results += " " + countBytes(content.stream());
        return results;
    }

    private static void printCount(String filename, long total) {
        if (filename != null) {
            System.out.println(total + " " + filename);
        } else {
            System.out.println(total);
        }
    }

    private static void printCount(String filename, String results) {
        System.out.println(results + " " + filename);
    }
}
