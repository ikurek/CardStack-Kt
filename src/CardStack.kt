import enums.DeckPosition
import enums.DrawType
import enums.SplitType
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
        this.listOfCards = mutableListOf()
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
        this.listOfCards = mutableListOf()
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
    fun size(): Int {
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

    /**
     * Splits deck to moany smaller decks
     */
    fun splitBetween(numberOfDecks: Int, drawType: DrawType, splitType: SplitType): List<CardStack> {

        // Create list of decks
        var listOfDecks = mutableListOf<CardStack>()
        for (i in 0..(numberOfDecks - 1)) {
            listOfDecks.add(CardStack(Any(), drawType))
        }

        when (splitType) {

        // Split all cards, don't check if each player has equal number
            SplitType.ALL -> {
                // Iterating over cards in current deck
                while (!listOfCards.isEmpty()) {
                    // Iterating over all new decks to split cards equally
                    for (i in 0..(numberOfDecks - 1)) {
                        // If there are cards left
                        if (!listOfCards.isEmpty()) {
                            // Put top card to next deck
                            listOfDecks[i].put(listOfCards[0], DeckPosition.TOP)
                            // Remove it from current deck
                            listOfCards.removeAt(0)
                        }
                    }
                }
            }

        // Splits cards equally, so each deck has the same number of cards
        // FIXME: Works badly
            SplitType.EQUAL -> {
                // Check how many cards should be left in the deck
                val cardsToLeave = (listOfCards.size) % numberOfDecks
                // Counter that holds number of iterations
                var counter = 0
                // Iterating over cards in current deck
                while (counter <= (listOfCards.size - cardsToLeave)) {
                    // Iterating over all new decks to split cards equally
                    for (i in 0..(numberOfDecks - 1)) {
                        // If there are cards left
                        if (counter != (listOfCards.size - cardsToLeave)) {
                            // Put top card to next deck
                            listOfDecks[i].put(listOfCards[0], DeckPosition.TOP)
                            // Remove it from current deck
                            listOfCards.removeAt(0)
                            counter++
                        }
                    }
                }
            }
        }

        // Return created list of decks
        return listOfDecks


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