# Chess
A command-line app for playing chess. 

## Build
Built with Java 23. Can be executed from an IDE or quickly compiled into a `.jar` file and run from the command line.

## How to play
Enter moves in the following formats:
* Pawn move: simply enter the column and row of the destination square. For example, if White wishes to start the game by moving the e2 pawn to the e4 square, they would enter 'e4'.
* Piece move: enter the piece token followed by the destination square. For example, if White wishes to start the game by moving the b1 knight to the c3 square, they would enter 'Nc3'.
* Ambiguous moves: if multiple pieces could legally reach the same destination square, a starting square must also be provided. For example, suppose Black has a pawn on d5 while White has pawns on c4 and e4. If White wishes to capture the pawn on d5 with one of the attacking pawns, they would enter either 'Pc4d5' or 'Pe4d5'.

This application is freely provided under the MIT License. Copyright 2025 Jacob Curley.
