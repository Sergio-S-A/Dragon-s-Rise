# Dragon's Rise 🐉

**Language:** Java  
**License:** GNU General Public License v3.0  
**Description:** A 2D isometric game engine built with Hexagonal Architecture (DDD) featuring Newtonian physics simulation, procedural terrain generation, and modular feature-based design.

---

## Características

- **Arquitectura Hexagonal (DDD):** Separación estricta entre capas Domain, Application e Infrastructure, modularizada por features
- **Sistema de Física Newtoniana:** Implementación completa con fuerza, aceleración, velocidad, fricción y límites de magnitud
- **Sistema de Animación Multi-Estado:** Gestión de animaciones con estados (IDLE, RUNNING, ATTACKING) y direcciones (NORTH, SOUTH, EAST, WEST)
- **Renderizado Isométrico:** Sistema de tiles isométricos con soporte para mapas y terrenos visualizados en perspectiva
- **Generación Procedural de Terreno:** Algoritmos de generación (FlatTerrain, ProceduralTerrain, SinCosTerrainGenerator)
- **Estrategias de Texturizado:** Sistema estratégico de texturas para terrenos (CrystalCaveTexture)
- **Sistema de Cámaras:** Implementaciones Classic e Isometric para diferentes perspectivas de visualización
- **Gestión de Estados de Juego:** State pattern con GameStateManager (Singleton) para estados de menú y nivel
- **Sistema de UI Modular:** Componentes reutilizables (Button, Label) con builders fluidos
- **Object Pooling:** Optimización de memoria para objetos Vector2d de uso frecuente
- **Caching de Assets:** Precarga y almacenamiento en caché de sprites, fuentes e imágenes
- **Input Handling Adaptable:** Abstracción de controles con adaptadores específicos de plataforma

---

## Requisitos

- **JDK:** Java 8 o superior
- **Build System:** IntelliJ IDEA (proyecto .iml incluido)

---

## Estructura del Proyecto

