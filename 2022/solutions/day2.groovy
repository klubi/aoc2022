var file = new File('../input/day2').readLines()
def points = 0
file.each {line ->
  def lineArray = "$line".split(" ")
  def him = Shape.fromLetter(lineArray[0])
  def me = Shape.fromLetter(lineArray[1])
  points = points + me.points
  points = points + me.result(him)
}

println(points)

points = 0
file.each {line ->
  def lineArray = "$line".split(" ")
  def him = Shape.fromLetter(lineArray[0])
  def me = Shape.fromResult(him, lineArray[1])
  points = points + me.points
  points = points + me.result(him)
}

println(points)

enum Shape {

  Rock(1, "Scissors", "Paper"),
  Paper(2, "Rock", "Scissors"),
  Scissors(3, "Paper", "Rock")

  int points
  String wins, looses

  Shape(int points, String wins, String looses) {
    this.points = points
    this.wins = wins
    this.looses = looses
  }

  int result(Shape shape) {
    if (this == shape) {
      return 3
    } else if (this.wins == shape.toString()) {
      return 6
    } else if (this.looses == shape.toString()) {
      return 0
    } else {
      throw new IllegalStateException("WHAT!!")
    }
  }

  static Shape fromLetter(String letter) {
    switch (letter) {
      case "A":
      case "X":
        return Rock
      case "B":
      case "Y":
        return Paper
      case "C":
      case "Z":
        return Scissors
      default:
        throw new IllegalStateException("DARN!")
    }
  }

  static Shape fromResult(Shape him, String result) {
    switch (result) {
      case "X":
        return valueOf(him.wins)
      case "Y":
        return him
      case "Z":
        return valueOf(him.looses)
      default:
        throw new IllegalStateException("DARN!")
    }
  }
}
