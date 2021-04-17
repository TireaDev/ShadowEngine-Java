package com.tireadev.shadowengine;

import com.tireadev.shadowengine.math.Vec2f;
import com.tireadev.shadowengine.math.Vec2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RasterFormatException;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

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


    // Drawing ========================================================
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
        glBegin(GL_POINTS);
        glColor4ub(c[0], c[1], c[2], c[3]);
        glVertex2f((2f*x / width - 1), -(2f*y / height - 1));
        glEnd();
    }

    public void drawLine(Vec2i p1, Vec2i p2, final byte[] c) {
        drawLine(p1.x, p1.y, p2.x, p2.y, c);
    }
    public void drawLine(int x1, int y1, int x2, int y2, final byte[] c) {
        glBegin(GL_LINES);
        glColor4ub(c[0], c[1], c[2], c[3]);
        glVertex2f((2f*x1 / width - 1), -(2f*y1 / height - 1));
        glVertex2f((2f*x2 / width - 1), -(2f*y2 / height - 1));
        glEnd();
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
        glBegin(GL_TRIANGLES);
        glColor4ub(c[0], c[1], c[2], c[3]);
        glVertex2f((2f*x1 / width - 1), -(2f*y1 / height - 1));
        glVertex2f((2f*x2 / width - 1), -(2f*y2 / height - 1));
        glVertex2f((2f*x3 / width - 1), -(2f*y3 / height - 1));
        glEnd();
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
        glBegin(GL_POLYGON);
        glColor4ub(c[0], c[1], c[2], c[3]);
        glVertex2f((2f*x / width - 1), -(2f*y / height - 1));
        glVertex2f((2f*(x+w) / width - 1), -(2f*y / height - 1));
        glVertex2f((2f*(x+w) / width - 1), -(2f*(y+h) / height - 1));
        glVertex2f((2f*x / width - 1), -(2f*(y+h) / height - 1));
        glEnd();
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
        drawCircle(x, y, r, c);
        int y1, x1;
        for (y1 = r; y1 >= -r; y1--) {
            x1 = (int)Math.sqrt(r*r - y1*y1);
            drawLine(x - x1, y + y1, x + x1, y + y1, c);
        }
    }

    public byte[] loadImage(String path)
            throws IOException, IllegalArgumentException, RasterFormatException {
        BufferedImage image;
        byte[] data;

        image = ImageIO.read(new File(path));
        byte[] buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        data = ByteBuffer
                .allocate(8 + buffer.length)
                .putInt(image.getWidth())
                .putInt(image.getHeight())
                .put(buffer)
                .array();

        return data;
    }

    public byte[] loadImage(String path, int x, int y, int w, int h)
            throws IOException, IllegalArgumentException, RasterFormatException {
        BufferedImage image, subImg, toReturn;
        byte[] data;

        image = ImageIO.read(new File(path));
        subImg = image.getSubimage(x, y, w, h);
        toReturn = new BufferedImage(
                image.getColorModel(),
                image.getRaster().createCompatibleWritableRaster(w, h),
                image.isAlphaPremultiplied(),
                null);
        subImg.copyData(toReturn.getRaster());
        byte[] buffer = ((DataBufferByte) toReturn.getRaster().getDataBuffer()).getData();
        data = ByteBuffer
                .allocate(8 + buffer.length)
                .putInt(toReturn.getWidth())
                .putInt(toReturn.getHeight())
                .put(buffer)
                .array();

        return data;
    }

    public void drawImage(Vec2i pos, byte[] data, int scale) {
        drawImage(pos.x, pos.y, data, scale);
    }
    public void drawImage(int x, int y, byte[] data, int scale) {
        if (data.length <= 8) return;

        int ix, iy, i, ox, oy;

        ByteBuffer wrapped = ByteBuffer.wrap(data);
        int w = wrapped.getInt();

        for (ix = 0, iy = 0, i = 0; i + 3 + 8 < data.length; i += 4) {
            if (wrapped.get(i + 8) != 0)
                for (ox = 0; ox < scale; ox++)
                    for (oy = 0; oy < scale; oy++)
                        draw(x + ix + ox, y + iy + oy, new byte[] {
                                wrapped.get(i + 3 + 8),
                                wrapped.get(i + 2 + 8),
                                wrapped.get(i + 1 + 8),
                                wrapped.get(i + 8)
                        });

            ix += scale;
            if (ix == w * scale) {
                ix = 0;
                iy += scale;
            }
        }
    }

    public int getImageWidth(byte[] data) {
        if (data.length < 4) return 0;
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        return wrapped.getInt();
    }

    public int getImageHeight(byte[] data) {
        if (data.length < 8) return 0;
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        wrapped.getInt();
        return wrapped.getInt();
    }



    // Sound ==========================================================
    public byte[] loadSound(String path)
            throws IOException, UnsupportedAudioFileException {
        AudioInputStream ais;
        ByteBuffer toReturn;

        ais = AudioSystem.getAudioInputStream(new File(path));
        byte[] buffer = new byte[1024*32];
        int read = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length);

        while ((read = ais.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }

        AudioInputStream reusableAis = new AudioInputStream(
                new ByteArrayInputStream(baos.toByteArray()),
                ais.getFormat(),
                AudioSystem.NOT_SPECIFIED
        );

        // float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian
        ByteBuffer format = ByteBuffer.allocate(4 + 4 + 4 + 1 + 1);
        AudioFormat aisFormat = ais.getFormat();
        format.putFloat(aisFormat.getSampleRate())
                .putInt(aisFormat.getSampleSizeInBits())
                .putInt(aisFormat.getChannels())
                .put(aisFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) ? (byte) 1 : (byte) 0)
                .put(aisFormat.isBigEndian() ? (byte) 1 : (byte) 0);

        toReturn = ByteBuffer.allocate(format.array().length + baos.size());
        toReturn.put(format.array());
        toReturn.put(baos.toByteArray());

        ais.close();

        return toReturn.array();
    }

    public void playSound(byte[] data)
            throws IOException, LineUnavailableException {
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        AudioFormat af = new AudioFormat(
                wrapped.getFloat(),
                wrapped.getInt(),
                wrapped.getInt(),
                wrapped.get() == (byte) 1,
                wrapped.get() == (byte) 1
        );
        ByteArrayInputStream bais = new ByteArrayInputStream(Arrays.copyOfRange(wrapped.array(), 4+4+4+1+1, wrapped.array().length));
        AudioInputStream ais = new AudioInputStream(bais, af, AudioSystem.NOT_SPECIFIED);
        ais.reset();
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }
}
