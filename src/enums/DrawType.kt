package enums

/**
 * Enum represents methods of drawing cards
 */
enum class DrawType {
    // After draw, copy of drawn card is shuffled back into the deck
    SHUFFLE_BACK,
    // After draw, card is removed from the deck
    REMOVE
}