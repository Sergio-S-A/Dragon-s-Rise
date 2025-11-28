package features.tile.domain.model;

public enum TileType {

    STONE1((byte) 0, 64, 64, true, 0),
    STONE2((byte) 1, 64, 64, true, 0),
    STONE3((byte) 2, 64, 64, true, 0),
    WATER((byte) 3, 64, 64, false, -2),
    CRYSTAL_MEDIUM1((byte) 4, 64, 128, false, 4),
    CRYSTAL_MEDIUM2((byte) 5, 64, 128, false, 4),
    CRYSTAL_SMALL1((byte) 6, 64, 64, true, 0),
    CRYSTAL_SMALL2((byte) 7, 64, 64, true, 0),
    CRYSTAL_SMALL3((byte) 8, 64, 64, true, 0),
    CRYSTAL_SMALL4((byte) 9, 64, 64, true, 0),
    PILAR_BIG1((byte) 10, 128, 192, false, 7),
    PILAR_BIG2((byte) 11, 128, 256, false, 11),
    STALAGMITE((byte) 12, 64, 80, true, 1);

    public final byte id;
    public final int width;
    public final int height;
    public final boolean isWalkable;
    public final int variationHeight;

    TileType(byte id, int width, int height, boolean isWalkable, int variationHeight) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.isWalkable = isWalkable;
        this.variationHeight = variationHeight;
    }

    @Override
    public String toString() {
        return "TileType{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", isWalkable=" + isWalkable +
                '}';
    }
}
