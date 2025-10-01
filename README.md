# Draon's Rise - Prototipo de Videojuego 2D

**Lenguaje:** Java  
**Licencia:** GPL v3  

Un prototipo de videojuego 2D desarrollado en **Java puro** que sirve como base para el desarrollo de juegos con programación orientada a objetos.

---

## Características
- Motor de juego personalizado desde cero  
- Sistema de física 2D con vectores  
- Animaciones fluidas  
- Gestión de recursos (imágenes, sonidos, fuentes)  
- Sistema de niveles y menús interactivos  
- Control por teclado y ratón  

---

## Requisitos
- Java **17** o superior  

---

## Estructura del Proyecto
src/
core/ # Lógica principal del juego
  inputs/ # Manejo de entrada
  main/ # Clases principales
  math/ # Matemáticas y física
entities/ # Entidades del juego
graphics/ # Renderizado
modes/ # Modos de juego y menús
resources/ # Recursos del juego

yaml
Copiar código

---

## Cómo Ejecutar

Clona el repositorio:
```
git clone https://github.com/tu-usuario/draons-rise.git
cd draons-rise
Compila y ejecuta:

```

Copiar código
```
mvn clean compile exec:java -Dexec.mainClass="core.main.Main"
```
Controles
```
WASD / Flechas → Movimiento del personaje
Ratón → Interfaz y acciones
```
Licencia
```
Este proyecto está bajo la licencia GPL-3.0.
Consulta el archivo LICENSE para más información.
---








