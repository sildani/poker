import org.junit.Before
import org.junit.Test

class DeckTest {

  def deck

  @Before
  void init() {
    deck = new Deck()
  }

  @Test
  void test_get_card_from_deck() {
    deck.getCard('2H') == new Card(Card.TWO, Card.HEARTS)
  }

  @Test
  void test_cannot_get_same_card_twice() {
    def c1
    def c2
    def c3
    try {
      c1 = deck.getCard('3H')
      c2 = deck.getCard('4H')
      c3 = deck.getCard('4H') // this is the one that should fail
    } catch (IllegalStateException e) {
      assert c1
      assert c2
      assert c3 == null
    }
  }
}