package com.tireadev.shadowengine.event;

public class KeyEvent {

    static final int SIZE = 256;

    static boolean[] keys = new boolean[SIZE];
    static boolean[] last_keys = new boolean[SIZE];

    public static final int KEY_FIRST = 0;
    public static final int KEY_LAST = SIZE;

    public static final int KEY_W = 'w';
    public static final int KEY_A = 'a';
    public static final int KEY_S = 's';
    public static final int KEY_D = 'd';

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
