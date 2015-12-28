package celo.mine2d.utils;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class Vec2 {
    public float x, y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(float d) {
        this(d, d);
    }

    public Vec2 add(float dx, float dy) {
        return new Vec2(x + dx, y + dy);
    }

    public Vec2 add(float d) {
        return add(d, d);
    }

    public Vec2 add(Vec2 v) {
        return add(v.x, v.y);
    }

    public Vec2 sub(float dx, float dy) {
        return new Vec2(x - dx, y - dy);
    }

    public Vec2 sub(float d) {
        return sub(d, d);
    }

    public Vec2 sub(Vec2 v) {
        return sub(v.x, v.y);
    }

    public Vec2 mul(float dx, float dy) {
        return new Vec2(x * dx, y * dy);
    }

    public Vec2 mul(float d) {
        return mul(d, d);
    }

    public Vec2 mul(Vec2 v) {
        return mul(v.x, v.y);
    }

    public Vec2 div(float dx, float dy) {
        return new Vec2(x / dx, y / dy);
    }

    public Vec2 div(float d) {
        return div(d, d);
    }

    public Vec2 div(Vec2 v) {
        return div(v.x, v.y);
    }

    public float dot(Vec2 v) {
        Vec2 t = this.mul(v);
        return t.x + t.y;
    }

    public float dot() {
        return dot(this);
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }

    public Vec2 normalize() {
        return div(length());
    }

    public float distance(Vec2 v) {
        return sub(v).length();
    }


    public Vec2 shake(String ptrn) {
        ptrn = ptrn.toUpperCase();
        if (ptrn == null || ptrn.isEmpty() || ptrn.length() > 2)
            ptrn = "XY";
        float[] v = new float[2];
        for (int i = 0; i < ptrn.length(); ++i) {
            switch (ptrn.charAt(i)) {
                case 'X':
                    v[i] = x;
                    break;
                case 'Y':
                    v[i] = y;
                    break;
                default:
                    v[i] = i == 0 ? x : y;
            }
        }
        return new Vec2(v[0], v[1]);
    }

    public Vec2 clone() {
        return new Vec2(x, y);
    }

    public Vec2 clamp(float min, float max) {
        Vec2 v = clone();
        v.x = clamp(v.x, min, max);
        v.y = clamp(v.y, min, max);
        return v;

    }

    private float clamp(float d, float min, float max) {
        if (d < min)
            return min;
        if (d > max)
            return max;
        return d;
    }

    public String toString() {
        return String.format("Vec2[%d]-> x: %f; y: %f", this.hashCode(), x, y);
    }
}
