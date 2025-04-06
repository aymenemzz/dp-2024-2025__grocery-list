# Rapport technique — Architecture du projet Grocery List

## Vue d’ensemble

Ce projet implémente une application en ligne de commande permettant de gérer une liste de courses. L’architecture suit une logique modulaire, découplée autour de plusieurs couches distinctes :

## Architecture

### 1. **Facade d’exécution**
- `CLIApplicationFacade` orchestre l’ensemble de l’exécution.
- Elle utilise `CLIApplicationBuilder` pour construire le service métier et parser les arguments.

### 2. **Contrôleur**
- `CLICommandController` reçoit les arguments de la CLI et appelle la commande adéquate via la `CommandFactory`.

### 3. **Commandes**
- Les commandes (`AddItemCommand`, `RemoveItemCommand`, et `ListItemCommand`) implémentent l’interface `Command`.
- Elles overides la methode `execute()` qui appelle les méthodes du service métier.

### 4. **Service métier**
- `GroceryListServiceImpl` implémente `GroceryListService`.
- Il centralise la logique métier (ajout, suppression, listing) et délègue les accès aux données à un DAO.

### 5. **DAO & stockage**
- Deux implémentations de `GenericDAO` : `JsonStorageDAO` et `CsvStorageDAO`.
- La sélection du format est gérée via `StorageFactory`.

### 6. **Value Object**
- `Item` est un value object représentant un article avec nom, quantité, catégorie.

### 7. **Factories**
- `GroceryAppFactory`, `CommandFactory` et `StorageFactory` centralisent l’instanciation des services, commandes, et DAOs.
