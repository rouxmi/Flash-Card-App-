# Coding week 2023 - Telecom Nancy FlashCards

* Responsable : Noémie Gonnier <<noemie.gonnier@telecomnancy.eu>>

## Membres du groupe

* Lucie Boucher <<lucie.boucher@telecomnancy.eu>>
* Rémi Bourdais <<remi.bourdais@telecomnancy.eu>>
* Louis Chatard <<louis.chatard@telecomnancy.eu>>
* Lola Montignier <<lola.montignier@telecomnancy.eu>>

## Description du projet

Ce projet implémente une application de Flash Cards. Les Flash Cards sont un système d'apprentissage qui utilise des cartes avec d'un côté une question et la réponse associée au dos. L'intérêt de cette méthode est de la pratiquer régulièrement, en triant les cartes et en mettant en premier les cartes posant des difficultés.

[Le sujet détaillé est disponible en version PDF](./Documents/CodingWeek%202022-2023%20-%20Sujet.pdf)

## Vidéo de présentation

Vidéo d'une dizaine de minutes présentant le projet et les fonctionnalités implémentées.

[![Vidéo de présentation](https://img.youtube.com/vi/0Z0Z0Z0Z0Z0/0.jpg)](https://youtu.be/kiIzWanBTbM)


### Lancement du programme Jar

Le fichier .jar se trouve dans le dossier out/artifacts/codingweek_jar, et doit etre lancé depuis le répertoire source du projet pour pouvoir charger toutes les ressources avec les paquets de FlashCards avec la commande suivante.

```bash
$ java -jar out/artifacts/codingweek_jar/codingweek.jar
```

### Compilation du Code

Le code source se trouve dans le dossier src, il faut ensuite compiler le code,
en ajoutant les dépendances suivantes :

-sqlite-jdbc-3.7.2.jar
-javafx.base
-javafx.fxml
-javafx.controls
-javafx.media
-gson-2.8.6.jar


### Utilisation de l'application par fenêtre

L'application se lance avec une fenêtre contenant le menu principal avec les boutons suivants :

- Importer: Charger un paquet de FlashCards (à partir d'un fichier .json ou .apkg)
- Quitter: permet de quitter l'application
- Trier: Tri des paquets de FlashCards
- Partager: Partager un paquet de FlashCards
- Techniques D'apprentissages: Choisir la technique d'apprentissage de tout les paquets (par défaut : aléatoire)
- Gestion/Apprentissage: Choisir si en cliquant sur un paquet on arrive sur la gestion des cartes ou sur l'apprentissage
- Cliquer sur un paquet pour l'ouvrir
- Nouveau paquet de FlashCards (Paquet vide) en cliquant sur le paquet avec un + dessus

En cliquant un paquet, on arrive sur une fenêtre de gestion avec les cartes du paquet, avec les boutons suivants :

- Ajouter une carte: Ajouter une carte au paquet
- Supprimer le paquet: Supprimer le paquet
- Accueil: Retourner au menu principal
- Cliquer sur une carte pour l'ouvrir
- Quitter: Quitter l'application
- Technique d'apprentissage: Choisir la technique d'apprentissage (par défaut : aléatoire) du paquet.
- Exporter: Exporter le paquet de FlashCards (en .json)
- Modification du nom/de la description/des tags du paquet
- 
### Fonctionnalités implémentées

L’application permet :

• de créer une carte, d’éditer son contenu (face question, face réponse), de supprimer une carte.


• de créer un paquet de carte (possedant un titre, une description, et des étiquettes), de modifier un paquet, de supprimer un paquet ;

• d’exporter et d’importer un paquet de cartes en format JSON .

• de travailler l’apprentissage d’un paquet de cartes sélectionné au préalable. L’application présente la question,
laisser un temps de réflexion(choisi par l'utilisateur dans le mode révision et infini si dans le mode entrainement), montrer la réponse, permettre à l’utilisateur de s’auto-évaluer sur la carte présentée ;

• de configurer l’apprentissage en choisissant un mode de tirage de carte (Random, Classique, Libre et Master)
    
    - Random : les cartes sont tirées au hasard
    - Classique : les cartes sont tirées dans l'ordre de création
    - Libre : L'utilisateur choisit le pourcentage de cartes qu'il souhaite travailler parmi les 5 états de carte différents (non vue, a revoir, debut apprentissage, fin apprentissage, aquise parfaite)
    - Master: favorise les cartes qui peuvent changer d'état (non vue, a revoir, debut apprentissage, fin apprentissage), ensuite priorise les cartes qui sont le plus loin de l'état acquise parfaite

• de consulter les statistiques d’apprentissage d’un paquet de cartes, d'une carte et d'une session d'entraînement.

### Les extensions implémentées

L'application permet également :

• de partager en ligne des paquets de cartes dans un drive google en ligne. ;

• de intégrer des données multimédia à vos cartes (images (.png,.jpeg,.gif), sons (.wav)). Pour les données sons, il faut recharger la charge afin que le son puisse être lu;

• d'importer les piles de cartes provenant d'Anki avec le format .apkg ;

• de proposer une organisation des paquets de cartes par avancement dans l'apprentissage.

. de copier/coller des cartes entre paquets.

• de proposer un mode où l’apprenant doit saisir la réponse.