package com.tireadev.shadowengine;

public abstract class Scene {
    public static Scene active;

    public void setActive() {
        active = this;
    }

    public void onAwake(ShadowEngine se) { }

    public void onStart(ShadowEngine se) { }

    public void onUpdate(ShadowEngine se, float deltaTime) {

    }

    public void onClose(ShadowEngine se) { }
}
