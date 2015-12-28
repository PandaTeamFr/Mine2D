package celo.mine2d.utils;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class Vec4 {
    public float x, y, z, w;

    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4(float d) {
        this(d, d, d,d);
    }

    public Vec4(Vec3 v, float d) {
        this(v.x, v.y, v.z, d);
    }

    public Vec4(Vec2 v, Vec2 v2) {
        this(v.x, v.y, v2.x, v2.y);
    }

    public Vec4 add(float dx, float dy, float dz, float dw) {
        return new Vec4(x + dx, y + dy, z + dz, w + dw);
    }

    public Vec4 add(float d) {
        return add(d, d, d, d);
    }

    public Vec4 add(Vec4 v) {
        return add(v.x, v.y, v.z, v.w);
    }

    public Vec4 sub(float dx, float dy, float dz, float dw) {
        return new Vec4(x - dx, y - dy, z - dz, w - dw);
    }

    public Vec4 sub(float d) {
        return sub(d, d, d, d);
    }

    public Vec4 sub(Vec4 v) {
        return sub(v.x, v.y, v.z, v.w);
    }

    public Vec4 mul(float dx, float dy, float dz, float dw) {
        return new Vec4(x * dx, y * dy, z * dz, w * dw);
    }

    public Vec4 mul(float d) {
        return mul(d, d, d, d);
    }

    public Vec4 mul(Vec4 v) {
        return mul(v.x, v.y, v.z, v.w);
    }

    public Vec4 div(float dx, float dy, float dz, float dw) {
        return new Vec4(x / dx, y / dy, z / dz, w / dw);
    }

    public Vec4 div(float d) {
        return div(d, d, d, d);
    }

    public Vec4 div(Vec4 v) {
        return div(v.x, v.y, v.z, v.w);
    }

    public float dot(Vec4 v) {
        Vec4 t = this.mul(v);
        return t.x + t.y + t.z + t.w;
    }

    public float dot() {
        return dot(this);
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }

    public Vec4 normalize() {
        return div(length());
    }

    public float distance(Vec4 v) {
        return sub(v).length();
    }


    public Vec4 shake(String ptrn) {
        ptrn = ptrn.toUpperCase();
        if (ptrn == null || ptrn.isEmpty() || ptrn.length() > 4)
            ptrn = "XYZW";
        float[] v = new float[4];
        for (int i = 0; i < ptrn.length(); ++i) {
            switch (ptrn.charAt(i)) {
                case 'X':
                    v[i] = x;
                    break;
                case 'Y':
                    v[i] = y;
                    break;
                case 'Z':
                    v[i] = z;
                    break;
                case 'w':
                    v[i] = w;
                    break;
                default:
                    v[i] = i == 0 ? x : (i == 1 ? y : i == 2 ? z : w);
            }
        }
        return new Vec4(v[0], v[1], v[2], v[3]);
    }

    public Vec3 shake3(String ptrn) {
        ptrn = ptrn.toUpperCase();
        if (ptrn == null || ptrn.isEmpty() || ptrn.length() > 3)
            ptrn = "XYZ";
        float[] v = new float[3];
        for (int i = 0; i < ptrn.length(); ++i) {
            switch (ptrn.charAt(i)) {
                case 'X':
                    v[i] = x;
                    break;
                case 'Y':
                    v[i] = y;
                    break;
                case 'Z':
                    v[i] = z;
                    break;
                default:
                    v[i] = i == 0 ? x : (i == 1 ? y : z);
            }
        }
        return new Vec3(v[0], v[1], v[2]);
    }

    public Vec2 shake2(String ptrn) {
        ptrn = ptrn.toUpperCase();
        if (ptrn == null || ptrn.length() != 2)
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
                case 'Z':
                    v[i] = z;
                    break;
                default:
                    v[i] = x;
            }
        }
        return new Vec2(v[0], v[1]);
    }

    public Vec4 clone() {
        return new Vec4(x, y, z, w);
    }

    public Vec4 clamp(float min, float max) {
        Vec4 v = clone();
        v.x = clamp(v.x, min, max);
        v.y = clamp(v.y, min, max);
        v.z = clamp(v.z, min, max);
        v.w = clamp(v.w, min, max);
        return v;

    }

    public Color toColor() {
        return new Color(x,y,z,w);
    }

    private float clamp(float d, float min, float max) {
        if (d < min)
            return min;
        if (d > max)
            return max;
        return d;
    }

    public String toString() {
        return String.format("Vec4[%d]-> x: %f; y: %f; z: %f; w: %f", this.hashCode(), x, y, z, w);
    }
}
