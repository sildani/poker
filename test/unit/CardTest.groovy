import org.junit.Test

class CardTest {

  @Test
  void test_card_value_ranking() {
    assert Card.TWO   < Card.THREE
    assert Card.THREE < Card.FOUR
    assert Card.FOUR  < Card.FIVE
    assert Card.FIVE  < Card.SIX
    assert Card.SIX   < Card.SEVEN
    assert Card.SEVEN < Card.EIGHT
    assert Card.EIGHT < Card.NINE
    assert Card.NINE  < Card.TEN
    assert Card.TEN   < Card.JACK
    assert Card.JACK  < Card.QUEEN
    assert Card.QUEEN < Card.KING
    assert Card.KING  < Card.ACE
  }

  @Test
  void test_suits_alphabetical_ranking() {
    assert Card.CLUBS < Card.DIAMONDS
    assert Card.DIAMONDS < Card.HEARTS
    assert Card.HEARTS < Card.SPADES
  }

  @Test
  void test_card_sort() {
    def cards = new TreeSet()
    def c1 = new Card(Card.JACK, Card.DIAMONDS)
    def c2 = new Card(Card.THREE, Card.DIAMONDS)
    def c3 = new Card(Card.TEN, Card.CLUBS)

    cards << c1
    cards << c2
    cards << c3

    assert cards[0] == c2
    assert cards[1] == c3
    assert cards[2] == c1
  }

  @Test
  void test_card_sort_same_value_different_suit() {
    def cards = new TreeSet()
    def c1 = new Card(Card.TWO, Card.DIAMONDS)
    def c2 = new Card(Card.TWO, Card.HEARTS)
    def c3 = new Card(Card.TWO, Card.SPADES)
    def c4 = new Card(Card.TWO, Card.CLUBS)

    cards << c1
    cards << c2
    cards << c3
    cards << c4

    assert cards.size() == 4
    assert cards[0] == c4
    assert cards[1] == c1
    assert cards[2] == c2
    assert cards[3] == c3
  }

  @Test
  void should_parse_card_from_string_representation() {
    assert Card.getCard('2D') == new Card(Card.TWO, Card.DIAMONDS)
  }

  @Test
  void should_provide_string_representation_from_card() {
    assert new Card(Card.TWO, Card.CLUBS).toString()    == '2 of Clubs'
    assert new Card(Card.TEN, Card.DIAMONDS).toString() == '10 of Diamonds'
    assert new Card(Card.KING, Card.HEARTS).toString()  == 'King of Hearts'
    assert new Card(Card.ACE, Card.SPADES).toString()   == 'Ace of Spades'
  }

  @Test
  void should_provide_short_form_string_representation_from_card() {
    assert new Card(Card.TWO, Card.CLUBS).shortForm()    == '2C'
    assert new Card(Card.TEN, Card.DIAMONDS).shortForm() == '0D'
    assert new Card(Card.KING, Card.HEARTS).shortForm()  == 'KH'
    assert new Card(Card.ACE, Card.SPADES).shortForm()   == 'AS'
  }

  @Test
  void should_support_string_contructor() {
    assert new Card('2C') == new Card(Card.TWO, Card.CLUBS)
    assert new Card('0D') == new Card(Card.TEN, Card.DIAMONDS)
    assert new Card('KH') == new Card(Card.KING, Card.HEARTS)
    assert new Card('AS') == new Card(Card.ACE, Card.SPADES)
  }

}