# CardStack-Kt
Kotlin library for data type representing a stack of cards

## Purpose:
CardStack-Kt is a lightweigth library providing a Kotlin data type, representing a stack of cards, mostly used in trading card games, etc.

## Example usage:
```kotlin
val deck = CardStack(CardModel::class, DrawType.SHUFFLE_BACK)
    deck.put(CardModel("test0"), DeckPosition.RANDOM)
    deck.put(CardModel("test1"), DeckPosition.RANDOM)
    deck.put(CardModel("test2"), DeckPosition.RANDOM)
    deck.put(CardModel("test3"), DeckPosition.RANDOM)
    deck.put(CardModel("test4"), DeckPosition.RANDOM)

var list: ArrayList<CardModel> = arrayListOf()
        list.add(CardModel("list0"))
        list.add(CardModel("list1"))
        list.add(CardModel("list2"))

deck.put(list, DeckPosition.TOP)

if (deck.canDrawMany(5)) {
    deck.draw(5, DeckPosition.TOP)
}
```


## Methods
### Constructors:
* `CardStack(dataModel: Any, drawType: DrawType)` creates an empty deck of cards with specified draw type
* `CardStack(collection: Collection<Any>, drawType: DrawType)` creates deck using a collection of objects and a draw type
* `CardStack(dataModel: Any)` creates an empty deck with draw type set to default (`DrawType.REMOVE`)

### Draws:
* `draw(deckPosition: DeckPosition): Any` returns card drawn from a specified position
* `draw(numberOfCards : Int, deckPosition: DeckPosition) : CardStack` returns a new CardStack with cards drawn from previous one. New CardStack has the same DrawType as the one it's drawn from
* `draw(numberOfCards : Int, deckPosition: DeckPosition, drawType: DrawType) : CardStack` returns a new CardStack with cards drawn from previous one, and a specified DrawType

### Puts:
* `put(dataModel: Any, deckPosition: DeckPosition)` put a single card to a specified position
* `put(collection: Collection<Any>, deckPosition: DeckPosition)` put a collection of cards to a specified position. Collection's elements are put in the same order they are stored (element '0' in collection will be placed on top)

### Checks:
* `canDraw() : Boolean` true, if next card can be drawn
* `canDrawMany(numberOfCards: Int) : Boolean` true, if specified number of cards can be drawn
* `fun cardsLeft() : Int` returns number of cards left

### Data operations:
* `shuffle()`
* `reverse()`

## Enumerators:
### DrawType:
* `DrawType.REMOVE` causes drawn card to be removed from the deck
* `DrawType.SHUFFLE_BACK` causes drawn card to stay in the deck after draw, but it's position is changed randomly

### DeckPosition:
* `DeckPosition.TOP` puts/draws card from the top of the deck
* `DeckPosition.BOTTOM` puts/draws card from the bottom of the deck
* `DeckPosition.RANDOM` puts/draws card from a random position

## Type Converters:
* `fun toMutableList(): MutableList<Any>`
* `fun toTypedArray(): Array<Any>`
* `fun toArrayList(): ArrayList<Any>`