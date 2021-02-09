package com.tireadev.shadowengine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public abstract class ShadowEngine {

    long window;
    boolean running;

    public abstract void start();
    public abstract void update();

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

    public void run() {
        start();
        running = true;
        while (running) {
            update();
            glfwPollEvents();
            glfwSwapBuffers(window);
        }
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void close() {
        running = false;
    }
}
