# AGRICONNECT
- M1 STRI 2023/2024
- Modèles et Concepts du Parallélisme et de la Répartition
- Projet JAVA – Sockets/RMI
- Conception : Emma Guillemet & Matteo Léger
- Code : Enzo Soulan & Yon Beaurain

## Description du projet
L'application Agriconnect vise à mettre en œuvre un réseau de capteurs et d'actionneurs pour faciliter le travail des agriculteurs. L'objectif est de surveiller deux paramètres essentiels des cultures, à savoir l'humidité du sol et la température de l'air, grâce à des capteurs. En utilisant des actionneurs, le système peut déclencher l'arrosage des cultures si nécessaire. Un agriculteur peut surveiller et gérer l'ensemble du système via une application cliente.

## Versions

### Version 1 : Gestion des capteurs
La première version du système comprend une centrale de gestion et des capteurs. Chaque capteur, représenté par une application Java, est identifié par un code unique et des coordonnées GPS. À intervalle régulier, le capteur remonte à la centrale les informations de température et d'humidité. La centrale gère l'ajout et le retrait des capteurs, ainsi que l'enregistrement et l'affichage des données.

### Version 2 : Application cliente
Dans cette étape suivante, l'agriculteur peut utiliser une application Java pour interagir avec la centrale de gestion et les capteurs. L'application permet de lister les capteurs, d'obtenir les dernières informations d'un capteur, de calculer la moyenne et les tendances des mesures sur la dernière heure ou la dernière journée, de modifier les intervalles de mesures, et de déclarer ou retirer un capteur. L'ajout ou le retrait d'un capteur est désormais effectué par l'application cliente.

### Version 3 : Gestion de l’arrosage
Dans cette troisième version, le système Agriconnect évolue en intégrant la gestion d'actionneurs dédiés à l'arrosage. Ces actionneurs peuvent être déclarés ou retirés, suivant le modèle des capteurs de la version 2. Le déclenchement de l'arrosage sera exclusivement sous le contrôle de la centrale de gestion.

## Lancement de l'application

### 1. Dans une première CLI on compile et on lance la centrale
- javac CentraleImpl.java (compilation de la centrale)
- java CentraleImpl (lancement de la centrale)

### 2. Dans une deuxième CLI, on compile et on lance le client
- javac Centrale.java (compilation de l'interface de la centrale RMI)
- javac Client.java (compilation du client)
- java Client (lancement du client)