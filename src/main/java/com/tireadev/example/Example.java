package com.tireadev.example;

import com.tireadev.shadowengine.ShadowEngine;

public class Example extends ShadowEngine {

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }



    public static void main(String[] args) {
        Example demo = new Example();
        if (demo.construct(256*2, 240*2, "demo")) {
            demo.run();
        }
    }
}
