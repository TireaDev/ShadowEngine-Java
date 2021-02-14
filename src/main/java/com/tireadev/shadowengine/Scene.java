package com.tireadev.shadowengine;

public abstract class Scene {

    public static Scene active;

    public ShadowEngine instance;

    public Scene(ShadowEngine instance) {
        this.instance = instance;
    }

    public void setActive() {
        active = this;
    }

    public void onAwake() { }

    public void onStart() { }

    public void onClose() { }

    public abstract void onUpdate(float deltaTime);
}
