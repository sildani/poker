class Card implements Comparable<Card> {
  // card values
  static final int TWO   = 2
  static final int THREE = 3
  static final int FOUR  = 4
  static final int FIVE  = 5
  static final int SIX   = 6
  static final int SEVEN = 7
  static final int EIGHT = 8
  static final int NINE  = 9
  static final int TEN   = 10
  static final int JACK  = 11
  static final int QUEEN = 12
  static final int KING  = 13
  static final int ACE   = 14

  // card values as string
  static final Map cardValues = [
    (Card.TWO):   '2',
    (Card.THREE): '3',
    (Card.FOUR):  '4',
    (Card.FIVE):  '5',
    (Card.SIX):   '6',
    (Card.SEVEN): '7',
    (Card.EIGHT): '8',
    (Card.NINE):  '9',
    (Card.TEN):   '10',
    (Card.JACK):  'Jack',
    (Card.QUEEN): 'Queen',
    (Card.KING):  'King',
    (Card.ACE):   'Ace'
  ]

  // card suits
  static final int CLUBS    = 20
  static final int DIAMONDS = 30
  static final int HEARTS   = 40
  static final int SPADES   = 50

  // card suits as string
  static final Map cardSuits = [
    (Card.CLUBS):    'Clubs',
    (Card.DIAMONDS): 'Diamonds',
    (Card.HEARTS):   'Hearts',
    (Card.SPADES):   'Spades'
  ]

  // different kinds of cards
  static final Map cards = [
    '2C':new Card(Card.TWO, Card.CLUBS),
    '3C':new Card(Card.THREE, Card.CLUBS),
    '4C':new Card(Card.FOUR, Card.CLUBS),
    '5C':new Card(Card.FIVE, Card.CLUBS),
    '6C':new Card(Card.SIX, Card.CLUBS),
    '7C':new Card(Card.SEVEN, Card.CLUBS),
    '8C':new Card(Card.EIGHT, Card.CLUBS),
    '9C':new Card(Card.NINE, Card.CLUBS),
    '0C':new Card(Card.TEN, Card.CLUBS),
    'JC':new Card(Card.JACK, Card.CLUBS),
    'QC':new Card(Card.QUEEN, Card.CLUBS),
    'KC':new Card(Card.KING, Card.CLUBS),
    'AC':new Card(Card.ACE, Card.CLUBS),
    '2D':new Card(Card.TWO, Card.DIAMONDS),
    '3D':new Card(Card.THREE, Card.DIAMONDS),
    '4D':new Card(Card.FOUR, Card.DIAMONDS),
    '5D':new Card(Card.FIVE, Card.DIAMONDS),
    '6D':new Card(Card.SIX, Card.DIAMONDS),
    '7D':new Card(Card.SEVEN, Card.DIAMONDS),
    '8D':new Card(Card.EIGHT, Card.DIAMONDS),
    '9D':new Card(Card.NINE, Card.DIAMONDS),
    '0D':new Card(Card.TEN, Card.DIAMONDS),
    'JD':new Card(Card.JACK, Card.DIAMONDS),
    'QD':new Card(Card.QUEEN, Card.DIAMONDS),
    'KD':new Card(Card.KING, Card.DIAMONDS),
    'AD':new Card(Card.ACE, Card.DIAMONDS),
    '2H':new Card(Card.TWO, Card.HEARTS),
    '3H':new Card(Card.THREE, Card.HEARTS),
    '4H':new Card(Card.FOUR, Card.HEARTS),
    '5H':new Card(Card.FIVE, Card.HEARTS),
    '6H':new Card(Card.SIX, Card.HEARTS),
    '7H':new Card(Card.SEVEN, Card.HEARTS),
    '8H':new Card(Card.EIGHT, Card.HEARTS),
    '9H':new Card(Card.NINE, Card.HEARTS),
    '0H':new Card(Card.TEN, Card.HEARTS),
    'JH':new Card(Card.JACK, Card.HEARTS),
    'QH':new Card(Card.QUEEN, Card.HEARTS),
    'KH':new Card(Card.KING, Card.HEARTS),
    'AH':new Card(Card.ACE, Card.HEARTS),
    '2S':new Card(Card.TWO, Card.SPADES),
    '3S':new Card(Card.THREE, Card.SPADES),
    '4S':new Card(Card.FOUR, Card.SPADES),
    '5S':new Card(Card.FIVE, Card.SPADES),
    '6S':new Card(Card.SIX, Card.SPADES),
    '7S':new Card(Card.SEVEN, Card.SPADES),
    '8S':new Card(Card.EIGHT, Card.SPADES),
    '9S':new Card(Card.NINE, Card.SPADES),
    '0S':new Card(Card.TEN, Card.SPADES),
    'JS':new Card(Card.JACK, Card.SPADES),
    'QS':new Card(Card.QUEEN, Card.SPADES),
    'KS':new Card(Card.KING, Card.SPADES),
    'AS':new Card(Card.ACE, Card.SPADES)
  ]

  static final Card getCard(String card) {
    return cards.get(card)
  }

  def value
  def suit

  public Card(value, suit) {
    this.value = value
    this.suit = suit
  }

  int compareTo(Card other) {
    def i = Integer.compare(this.value, other.value)
    if (i != 0) {
      return i
    } else {
      return Integer.compare(this.suit, other.suit)
    }
  }

  String toString() {
    "${Card.cardValues[value]} of ${Card.cardSuits[suit]}"
  }

  String shortForm() {
    if (value == Card.TEN) {
      return "0${Card.cardSuits[suit][0]}"
    } else {
      return "${Card.cardValues[value][0]}${Card.cardSuits[suit][0]}"
    }
  }
}