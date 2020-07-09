# BDD-app

Programme d'étude de base de données écrit en Java

Scripts SQL :
Pour exécuter ces scripts, se placer dans le dossier SQL
Pour lancer le script de création de la BD : sqlplus pseudo/mdp@Oracle1 @creer_base.sql
de même, pour peupler la BD : sqlplus pseudo/mdp@Oracle1 @insertion_donnees.sql
Pour effectuer les 2 a la fois : make all (dans le dossier SQL)


javaApp :
Ce placer dans la  racine du projet (la où est situé ce README)
Pour compiler le projet : make all

Pour lancer le projet, se placer dans le dossier bin et exécuter : java javaApp
