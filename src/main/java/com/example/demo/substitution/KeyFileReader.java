package com.example.demo.substitution;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyFileReader {

    public static Map<Character, Character> readKeyFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(window);
        if (file == null) {
            return null;
        }

        Map<Character, Character> keyMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    char key = parts[0].charAt(0);
                    char value = parts[1].charAt(0);
                    keyMap.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }

        return keyMap;
    }
}
