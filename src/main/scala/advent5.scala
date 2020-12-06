import scala.::
import scala.collection.mutable.ListBuffer
import scala.io.Source

object advent5 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent5.txt"
    val file = Source.fromFile(filename)
    val lines = file.getLines().toList;

    println("Part 1 answer is: "+challenge1(lines))
    println("Part 2 answer is: "+challenge2(lines))

    file.close()
  }

  def challenge1(lines: List[String]) = {
    var maxBoardingPassId = 0;

    for (line <- lines) {
      val boardingPassIdList = line.trim.toList
      val boardingPassId = findBoarding(boardingPassIdList, 0, 127, 0, 0, 7, 0)
      if(boardingPassId > maxBoardingPassId)
        maxBoardingPassId = boardingPassId
    }
    maxBoardingPassId
  }

  def challenge2(lines: List[String]) = {
    var myBoardingPassId = 0;
    var boardingPassIds = ListBuffer[Int]()
    for (line <- lines) {
      val boardingPassIdList = line.trim.toList
      val boardingPassId = findBoarding(boardingPassIdList, 0, 127, 0, 0, 7, 0)
      boardingPassIds += boardingPassId
    }
    boardingPassIds = boardingPassIds.sortWith(_<_);
    var prevId = (boardingPassIds.head - 1)
    for (id <- boardingPassIds) {
      if (id - prevId > 1) {
        myBoardingPassId = id - 1
      }
      prevId = id
    }
    myBoardingPassId
  }


  // row first, column second
  def findBoarding(boardingPassId: List[Char], row: Int, rowMax: Int, rowMin: Int, column: Int, columnMax: Int, columnMin: Int): Int = {
    //println(boardingPassId + " " + row + " " + rowMax + " " + rowMin + " " + column + " " + columnMax + " " + columnMin)
    boardingPassId match {
      case Nil => {
        row * 8 + column
      }
      case x :: xs => {
        if (x == 'F') {
          val newRowMax = rowMin + (rowMax - rowMin) / 2
          return findBoarding(xs, newRowMax, newRowMax, rowMin, column, columnMax, columnMin)
        }
        if (x == 'B') {
          val newRowMin = rowMin + (rowMax - rowMin) / 2 + 1
          return findBoarding(xs, newRowMin, rowMax, newRowMin, column, columnMax, columnMin)
        }
        if (x == 'L') {
          val newColumnMax = columnMin + (columnMax - columnMin) / 2
          return findBoarding(xs, row, rowMax, rowMin, newColumnMax, newColumnMax, columnMin)
        }
        if (x == 'R') {
          val newColumnMin = columnMin + (columnMax - columnMin) / 2 + 1
          return findBoarding(xs, row, rowMax, rowMin, newColumnMin, columnMax, newColumnMin)
        }
        println("Unexpected character +"+x)
        0
      }
    }
  }
}
