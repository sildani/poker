import org.junit.Before
import org.junit.Test

class PokerTest {
  def poker

  @Before
  void before_test() {
    poker = new Poker()
  }

  @Test
  void test_hand_value_ranking() {
    assert poker.PAIR            < Poker.TWO_PAIRS
    assert poker.TWO_PAIRS       < Poker.THREE_OF_A_KIND
    assert poker.THREE_OF_A_KIND < Poker.STRAIGHT
    assert poker.STRAIGHT        < Poker.FLUSH
    assert poker.FLUSH           < Poker.FULL_HOUSE
    assert poker.FULL_HOUSE      < Poker.FOUR_OF_A_KIND
    assert poker.FOUR_OF_A_KIND  < Poker.STRAIGHT_FLUSH
  }

  @Test
  void test_card_value_counts() {
    def count = [:]
    poker.count(count, new Card(Card.TWO, Card.CLUBS))
    assert count[Card.TWO] == 1
    poker.count(count, new Card(Card.TWO, Card.DIAMONDS))
    assert count[Card.TWO] == 2
    poker.count(count, new Card(Card.THREE, Card.CLUBS))
    assert count[Card.TWO] == 2
    assert count[Card.THREE] == 1
  }

  @Test
  void test_straight_detection() {
    assert poker.isStraight([1,2,3,4,5]) == true
    assert poker.isStraight([1,2,5,4,3]) == true
    assert poker.isStraight([1,2,5,6,9]) == false
  }

  @Test
  void should_evaluate_to_a_straight_flush() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.THREE, Card.CLUBS),
        new Card(Card.FOUR,  Card.CLUBS),
        new Card(Card.FIVE,  Card.CLUBS),
        new Card(Card.SIX,   Card.CLUBS)
      ])) == [Poker.STRAIGHT_FLUSH, Card.SIX]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TEN,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.DIAMONDS),
        new Card(Card.QUEEN, Card.DIAMONDS),
        new Card(Card.KING,  Card.DIAMONDS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) == [Poker.STRAIGHT_FLUSH, Card.ACE]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TEN,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.CLUBS),
        new Card(Card.QUEEN, Card.SPADES),
        new Card(Card.KING,  Card.HEARTS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) != [Poker.STRAIGHT_FLUSH, Card.ACE]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.DIAMONDS),
        new Card(Card.QUEEN, Card.DIAMONDS),
        new Card(Card.KING,  Card.DIAMONDS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) != [Poker.STRAIGHT_FLUSH, Card.ACE]
  }

  @Test
  void should_evaluate_to_a_four_of_a_kind() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO, Card.CLUBS),
        new Card(Card.TWO, Card.DIAMONDS),
        new Card(Card.TWO, Card.HEARTS),
        new Card(Card.TWO, Card.SPADES),
        new Card(Card.ACE, Card.CLUBS)
      ])) == [Poker.FOUR_OF_A_KIND, Card.TWO]
  }

  @Test
  void should_evaluate_to_a_full_house() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO, Card.CLUBS),
        new Card(Card.TWO, Card.DIAMONDS),
        new Card(Card.TWO, Card.HEARTS),
        new Card(Card.ACE, Card.SPADES),
        new Card(Card.ACE, Card.CLUBS)
      ])) == [Poker.FULL_HOUSE, Card.TWO]
  }

  @Test
  void should_evaluate_to_a_flush() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.FIVE,  Card.CLUBS),
        new Card(Card.EIGHT, Card.CLUBS),
        new Card(Card.JACK,  Card.CLUBS),
        new Card(Card.ACE,   Card.CLUBS)
      ])) == [Poker.FLUSH, Card.ACE]
  }

  @Test
  void should_evaluate_to_a_straight() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.THREE, Card.DIAMONDS),
        new Card(Card.FOUR,  Card.HEARTS),
        new Card(Card.FIVE,  Card.SPADES),
        new Card(Card.SIX,   Card.CLUBS)
      ])) == [Poker.STRAIGHT, Card.SIX]
  }

  @Test
  void should_evaluate_to_a_three_of_a_kind() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.TWO,  Card.HEARTS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.THREE_OF_A_KIND, Card.TWO]
  }

  @Test
  void should_evaluate_to_a_two_pairs() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.JACK, Card.DIAMONDS),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.TWO_PAIRS, Card.JACK]
  }

  @Test
  void should_evaluate_to_a_one_pair() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.NINE, Card.DIAMONDS),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.PAIR, Card.TWO]
  }

  @Test
  void should_evaluate_to_only_high_card() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.THREE,  Card.DIAMONDS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.NINE, Card.DIAMONDS),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [null, Card.ACE]
  }

}