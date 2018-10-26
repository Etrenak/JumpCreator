# JumpCreator
## Config
#### Principe
Le principe est de créer des Levels, niveaux de difficultés,
dans lesquels on paramètre le squelette des jumps crées avec ce
level. On choisit par exemple les différents types de blocks, ainsi
que les paramètres des éléments de jump.

#### Structure générale
Voici la structure du fichier de config :

**levels.yml**
```YAML 
Élément entre [] = facultatif. La valeur par défaut est affichée après le =

Nom_du_level:
  MaxGap: Espace max entre 2 éléments (Max possible = 4)
  MinGap: Espace min entre 2 éléments (Min possible = 1)
  ChangeDirectionChance: Pourcentage de chance que le jump change de direction. Ajoute de la difficulté en augmentant.
  Elements:
   Nom_au_pif:
    Type: "Type de l'élément"
    Chance: Pourcentage de chance que cet élément soit utilisé. Plus la chance est grande, plus on trouvera cet élément sur les jumps crées avec ce level.
    [InHeight=1]: Hauteur à laquelle se trouve la surface sur laquelle le joueur atterrira, en partant de l'élément précédent.
    [OutHeight=1]: Hauteur à laquelle se trouve la surface sur laquelle le joueur sautera sur l'élément suivant, en partant de l'élément précédent.
    [InWidth=1]: Largeur de la surface sur laquelle le joueur atterrira. Bloc plein = 1
    [OutWidth=1]: Largeur de la surface sur laquelle le joueur sautera sur l'élément suivant.
   2eme_nom_au_pif:
    Type: "Type de l'élément"
    ...
  DefaultBlock: 
    Type: "Type de l'élément" # Il est très fortement conseillé d’utiliser le type Block. Ne changez que si vous cochez les cases "Expert" et "Options avancées" dans différents logiciels.
    Material: "Type du block" # Ex: GLASS, STONE
```

#### Les différents types
Les différents types d'élements possibles sont les suivants :
- Block
- LadderTower
- SlabTower
- RoofedBlock
- Wall

Ils ont tous des propriétés propres, exposés ci dessous :

**Block**

Image: \
![Exemple de Block](https://imgur.com/ati2kS3m.png)

Exemple de configuration : 
```YAML
level:
  Elements:
    MyBlock:
      Type: "Block"
      Material: "SKULL"
      Chance: 5
      [Data=0]: 3
      [Owner=""]: "FuzeIII"
      [Height=1]: 0.5 #Signifie InHeight & OutHeight
      [Width=1]: 0.5 #Signifie InHeight & OutHeight
```

**LadderTower**

Image: \
![Exemple de LadderTower](https://imgur.com/M0A4ClNm.png)\
[>> Plus grand <<](https://imgur.com/M0A4ClN.png)

Exemple de configuration : 
```YAML
Élément entre [] = facultatif. La valeur par défaut est affichée après le =

level:
  Elements:
    MyLadderTower:
      Type: "LadderTower"
      Chance: 1
      Size: 10
      [TowerBlock=QUARTZ_BLOCK]: DIRT
```

**SlabTower**

Image: \
![Exemple de SlabTower](https://imgur.com/mpcZ0AMm.png)\
[>> Plus grand <<](https://imgur.com/mpcZ0AM.png)

Exemple de configuration : 
```YAML
Élément entre [] = facultatif. La valeur par défaut est affichée après le =

level:
  Elements:
    MySlabTower:
      Type: "SlabTower"
      Chance: 1
      Size: 10
      [TowerBlock=QUARTZ_BLOCK]: DIRT
```




**RoofedBlock**

Image: \
![Exemple de RoofedBlock](https://imgur.com/fXsqYJNm.png)\

Exemple de configuration : 
```YAML
Élément entre [] = facultatif. La valeur par défaut est affichée après le =

level:
  Elements:
    MyRoofedBlock:
      Type: "RoofedBlock"
      Chance: 1
```

**Wall**

Images: \
![Exemple de Wall](https://imgur.com/NzICTwGm.png) 
![Exemple de Wall](https://imgur.com/F21GWNbm.png)

Exemple de configuration : 
```YAML
Élément entre [] = facultatif. La valeur par défaut est affichée après le =

level:
  Elements:
    MyWall:
      Type: "Wall"
      Chance: 1
      Gap: 3 # Max = 4 et Min = 2
      [WallBlock=IRON_BLOCK]: GOLD_BLOCK
```


## Disclaimer
Les conseils et valeurs min/max sont données de manière à 
obtenir à 100% un jump faisable, bien que parfois assez 
complexe tout de même. Le level ultra dans la config de base
exploite pratiquement toutes les possibilités du plugin, je vous
conseille de vous en servir comme référence.

Si vous préférez aller plus loin que les valeurs max/min, vous
vous exposez à des jumps infaisable.