package example;

import com.tireadev.shadowengine.ShadowEngine;

public class Example extends ShadowEngine {

    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onClose() {

    }



    public static void main(String[] args) {
        Example demo = new Example();
        if (demo.construct(512, 480, "demo")) {
            demo.start();
        }
    }
}
