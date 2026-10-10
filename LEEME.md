# Espacio Vacío — mod para Minecraft 1.20.1 (Forge 47.x)

Dimensión `espaciovacio:espacio`: vacía, negra, con estrellas y un solo bloque (el Bloque Ancla en 0, 64, 0).

## Compilar
Requisitos: JDK 17.
1. Descarga el MDK de Forge 1.20.1 (47.3.0 o el que uses) desde https://files.minecraftforge.net
2. Copia dentro del MDK: la carpeta `src/`, `build.gradle`, `gradle.properties` y `settings.gradle` de este proyecto (reemplaza los del MDK; conserva `gradlew` y la carpeta `gradle/`).
3. Ejecuta `./gradlew build` (en Windows: `gradlew.bat build`).
4. El mod queda en `build/libs/espaciovacio-1.0.0.jar` → ponlo en tu carpeta `mods`.
Para probarlo en desarrollo: `./gradlew runClient`.

## Cómo se entra
- **Pistola de portales (Rick's Stuff):** elige la dimensión `espaciovacio:espacio` en el menú de la pistola. No necesita dependencia: es una dimensión normal.
- **Portal propio (difícil de conseguir):**
  - Marco Dimensional x2: 4 lingotes de netherita + 4 ojos de ender + 1 estrella del Nether (en cuadrícula 3x3, estrella al centro). Necesitas 10 bloques = 5 crafteos.
  - Núcleo Dimensional: 4 fragmentos de eco + 4 caparazones de shulker + 1 estrella del Nether.
  - Construye el marco (4 de ancho x 5 de alto, esquinas opcionales, interior 2x3 libre) y haz clic derecho en el marco con el Núcleo.
- **Comando (operadores):** `/espaciovacio ir` y `/espaciovacio volver`.

## Cómo se vuelve
- Cruzando un portal igual en la dimensión, o
- **Shift + clic derecho con las manos vacías** sobre el Bloque Ancla (regresas al punto exacto de donde saliste).

## Dónde cambiar cosas
- Recetas: `src/main/resources/data/espaciovacio/recipes/`
- Cantidad de estrellas: `STAR_COUNT` en `client/VoidSpaceEffects.java`
- Luz ambiental (0.1 = casi oscuro): `ambient_light` en `data/espaciovacio/dimension_type/espacio.json`
- Hacer el Bloque Ancla rompible: cambia `strength(-1.0F, ...)` en `registry/ModBlocks.java`
