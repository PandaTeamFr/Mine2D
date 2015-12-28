package celo.mine2d.utils;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class Vec3 {
    public float x, y, z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(float d) {
        this(d, d, d);
    }

    public Vec3(Vec2 v, float d) {
        this(v.x, v.y, d);
    }

    public Vec3 add(float dx, float dy, float dz) {
        return new Vec3(x + dx, y + dy, z + dz);
    }

    public Vec3 add(float d) {
        return add(d, d, d);
    }

    public Vec3 add(Vec3 v) {
        return add(v.x, v.y, v.z);
    }

    public Vec3 sub(float dx, float dy, float dz) {
        return new Vec3(x - dx, y - dy, z - dz);
    }

    public Vec3 sub(float d) {
        return sub(d, d, d);
    }

    public Vec3 sub(Vec3 v) {
        return sub(v.x, v.y, v.z);
    }

    public Vec3 mul(float dx, float dy, float dz) {
        return new Vec3(x * dx, y * dy, z * dz);
    }

    public Vec3 mul(float d) {
        return mul(d, d, d);
    }

    public Vec3 mul(Vec3 v) {
        return mul(v.x, v.y, v.z);
    }

    public Vec3 div(float dx, float dy, float dz) {
        return new Vec3(x / dx, y / dy, z / dz);
    }

    public Vec3 div(float d) {
        return div(d, d, d);
    }

    public Vec3 div(Vec3 v) {
        return div(v.x, v.y, v.z);
    }

    public float dot(Vec3 v) {
        Vec3 t = this.mul(v);
        return t.x + t.y + t.z;
    }

    public float dot() {
        return dot(this);
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }

    public Vec3 normalize() {
        return div(length());
    }

    public float distance(Vec3 v) {
        return sub(v).length();
    }

    public Vec3 cross(Vec3 v) {
        return shake("yzx").mul(v.shake("zxy")).sub(shake("zxy").mul(v.shake("yzx")));
    }

    public Vec3 lookAt(Vec3 p, Vec3 rd) {
        Vec3 cf = p.sub(this).normalize();
        Vec3 cs = cf.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 cu = cs.cross(cf).normalize();
        return cs.mul(rd.x).add(cu.mul(rd.y)).add(cf.mul(rd.z)).normalize();
    }

    public Vec3 shake(String ptrn) {
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

    public Vec3 clone() {
        return new Vec3(x, y, z);
    }

    public int toInt() {
        byte r = (byte) ((int) (x * 255.0));
        byte g = (byte) ((int) (y * 255.0));
        byte b = (byte) ((int) (z * 255.0));
        return (((r << 16) | (g << 8) | b) << 8) >>> 8;
    }

    public Vec3 toVec(int c) {
        byte r = (byte) ((c >> 16) & 0xff);
        byte g = (byte) ((c >> 8) & 0xff);
        byte b = (byte) (c & 0xff);
        return new Vec3(r, g, b).div(255);
    }

    public Vec3 clamp(float min, float max) {
        Vec3 v = clone();
        v.x = clamp(v.x, min, max);
        v.y = clamp(v.y, min, max);
        v.z = clamp(v.z, min, max);
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
        return String.format("Vec3[%d]-> x: %f; y: %f; z: %f", this.hashCode(), x, y, z);
    }
}
