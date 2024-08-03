package com.example.demo.ciphers.otp;

import com.example.demo.base.BaseCipherApplication;

public class OTPCipherApplication extends BaseCipherApplication<Object> {

    private static final OTPCipherLogic cipher = new OTPCipherLogic();

    public OTPCipherApplication() {
        super(
                "One Time Pad Cipher",
                null,
                cipher,
                false,
                false,
                null
        );
    }

    @Override
    protected void setKEY() {
        key = cipher.key;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
