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
      ])) == [Poker.STRAIGHT_FLUSH, new Card(Card.SIX, Card.CLUBS)]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TEN,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.DIAMONDS),
        new Card(Card.QUEEN, Card.DIAMONDS),
        new Card(Card.KING,  Card.DIAMONDS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) == [Poker.STRAIGHT_FLUSH, new Card(Card.ACE, Card.DIAMONDS)]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TEN,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.CLUBS),
        new Card(Card.QUEEN, Card.SPADES),
        new Card(Card.KING,  Card.HEARTS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) != [Poker.STRAIGHT_FLUSH, new Card(Card.ACE, Card.DIAMONDS)]

    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.DIAMONDS),
        new Card(Card.JACK,  Card.DIAMONDS),
        new Card(Card.QUEEN, Card.DIAMONDS),
        new Card(Card.KING,  Card.DIAMONDS),
        new Card(Card.ACE,   Card.DIAMONDS)
      ])) != [Poker.STRAIGHT_FLUSH, new Card(Card.ACE, Card.DIAMONDS)]
  }

  @Test
  void should_evaluate_to_a_four_of_a_kind() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO, Card.CLUBS),
        new Card(Card.TWO, Card.DIAMONDS),
        new Card(Card.TWO, Card.HEARTS),
        new Card(Card.TWO, Card.SPADES),
        new Card(Card.ACE, Card.CLUBS)
      ])) == [Poker.FOUR_OF_A_KIND, new Card(Card.TWO, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_a_full_house() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO, Card.CLUBS),
        new Card(Card.TWO, Card.DIAMONDS),
        new Card(Card.TWO, Card.HEARTS),
        new Card(Card.ACE, Card.SPADES),
        new Card(Card.ACE, Card.CLUBS)
      ])) == [Poker.FULL_HOUSE, new Card(Card.TWO, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_a_flush() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.FIVE,  Card.CLUBS),
        new Card(Card.EIGHT, Card.CLUBS),
        new Card(Card.JACK,  Card.CLUBS),
        new Card(Card.ACE,   Card.CLUBS)
      ])) == [Poker.FLUSH, new Card(Card.ACE, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_a_straight() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.THREE, Card.DIAMONDS),
        new Card(Card.FOUR,  Card.HEARTS),
        new Card(Card.FIVE,  Card.SPADES),
        new Card(Card.SIX,   Card.CLUBS)
      ])) == [Poker.STRAIGHT, new Card(Card.SIX, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_a_three_of_a_kind() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.TWO,  Card.HEARTS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.THREE_OF_A_KIND, new Card(Card.TWO, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_a_two_pairs() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.JACK, Card.DIAMONDS),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.TWO_PAIRS, new Card(Card.JACK, Card.DIAMONDS)]
  }

  @Test
  void should_evaluate_to_a_one_pair() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,  Card.CLUBS),
        new Card(Card.TWO,  Card.DIAMONDS),
        new Card(Card.JACK, Card.SPADES),
        new Card(Card.NINE, Card.DIAMONDS),
        new Card(Card.ACE,  Card.CLUBS)
      ])) == [Poker.PAIR, new Card(Card.TWO, Card.CLUBS)]
  }

  @Test
  void should_evaluate_to_only_high_card() {
    assert poker.evaluateHand(new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.THREE, Card.DIAMONDS),
        new Card(Card.JACK,  Card.SPADES),
        new Card(Card.NINE,  Card.DIAMONDS),
        new Card(Card.ACE,   Card.CLUBS)
      ])) == [null, new Card(Card.ACE, Card.CLUBS), new Card(Card.JACK, Card.SPADES)]
  }

  @Test
  void should_parse_scoring_hand_string_by_scoring_hand() {
    assert Poker.scoringHand[Poker.PAIR]            == 'Pair'
    assert Poker.scoringHand[Poker.TWO_PAIRS]       == 'Two pairs'
    assert Poker.scoringHand[Poker.THREE_OF_A_KIND] == 'Three of a kind'
    assert Poker.scoringHand[Poker.STRAIGHT]        == 'Straight'
    assert Poker.scoringHand[Poker.FLUSH]           == 'Flush'
    assert Poker.scoringHand[Poker.FULL_HOUSE]      == 'Full house'
    assert Poker.scoringHand[Poker.FOUR_OF_A_KIND]  == 'Four of a kind'
    assert Poker.scoringHand[Poker.STRAIGHT_FLUSH]  == 'Straight flush'
  }

  @Test
  void should_select_correct_winner() {
    // high card wins
    assert evalGame(
      new PokerHand('2H 3D 5S 9C KD'),
      new PokerHand('2C 3H 4S 8C AH')) == 'White wins - High card: Ace of Hearts'

    // full house wins
    assert evalGame(
      new PokerHand('2H 4S 4C 2D 4H'),
      new PokerHand('2S 8S AS QS 3S')) == 'Black wins - Full house'

    // high card ties, tie breaker is 9 > 8
    assert evalGame(
      new PokerHand('2H 3D 5S 9C KD'),
      new PokerHand('2C 3H 4S 8C KH')) == 'Black wins - High card: 9 of Clubs'

    // proper tie
    assert evalGame(
      new PokerHand('2H 3D 5S 9C KD'),
      new PokerHand('2D 3H 5C 9S KH')) == 'Tie'
  }

  def evalGame(hand1, hand2) {
    return poker.evaluateGame(hand1, hand2) 
  }

}