# box-take-home
Box take home challenge - hi! Here's my rendition on designing the box MiniShogi game. Below, I'll be explaining how I 
designed the game, why I chose my design, and finally some possible improvements!
# Structure
There are three packages for this project: `board`, `game`, and `pieces`. 
## Board
The board package is the representation of a board
in the game of Shogi and handles a lot of the piece movement across the game. It also validates whether certain moves or
drops are legal, as well as checking whether any players are in a Check or Checkmate position. I also decided to create 
a `Square` class to represent a physical location on the board - this makes the translation of movement or location on the
board to be more convenient when interacting with other classes, mainly the Pieces class. There are also the `Move` and 
`Drop` class, which represents a move or drop that a player can make within the game. Finally, there is the `Direction` enum
class which represents a direction that can be made by a move. In this game, we only consider 8 cardinal directions.

## Pieces
This package handles the representation of the Pieces within the game: Drive, Governance, Notes, Preview, Relay, and 
Shield. Each piece has an `owner` - either UPPER or lower. This is important in determining the validity of moves, as well
as determining the valid directions in which a piece can move (for instance, the Preview piece can only move upwards for LOWER,
and downwards for UPPER). Some pieces can be promoted, so for these pieces we need to consider their promotion status when 
checking for move validity. I also decided to place a `location` variable for each piece; this is meant to make the 
process of getting all possible moves for a particular player to be more efficient.

## Game
Finally, we have the game package. This package handles how a game is run. The `Controller` manages the flow of the game
from start to finish. We can either run the game in file or interaction mode, this is also handled by the Controller. The 
Controller decides when a game is finished, based on a `tie` if there are too many moves, an illegal move, or a checkmate.
The `Reporter` class handles the communication of what happens in the game to us through printing out the board state and 
the moves executed by each player. Finally, the `Player` class represents the players in Shogi. Each `Controller` has an 
UPPER and a LOWER player - this is also helped through the `PlayerType` enum class.

# Improvements
I definitely feel that there is a way to improve the way to check whether a piece is in check or checkmate. It involves 
too many iterations through possible moves in the board, as well as a lot of validation checks. I tried to mitigate this
by introducing a `location` variable for each Piece but it does not entirely solve the problem. Also, checking whether a 
piece movement is valid does not only depend on the movement itself but the state of the Board as well, so there is a lot of 
interdependency between these two.
