import groovy.transform.Field
import java.util.regex.Matcher
import java.util.regex.Pattern

var file = new File('../input/day7').readLines()

class Dir {
  String name
  Dir parent
  Set<Dir> subFolders = []
  Set<Fil> files = []

  Dir(String name, Dir parent) {
    this.name = name
    this.parent = parent
  }

  int totalSize() {
    int size = 0
    size += files.sum {it.size} ?: 0
    subFolders.sum {
      size += it.files.sum {it.size} ?: 0
      size += it.subFolders.sum {it.totalSize()} ?: 0
    }
    size
  }
}

class Fil {
  String name
  int size

  Fil(String name, int size) {
    this.name = name
    this.size = size
  }
}

@Field
Pattern cdcmd = ~/^\$ cd (\S+)$/
@Field
Pattern lscmd = ~/^\$ ls$/
@Field
Pattern dirlisting = ~/^dir (\S+)$/
@Field
Pattern filelisting = ~/^(\d+) (\S+)$/

Dir parseData(def rows) {
  Dir root = new Dir("/", null)

  Dir currentDir = root
  rows.eachWithIndex {String row, int index ->
    Matcher cdmatch = cdcmd.matcher(row)
    Matcher lsmatch = lscmd.matcher(row)
    Matcher dirmatch = dirlisting.matcher(row)
    Matcher filematch = filelisting.matcher(row)

    if (cdmatch.matches()) {
      def name = cdmatch[0][1]
      if (name == "..") {
        currentDir = currentDir.parent
      } else if (name == "/") {
        currentDir = root
      } else {
        currentDir = currentDir.subFolders.find {it.name == name}
      }
    } else if (lsmatch.matches()) {
      //Do nothing
    } else if (dirmatch.matches()) {
      currentDir.subFolders << new Dir(dirmatch[0][1] as String, currentDir)
    } else if (filematch.matches()) {
      currentDir.files << new Fil(filematch[0][2] as String, filematch[0][1] as int)
    }
  }
  return root
}

def getSmallFolders(Dir tree, int maxSize, def smallFolders) {
  tree.subFolders.findResults {dir ->
    if (dir.totalSize() < maxSize) {
      smallFolders << dir
    }
    getSmallFolders(dir, maxSize, smallFolders)
  }
}

def getBigDirs(Dir tree, int minSize, def bigFolders) {
  tree.subFolders.findResults {dir ->
    if (dir.totalSize() > minSize) {
      bigFolders << dir
      getBigDirs(dir, minSize, bigFolders)
    }
  }
}

def part1(Dir tree) {
  def MAX_SIZE = 100_000
  def smallFolders = [] as List<Dir>
  getSmallFolders(tree, MAX_SIZE, smallFolders)

  println("SUM: " + smallFolders.sum {it.totalSize()})
}

def part2(Dir tree) {
  def MAX_SPACE = 70_000_000
  def SPACE_REQUIRED = 30_000_000

  def totalUsed = tree.totalSize()
  def freeSpace = MAX_SPACE - totalUsed
  def toRemove = SPACE_REQUIRED - freeSpace

  def bigFolders = [] as List<Dir>
  getBigDirs(tree, toRemove, bigFolders)

  println("Big folder size:" + bigFolders.min {it.totalSize()}.totalSize())
}

def tree = parseData(file)

part1(tree)
part2(tree)
