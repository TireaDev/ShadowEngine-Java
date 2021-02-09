package com.tireadev.shadowengine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public abstract class ShadowEngine {

    // Abstraction ======================================================
    public abstract void onAwake();
    public abstract void onStart();
    public abstract void onUpdate();
    public abstract void onClose();


    // Runnable =========================================================
    long window;

    public boolean construct(int width, int height, String title) {
        if (!glfwInit()) return false;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);
        glfwWindowHint(GLFW_RESIZABLE, 0);

        window = glfwCreateWindow(width, height, title, 0, 0);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(1);

        glfwShowWindow(window);

        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            keys[key] = action == GLFW_PRESS || action == GLFW_REPEAT;
        });

        glfwSetMouseButtonCallback(window, (window1, button, action, mods) -> {
            buttons[button] = action == GLFW_PRESS;
        });

        glfwSetCursorPosCallback(window, (window1, xpos, ypos) -> {
            mouseX = (int) xpos; mouseY = (int) ypos;
        });

        glfwSetScrollCallback(window, (window1, xoffset, yoffset) -> {
            scrollX = (int) xoffset; scrollY = (int) yoffset;
        });

        onAwake();

        return true;
    }

    public void start() {
        onStart();
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            onUpdate();

            glfwSwapBuffers(window);

            updateKeys();
            updateMouse();
        }
        onClose();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }


    // Key Event ========================================================
    public final int KEY_LAST = GLFW_KEY_LAST;

    boolean[] keys = new boolean[KEY_LAST];
    boolean[] keys_last = new boolean[KEY_LAST];

    public boolean keyDown(int key) {
        return keys[key];
    }

    public boolean keyUp(int key) {
        return !keys[key];
    }

    public boolean keyPressed(int key) {
        return !keys_last[key] && keys[key];
    }

    public boolean keyReleased(int key) {
        return keys_last[key] && !keys[key];
    }

    void updateKeys() {
        System.arraycopy(keys, 0, keys_last, 0, KEY_LAST);
    }


    // Mouse Event ======================================================
    public final int BUTTON_LAST = GLFW_MOUSE_BUTTON_LAST;

    boolean[] buttons = new boolean[BUTTON_LAST];
    boolean[] buttons_last = new boolean[BUTTON_LAST];

    int mouseX = 0, mouseY = 0;
    int scrollX = 0, scrollY = 0;

    public boolean mouseDown(int button) {
        return buttons[button];
    }

    public boolean mouseUp(int button) {
        return !buttons[button];
    }

    public boolean mousePressed(int button) {
        return !buttons_last[button] && buttons[button];
    }

    public boolean mouseReleased(int button) {
        return buttons_last[button] && !buttons[button];
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScrollX() {
        return scrollX;
    }

    public int getScrollY() {
        return scrollY;
    }

    void updateMouse() {
        System.arraycopy(buttons, 0, buttons_last, 0, BUTTON_LAST);

        scrollX = 0;
        scrollY = 0;
    }
}
