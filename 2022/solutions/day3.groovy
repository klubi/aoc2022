letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
var file = new File('../input/day3').readLines()

def priorities = 0
file.each { line ->

  def firstCompartment = []
  line.substring(0, line.size()/2 as int).each { letter ->
    firstCompartment << letter
  }
  def secondCompartment = []
  line.substring(line.size()/2 as int, line.size() as int).each { letter ->
    secondCompartment << letter
  }

  def result = firstCompartment.intersect(secondCompartment)
  priorities = priorities + letters.indexOf(result[0] as String) + 1
}

println(priorities)

priorities = 0
file.collate(3).each {group ->
  priorities = priorities + letters.indexOf(group[0].find {
    group[1].contains(it) && group[2].contains(it)
  } as String) + 1
}

println(priorities)
