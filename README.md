# Chess

A command-line app for playing chess.

## Build

Built with Java JDK 23. Can be executed from an IDE or quickly compiled into a `.jar` file and run from the command
line.

## How to play

Enter moves in the following formats:

* Pawn move: simply enter the column and row of the destination square. For example, if White wishes to start the game
  by moving the e2 pawn to the e4 square, they would enter 'e4'.
* Piece move: enter the piece token followed by the destination square. For example, if White wishes to start the game
  by moving the b1 knight to the c3 square, they would enter 'Nc3'.
* Ambiguous moves: if multiple pieces could legally reach the same destination square, an extra character to
  disambiguate the pieces must be provided. For example, suppose Black has a pawn on d5 while White has pawns on c4 and
  e4. If White wishes to capture the pawn on d5 with one of the attacking pawns, they would enter either 'cd5' or
  'ed5'. Similarly, if Black had rooks on e2 and e6 that could both reach the e3 square, Black would have to enter
  either 'R2e3' or 'R6e3'.
    * If this is still insufficient to uniquely identify a piece, the full origin coordinates of the desired piece must
      be specified. Example: 'Re4c4' moves the rook currently on e4 to c4.
* Castling: To castle, enter either 'O-O' or 'O-O-O' for king- or queen-side castling, respectively.
* Promoting a pawn: When a pawn reaches the opponent's back rank, a promotion must be specified. Use the normal pawn
  move input ('e8', 'h1', etc.), with '=X' at the end, where 'X' is the type of piece to promote to.
    * Example: 'f1=Q' indicates that a black pawn should advance to the F1 square and promote to a Queen

This application is freely provided under the MIT License. Copyright 2025 Jacob Curley.
