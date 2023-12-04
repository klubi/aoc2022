def file = new File('../input/day5').readLines()
def input = getInput(file)

def stack = [
    ['F', 'D', 'B', 'Z', 'T', 'J', 'R', 'N'],
    ['R', 'S', 'N', 'J', 'H'],
    ['C', 'R', 'N', 'J', 'G', 'Z', 'F', 'Q'],
    ['F', 'V', 'N', 'G', 'R', 'T', 'Q'],
    ['L', 'T', 'Q', 'F'],
    ['Q', 'C', 'W', 'Z', 'B', 'R', 'G', 'N'],
    ['F', 'C', 'L', 'S', 'N', 'H', 'M'],
    ['D', 'N', 'Q', 'M', 'T', 'J'],
    ['P', 'G', 'S']
]

part1(input, deepcopy(stack) as ArrayList<List<String>>)
part2(input, deepcopy(stack) as ArrayList<List<String>>)

def part1(List<ArrayList<Integer>> input, ArrayList<List<String>> stack) {
  input.each {move ->
    move[0].times {cratesCount ->
      def removed = stack.get(move[1] - 1).removeLast()
      stack.get(move[2] - 1).add(removed)
    }
  }

  printStack(stack)
}

def part2(List<ArrayList<Integer>> input, ArrayList<List<String>> stack) {
  input.each {move ->
    def removed = stack.get(move[1] - 1).takeRight(move[0])
    stack.get(move[2] - 1).addAll(removed)
    //Because there seems to be a bug somewhere in groovy
    //In one of the cases `stack.get(move[1] - 1).removeAll(removed)`
    // was removing all elements from stack, instead of just the desired number...
    removed.size().times {
      stack.get(move[1] - 1).removeLast()
    }
  }

  printStack(stack)
}

def getInput(List<String> lines) {
  lines.findAll {line -> line.startsWith("move")}
      .collect {line ->
        line.findAll(/\d+/)*.toInteger()
      }
}

def printStack(ArrayList<List<String>> stack) {
  def stackNo = 1
  stack.each {
    println("$stackNo: $it")
    stackNo++
  }
}

def deepcopy(orig) {
  def bos = new ByteArrayOutputStream()
  def oos = new ObjectOutputStream(bos)
  oos.writeObject(orig); oos.flush()
  def bin = new ByteArrayInputStream(bos.toByteArray())
  def ois = new ObjectInputStream(bin)
  return ois.readObject()
}
