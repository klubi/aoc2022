def file = new File('../input/day6').readLines().join()

part1buffer = 4
part2buffer = 14

println(getFirstOccurrence(file, part1buffer))
println(getFirstOccurrence(file, part2buffer))

def getFirstOccurrence(String line, int bufferSize) {
  def count = bufferSize
  for (i in bufferSize..<line.size()) {
    def uniqueCount = line.substring(i - bufferSize, i).chars().distinct().count()
    if (uniqueCount < bufferSize) {
      count++
    } else if (uniqueCount == bufferSize) {
      count + 4
      break
    }
  }
  return count
}
