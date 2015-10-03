# GrooveBerry_server
Serveur de musique contrôlable à distance par socket Java

## Installation
1. Cloner le répertoire `Seikomi/GrooveBerry_server` : `git clone https://github.com/Seikomi/GrooveBerry_server.git`
2. Lancer `mvn install`

## Utilisation
1. Aller dans le fichier `GrooveBerry_server/src/main/java/grooveberry_server/server/net/Server.java` et modifier la ligne 89 avec le chemin absolut du répertoire de musique à lire.
2. Recompiler
3. Aller dans `GrooveBerry_server/target/` et executer le jar

## Notes de version
- Lit seulement les formats `MP3` et `WAV`
- Les fichiers dans des sous-répertoires du répertoire de musique à lire ne sont pas lus.
