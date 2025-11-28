package features.tile.domain.model;

public record Tile(
        String id,
        TileType tileType,
        boolean isTransparent,
        boolean isWalkable
) {

    public Tile {
        if (tileType == null) {
            throw new IllegalArgumentException("Texture ID cannot be null");
        }
        if (isTransparent && isWalkable) {
            throw new IllegalArgumentException("Tile cannot be transparent and walkable");
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
                "id='" + id + '\'' +
                ", tileTipe=" + tileType +
                ", isTransparent=" + isTransparent +
                ", isWalkable=" + isWalkable +
                '}';
    }
}