package io.invokegs.pta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final int CONNECTION_LENGTH = 2;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar pta.jar <file>");
            return;
        }

        Path path = Paths.get(args[0]);
        if (!Files.isRegularFile(path)) {
            System.err.println("Invalid file: " + path);
            return;
        }

        List<String> fragments;
        try {
            fragments = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
            return;
        }

        try {
            ConnectionSolver solver = new ConnectionSolver(CONNECTION_LENGTH);
            ConnectionSolver.Result longestSequence = solver.findLongestSequence(fragments);
            printResult(longestSequence);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void printResult(ConnectionSolver.Result longestSequence) {
        System.out.println("Longest Sequence: " + longestSequence.sequence());
        System.out.println("Sequence Length: " + longestSequence.sequence().length());
        System.out.println("Fragments: " + " [" + String.join(", ", longestSequence.fragments()) + "]");
        System.out.println("Fragments Count: " + longestSequence.fragments().size());
    }
}