```
src
+---core
|   +---config
|   |       GameConfig.java
|   |
|   +---loop
|   |       GameLoop.java
|   |
|   +---math
|   |       FastMath.java
|   |       Vector2d.java
|   |
|   +---memory
|   |       Vector2dPool.java
|   |
|   \---physics
|       |   Acceleration.java
|       |   Force.java
|       |   PhysicsSystem.java
|       |   Position.java
|       |   Velocity.java
|       |
|       \---impl
|               NewtonianPhysics.java
|               PhysicsBehavior.java
|
+---features
|   +---entities
|   |   +---base
|   |   |   +---domain
|   |   |   |   +---animation
|   |   |   |   |       AnimationFrame.java
|   |   |   |   |       AnimationState.java
|   |   |   |   |       AnimationSystem.java
|   |   |   |   |       AnimationTrack.java
|   |   |   |   |       Direction.java
|   |   |   |   |
|   |   |   |   +---input
|   |   |   |   |       InputController.java
|   |   |   |   |
|   |   |   |   +---model
|   |   |   |   |       Actor.java
|   |   |   |   |       EntityType.java
|   |   |   |   |       GameEntity.java
|   |   |   |   |
|   |   |   |   \---ports
|   |   |   |           SpriteRepository.java
|   |   |   |
|   |   |   \---infrastructure
|   |   |           AssetSpriteRepository.java
|   |   |           CharacterRenderer.java
|   |   |           SpriteMemoryCache.java
|   |   |
|   |   \---player
|   |       +---application
|   |       |       PlayerController.java
|   |       |
|   |       +---domain
|   |       |       PlayerAvatar.java
|   |       |
|   |       \---infrastructure
|   |               PlayerRenderer.java
|   |
|   +---gamestates
|   |   +---base
|   |   |   +---domain
|   |   |   |   |   GameState.java
|   |   |   |   |   TypeState.java
|   |   |   |   |
|   |   |   |   +---ports
|   |   |   |   |       RendererState.java
|   |   |   |   |
|   |   |   |   \---service
|   |   |   |           GameStateManager.java
|   |   |   |
|   |   |   \---infrastructure
|   |   +---level
|   |   |   +---application
|   |   |   +---domain
|   |   |   |       LevelState.java
|   |   |   |
|   |   |   \---infrastructure
|   |   |           CrystalCaveLevel.java
|   |   |
|   |   \---menu
|   |       +---domain
|   |       \---infrastructure
|   |               MainMenuState.java
|   |               MenuRenderer.java
|   |
|   +---tile
|   |   +---application
|   |   |       IsometricTileRenderer.java
|   |   |
|   |   +---domain
|   |   |   |   TileRepository.java
|   |   |   |
|   |   |   \---model
|   |   |           Tile.java
|   |   |           TileType.java
|   |   |
|   |   \---infrastructure
|   |           TileImageCache.java
|   |           TileMapRepository.java
|   |
|   +---ui
|   |   +---domain
|   |   |   |   ActionButton.java
|   |   |   |
|   |   |   +---builder
|   |   |   |       ButtonBuilder.java
|   |   |   |       LabelBuilder.java
|   |   |   |
|   |   |   \---model
|   |   |           Button.java
|   |   |           Component.java
|   |   |           Label.java
|   |   |
|   |   \---infrastructure
|   |           ButtonRenderer.java
|   |           LabelRenderer.java
|   |
|   +---view
|   |   +---application
|   |   |       ClassicCamera.java
|   |   |       IsometricCamera.java
|   |   |
|   |   \---domain
|   |           Camera.java
|   |
|   \---world
|       +---application
|       |   |   TerrainMapRepository.java
|       |   |
|       |   +---generation
|       |   |       FlatTerrain.java
|       |   |       ProceduralTerrain.java
|       |   |       SinCosTerrainGenerator.java
|       |   |
|       |   \---texture
|       |           CrystalCaveTexture.java
|       |
|       +---domain
|       |   |   TerrainMap.java
|       |   |
|       |   \---model
|       |           RiverProvider.java
|       |           TerrainFunction.java
|       |           TextureStrategy.java
|       |
|       \---infrastructure
|               WorldRendererState.java
|
+---launcher
|       GameLauncher.java
|
+---platform
|   \---desktop
|       +---assets
|       |       AssetCache.java
|       |       AssetLoader.java
|       |       AssetPreloader.java
|       |       FileLoader.java
|       |
|       +---graphics
|       |       DesktopWindow.java
|       |       RenderCanvas.java
|       |
|       \---input
|               DesktopInputAdapter.java
|               InputHandler.java
|               KeyboardListener.java
|               MouseListener.java
|
\---resources
    +---fonts
    |       OFL.txt
    |       Tiny5-Regular.ttf
    |
    \---images
        |   Camaras_Subterraneas.jpg
        |
        +---isometric_tileset
        |       tile_000.png - tile_012.png
        |
        +---keney_ui_pack_rpg
        |       (UI assets: buttons, bars, arrows, cursors, icons, panels)
        |
        \---wolf
                wolf_attack.png
                wolf_death.png
                wolf_howl.png
                wolf_idle.png
                wolf_run.png
```

---

## Arquitectura

### Hexagonal Architecture (DDD)

El proyecto implementa **Hexagonal Architecture** con estricta separación entre capas, organizado modularmente por features:

#### **Domain Layer (Núcleo de Negocio)**
Contiene la lógica de dominio pura, independiente de infraestructura:
- **Models:** `GameEntity`, `Tile`, `Button`, `Label`, `TerrainMap`
- **Services:** `GameStateManager`, `AnimationSystem`, `PhysicsSystem`
- **Ports (Interfaces):** `SpriteRepository`, `TileRepository`, `InputController`, `RendererState`
- **Value Objects:** `Position`, `Velocity`, `Acceleration`, `Force`, `AnimationFrame`

#### **Application Layer (Casos de Uso)**
Coordina la lógica de aplicación y orquesta las operaciones del dominio:
- **Controllers:** `PlayerController`
- **Services:** `IsometricTileRenderer`, `ClassicCamera`, `IsometricCamera`
- **Generators:** `FlatTerrain`, `ProceduralTerrain`, `SinCosTerrainGenerator`
- **Repositories:** `TerrainMapRepository`

