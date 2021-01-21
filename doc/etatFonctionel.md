# Etat fonctionel

Le programme a été réalisé avec le système d'exploitation Windows.


## Fonctionnalités en jeu 

3 bouttons sont présents sur l'interface du jeu :

* Le boutton "Fin de tour" permet de changer de joueur.
* Le boutton "Piocher" permet de piocher une carte.
* Le boutton "Bouger" permet de bouger une carte.

# Foncitonnement en jeu

* Pour placer une carte il faut cliquer sur l'emplacement voulue de l'interface.
* Le boutton "Fin de tour" ne fonctionnera que si je joueur en cours a au moins piocher et poser une carte sur le plateau.
* Le boutton "Bouger" initialise la séquence qui permet de bouger une carte. Une fois cette séquence initié, il faut cliquer sur la carte que l'on souhaite bouger, et ensuite sur l'emplacement sur lequel on veut bouger cette carte.

## Modes de jeu

Le jeu possède 3 paramètre qui permettes de modifer le mode de jeu :

```java
boolean modeAvance = false;
boolean modePlateauLibre = false;
boolean modeTroisJoueur = false;
```

### Mode avancé

En mode avancé le joueur a 3 carte en main. l'interface s'adapte donc pour afficher 3 carte dans la main du joueur. Pour choisir une carte il faut cliquer sur la carte voulue. Il suffit ensuite de placer la carte sur l'endroit voulue comme en mode normal. Par défaut la carte choisie est la première.

### Mode plateau libre

Ce mode permet de joueur sur un plateau a forme libre, l'interface change donc poura afficher 10 * 10 cases a l'écran.

### Mode trois joueurs

Ce mode permet de joueur à 3.



## Liste de ce qui fonctionne :

* Mode normal du jeu : OUI `#f03c15`