package com.tireadev.shadowengine.event;

public class MouseEvent {

    static final int SIZE = 3;

    static boolean[] buttons = new boolean[SIZE];
    static boolean[] last_buttons = new boolean[SIZE];

    static int mouseX = 0, mouseY = 0;
    static int scrollX = 0, scrollY = 0;

    public static final int MOUSE_1 = 0;
    public static final int MOUSE_2 = 1;
    public static final int MOUSE_3 = 2;

    public static boolean mouseDown(int button) {
        return buttons[button];
    }

    public static boolean mouseUp(int button) {
        return !buttons[button];
    }

    public static boolean mousePressed(int button) {
        return !last_buttons[button] && buttons[button];
    }

    public static boolean mouseReleased(int button) {
        return last_buttons[button] && !buttons[button];
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static int getScrollX() {
        return scrollX;
    }

    public static int getScrollY() {
        return scrollY;
    }

    protected static void update() {
        last_buttons = buttons;
        scrollX = 0;
        scrollY = 0;
    }

}
