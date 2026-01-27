·         SR1: On launch of QSS, an empty square board shall be displayed indicating BLACK’s turn

o   SR1.1: Title will be “Quax (Human V Human)” or “Quax (Human V Bot)” depending on whether the two players are humans or one play is human and the other, a bot

o   SR1.2: There will be 11 octogonal cells per side.

o   SR1.3: There will be rhombic cells between two diagonally adjacent octagonal cells.

o   SR1.4: The BLACK sides will have alphabets from A to K next to the octogonal cells.

o   SR1.5: The WHITE sides will have numbers from 1 to 11 next to the octogonal cells.

\-          A feature that creates the board layout including both octagonal and rhombus tiles

 

·         SR2: In the first move, BLACK will have a choice to place an octagonal stone or a rhombic tile.

o   SR2.1: The following text shall be displayed next to a black octagonal stone and rhombic tile to indicate the BLACK’s turn.

o   SR2.2: In subsequent moves, if it is WHITE’s turn, the following text shall be displayed next to a white octagonal stone or rhombic tile to indicate the WHITE’s turn.

\-          A feature that introduces labels for each row and column, the board as a whole and text to indicate the current player’s turn

\-          A feature that allows user input to place a tile on a selected position

 

 

·         SR3: After the first move, WHITE is allowed to switch colors.

o   SR3.1: A button will be displayed allowing pie rule activation.

o   SR3.2: On activating Pie Rule via button click, WHITE becomes BLACK and BLACK becomes WHITE.

\-          A feature that implements correct turn order

\-          A feature that introduces the Pie Rule allowing the white player to swap to black

 

·         SR4.1: After the Pie Rule button click, the button will become invisible.

o   SR4.2: The following text shall be displayed next to a white octagonal stone and rhombic tile to indicate the WHITE’s turn:

 

·         SR5.1: If it is WHITE’s turn, WHITE must place a stone or tile in any unoccupied cell.

o   SR5.2: If it is BLACK’s turn, BLACK must place a stone or tile in any unoccupied cell.

 

·         SR6: When BLACK makes a move resulting in a connected chain from top to bottom, the following message will be displayed:

\-          A feature that implements the winning condition for the black player (vertical chain)

 

·         SR7: When WHITE makes a move resulting in a connected chain from left to right, the following message will be displayed:

\-          A feature that implements the winning condition for the white player (horizontal chain)

 

·         SR7: For the BOT making a move,

o   SR7.1: The BOT will use a strategy to make a move.

o   SR7.2: There will be a button called Show Strategy (only in development mode)

o   SR7.3: On Show Strategy button click, you should display a visual describing the strategy.

o   SR7.4: A Hide Strategy button should be displayed, which on click will disable visual display of the strategy.

\-          A feature that implements a bot with minimal strategy

\-          A feature that allows the bot to place tiles strategically with more advanced logic

\-          A feature that allows the player to see the strategy of the bot if they wish

\-          A feature that allows for either human vs human or human vs bot gameplay

 

 

 

 

 

 

LIST

\- A feature that creates the board layout including both octagonal and rhombus tiles

 \- A feature that introduces labels for each row and column, the board as a whole and text to indicate the current player’s turn

\- A feature that allows user input to place a tile on a selected position

\- A feature that implements correct turn order

\- A feature that introduces the Pie Rule allowing the white player to swap to black

\- A feature that implements the winning condition for the black player (vertical chain)

 \- A feature that implements the winning condition for the white player (horizontal chain)

\- A feature that implements a bot with minimal strategy

\- A feature that allows the bot to place tiles strategically with more advanced logic

\- A feature that allows the player to see the strategy of the bot if they wish

\- A feature that allows for either human vs human or human vs bot gameplay.

 

\-- AI suggestion A feature that validates player moves and prevents illegal tile placement.

\-- AI suggestion A feature that terminates the game and disables further moves once a win condition is reached.

 

 

 

 

 

 

| SPRINT 1: Basics Board setup | \- A feature that creates the board layout including both octagonal and rhombus tiles   \- A feature that introduces labels for each row and column, the board as a whole and text to indicate the current player’s turn   \- A feature that allows user input to place a tile on a selected position       |
| :---: | ----- |
| SPRINT 2: Rules and Gameplay logic | \- A feature that implements correct turn order   \- A feature that introduces the Pie Rule allowing the white player to swap to black   \-- AI suggestion A feature that validates player moves and prevents illegal tile placement.   |
| SPRINT 3: Winning and Game Completion | \- A feature that implements the winning condition for the black player (vertical chain)    \- A feature that implements the winning condition for the white player (horizontal chain)   \-- AI suggestion A feature that terminates the game and disables further moves once a win condition is reached.   |
| SPRINT 4: AI Bot strategy | \- A feature that implements a bot with minimal strategy   \- A feature that allows the bot to place tiles strategically with more advanced logic   \- A feature that allows the player to see the strategy of the bot if they wish   \- A feature that allows for either human vs human or human vs bot gameplay.   |

 

