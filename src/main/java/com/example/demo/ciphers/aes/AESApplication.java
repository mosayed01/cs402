package com.example.demo.ciphers.aes;

import com.example.demo.base.BaseCipherApplication;
import com.example.demo.base.IAttackable;
import com.example.demo.base.ICipher;
import com.example.demo.ciphers.aes.logic.AESCipher;

public class AESApplication extends BaseCipherApplication<String> {

    private static final AESCipher aesCipher = new AESCipher();

    public AESApplication() {
        super(
               "AES",
               aesCipher,
               null
        );
    }

    @Override
    protected void setKEY() {
        key = keyTextField.getText();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
