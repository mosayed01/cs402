package com.example.demo.ciphers.substitution;

import com.example.demo.base.BaseCipherApplication;

import java.util.Map;

public class SubstitutionCipherApplication extends BaseCipherApplication<Map<Character, Character>> {
    private static final SubstitutionICipherLogic cipher = new SubstitutionICipherLogic();

    public SubstitutionCipherApplication() {
        super(
                "Substitution Cipher Application",
                new CSVKeyFileReader(),
                cipher,
                false,
                true,
                cipher
        );
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> showErrorDialog(throwable));
        launch(args);
    }
}
