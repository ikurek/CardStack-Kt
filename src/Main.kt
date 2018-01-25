import enums.DeckPosition
import enums.DrawType

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

        if (deck.canDrawMany(5)) {
            deck.draw(5, DeckPosition.TOP)
        }

        printDeck(deck)
    }

    fun printCard(cardModel: CardModel) {
        System.out.println("Card name: ${cardModel.name}")
    }

    fun printDeck(deck: CardStack) {
        for (any: Any in deck.toMutableList()) {
            if (any is CardModel) {
                System.out.println("Card name: ${any.name}")
            }
        }
    }

}