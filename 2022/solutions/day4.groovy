var file = new File('../input/day4').readLines()

def found = 0
def total = 0
file.each {line ->
  def pair = line.split(",").collect {group ->
    def list = groupToList(group)
    def numbers = []
    (Integer.valueOf(list[0] as String)..Integer.valueOf(list[1] as String)).each {number ->
      numbers << number
    }
    return numbers
  }
  if (pair[0].intersect(pair[1]).size() == pair[1].size() || pair[1].intersect(pair[0]).size() == pair[0].size()) {
    found++
  }
  if (pair[0].intersect(pair[1]).size() > 0 || pair[1].intersect(pair[0]).size() > 0) {
    total++
  }
}

println(found)
println(total)

def groupToList(String group) {
  group.split("-").collect()
}
