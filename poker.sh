#!/bin/bash

function help {
  echo "Usage: `basename $0` 'first hand' 'second hand'"
  echo "e.g., `basename $0` '2H 3D 5S 9C KD' '2C 3H 4S 8C AH'"
  exit 1
}

function help_with_syntax {
  echo "$1 is not valid syntax, must be five cards, with each card having a "
  echo "value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, "
  echo "ace (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A) and a suit, which "
  echo "is one of clubs, diamonds, hearts, or spades (denoted C, D, H, and S)."
  echo ""
  help
}

if [ $# -ne 2 ]; then
  help
fi

VALID='([0-9JQKA][CDHS] ){4}[0-9JQKA][CDHS]'
if [[ ! $1 =~ $VALID ]]; then
  help_with_syntax 'First hand'
fi
if [[ ! $2 =~ $VALID ]]; then
  help_with_syntax 'Second hand'
fi

java -cp gen/classes:lib/groovy-all-2.1.3.jar Poker "$1" "$2"
