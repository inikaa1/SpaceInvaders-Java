# SpaceInvaders-Java


HOW TO RUN:
1) Download folder from ed, should contain the src folder and a build.gradle file. 
2) Open terminal at folder
3) write the following command in the terminal: "gradle clean build run"
4) wait for 6 seconds until a window pops up

HOW TO PLAY: 
1) Navigate to the newly open window
2) As the window suggests: press the keys E, M, H on the keyboard for the levels Easy, Medium or Hard respectively
3) After you press the key the respective level should load 
4) Press the arrow keys to move the spaceship

HOW TO SELECT NEW LEVEL:
To run a new level, delete the window and run the application again from the terminal, when the window opens again, press the key for the new level you wish to select. 

FEATURES IMPLEMENTED:
- Difficulty Level
- Undo (half works because prototype works but memento doesn't work)

PATTERNS IMPLEMENTED:
- Singleton
	Classes used -> GameEngine, DifficultyLevel, App

- Prototype
	Classes used -> Prototype, GameEngine, Bunker, Enemy, GameObject, EnemyProjectile, FastProjectileStrategy, SlowprojectileStrategy, NormalProjectileStrategy, Player, PlayerProjectile, Projectile, ProjectileStrategy, Renderables, Vector2D, SpaceBackgorund 

- Memento (doesn't work)
	Classes used -> UndoMemento, GameEngine, Prototype

