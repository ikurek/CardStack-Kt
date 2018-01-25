import enums.DeckPosition
import enums.DrawType
import java.util.*
import kotlin.collections.ArrayList

/**
 * Core library class
 */
class CardStack {

    private var listOfCards: MutableList<Any>
    var drawType: DrawType

    // CONSTRUCTORS

    /**
     * Preffered empty constructor
     * @param dataModel Class thet will represent a single card
     * @param drawType Enum that describes action taken when card is drawn
     */
    constructor(dataModel: Any, drawType: DrawType) {
        this.listOfCards = mutableListOf(dataModel)
        this.drawType = drawType
    }

    /**
     * Preffered constructor
     * @param collection Collection of DataModel objects
     * @param drawType Enum that describes action taken when card is drawn
     */
    constructor(collection: Collection<Any>, drawType: DrawType) {
        this.listOfCards = collection.toMutableList()
        this.drawType = drawType
    }
    /**
     * Backup empty constructor, draw type is set to fixed by default
     * @param dataModel Class that will represent a single card
     */
    constructor(dataModel: Any) {
        this.listOfCards = mutableListOf(dataModel)
        this.drawType = DrawType.REMOVE
    }

    // SUPPORTING METHODS

    /**
     * @return true if there are cards left in deck
     */
    fun canDraw(): Boolean {
        return listOfCards.size > 0
    }

    /**
     * @return true if there more then specified cards left in deck
     */
    fun canDrawMany(numberOfCards: Int): Boolean {
        return listOfCards.size >= numberOfCards
    }

    /**
     * @return number of cards left in deck
     */
    fun cardsLeft(): Int {
        return listOfCards.size
    }
    /**
     * Shuffles whole deck
     */
    fun shuffle() {
        listOfCards.shuffle()
    }

    /**
     * Reverses whole deck
     */
    fun reverse() {
        listOfCards.reverse()
    }

    // ADDING / REMOVING ELEMENTS

    /**
     * Adds new card to deck
     * @param dataModel Object representing data model
     * @param deckPosition Enum corresponding to position new card will have in the deck
     */
    fun put(dataModel: Any, deckPosition: DeckPosition) {

        when (deckPosition) {
        // Putting on bottom
            DeckPosition.BOTTOM -> listOfCards.add(dataModel)
        // Putting on top
            DeckPosition.TOP -> listOfCards.add(0, dataModel)
        // Putting randomly
            DeckPosition.RANDOM -> {
                val randomInRange = Random().nextInt(listOfCards.size)
                listOfCards.add(randomInRange, dataModel)
            }

        }

    }

    /**
     * Adds colection of cards
     * @param collection Collection of dataModel objects (f. e. list)
     * @param deckPosition Enum corresponding to position new cards will have in the deck
     */
    fun put(collection: Collection<Any>, deckPosition: DeckPosition) {

        // Reverse collection to keep natural deck structure
        for (any: Any in collection.reversed()) {
            put(any, deckPosition)
        }
    }
    /**
     * Draws a card from the deck
     * @param deckPosition Enum describing where will the card be drawn from
     */
    fun draw(deckPosition: DeckPosition): Any {

        var drawnCard = Any()
        var random = Random().nextInt(listOfCards.size)

        when (deckPosition) {
        // Drawing from top
            DeckPosition.TOP -> {
                drawnCard = listOfCards[0]
                listOfCards.removeAt(0)
            }

        // Drawing from bottom
            DeckPosition.BOTTOM -> {
                drawnCard = listOfCards[listOfCards.size]
                listOfCards.removeAt(listOfCards.size)
            }

        // Drawing from random position
            DeckPosition.RANDOM -> {
                drawnCard = listOfCards[random]
                listOfCards.removeAt(random)
            }
        }

        // Restore card to deck if selected
        if (drawType == DrawType.SHUFFLE_BACK) {
            random = Random().nextInt(listOfCards.size)
            listOfCards.add(random, drawnCard)
        }

        // Return drawn card
        return drawnCard
    }

    /**
     * Draws multiple cards for the deck
     * @param numberOfCards Number of cards to draw
     * @param deckPosition Enum describing where will the card be drawn from
     * @return drawn cards as CardStack object
     */
    fun draw(numberOfCards: Int, deckPosition: DeckPosition): CardStack {
        val listOfDrawnCards = mutableListOf<Any>()

        for (i in 0..numberOfCards) {
            listOfDrawnCards.add(draw(deckPosition))
        }

        return CardStack(listOfDrawnCards, this.drawType)
    }

    /**
     * Draws multiple cards for the deck
     * @param numberOfCards Number of cards to draw
     * @param deckPosition Enum describing where will the card be drawn from
     * @param drawType Draw type for output deck
     * @return drawn cards as CardStack object
     */
    fun draw(numberOfCards: Int, deckPosition: DeckPosition, drawType: DrawType): CardStack {
        val listOfDrawnCards = mutableListOf<Any>()

        for (i in 0..numberOfCards) {
            listOfDrawnCards.add(draw(deckPosition))
        }

        return CardStack(listOfDrawnCards, drawType)
    }

    // CASTING TO OTHER OBJECTS

    fun toMutableList(): MutableList<Any> {
        return listOfCards
    }

    fun toTypedArray(): Array<Any> {
        return listOfCards.toTypedArray()
    }

    fun toArrayList(): ArrayList<Any> {
        return listOfCards.toCollection(ArrayList<Any>())
    }

}