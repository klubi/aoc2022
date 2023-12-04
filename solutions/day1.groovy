var file = new File('../input/day1').readLines()

def max = 0
def elf = 0
file.forEach {line ->
  if ("${line}" == "") {
    max = elf > max ? elf : max
    elf = 0
    return
  }
  elf = elf + Integer.valueOf(line)
}

println max

def all = []
elf = 0
file.forEach {line ->
  if ("${line}" == "") {
    all.add(elf)
    elf = 0
    return
  }
  elf = elf + Integer.valueOf(line)
}

def max3 = all.sort().subList(all.size() - 3, all.size())
println(max3.sum())
