package features.world.infrastructure;

import core.config.GameConfig;
import core.physics.Position;
import features.entities.base.infrastructure.CharacterRenderer;
import features.gamestates.base.domain.ports.RendererState;
import features.tile.application.IsometricTileRenderer;
import features.tile.domain.model.Tile;
import features.tile.infrastructure.TileImageCache;
import features.view.domain.Camera;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class WorldRendererState implements RendererState<Graphics2D> {

    private final IsometricTileRenderer isometricTileRenderer;
    private final Camera camera;
    private final Rectangle visibleBounds;
    private final AffineTransform reusableTransform = new AffineTransform();
    private final ViewportCache cache = new ViewportCache();
    private final BufferedImage imgDefault;
    private final List<CharacterRenderer> characterRendererList;
    private BufferedImage renderBuffer;
    private Graphics2D bufferG2d;

    public WorldRendererState(IsometricTileRenderer isometricTileRenderer, List<CharacterRenderer> characterRendererList) {
        this.isometricTileRenderer = isometricTileRenderer;
        this.camera = isometricTileRenderer.getCamera();
        int tileSize = camera.getTileSizeWidth() * 6;
        this.visibleBounds = new Rectangle(-tileSize, -tileSize,
                GameConfig.WINDOW_WIDTH + tileSize,
                GameConfig.WINDOW_HEIGHT + tileSize);
        Tile tileDefault = isometricTileRenderer.getTileDefault();
        imgDefault = TileImageCache.getImage(tileDefault.id());

        this.characterRendererList = characterRendererList;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        ensureBufferSize();
        bufferG2d.clearRect(0, 0, renderBuffer.getWidth(), renderBuffer.getHeight());
        renderWorld();
        graphics2D.drawImage(renderBuffer, 0, 0, null);
    }

    public int getTerrainHeight(int x, int y) {
        return cache.contains(x, y) && isometricTileRenderer.getTile(x, y) != null
                ? isometricTileRenderer.getHeight(x, y) : 0;
    }

    private void renderWorld() {
        cache.update(camera, isometricTileRenderer);

        for (CharacterRenderer renderer : characterRendererList) {
            Position position = renderer.getCharacter().getPosition();
            int characterX = (int) (position.getX() - 1);
            int characterY = (int) (position.getY() - 1);
            renderer.updateIsometricHeight(getTerrainHeight(characterX, characterY));
        }

        for (int y = cache.startRow; y < cache.endRow; y++) {
            for (int x = cache.startCol; x < cache.endCol; x++) {
                renderTile(x, y);

                for (CharacterRenderer renderer : characterRendererList) {

                    Position position = renderer.getCharacter().getPosition();
                    int characterX = (int) (position.getX() - 1);
                    int characterY = (int) (position.getY() - 1);

                    if (!visibleBounds.contains(characterX, characterY)) {
                        continue;
                    }
                    if (x == characterX && y == characterY) {
                        renderer.render(bufferG2d);
                    }
                }
            }
        }
    }

    private void renderTile(int x, int y) {
        Tile tile = isometricTileRenderer.getTile(x, y);

        if (tile == null) return;

        BufferedImage img = TileImageCache.getImage(tile.id());

        if (img == null) return;

        int currentHeight = isometricTileRenderer.getHeight(x, y);
        int minHeight = getMinVisibleHeight(x, y, currentHeight);

        for (int z = minHeight; z <= currentHeight; z++) {

            int screenX = (int) ((x - y) * 32 - cache.offsetX);
            int screenY = (int) ((x + y - z) * 16 - cache.offsetY);

            if (!visibleBounds.contains(screenX, screenY)) continue;

            if (z == currentHeight) {
                reusableTransform.setToTranslation(screenX, screenY);
                bufferG2d.drawImage(img, reusableTransform, null);
            } else if (z < currentHeight - tile.tileType().variationHeight) {
                reusableTransform.setToTranslation(screenX, screenY - tile.tileType().variationHeight + 1);
                bufferG2d.drawImage(imgDefault, reusableTransform, null);
            }

        }
    }

    private int getMinVisibleHeight(int x, int y, int currentHeight) {
        int minVisible = currentHeight;
        int heightXPlus = getHeightSafe(x + 1, y);
        int heightYPlus = getHeightSafe(x, y + 1);

        if (heightXPlus < currentHeight) minVisible = Math.min(minVisible, heightXPlus + 1);
        if (heightYPlus < currentHeight) minVisible = Math.min(minVisible, heightYPlus + 1);

        return minVisible;
    }

    private int getHeightSafe(int x, int y) {
        if (x < 0 || y < 0 || x >= isometricTileRenderer.getTerrainWidth()
                || y >= isometricTileRenderer.getTerrainLength()) return 0;

        Tile tile = isometricTileRenderer.getTile(x, y);
        return tile != null ? isometricTileRenderer.getHeight(x, y) : 0;
    }

    private int[] calculateScreenPosition(int x, int y, int z) {
        int screenX = (int) ((x - y) * 32 - cache.offsetX);
        int screenY = (int) ((x + y - z) * 16 - cache.offsetY);
        return new int[]{screenX, screenY};
    }

    private void ensureBufferSize() {
        int w = GameConfig.WINDOW_WIDTH;
        int h = GameConfig.WINDOW_HEIGHT;

        if (renderBuffer == null || renderBuffer.getWidth() != w || renderBuffer.getHeight() != h) {
            if (bufferG2d != null) bufferG2d.dispose();
            renderBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            bufferG2d = renderBuffer.createGraphics();
            bufferG2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            cache.invalidate();
        }
    }

    private static class ViewportCache {
        float offsetX = Float.NaN, offsetY = Float.NaN;
        int startRow = -1, endRow = -1, startCol = -1, endCol = -1;

        void update(Camera cam, IsometricTileRenderer ren) {
            this.offsetX = cam.getOffsetX();
            this.offsetY = cam.getOffsetY();
            this.startRow = ren.getStartRow();
            this.endRow = ren.getEndRow();
            this.startCol = ren.getStartCol();
            this.endCol = ren.getEndCol();
        }

        void invalidate() {
            offsetX = Float.NaN;
        }

        boolean contains(int x, int y) {
            return x >= startCol && x < endCol && y >= startRow && y < endRow;
        }
    }
}