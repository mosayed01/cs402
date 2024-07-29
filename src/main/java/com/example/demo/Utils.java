package com.example.demo;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Utils {

    public static String readTextFromFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        File file = fileChooser.showOpenDialog(window);
        if (file == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }

        return sb.toString();

    }

    public static void writeTextToFile(Window window, String text) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(
                file.getAbsolutePath() + ".txt"
        )) {
            writer.print(text);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
