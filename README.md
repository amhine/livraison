# Système de Gestion Optimisée de Tournées de Livraison


## 🎯 Présentation

Application **Spring Boot** de gestion et d'optimisation de tournées de livraison permettant de minimiser les distances parcourues tout en respectant les contraintes des véhicules.

### Objectifs
- Gérer une flotte de véhicules et des entrepôts
- Planifier et optimiser les tournées de livraison
- Comparer deux algorithmes d'optimisation : **Nearest Neighbor** et **Clarke & Wright**
- Fournir une API REST complète pour toutes les opérations

---

## ✨ Fonctionnalités

### Gestion des entités
- **CRUD complet** pour : Entrepôts, Véhicules, Livraisons, Tournées
- Statuts de livraison : `PENDING`, `IN_TRANSIT`, `DELIVERED`, `FAILED`
- Gestion des contraintes véhicules (poids, volume, capacité)

### Optimisation des tournées
- **Nearest Neighbor** : Algorithme rapide, solution locale
- **Clarke & Wright** : Algorithme des économies, meilleure solution globale
- Calcul automatique des distances (formule Haversine)
- Validation des contraintes en temps réel

---

## 🛠️ Technologies

| Technologie | Version | Usage |
|------------|---------|-------|
| Java | 17+ | Langage principal |
| Spring Boot | 3.3.5 | Framework |
| Spring Data JPA | - | Accès données |
| H2 Database | - | Base de données |
| Maven | 3.6+ | Gestionnaire de build |
| Lombok | - | Réduction code boilerplate |
| Swagger/OpenAPI | - | Documentation API |
| JUnit 5 | - | Tests unitaires |

---

## 🏗️ Architecture

### Structure du projet
```
src/main/java/com/livraison/
├── config/              # Configuration Spring & beans
├── controller/          # REST endpoints
├── dto/                 # Data Transfer Objects
├── entity/              # Entités JPA
├── mapper/              # Conversion DTO ↔ Entity
├── optimizer/           # Algorithmes d'optimisation
├── repository/          # Accès base de données
├── service/             # Logique métier
└── util/                # Utilitaires (calcul distance)

src/main/resources/
├── application.properties    # Configuration app
├── applicationContext.xml    # Configuration beans
└── data.sql                  # Données de test
```

### Couches applicatives
1. **Controller** : Exposition REST API
2. **Service** : Logique métier et orchestration
3. **Repository** : Accès base de données (Spring Data JPA)
4. **Optimizer** : Stratégies d'optimisation (pattern Strategy)

---

## 📦 Installation

### Prérequis
- **JDK 17+** installé avec `JAVA_HOME` configuré
- **Maven 3.6+**
- **Git**
- **IDE** (IntelliJ IDEA recommandé)
- **Postman** (pour tester l'API)

### Étapes d'installation

1. **Cloner le projet**
```bash
git clone https://github.com/amhine/livraison.git
cd livraison
```

2. **Compiler le projet**
```bash
mvn clean install
```

3. **Lancer l'application**
```bash
./mvnw spring-boot:run
```

L'application démarre sur : `http://localhost:port


### Contraintes véhicules

| Type | Poids max | Volume max | Livraisons max |
|------|-----------|------------|----------------|
| BIKE | 50 kg | 0.5 m³ | 15 |
| VAN | 1000 kg | 8 m³ | 50 |
| TRUCK | 5000 kg | 40 m³ | 100 |

---

## 🌐 API REST

**Base URL** : `http://localhost:/api`

### Entrepôts (Warehouses)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/warehouses` | Liste tous les entrepôts |
| GET | `/api/warehouses/{id}` | Détails d'un entrepôt |
| POST | `/api/warehouses` | Créer un entrepôt |
| PUT | `/api/warehouses/{id}` | Modifier un entrepôt |
| DELETE | `/api/warehouses/{id}` | Supprimer un entrepôt |

### Véhicules (Vehicles)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/vehicles` | Liste tous les véhicules |
| GET | `/api/vehicles/{id}` | Détails d'un véhicule |
| POST | `/api/vehicles` | Créer un véhicule |
| PUT | `/api/vehicles/{id}` | Modifier un véhicule |
| DELETE | `/api/vehicles/{id}` | Supprimer un véhicule |

### Livraisons (Deliveries)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/deliveries` | Liste toutes les livraisons |
| GET | `/api/deliveries/{id}` | Détails d'une livraison |
| POST | `/api/deliveries` | Créer une livraison |
| PUT | `/api/deliveries/{id}` | Modifier une livraison |
| PATCH | `/api/deliveries/{id}/status` | Changer le statut |
| DELETE | `/api/deliveries/{id}` | Supprimer une livraison |

### Tournées (Tours)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/tours` | Liste toutes les tournées |
| GET | `/api/tours/{id}` | Détails d'une tournée |
| POST | `/api/tours` | Créer une tournée |
| POST | `/api/tours/optimize` | Optimiser une tournée |
| GET | `/api/tours/{id}/distance` | Distance totale |
| DELETE | `/api/tours/{id}` | Supprimer une tournée |


## 📸 Captures d'écran

Les captures suivantes sont disponibles dans `/docs/screenshots/` :

1. **Swagger UI** - Documentation API
2. **Postman** - Tests d'endpoints
3. **H2 Console** - Base de données
4. **Logs** - Optimisation en cours
5. **Diagrammes UML** - Architecture

