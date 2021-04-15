package com.tireadev.shadowengine.math;

import java.util.Objects;

public class Vec2f {
    public float x, y;

    public static final Vec2f zero  = new Vec2f(0, 0);
    public static final Vec2f one   = new Vec2f(1, 1);

    public static final Vec2f up    = new Vec2f(0, 1);
    public static final Vec2f down  = new Vec2f(0, -1);
    public static final Vec2f left  = new Vec2f(-1, 0);
    public static final Vec2f right = new Vec2f(1, 0);

    public Vec2f() { this.x = 0; this.y = 0; }
    public Vec2f(float x, float y) { this.x = x; this.y = y; }
    public Vec2f(Vec2f vec) { this.x = vec.x; this.y = vec.y; }

    public Vec2f add(Vec2f vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
        return this;
    }

    public Vec2f sub(Vec2f vec) {
        this.x = this.x - vec.x;
        this.y = this.y - vec.y;
        return this;
    }

    public Vec2f negate() {
        this.x = this.x * -1;
        this.y = this.y * -1;
        return this;
    }

    public Vec2f scale(float n) {
        this.x = this.x * n;
        this.y = this.y * n;
        return this;
    }

    public Vec2f div(float n) {
        this.x = this.x / n;
        this.y = this.y / n;
        return this;
    }

    public float mag() { return mag(this); }
    public static float mag(Vec2f vec) {
        return (float) Math.sqrt(mag2(vec));
    }

    public float mag2() { return mag2(this); }
    public static float mag2(Vec2f vec) {
        return vec.x * vec.x + vec.y * vec.y;
    }

    public float dot(Vec2f vec) { return dot(this, vec); }
    public static float dot(Vec2f v1, Vec2f v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public Vec2f norm() {
        this.div(this.mag());
        return this;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2f vec = (Vec2f) o;
        return Float.compare(vec.x, x) == 0 && Float.compare(vec.y, y) == 0;
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
