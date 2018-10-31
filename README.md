# Chat en ligne
## Présentation
Le tchat n'est pas synchronisé entre les fenêtres mais en ligne, avec tous les clients Websocket connectés.
Il suffit d'un serveur 'dummy broadcast' qui renvoie à tout le monde ce qu'on lui envoie.

On peut donc se parler entre PCs ET entre fenêtres.
## Utilisation
La fermeture de la fenêtre "Chat manager" entraîne la fermeture des autres. Il est possible de la cache.
L'en-tête bleu indique que la connexion est établie.

Vous pouvez changer de nom d'utilisateur en envoyant ``/nick``

## Avancé
On peut lancer une interface terminal avec ``java -jar Chat.jar -cli``.

On peut démarrer directement une fenêtre de chat avec ``java -jar Chat.jar nom_utilisateur``

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
   ./build  4,44s user 0,32s system 65% cpu 7,267 total
   ant  14,90s user 1,28s system 48% cpu 33,529 total
```
* Sur home.ribes.me (dual core 2.8GHz, RAID btrfs hdd)
```
   ant  6.93s user 0.69s system 136% cpu 5.602 total
```
