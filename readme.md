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

### Lancement du programme

Le fichier .jar se trouve dans le dossier out/artifacts/codingweek_jar, et doit etre lancé depuis le répertoire source du projet pour pouvoir charger toutes les ressources avec les paquets de FlashCards avec la commande suivante.

```bash
$ java -jar out/artifacts/codingweek_jar/codingweek.jar
```

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