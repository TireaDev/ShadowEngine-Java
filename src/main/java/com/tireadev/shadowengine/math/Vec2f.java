package com.tireadev.shadowengine.math;

import java.lang.Math;
import java.util.Objects;

public class Vec2f {
    public float x, y;

    public static final Vec2f zero = new Vec2f(0, 0);
    public static final Vec2f one = new Vec2f(1, 1);

    public static final Vec2f up = new Vec2f(0, 1);
    public static final Vec2f down = new Vec2f(0, -1);
    public static final Vec2f left = new Vec2f(-1, 0);
    public static final Vec2f right = new Vec2f(1, 0);

    public Vec2f() { this.x = 0; this.y = 0; }
    public Vec2f(float x, float y) { this.x = x; this.y = y; }
    public Vec2f(Vec2f vec2f) { this.x = vec2f.x; this.y = vec2f.y; }

    public void add(Vec2f vec2f) {
        this.x = this.x + vec2f.x;
        this.y = this.y + vec2f.y;
    }

    public void sub(Vec2f vec2f) {
        this.x = this.x - vec2f.x;
        this.y = this.y - vec2f.y;
    }

    public void negate() {
        this.x = this.x * -1;
        this.y = this.y * -1;
    }

    public void scale(float n) {
        this.x = this.x * n;
        this.y = this.y * n;
    }

    public void div(float n) {
        this.x = this.x / n;
        this.y = this.y / n;
    }

    public float mag() { return mag(this); }
    public static float mag(Vec2f vec2f) {
        return (float) Math.sqrt(mag2(vec2f));
    }

    public float mag2() { return mag2(this); }
    public static float mag2(Vec2f vec2f) {
        return vec2f.x * vec2f.x + vec2f.y * vec2f.y;
    }

    public float dot(Vec2f vec2f) { return dot(this, vec2f); }
    public static float dot(Vec2f v1, Vec2f v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public void norm() {
        this.div(this.mag());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2f vec2f = (Vec2f) o;
        return Float.compare(vec2f.x, x) == 0 && Float.compare(vec2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vec2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
