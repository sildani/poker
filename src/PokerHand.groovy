class PokerHand {
  def cards
  
  public PokerHand(List cards) {
    this.cards = cards
  }

  public PokerHand(String cards) {
    this.cards = []
    cards.tokenize(' ').each { this.cards << Card.getCard(it) }
  }

  String toString() {
    cards.collect { it.shortForm() }.join(' ')
  }
}