---
geometry: margin=1in
---
# PROJECT Design Documentation


## Team Information
* Team name: CyberCheckers
* Team members
  * Ryan Cervantes
  * Wil Clancy
  * Abhimanyu Gupta
  * Sean Newman

## Executive Summary



### Purpose

The goal of this project is to create an online checkers service, in
which multiple people can challenge and play one another in games of 
checkers.

### Glossary and Acronyms

 Term | Definition 
------|------------
UI Tier | Part of the application that interacts with the user
Model Tier | Part of the program that represents the state of the application
Application Tier | Part of the application that manages user interactions with the application



## Requirements


### Definition of MVP
> The Minimum Viable Product must allow users to log into the
online service and challenge other players to checkers games that
follow the official checkers rules.

### MVP Features
> _The Minimum Viable Product includes the following:_

* **Log in and start a game:** users must be able to log into the
online service, and view the list of other players online. Upon
clicking a player's name, a game will be started between them.

* **Move and jump pieces:** upon entering a game, players must be
able to move their pieces (during their turn)
according to official American checkers rules.

* **Finish a game and return to lobby:** the game ends when a 
player is out of turns, or all their pieces are removed. Upon the
completion of the game, the players are returned to the lobby
so that they can start a new game again.

### Roadmap of Enhancements
> _The product will feature these enhancements to the MVP:_

* **Multiple games:** players will be able to be engaged in different
games with different players at the same time.

* **Asynchronous play:** players will be able to take their turns
on their own time. The game state for each game is saved so that
players can always leave and come back to it when they are ready.


## Application Domain

![The WebCheckers Domain Model](updated-domain.jpg)
> _The Domain Model_

A **_game_** of checker consists of two **_players_**. New games start on a fresh **_board_**,
which consists of an 8x8 grid of **_spaces_**, that can hold **_pieces_**. Each player starts
with 12 pieces, and take turns moving them around the board. Each piece is either white or red
and either a single piece or a king piece. During their turn, players can make two kinds of
moves: **_simple moves_** or **_jump moves_**. Additionally, sometimes players are able
to make multiple jump moves in sequence. Turns continue until one player's pieces are gone, or
a player cannot make a move. When the game ends, the player who lost all their pieces or could
not make a move loses the game.


## Architecture and Design


### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser. The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface.png)
_State chart of overall web application interface_

* Upon arriving at the site, the user is greeted with a **_home_** page that
  tells the user how many users are currently logged into the service. 
* By clicking on a sign in link, they are brought to the **_sign-in_** page,
  where they must enter a username that follows a certain set of rules. 
* Upon entering a valid username, the user will be brought a new version of the
  home page that shows all currently logged-in players.
* Clicking on one of these players will redirect both users to the **_game_**
  page, where the checkers game will be played.

![The Game interface statechart](gameview-interface.png)
_State chart of gameplay interface_
* When it is the user's turn, they will be able to click and drag any of their
  own pieces.
  - If they attempt to make an illegal move, it will be rejected and the piece
    will be put back
  - If they attempt to make a legal move, it will be accepted
* If a player has made a move, they will be allowed to revert that move or
  submit their turn
  - If the player is required by game rules to continue making moves, then they
    will not be allowed to submit their turn and must make the required moves
* If the player has jumped a piece for their move, they may make additional
  moves for any subsequent jumps that are possible


### UI Tier

The UI tier works closely with the application to determine what information
should be shown to the user. The GetHomeRoute class has the responsibility of
passing relevant information to the _home.ftl_ file so that the home page can
be rendered. When the user first accesses the home page before signing in, the
**_GetHomeRoute_** communicates with the PlayerLobby to determine if this user
has been signed in. Because the user hasn't signed in, the limited home page is
rendered. Usernames of signed in players can't be seen; only the number of
online users is given.

Next, the user wants to sign in, so that they can play a game. They click the
sign-in link on the home page to cause the **_GetSignInRoute_** class to check
if the player has been signed in already. Because this user hasn't, they are
taken to the sign-in page using the _signin.ftl_ file, where they can enter
their desired username.

Upon submitting their username, the **_PostSignInRoute_** will conduct the
checking of the username's validity. Entering an invalid username will cause
the page to reload for the user to try again. A valid one will redirect the
user back to the home page.

Except this time, the home page is different. The **_GetHomeRoute_** recognizes
that the user is now signed in, so the full home page is rendered. Instead of
the number of users being displayed, there is a list of all players currently
online and not in a game.

Clicking on one of these users will begin a game with them, unless they happen
to have just been selected by someone else before you. This is where the
**_GetGameRoute_** comes in. The GetGameRoute will match the current user and
the challenged user in a new game, where the current user is the red player,
and the selected player is the white player. The game page is rendered using
this information.

When in a game, players must be able to make turns only when it is his/her turn
to move. In order to handle this we created the **_PostCheckTurnRoute_** which
will do exactly that. Periodically, the client will send an AJAX request to the
server asking to check if it is the players move. This route handles this by
querying the underlying model to see if it said players turn. If it is, the
client will receive a JSON response saying so. 

