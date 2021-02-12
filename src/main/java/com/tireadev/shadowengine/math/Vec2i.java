package com.tireadev.shadowengine.math;

import java.util.Objects;

public class Vec2i {
    public int x, y;

    public static final Vec2i zero  = new Vec2i(0, 0);
    public static final Vec2i one   = new Vec2i(1, 1);

    public static final Vec2i up    = new Vec2i(0, 1);
    public static final Vec2i down  = new Vec2i(0, -1);
    public static final Vec2i left  = new Vec2i(-1, 0);
    public static final Vec2i right = new Vec2i(1, 0);

    public Vec2i() { this.x = 0; this.y = 0; }
    public Vec2i(int x, int y) { this.x = x; this.y = y; }
    public Vec2i(Vec2i vec) { this.x = vec.x; this.y = vec.y; }

    public void add(Vec2i vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
    }

    public void sub(Vec2i vec) {
        this.x = this.x - vec.x;
        this.y = this.y - vec.y;
    }

    public void negate() {
        this.x = this.x * -1;
        this.y = this.y * -1;
    }

    public void scale(int n) {
        this.x = this.x * n;
        this.y = this.y * n;
    }

    public void div(int n) {
        this.x = this.x / n;
        this.y = this.y / n;
    }

    public float mag() { return mag(this); }
    public static float mag(Vec2i vec) {
        return (float) Math.sqrt(mag2(vec));
    }

    public float mag2() { return mag2(this); }
    public static float mag2(Vec2i vec) {
        return vec.x * vec.x + vec.y * vec.y;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2i vec = (Vec2i) o;
        return Float.compare(vec.x, x) == 0 && Float.compare(vec.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vec2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
