#Chat local
Je prévois de mettre une fonction Websocket pour tester avec tout le monde.

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
