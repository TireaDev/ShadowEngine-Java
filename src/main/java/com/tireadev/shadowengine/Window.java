package com.tireadev.shadowengine;

import org.lwjgl.glfw.GLFW;

public class Window {

    public static long createWindow(int width, int height, String title) {
        if (!GLFW.glfwInit()) throw new IllegalStateException("init failed");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 1);

        long id = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        GLFW.glfwMakeContextCurrent(id);
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(id);

        return id;
    }
}
