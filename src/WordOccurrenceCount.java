import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Otabek Aripdjanov
 * This is a WordOccurrenceCount class
 * that read a text file and count the number of times each word occurs
 * using a TreeMap. Words are anything separated by white space.
 *
 * last updated: 04/30/2023
 */

public class WordOccurrenceCount {

    public static void main(String[] args) throws IOException {
        String filename = "./src/Dropped stitches in Tennessee history by John Allison.txt";
        List<String> words = readAndProcessWords(filename);

        long startTime = System.nanoTime();
        TreeMap<String, Integer> treeMap = countWordsWithTreeMap(words);
        System.out.println("Word occurrence and count:");
        printTopWords(treeMap, 5, 6);
        long endTime = System.nanoTime();
        long elapsedTimeInNanoseconds = endTime - startTime;
        double elapsedTimeInMilliseconds = elapsedTimeInNanoseconds / 1e6;
        System.out.println("TreeMap execution time: " + elapsedTimeInMilliseconds + " milliseconds or " +
                elapsedTimeInNanoseconds + " nanoseconds");


        System.out.println("*===============================*");

        startTime = System.nanoTime();
        HashMap<String, Integer> hashMap = countWordsWithHashMap(words);
        System.out.println("Word occurrence and count:");
        printTopWords(hashMap, 5, 6);
        endTime = System.nanoTime();
        elapsedTimeInNanoseconds = endTime - startTime;
        elapsedTimeInMilliseconds = elapsedTimeInNanoseconds / 1e6;
        System.out.println("HashMap execution time: " + elapsedTimeInMilliseconds + " milliseconds or " +
                elapsedTimeInNanoseconds + " nanoseconds");

    }

    public static List<String> readAndProcessWords(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        content = content.replaceAll("[,.?!]", "").toLowerCase(Locale.ENGLISH);
        return Arrays.asList(content.split("\\s+"));
    }

    public static TreeMap<String, Integer> countWordsWithTreeMap(List<String> words) {
        TreeMap<String, Integer> wordCount = new TreeMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    public static HashMap<String, Integer> countWordsWithHashMap(List<String> words) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    public static void printTopWords(Map<String, Integer> wordCount, int topN, int minWordLength) {
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getKey().length() > minWordLength) {
                minHeap.offer(entry);
                if (minHeap.size() > topN) {
                    minHeap.poll();
                }
            }
        }

        List<Map.Entry<String, Integer>> topWords = new ArrayList<>(minHeap);
        topWords.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        for (Map.Entry<String, Integer> entry : topWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
