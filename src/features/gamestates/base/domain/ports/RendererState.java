package features.gamestates.base.domain.ports;

public interface RendererState<T> {
    void render(T graphicsContext);
}
