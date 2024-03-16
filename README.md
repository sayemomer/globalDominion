# Project Title: Advanced Programming Practices - Build 2

## Description

This repository contains the first incremental code build for the Advanced Programming Practices course (SOEN 6441) at
Concordia University, Winter 2024. This build demonstrates a subset of the system's capabilities, focusing on delivering
operational code that addresses specific project problems and implements key features effectively.

## Build Goal

The goal of this build is to showcase the development of a game that includes a command prompt interface alongside a
GUI, emphasizing user-driven map editing, game phase management, and player interactions as per the detailed
requirements provided.

## Architectural Design

A brief overview of our project's architectural design is included in the `design` directory. This design outlines
the system's modular structure, detailing how different components interact to facilitate clear, maintainable code
development.

### Key Features

- **Map Editor**: Supports creating, deleting, and editing map elements such as continents and countries, ensuring map
  correctness through validation.
- **Game Play**: Implements a `GameEngine` class to manage game phases according to specified rules, with functionality
  for map loading, player creation, country assignment, and order management.
- **Command Prompt Interface**: Available throughout the game, allowing for the execution of game and map editor
  commands with validation and feedback.

## Installation and Setup

Instructions on setting up the project locally, including any prerequisites, library dependencies, and environment
setup.

```bash
# Clone the repository
git clone <repository-url>

# Navigate to the project directory
cd <project-directory>

# Instructions to install dependencies
mvn install
mvn build
```


## Testing

- [ ] Include at least 10 relevant test cases covering key aspects of the code as specified.

- Map tests :
    1. Map validation – checks if map is a connected graph
    2. Continent validation – checks if continent is a connected subgraph
    3. Map validation – checks if map doesn't has self loop
- GameState tests:
    1. Country validation- checks if the list of country IDs for a given continent is correct
    2. Continent validation- checks if returned list of continents owned by players is correct 
    3. reinforcements validation- check if assign reinforcements works correctly
 - Controllers tests:
    1. player validation- checks if add player command doesn't add multiple players with the same name
    2. player validation- checks if remove player command doesn't remove a player that doesn't exist
    3. player validation- checks if the number of players reduce after removing a player
    4. map edit- checks if the related countries are also removed after removing a continent
    5. map edit- checks if the related connections are also removed after removing a country
    6. map edit- checks that the disparity in the number of assigned countries per player does not exceed one
    7. map edit- checks if the continent exist before adding a country to it
    8. map edit- checks if the continent is removed correctly from game state
 - Order tests:
   1. AirLift order- After successful airlift, the armies should be moved to the target country
   2. Blockade order- After successful blockade, the target country should be neutral and the armies should be tripled
   3. Bomb order- After successful bomb, the target country should be neutral and the armies should be halved
   4. Deploy order- After successful deploy, the armies should be added to the target country
   5. Negotiate order- After successful negotiate, the players should not attack each other
   6. Advance order- After successful advance, the armies should be moved to the target country

```bash
#Removes the target directory with all the build data before starting a new build.
mvn clean
#: Compiles the source code of the project.
mvn compile
# command to run test
mvn test

# To generate test coverage report

mvn jacoco:report

# find the report in target/site/jacoco/index.html
```

## Coding Conventions

Our project adheres to specific coding conventions for clarity and maintainability:

- Naming Conventions: Use CamelCase for class names, prefix data members with d_, method parameters with p_, local
  variables with l_, and global variables in all capitals.
- Code Layout: Ensure consistent layout throughout the codebase, utilizing IDE auto-formatter.
- Commenting Convention: Include Javadoc comments for every class and method; document long methods with comments for
  procedural steps; avoid leaving commented-out code

## Continuous Integration

We use a CI solution to ensure that every push compiles successfully, passes all unit tests, and generates complete
Javadoc documentation.

### Thank you for exploring our project!
