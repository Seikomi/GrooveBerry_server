# GrooveBerry_server
Serveur de musique contrôlable à distance par socket Java

- [Installation](#installation)
- [Utilisation](#utilisation)
- [Notes de version](#notes-de-version)

## Installation
1. Cloner le répertoire `Seikomi/GrooveBerry_server` : `git clone https://github.com/Seikomi/GrooveBerry_server.git`
2. Sous Eclipse, `Import... > Maven > Existing Maven projects...`, puis avec le plugin Maven : `Configure > Convert to Maven project`

## Utilisation
1. Aller dans le fichier `GrooveBerry_server/src/main/java/grooveberry_server/server/net/Server.java` et modifier la ligne 89 avec le chemin absolu du répertoire de musique à lire.
2. Lancer `mvn install`
3. Aller dans `GrooveBerry_server/target/` et executer le jar `grooveberry_server-1.0-SNAPSHOT-jar-with-dependencies`

## Notes de version
- Lit seulement les formats `MP3` et `WAV`
- Les fichiers dans des sous-répertoires du répertoire de musique à lire ne sont pas lus.