But, in order to render the game view, a visual representation of the board is
needed. The board view is made up of several sub classes: a **_PieceView_**
represents an individual piece that can be owned by a **_SpaceView_**, that,
when put together with others, makes up a **_RowView_**, the visual
representation of a row of the board. Several RowViews make up the entire
**_BoardView_**. These things are created based on the current state of the
board, which, in this case, is a new, fresh board, with the current player's
pieces appearing at the bottom of the board.

Now that the BoardView is made, the game page can be rendered using the
**_game.ftl_** file and the game can begin. The game page is reloaded
consistently as the game progresses.


### Application Tier

The most important aspect of the application tier is the **_PlayerLobby_**
class, which keeps track of the players signed into the service. The
PlayerLobby contains a HashMap that matches player usernames to their
**_Player_** objects. When a user is signed in, they are added to the HashMap,
and they are removed when they sign out.

After signing in, the user can challenge another user to a game. Upon
challenging another user, the PlayerLobby tells another class, the
**_GameCenter_**, to start a new game. The GameCenter is in charge of creating
new games and keeping track of all current games. When the user challenges
another player, the GameCenter creates a game between these two players, and
the game is added to the GameCenter's list of current games. The two players
are also added to a HashMap that connects players to their current opponents.

![The Sequence Diagram for starting a game](start-a-game.png)

#### TurnControl Subsystem
When making a move, the client-side code will check with the server to make
sure the move that the user selected is valid. This is handled by the
**_TurnControl_** class, which provides an interface for the UI-tier
**_PostValidateMoveRoute_** class to interact with the Model-tier. This
**_TurnControl_** class then parses the JSON request sent by the client and
creates one of multiple kinds of Model-tier representations of classes. This
object is then used to validate the move. If the move is determined to be
valid, it is stored on the move queue for the **_Game_**. If the move is
invalid, it does not get stored on the move queue. Then, the **_TurnControl_**
class creates a message that gets passed back to the route component, which
renders the message for the user.

[The TurnControl Subsystem, and its interactions](start-a-game.png)

### Model Tier

Prior to signing in, users have very little access to anything on the site. It isn't until after
signing in that an instance of **_Player_** is created for the user. Players are identified not only by
the unique username that the user submits when signing in, but also the user's session ID.

When players challenge one another, a new instance of **_Game_** is created. The game holds all of the data
needed for a game to happen between two players. The players on each team, the current turn, and, most
importantly, the _Board_.

The **_Board_** class is the class in charge of keeping track of the data regarding the game board state.
The board is made up of a 2D array of **_Space_** instances, which can each potentially hold a **_Piece_**,
if the space is colored black. 

Players take turns moving their pieces from space to space on the board to remove their opponents pieces,
and ultimately win the game.

#### Moves
The different types of moves are represented by the **_Move_** class and its
subclasses. This class stores the information for the starting and ending
position of a piece being moved. It can check a game to see if the move is
valid, and it can apply itself to the game once the turn is submitted.

Subclasses to the **_Move_** class must override the functionality for
validating and executing the move. This is because each type of move has
different rules that may make it valid or invalid.

The first **_Move_** subclass is the **_SimpleMove_**. This represents a
typical move, that travels diagonally a single space without capturing a piece.

The second **_Move_** subclass is the **_JumpMove_**. This represents a typical jump
move in which a player jumps **one** piece that is diagonally to the left or right,
given that the space they end on is empty.

### Design Improvements

In the future, the team can adhere more to the design principles of
Law of Demeter and Low Coupling. Up until recently, the _Board_ class had the 
responsibility of building the _BoardView_ in the UI tier, which was a significant
violation of design principles, as the Model and UI have no reason to directly interact.
However, this violation has since been corrected. Hopefully, we will be more aware in 
the future to avoid violations such as that, now that we have identified one and gone
through the process of correcting it.

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing


There have currently been three user stories and two spike stories completed:
1. Player Sign-In
2. Start a Game
3. Spike: Domain-Driven Design
4. Spike: Web Architecture
5. Simple Move

These stories have all passed their acceptance criteria. No other stories have 
reached the end of development to be tested as of yet.


### Unit Testing and Code Coverage

For unit testing, responsibility was split up so that that each team member completed
unit tests for two classes. Because these were our first classes to be tested, we tried to
focus on simpler classes to test first, to familiarize ourselves with how testing works and
should be done, so that the more complicated classes would potentially be less daunting. For
that reason, our UI "route" classes have not yet been tested, since they have more than one
component to be tested. Our coverage of the classes we did test, however, can be improved. In
the UI tier, results of testing are good, but could use more branch coverage. Our testing
in the model and application seem more thorough, with the outlier being the PlayerLobby class and 
Player classes, which are missing some coverage and need to be revisited.
