package com.tireadev.shadowengine.event;

public class KeyEvent {

    public static final int LAST = 256;

    static boolean[] keys = new boolean[LAST];
    static boolean[] last_keys = new boolean[LAST];

    public static boolean keyDown(int key) {
        return keys[key];
    }

    public static boolean keyUp(int key) {
        return !keys[key];
    }

    public static boolean keyPressed(int key) {
        return !last_keys[key] && keys[key];
    }

    public static boolean keyReleased(int key) {
        return last_keys[key] && !keys[key];
    }

    protected static void update() {
        last_keys = keys;
    }

}
