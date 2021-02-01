package com.tireadev.shadowengine.event;

public interface KeyListener extends EventListener {
    void onKeyDown(KeyEvent e);
    void onKeyPressed(KeyEvent e);
    void onKeyReleased(KeyEvent e);
}
