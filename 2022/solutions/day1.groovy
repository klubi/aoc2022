var file = new File('../input/day1')

def max = 0
file.withReader { reader ->
    def elf = 0
    while ((line = reader.readLine()) != null ) {
        if ("${line}" == "") {
            max = elf > max ? elf : max
            elf = 0
            continue
        }
        elf = elf + Integer.valueOf(line)
    }
}

println max

def all = []
file.withReader {reader ->
    def elf = 0
    while ((line = reader.readLine()) != null ) {
        if ("${line}" == "") {
            all.add(elf)
            elf = 0
            continue
        }
        elf = elf + Integer.valueOf(line)
    }
}
def max3 = all.sort().subList(all.size() - 3, all.size())
println(max3)
println(max3.sum())
