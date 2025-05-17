# Rapport technique — Architecture du projet Grocery List

## Rapport final

### Ce que je n’ai pas eu le temps de faire
- Implementer le format SQL pour une connection avec une BDD
- Support de plusieurs listes nommées ou multi-utilisateur.

### Ce qui a été difficile
- Assurer une couverture de test exhaustive, notamment sur les DAO avec accès fichiers. La gestion des mocks statiques pour tester certaines factories (ex: CLIApplicationBuilder).
- Comprendre maven et ses crise (qaund il utilise une dependance local au lieu de la recuperer sur maven central).

### Quels design patterns ont été utilisés et pourquoi ?
- **Factory Pattern** : utilisé pour créer dynamiquement des commandes (`CommandFactory`), DAO (`StorageFactory`), et services (`GroceryAppFactory`) en fonction des paramètres. Cela permet une forte extensibilité.
- **Command Pattern** : chaque action CLI est encapsulée dans une classe avec une méthode `execute()`, ce qui permet de facilement rajouter ou modifier des comportements.
- **Facade Pattern** : `CLIApplicationFacade` simplifie l’accès au cœur applicatif depuis la CLI.
- **DTO Pattern (Data Transfer Object)** : utilisé pour structurer les échanges de données entre les différentes couches, notamment entre les entités métier (`GroceryItem`) et les interfaces Web (`WebGroceryItemDTO`).
- **Architecture hexagonale (Ports & Adapters)** : l’ensemble du projet est structuré selon ce modèle. Le domaine métier est isolé (`domain`), les ports (`GroceryListService`, `GroceryDAO`) définissent les interfaces entre l'application et l'extérieur, tandis que les adaptateurs (CLI, Web, stockage JSON/CSV) sont découplés et interchangeables. Ce choix permet une forte testabilité, une extension facile (nouveaux formats ou interfaces), et une bonne séparation des responsabilités.

### Réponses aux questions

#### Comment ajouter une nouvelle commande (en théorie, sans code) ?
- Créer une classe implémentant `Command` avec une méthode `execute()`.
- Ajouter un `case` dans la `CommandFactory` pour cette commande avec la logique d’instanciation.
- C’est tout, la façade et le contrôleur n’ont pas besoin de changer.

#### Comment ajouter une nouvelle source de données (en théorie, sans code) ?
- Créer une classe qui implémente `GroceryDAO` (ex: `DatabaseStorageDAO`).
- Ajouter un `case` dans `StorageFactory.getStorage(...)` pour retourner cette implémentation si le format est demandé.
- Le reste (service métier, commandes) reste inchangé.

#### Que dois-je modifier si je veux spécifier un magasin pour ajouter mes courses (en théorie, sans code) ?
- Ajouter un champ `store` dans `GroceryItem` (et dans les DTO).
- potentielemnt ajouter une option dans le builder `-m` par exemple 
- Adapter les commandes pour inclure ce champ lors de la création d’un item.
- mettre a jour le DTO et les mapper pour etre sur de recuperre ce qu'il faut de l'interface web.
- Éventuellement, modifier le DAO pour grouper ou stocker les items par magasin.