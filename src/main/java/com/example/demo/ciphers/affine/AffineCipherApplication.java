package com.example.demo.ciphers.affine;

import com.example.demo.base.BaseCipherApplication;
import javafx.stage.Stage;
import javafx.util.Pair;

public class AffineCipherApplication extends BaseCipherApplication<Pair<Integer, Integer>> {
    private static final AffineCipherLogic cipher = new AffineCipherLogic();

    public AffineCipherApplication() {
        super("Affine Cipher", cipher, cipher);
    }

    @Override
    protected void setKEY() {
        if (keyTextField.getText().isEmpty()) {
            key = new Pair<>(9, 13);
            return;
        }

        String[] keys = keyTextField.getText().split(",");
        key = new Pair<>(Integer.parseInt(keys[0]), Integer.parseInt(keys[1]));
    }

    @Override
    protected void otherSetup(Stage stage) {
        keyLabel.setText("Key (hint: 9,13):\nIf empty, \ndefault key will be used");
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> showErrorDialog(throwable));
        launch(args);
    }
}
