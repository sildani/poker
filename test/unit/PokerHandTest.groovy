import org.junit.Test

class PokerHandTest {

  @Test
  void should_construct_with_card_instances() {
    def hand = new PokerHand([
        new Card(Card.TWO,   Card.CLUBS),
        new Card(Card.THREE, Card.CLUBS),
        new Card(Card.FOUR,  Card.CLUBS),
        new Card(Card.FIVE,  Card.CLUBS),
        new Card(Card.SIX,   Card.CLUBS)
      ])
    assert hand.cards
  }

  @Test
  void should_construct_with_string() {
    def hand = new PokerHand('2H 3D 5S 9C KD')
    assert hand.cards
    assert hand.cards[0] == new Card(Card.TWO, Card.HEARTS)
    assert hand.cards[1] == new Card(Card.THREE, Card.DIAMONDS)
    assert hand.cards[2] == new Card(Card.FIVE, Card.SPADES)
    assert hand.cards[3] == new Card(Card.NINE, Card.CLUBS)
    assert hand.cards[4] == new Card(Card.KING, Card.DIAMONDS)
  }

  @Test
  void should_pretty_print_to_string() {
    assert new PokerHand('2H 3D 5S 9C KD').toString() == '2H 3D 5S 9C KD'
  }

}