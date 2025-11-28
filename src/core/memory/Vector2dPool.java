package core.memory;

import core.math.Vector2d;

import java.util.ArrayDeque;
import java.util.Deque;

public class Vector2dPool {
    private static final int POOL_SIZE = 50;
    private static final Deque<Vector2d> pool = new ArrayDeque<>(POOL_SIZE);

    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.push(new Vector2d(0, 0));
        }
    }

    public static Vector2d acquire() {
        Vector2d vector = pool.poll();
        if (vector == null) {
            return new Vector2d(0, 0);
        }
        vector.set(0, 0);
        return vector;
    }

    public static void release(Vector2d vector) {
        if (pool.size() < POOL_SIZE && vector != null) {
            pool.push(vector);
        }

    }
}
