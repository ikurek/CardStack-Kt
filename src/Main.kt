import enums.DeckPosition
import enums.DrawType
import enums.SplitType

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

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
        System.out.println("Added ${deck.size()} cards")
        validate(deck)
        printDeck(deck)

        var splitted = deck.splitBetween(3, DrawType.REMOVE, SplitType.ALL)

        System.out.println("Created ${splitted.size} decks")

        printMultipleDecks(splitted)

    }

    fun printCard(cardModel: CardModel) {
        System.out.println("Card name: ${cardModel.name}")
    }

    fun printDeck(deck: CardStack) {
        System.out.println("Deck:")
        for (any: Any in deck.toMutableList()) {
            if (any is CardModel) {
                System.out.println("    Card name: ${any.name}")
            }
        }
    }

    fun printMultipleDecks(list: List<CardStack>) {
        for (deck: CardStack in list) {
            System.out.println("Deck size: ${deck.size()}")
            for (any: Any in deck.toMutableList()) {
                if (any is CardModel) {
                    System.out.println("    Card name: ${any.name}")
                } else {
                    System.out.println("    BAD CARD!")
                }
            }
        }
    }

    /**
     * Checks if all cards are correct
     */
    fun validate(deck: CardStack) {
        System.out.println("Validating deck...")
        var counter = 0
        for (any: Any in deck.toMutableList()) {
            if (any is CardModel)
            else {
                System.out.println("    Found card of type ${any::class.simpleName}")
                counter++
            }
        }
        if (counter != 0) {
            System.out.println("Broken cards: ${counter}")
        }
    }

}