package modes.menus;

import java.util.Random;

public class LevelData {
    public static int[][] testLevel = generarMatriz(100, 100);

    public static int[][] generarMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        Random random = new Random();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = 17 + random.nextInt(5); // 17,18 o 19
            }
        }
        return matriz;
    }


}