package com.tireadev.shadowengine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public abstract class ShadowEngine {

    long window;

    public abstract void onStart();
    public abstract void onUpdate();
    public abstract void onClose();

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

        return true;
    }

    public void start() {
        onStart();
        while (!glfwWindowShouldClose(window)) {
            onUpdate();
            glfwPollEvents();
            glfwSwapBuffers(window);
        }
        onClose();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }
}
