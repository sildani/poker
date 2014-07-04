class Deck {

  def cardsInDeck = [:]

  public Deck() {
    Card.cards.each { k,v -> cardsInDeck.put(k, new Card(v.value, v.suit)) }
  }

  Card getCard(card) {
    return cardsInDeck.get(card)
  }
}