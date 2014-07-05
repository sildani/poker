class Poker {

  // scoring hands
  static final int PAIR            = 100
  static final int TWO_PAIRS       = 200
  static final int THREE_OF_A_KIND = 300
  static final int STRAIGHT        = 400
  static final int FLUSH           = 500
  static final int FULL_HOUSE      = 600
  static final int FOUR_OF_A_KIND  = 700
  static final int STRAIGHT_FLUSH  = 800

  // string representation of scoring hands
  static final Map scoringHand = [
    (Poker.PAIR):            'Pair',
    (Poker.TWO_PAIRS):       'Two pairs',
    (Poker.THREE_OF_A_KIND): 'Three of a kind',
    (Poker.STRAIGHT):        'Straight',
    (Poker.FLUSH):           'Flush',
    (Poker.FULL_HOUSE):      'Full house',
    (Poker.FOUR_OF_A_KIND):  'Four of a kind',
    (Poker.STRAIGHT_FLUSH):  'Straight flush'
  ]

  static void main(String[] args) {
    def blackHand = new PokerHand(args[0])
    def whiteHand = new PokerHand(args[1])
    println "Input: Black: ${blackHand} White: ${whiteHand}"
    println "Output: ${new Poker().evaluateGame(blackHand, whiteHand)}"
  }

  // game evaluator
  def evaluateGame(blackHand, whiteHand) {
    StringBuilder result = new StringBuilder()

    def whiteResult = evaluateHand(whiteHand)
    def blackResult = evaluateHand(blackHand)

    if (whiteResult[0] > blackResult[0]) {
      result << "White wins - ${scoringHand[whiteResult[0]]}"
    }

    if (whiteResult[0] < blackResult[0]) {
      result << "Black wins - ${scoringHand[blackResult[0]]}"
    }

    if (whiteResult[0] == blackResult[0] && whiteResult[1].value > blackResult[1].value) {
      result << "White wins - High card: ${whiteResult[1]}"
    }

    if (whiteResult[0] == blackResult[0] && whiteResult[1].value < blackResult[1].value) {
      result << "Black wins - High card: ${blackResult[1]}"
    }

    if (whiteResult[0] == blackResult[0] && whiteResult[1].value == blackResult[1].value) {
      def tieBreakerResult = tieBreaker(blackHand, whiteHand)
      if (tieBreakerResult) {
        if (tieBreakerResult[0] == whiteHand) result << "White wins - High card: ${tieBreakerResult[1]}"
        if (tieBreakerResult[0] == blackHand) result << "Black wins - High card: ${tieBreakerResult[1]}"
      } else {
        result << "Tie"
      }
    }

    return result.toString()
  }

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
      return [Poker.STRAIGHT_FLUSH, evaluatedCards.last()]
    }

    if (cardCounts.values().contains(4)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 4}).keySet().first()
      return [Poker.FOUR_OF_A_KIND, evaluatedCards.findAll { it.value == highCardVal }.first()]
    }

    if (cardCounts.values().contains(3) && cardCounts.values().contains(2)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 3}).keySet().first()
      return [Poker.FULL_HOUSE, evaluatedCards.findAll { it.value == highCardVal }.first()]
    }

    if (sameSuit) {
      return [Poker.FLUSH, evaluatedCards.last()]
    }

    if (isStraight && !sameSuit) {
      return [Poker.STRAIGHT, evaluatedCards.last()]
    }

    if (cardCounts.values().contains(3)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 3}).keySet().first()
      return [Poker.THREE_OF_A_KIND, evaluatedCards.findAll { it.value == highCardVal }.first()]
    }

    if (cardCounts.findAll({ k,v -> v == 2 }).size() == 2) {
      def pairs = cardCounts.findAll({ k,v -> v == 2}).keySet()
      def highCardVal = pairs[0] > pairs[1] ? pairs[0] : pairs[1]
      return [Poker.TWO_PAIRS, evaluatedCards.findAll { it.value == highCardVal }.first()]
    }

    if (cardCounts.values().contains(2)) {
      def highCardVal = cardCounts.findAll({ k,v -> v == 2}).keySet().first()
      return [Poker.PAIR, evaluatedCards.findAll { it.value == highCardVal }.first()]
    }

    return [null, evaluatedCards[4]]
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

  def tieBreaker(hand1, hand2) {
    def hand1Cards = hand1.cards.sort().reverse()
    def handValues1 = hand1Cards.collect { it.value }

    def hand2Cards = hand2.cards.sort().reverse()
    def handValues2 = hand2.cards.sort().reverse().collect { it.value }

    def i = 0
    while (i < 5) {
      if (handValues1[i] > handValues2[i]) return [hand1, hand1Cards[i]]
      if (handValues1[i] < handValues2[i]) return [hand2, hand2Cards[i]]
      i++
    }

    return null
  }
}