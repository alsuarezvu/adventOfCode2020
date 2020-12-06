import scala.io.Source

object advent3 {

  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent3.txt"
    val file = Source.fromFile(filename)
    val lines =file .getLines().toList;

    println("There are " + challenge1(lines) + " trees.")

    file.close()
  }

  def challenge1(lines: List[String]): Integer = {
    var currentLocation = 0;
    var treeCounter = 0;

    val lineLength = lines(0).length

    //for(i <- 2 until lines.length by 2)
    //change according to slope
    for (i <- 1 until lines.length) {
      //Change according to slope
      currentLocation = currentLocation + 1;

      if (currentLocation > lineLength) {
        println("Not enough column blocks")
      }
      else {
        val currentChar = lines(i).charAt(currentLocation)
        if (currentChar == '#') {
          treeCounter = treeCounter + 1
          println("Found tree on line:" + (i + 1) + " at location " + currentLocation + " " + lines(i).charAt(currentLocation))
        }
      }
    }

    treeCounter
  }

}