#### **Infrastructure Layer (Adaptadores)**
Provee implementaciones concretas de los ports del dominio:
- **Repositories:** `AssetSpriteRepository`, `TileMapRepository`
- **Renderers:** `CharacterRenderer`, `PlayerRenderer`, `ButtonRenderer`, `LabelRenderer`, `MenuRenderer`
- **Caches:** `SpriteMemoryCache`, `TileImageCache`, `AssetCache`
- **Input Adapters:** `DesktopInputAdapter`, `KeyboardListener`, `MouseListener`
- **Graphics:** `DesktopWindow`, `RenderCanvas`

#### **Platform Layer (Específico de Plataforma)**
Encapsula dependencias de la plataforma Desktop (Java Swing/AWT):
- **Assets Management:** `AssetLoader`, `AssetPreloader`, `FileLoader`
- **Graphics:** Gestión de ventanas y renderizado
- **Input:** Manejo de teclado y mouse específico de AWT

### Patrones de Diseño Identificados

1. **Repository Pattern**
    - `SpriteRepository` (port) → `AssetSpriteRepository` (adapter)
    - `TileRepository` (port) → `TileMapRepository` (adapter)
    - Abstrae el acceso a datos de sprites, tiles y mapas del dominio

2. **Builder Pattern**
    - `GameEntity.EntityBuilder<T>`: Builder genérico con herencia para construcción fluida de entidades
    - `ButtonBuilder`: Construcción paso a paso de componentes UI Button con validaciones
    - `LabelBuilder`: Creación configurable de componentes de texto

3. **Singleton Pattern**
    - `GameStateManager`: Instancia única para gestión centralizada de estados de juego
    - `AssetCache`: Caché global de recursos precargados
    - `InputHandler`: Gestor único de entrada de usuario

4. **State Pattern**
    - `GameState` (abstracto) → `LevelState`, `MainMenuState`
    - `TypeState`: Enumeración de tipos de estado
    - Permite transiciones dinámicas entre estados del juego (menú, nivel, etc.)

5. **Object Pool Pattern**
    - `Vector2dPool`: Pool de objetos `Vector2d` pre-inicializados (50 objetos)
    - Reduce garbage collection mediante reutilización de instancias frecuentes

6. **Strategy Pattern**
    - `PhysicsSystem` (interfaz) → `NewtonianPhysics` (implementación)
    - `TerrainFunction`: Estrategia de función matemática para generación de terreno
    - `TextureStrategy`: Estrategia de aplicación de texturas (`CrystalCaveTexture`)
    - Permite intercambiar algoritmos de física, generación y texturizado

7. **Adapter Pattern**
    - `DesktopInputAdapter`: Adapta `KeyboardListener` al port `InputController`
    - Desacopla el dominio de la implementación específica de input AWT/Swing

8. **Cache Pattern**
    - `SpriteMemoryCache`: Almacenamiento en memoria de sprites procesados
    - `TileImageCache`: Caché de imágenes de tiles
    - `AssetCache`: Precarga y almacenamiento de recursos globales

---

## Cómo Ejecutar

### Compilación y Ejecución desde Terminal

```bash
# Navegar al directorio del proyecto
cd c:\Users\Sergio\Desktop\DragonsRise

# Compilar el proyecto
javac -d out -sourcepath src src/launcher/GameLauncher.java

# Ejecutar el juego
java -cp out launcher.GameLauncher
```

### Ejecución desde IntelliJ IDEA

1. Abrir el proyecto en IntelliJ IDEA
2. Configurar el JDK (Java 8 o superior)
3. Ejecutar la clase `launcher.GameLauncher`
4. El juego iniciará en modo pantalla completa

**Entry Point:** `launcher.GameLauncher.main()`

---

## Controles

### Teclado

| Acción | Teclas |
|--------|--------|
| **Mover Norte** | `W` o `↑` |
| **Mover Sur** | `S` o `↓` |
| **Mover Este** | `D` o `→` |
| **Mover Oeste** | `A` o `←` |
| **Atacar** | `SPACE` |

### Mouse

- **Click Izquierdo:** Interacción con elementos UI
- **Movimiento:** Navegación de menús

---

## Licencia

Este proyecto está licenciado bajo **GNU General Public License v3.0**.  
Consulta el archivo `LICENSE` para más detalles.
