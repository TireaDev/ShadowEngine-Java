package com.tireadev.shadowengine.event;

public interface MouseListener extends EventListener {
    void onMouseDown(MouseEvent e);
    void onMousePressed(MouseEvent e);
    void onMouseReleased(MouseEvent e);
    void onMouseMove(MouseEvent e);
    void onScroll(MouseEvent e);
}
