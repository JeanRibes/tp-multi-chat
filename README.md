#Chat en ligne
Le tchat n'est pas synchronisé entre les fenêtres mais en ligne, avec tous les clients Websocket connectés.
Il suffit d'un serveur 'dummy broadcast' qui renvoie à tout le monde ce qu'on lui envoie.

On peut donc se parler entre PCs ET entre fenêtres.

# Installer
Il faut avoir `ant`, le CMake de Java.
## Recettes:
* `clean` : nettoie tout
* `compile`
* `package`: crée le JAR
* `run`: lance le JAR. Il faut avoir fait `compile` avant au moins une fois !
* `all` : compile et lance l'appli


## Comparatif de performances
* Sur Tanit (single-core i686 1.4 GHz, hdd IDE)
```
   ./build  1,01s user 0,17s system 18% cpu 6,513 total
   ant  14,90s user 1,28s system 48% cpu 33,529 total
```
* Sur home.ribes.me (dual core 2.8GHz, RAID btrfs hdd)
```
   ./build  0.39s user 0.08s system 115% cpu 0.405 total
   ant  6.93s user 0.69s system 136% cpu 5.602 total
```
