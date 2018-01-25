# CardStack-Kt
Kotlin library for data type representing a stack of cards

## Purpose:
CardStack-Kt is a lightweigth library providing a Kotlin data type, representing a stack of cards, mostly used in trading card games, etc.

## Example usage:
```kotlin
// Creates an empty deck with draw() method removing elements from it
var myDeck: Cardstack = CardStack(cardModel : MyCardModel, DrawType.REMOVE)
// Creates an empty deck with draw() method causing elements to be reshuffled into the deck
var myDeck: Cardstack = CardStack(cardModel : MyCardModel, DrawType.SHUFFLE_BACK)
// Draws a card from the top of the deck
myDeck.draw(DeckPosition.TOP)
// Adds a card to the bottom of the deck
myDeck.put(DeckPosition.BOTTOM)
```

## Draw modes:
* `DrawType.REMOVE` causes drawn card to be removed from the deck
* `DrawType.SHUFFLE_BACK` causes drawn card to stay in the deck after draw, but it's position is changed randomly

## Deck positions:
* `DeckPosition.TOP` puts/draws card from the top of the deck
* `DeckPosition.BOTTOM` puts/draws card from the bottom of the deck
* `DeckPosition.RANDOM` puts/draws card from a random position