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

  // card suits
  static final int CLUBS    = 20
  static final int DIAMONDS = 30
  static final int HEARTS   = 40
  static final int SPADES   = 50

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
}