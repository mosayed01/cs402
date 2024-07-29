package com.example.demo.ciphers.shift;

import com.example.demo.base.BaseCipherApplication;

public class ShiftCipherApplication extends BaseCipherApplication<Integer> {
    private static final ShiftCipherLogic cipher = new ShiftCipherLogic();

    public ShiftCipherApplication() {
        super(
                "Shift Cipher Application",
                cipher,
                cipher
        );
    }

    @Override
    protected void setKEY() {
        if (keyTextField.getText().isEmpty()) {
            outputTextArea.setText("Key is empty");
            return;
        }

        key = Integer.parseInt(keyTextField.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
