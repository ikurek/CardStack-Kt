import enums.DeckPosition
import enums.DrawType
import java.util.*

/**
 * Core library class
 */
class CardStack {

    private var listOfCards: MutableList<Any>
    private var drawType: DrawType

    /**
     * Preffered constructor
     * @param dataModel Class thet will represent a single card
     * @param drawType Enum that describes action taken when card is drawn
     */
    constructor(dataModel: Any, drawType: DrawType) {
        this.listOfCards = mutableListOf(dataModel)
        this.drawType = drawType
    }

    /**
     * Backup constructor, draw type is set to fixed by default
     * @param dataModel Class that will represent a single card
     */
    constructor(dataModel: Any) {
        this.listOfCards = mutableListOf(dataModel)
        this.drawType = DrawType.REMOVE
    }

    /**
     * Shuffles whole deck
     */
    fun shuffle() {
        listOfCards.shuffle()
    }

    /**
     * Adds new card to deck
     * @param dataModel Object representing data model
     * @param deckPosition Enum corresponding to position new card will have in the deck
     */
    fun put(dataModel: Any, deckPosition: DeckPosition) {

        // Putting on the bottom of the deck
        if (deckPosition == DeckPosition.BOTTOM) {
            listOfCards.add(dataModel)
        }

        // Putting on the top of the deck
        if (deckPosition == DeckPosition.TOP) {
            listOfCards.add(0, dataModel)
        }

        // Putting to a random position
        if (deckPosition == DeckPosition.RANDOM) {
            val randomInRange = Random().nextInt(listOfCards.size)
            listOfCards.add(randomInRange, dataModel)
        }

    }

    /**
     * Draws a card from the deck
     * @param deckPosition Enum describing where will the card be drawn from
     */
    fun draw(deckPosition: DeckPosition): Any {

        var drawnCard = Any()
        var random = Random().nextInt(listOfCards.size)

        // Drawing from top
        if (deckPosition == DeckPosition.TOP) {
            drawnCard = listOfCards[0]
            listOfCards.removeAt(0)
        }

        // Drawing from bottom
        if (deckPosition == DeckPosition.BOTTOM) {
            drawnCard = listOfCards[listOfCards.size]
            listOfCards.removeAt(listOfCards.size)
        }

        // Drawing from random position
        if (deckPosition == DeckPosition.RANDOM) {
            drawnCard = listOfCards[random]
            listOfCards.removeAt(random)
        }

        // Restore card to deck if selected
        if (drawType == DrawType.SHUFFLE_BACK) {
            random = Random().nextInt(listOfCards.size)
            listOfCards.add(random, drawnCard)
        }

        // Return drawn card
        return drawnCard
    }

    fun toMutableList(): MutableList<Any> {
        return listOfCards
    }

}