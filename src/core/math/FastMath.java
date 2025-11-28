package core.math;

public class FastMath {
    private static final int scale = 10;
    private static final int ANGLE_PRECISION = 360 * scale;
    private static final float[] SIN_TABLE = new float[ANGLE_PRECISION];
    private static final float[] COS_TABLE = new float[ANGLE_PRECISION];
    private static final float PI = 3.14159265f;
    private static final float TWO_PI = 2 * PI;
    private static final float PRECISION = TWO_PI / ANGLE_PRECISION;
    private static final float CONVERT = 180f / PI;

    static {
        for (int i = 0; i < ANGLE_PRECISION; i++) {
            float radians = i * PRECISION;
            SIN_TABLE[i] = (float) Math.sin(radians);
            COS_TABLE[i] = (float) Math.cos(radians);
        }
    }

    private static int normalizeIndex(int index) {
        index %= ANGLE_PRECISION;
        return index < 0 ? index + ANGLE_PRECISION : index;
    }

    private static float lookupTrig(float radianAngle, float[] table) {
        int index = normalizeIndex((int) (radianAngle * CONVERT));
        return table[index];
    }

    public static float sin(float radianAngle) {
        return lookupTrig(radianAngle * scale, SIN_TABLE);
    }

    public static float cos(float radianAngle) {
        return lookupTrig(radianAngle * scale, COS_TABLE);
    }
}
