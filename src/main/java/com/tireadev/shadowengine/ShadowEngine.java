package com.tireadev.shadowengine;

import com.tireadev.shadowengine.math.Vec2f;
import com.tireadev.shadowengine.math.Vec2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class ShadowEngine {

    public static ShadowEngine instance;

    // Abstract =======================================================
    public abstract void onAwake();
    public abstract void onStart();
    public abstract void onUpdate(float deltaTime);
    public abstract void onClose();


    // Runnable =======================================================
    long window;

    public int width, height;
    public String title;

    long currentTime, oldTime;
    float deltaTime;

    public boolean construct(int width, int height, String title, boolean vsync, boolean hideCursor) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) return false;

        instance = this;

        this.width = width;
        this.height = height;
        this.title = title;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);
        glfwWindowHint(GLFW_RESIZABLE, 0);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) return false;

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(vsync ? 1 : 0);
        glfwSetInputMode(window, GLFW_CURSOR, hideCursor ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL);

        glfwShowWindow(window);

        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) ->
            keys[key] = action == GLFW_PRESS || action == GLFW_REPEAT
        );

        glfwSetMouseButtonCallback(window, (window1, button, action, mods) ->
            buttons[button] = action == GLFW_PRESS
        );

        glfwSetCursorPosCallback(window, (window1, xpos, ypos) -> {
            mouseX = (int) xpos;
            mouseY = (int) ypos;
        });

        glfwSetScrollCallback(window, (window1, xoffset, yoffset) -> {
            scrollX = (float) xoffset;
            scrollY = (float) yoffset;
        });

        currentTime = System.nanoTime();
        onAwake();

        return true;
    }

    public void start() {
        onStart();
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            oldTime = currentTime;
            currentTime = System.nanoTime();
            deltaTime = (currentTime - oldTime) / 1000000000f;

            onUpdate(deltaTime);

            glfwSwapBuffers(window);

            System.arraycopy(keys, 0, keys_last, 0, KEY_LAST);
            System.arraycopy(buttons, 0, buttons_last, 0, BUTTON_LAST);

            scrollX = 0;
            scrollY = 0;

        }
        onClose();

        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }


    // Window =========================================================
    public void setWindowTitle(String title) {
        glfwSetWindowTitle(window, title);
        this.title = title;
    }

    public void updateWindowTitle() {
        glfwSetWindowTitle(window, this.title);
    }


    // Util ===========================================================
    static final double pi180 = Math.PI / 180;

    public static double degToRad(float deg) {
        return deg * pi180;
    }

    public static float[] byteColorToFloat(final byte[] b) {
        int i, l = b.length;
        float[] toReturn = new float[l];
        for (i = 0; i < l; i++) toReturn[i] = Byte.toUnsignedInt(b[i]) / 255f;
        return toReturn;
    }


    // Keyboard =======================================================
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


    // Mouse ==========================================================
    public final int BUTTON_LAST = GLFW_MOUSE_BUTTON_LAST;

    boolean[] buttons = new boolean[BUTTON_LAST];
    boolean[] buttons_last = new boolean[BUTTON_LAST];

    int mouseX = 0, mouseY = 0;
    float scrollX = 0, scrollY = 0;

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

    public Vec2i getMousePos() {
        return new Vec2i(mouseX, mouseY);
    }

    public float getScrollX() {
        return scrollX;
    }

    public float getScrollY() {
        return scrollY;
    }

    public Vec2f getScroll() {
        return new Vec2f(scrollX, scrollY);
    }


    // Drawing
    public static final byte[] BLACK          = new byte[] { (byte) 12, (byte) 12, (byte) 12, (byte) 255 };
    public static final byte[] DARK_BLUE      = new byte[] { (byte)  0, (byte) 55, (byte)218, (byte) 255 };
    public static final byte[] DARK_GREEN     = new byte[] { (byte) 19, (byte)161, (byte) 14, (byte) 255 };
    public static final byte[] DARK_CYAN      = new byte[] { (byte) 58, (byte)150, (byte)221, (byte) 255 };
    public static final byte[] DARK_RED       = new byte[] { (byte)197, (byte) 15, (byte) 31, (byte) 255 };
    public static final byte[] DARK_MAGENTA   = new byte[] { (byte)136, (byte) 23, (byte)152, (byte) 255 };
    public static final byte[] DARK_YELLOW    = new byte[] { (byte)193, (byte)156, (byte)  0, (byte) 255 };
    public static final byte[] DARK_WHITE     = new byte[] { (byte)204, (byte)204, (byte)204, (byte) 255 };
    public static final byte[] BRIGHT_BLACK   = new byte[] { (byte)118, (byte)118, (byte)118, (byte) 255 };
    public static final byte[] BRIGHT_BLUE    = new byte[] { (byte) 59, (byte)120, (byte)255, (byte) 255 };
    public static final byte[] BRIGHT_GREEN   = new byte[] { (byte) 22, (byte)198, (byte) 12, (byte) 255 };
    public static final byte[] BRIGHT_CYAN    = new byte[] { (byte) 97, (byte)214, (byte)214, (byte) 255 };
    public static final byte[] BRIGHT_RED     = new byte[] { (byte)231, (byte) 72, (byte) 86, (byte) 255 };
    public static final byte[] BRIGHT_MAGENTA = new byte[] { (byte)180, (byte)  0, (byte)158, (byte) 255 };
    public static final byte[] BRIGHT_YELLOW  = new byte[] { (byte)249, (byte)241, (byte)165, (byte) 255 };
    public static final byte[] WHITE          = new byte[] { (byte)242, (byte)242, (byte)242, (byte) 255 };


    public void clear(final byte[] c) {
        clear(byteColorToFloat(c));
    }
    public void clear(final float[] c) {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(c[0], c[1], c[2], c[3]);
    }

    public void draw(Vec2i pos, final byte[] c) {
        draw(pos.x, pos.y, c);
    }
    public void draw(int x, int y, final byte[] c) {
        float fx = 2f*x / width - 1;
        float fy = -(2f*y / height - 1);

        glBegin(GL_POINTS);
        glColor4ub(c[0], c[1], c[2], c[3]);
        glVertex2f(fx, fy);
        glEnd();
    }

    public void drawLine(Vec2i p1, Vec2i p2, final byte[] c) {
        drawLine(p1.x, p1.y, p2.x, p2.y, c);
    }
    public void drawLine(int x1, int y1, int x2, int y2, final byte[] c) {
        int x, y, dx, dy, dx1, dy1, px, py, xe, ye, i;
        dx = x2 - x1;
        dy = y2 - y1;

        if (dx == 0) {
            if (y2 < y1) { int tmp = y2; y2 = y1; y1 = tmp; }
            for (y = y1; y <= y2; y++) draw(x1, y, c);
            return;
        }

        if (dy == 0) {
            if (x2 < x1) { int tmp = x2; x2 = x1; x1 = tmp; }
            for (x = x1; x <= x2; x++) draw(x, y1, c);
            return;
        }

        dx1 = Math.abs(dx);
        dy1 = Math.abs(dy);
        px = 2 * dy1 - dx1;
        py = 2 * dx1 - dy1;
        if (dy1 <= dx1) {
            if (dx >= 0) {
                x = x1; y = y1; xe = x2;
            } else {
                x = x2; y = y2; xe = x1;
            }

            draw(x, y, c);

            for (i = 0; x < xe; i++) {
                x = x + 1;
                if (px < 0) px = px + 2 * dy1;
                else {
                    if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) y = y + 1; else y = y - 1;
                    px = px + 2* (dy1 - dx1);
                }
                draw(x, y, c);
            }
        }
        else {
            if (dy >= 0) {
                x = x1; y = y1; ye = y2;
            } else {
                x = x2; y = y2; ye = y1;
            }

            draw(x, y, c);

            for (i = 0; y < ye; i++) {
                y = y + 1;
                if (py <= 0) py = py + 2 * dx1;
                else {
                    if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) x = x + 1; else x = x - 1;
                    py = py + 2 * (dx1 - dy1);
                }
                draw(x, y, c);
            }
        }
    }

    public void drawTriangle(Vec2i p1, Vec2i p2, Vec2i p3, final byte[] c) {
        drawTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, c);
    }
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, final byte[] c) {
        drawLine(x1, y1, x2, y2, c);
        drawLine(x2, y2, x3, y3, c);
        drawLine(x3, y3, x1, y1, c);
    }

    public void fillTriangle(Vec2i p1, Vec2i p2, Vec2i p3, final byte[] c) {
        fillTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, c);
    }
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, final byte[] c) {
        int tmp;

        if (y1 > y2) {
            tmp = y1;
            y1  = y2;
            y2  = tmp;

            tmp = x1;
            x1  = x2;
            x2  = tmp;
        }

        if (y2 > y3) {
            tmp = y2;
            y2 = y3;
            y3 = tmp;

            tmp = x2;
            x2 = x3;
            x3 = tmp;
        }

        if (y1 > y2) {
            tmp = y1;
            y1  = y2;
            y2  = tmp;

            tmp = x1;
            x1  = x2;
            x2  = tmp;
        }

        if (y2 == y3) {
            float is1 = (x2 - x1) / (float)(y2 - y1);
            float is2 = (x3 - x1) / (float)(y3 - y1);

            float cx1 = x1;
            float cx2 = x1;

            for (int sy = y1; sy <= y2; sy++) {
                drawLine((int)cx1, sy, (int)cx2, sy, c);
                cx1 += is1;
                cx2 += is2;
            }
        } else if (y1 == y2) {
            float is1 = (x3 - x1) / (float)(y3 - y1);
            float is2 = (x3 - x2) / (float)(y3 - y2);

            float cx1 = x3;
            float cx2 = x3;

            for (int sy = y3; sy > y1; sy--) {
                drawLine((int)cx1, sy, (int)cx2, sy, c);
                cx1 -= is1;
                cx2 -= is2;
            }
        } else {
            int x4, y4;

            x4 = (int)(x1 + ((float)(y2 - y1) / (float)(y3 - y1)) * (x3 - x1));
            y4 = y2;

            float is1 = (x2 - x1) / (float)(y2 - y1);
            float is2 = (x4 - x1) / (float)(y4 - y1);

            float cx1 = x1;
            float cx2 = x1;

            for (int sy = y1; sy <= y2; sy++) {
                drawLine((int)cx1, sy, (int)cx2, sy, c);
                cx1 += is1;
                cx2 += is2;
            }

            is1 = (x3 - x2) / (float)(y3 - y2);
            is2 = (x3 - x4) / (float)(y3 - y4);

            cx1 = x3;
            cx2 = x3;

            for (int sy = y3; sy > y2; sy--) {
                drawLine((int)cx1, sy, (int)cx2, sy, c);
                cx1 -= is1;
                cx2 -= is2;
            }
        }
    }

    public void drawRect(Vec2i pos, Vec2i size, final byte[] c) {
        drawRect(pos.x, pos.y, size.x, size.y, c);
    }
    public void drawRect(int x, int y, int w, int h, final byte[] c) {
        drawLine(x, y, x + w, y, c);
        drawLine(x + w, y, x + w, y + h, c);
        drawLine(x + w, y + h, x, y + h, c);
        drawLine(x, y + h, x, y, c);
    }

    public void fillRect(Vec2i pos, Vec2i size, final byte[] c) {
        fillRect(pos.x, pos.y, size.x, size.y, c);
    }
    public void fillRect(int x, int y, int w, int h, final byte[] c) {
        int x2 = x + w;
        int y2 = y + h;

        if (x2 < x) {
            int tmp = x2;
            x2 = x;
            x = tmp;
        }

        if (y2 < y) {
            int tmp = y2;
            y2 = y;
            y = tmp;
        }

        for (int i = x; i <= x2; i++)
            for (int j = y; j <= y2; j++)
                draw(i, j, c);
    }

    public void drawCircle(Vec2i pos, int r, final byte[] c) {
        drawCircle(pos.x, pos.y, r, c);
    }
    public void drawCircle(int x, int y, int r, final byte[] c) {
        if (r < 0) return;

        int y1, x1;

        for (y1 = r; y1 >= -r; y1--) {
            x1 = (int)Math.sqrt(r*r - y1*y1);
            draw(x - x1, y + y1, c);
            draw(x + x1, y + y1, c);
        }

        for (x1 = r; x1 >= -r; x1--) {
            y1 = (int)Math.sqrt(r*r - x1*x1);
            draw(x + x1, y + y1, c);
            draw(x + x1, y - y1, c);
        }
    }

    public void fillCircle(Vec2i pos, int r, final byte[] c) {
        fillCircle(pos.x, pos.y, r, c);
    }
    public void fillCircle(int x, int y, int r, final byte[] c) {
        if (r < 0) return;
        int y1, x1;
        for (y1 = r; y1 >= -r; y1--) {
            x1 = (int)Math.sqrt(r*r - y1*y1);
            drawLine(x - x1, y + y1, x + x1, y + y1, c);
        }
    }
}
