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
  ant  11,92s user 0,84s system 13% cpu 1:37,67 total
  ./build  4,13s user 0,31s system 56% cpu 7,811 total

```
