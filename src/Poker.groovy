class Poker {

  // hands
  static final int PAIR            = 100
  static final int TWO_PAIRS       = 200
  static final int THREE_OF_A_KIND = 300
  static final int STRAIGHT        = 400
  static final int FLUSH           = 500
  static final int FULL_HOUSE      = 600
  static final int FOUR_OF_A_KIND  = 700
  static final int STRAIGHT_FLUSH  = 800

  // hand evaluator
  def evaluateHand(hand) {
    def evaluatedCards = new TreeSet()
    def cardCounts = [:]
    def sameSuit = true
    def lastCard

    hand.cards.eachWithIndex { card, i ->
      evaluatedCards << card
      count(cardCounts,card)
      if (lastCard && card.suit != lastCard.suit) {
        sameSuit = false
      }
      lastCard = card
    }

    def isStraight = isStraight(evaluatedCards.collect { it.value })

    if (isStraight && sameSuit) {
      return [Poker.STRAIGHT_FLUSH, evaluatedCards.last().value]
    }

    if (cardCounts.values().contains(4)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 4}).keySet().first()
      return [Poker.FOUR_OF_A_KIND, evaluatedCards.findAll { it.value == highCardVal }.first().value]
    }

    if (cardCounts.values().contains(3) && cardCounts.values().contains(2)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 3}).keySet().first()
      return [Poker.FULL_HOUSE, evaluatedCards.findAll { it.value == highCardVal }.first().value]
    }

    if (sameSuit) {
      return [Poker.FLUSH, evaluatedCards.last().value]
    }

    if (isStraight && !sameSuit) {
      return [Poker.STRAIGHT, evaluatedCards.last().value]
    }

    if (cardCounts.values().contains(3)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 3}).keySet().first()
      return [Poker.THREE_OF_A_KIND, evaluatedCards.findAll { it.value == highCardVal }.first().value]
    }

    if (cardCounts.findAll({ k,v -> v == 2 }).size() == 2) {
      def pairs = cardCounts.findAll({ k,v -> v == 2}).keySet()
      def highCardVal = pairs[0] > pairs[1] ? pairs[0] : pairs[1]
      return [Poker.TWO_PAIRS, evaluatedCards.findAll { it.value == highCardVal }.first().value]
    }

    if (cardCounts.values().contains(2)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 2}).keySet().first()
      return [Poker.PAIR, evaluatedCards.findAll { it.value == highCardVal }.first().value]
    }

    return [null, evaluatedCards.last().value]
  }

  def count(runningCount, card) {
    def count = runningCount[card.value]
    if (count) {
      runningCount[card.value] = count + 1
    } else {
      runningCount[card.value] = 1
    }
  }

  def isStraight(values) {
    def deltas = []
    def lastValue = null
    values.sort().each { value ->
      if (lastValue) {
        deltas << value - lastValue
      }
      lastValue = value
    }
    deltas.sum().abs() == 4
  }
}