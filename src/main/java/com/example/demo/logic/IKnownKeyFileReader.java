package com.example.demo.logic;

import javafx.stage.Window;

public interface IKnownKeyFileReader<O> {
    O readKeyFile(Window window);
}
