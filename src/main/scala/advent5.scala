import scala.io.Source

object advent5 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/scala/advent5.txt"
    val lines = Source.fromFile(filename).getLines().toList;

    println("There are " + challenge1(lines) + " valid passports.")
  }

  def challenge1(lines: List[String]) = {
    var maxBoardingPassId = 0;


    for (line <- lines) {
      val boardingPassIdList = line.trim.toList
      println(findBoarding(boardingPassIdList, 0, 127, 0, 0, 7, 0))
      //      println(boardingPassId)
      //      if(boardingPassId > maxBoardingPassId)
      //        maxBoardingPassId = boardingPassId
    }

    println("Max boarding pass id " + maxBoardingPassId)
  }


  // row first, column second
  def findBoarding(boardingPassId: List[Char], row: Int, rowMax: Int, rowMin: Int, column: Int, columnMax: Int, columnMin: Int): Int = {
    println(boardingPassId + " " + row + " " + rowMax + " " + rowMin + " " + column + " " + columnMax + " " + columnMin)
    boardingPassId match {
      case Nil => {
        row + column
      }
      case x :: xs => {
        if (x == 'F') {
          val newRowMax = rowMin + (rowMax - rowMin) / 2
          findBoarding(xs, newRowMax, newRowMax, rowMin, column, columnMax, columnMin)
        }
        if (x == 'B') {
          val newRowMin = rowMin + (rowMax - rowMin) / 2 + 1
          findBoarding(xs, newRowMin, rowMax, newRowMin, column, columnMax, columnMin)
        }
        if (x == 'L') {
          val newColumnMax = columnMin + (columnMax - columnMin) / 2
          findBoarding(xs, row, rowMax, rowMin, newColumnMax, newColumnMax, columnMin)
        }
        if (x == 'R') {
          val newColumnMin = columnMin + (columnMax - columnMin) / 2 + 1
          findBoarding(xs, row, rowMax, rowMin, newColumnMin, columnMax, newColumnMin)
        }
        (row * 8) + column
      }
    }
  }
}
