import scala.io.Source

object advent1 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent1.txt"
    val lines = Source.fromFile(filename).getLines().toList.map(_.toInt).sorted;
    val total = 2020


    for (i <- 0 until lines.length - 2) {
      var firstPtr = i + 1
      var secondPtr = lines.length - 1
      while (firstPtr < secondPtr) {
        val anchorValue = lines(i)
        val firstPtrValue = lines(firstPtr)
        val secondPtrValue = lines(secondPtr)
        val sum = anchorValue + firstPtrValue + secondPtrValue
        if (sum == total) {
          System.out.println("Triplet is: " + lines(i) + " " + lines(firstPtr) + " " + lines(secondPtr))
          return true;
        }
        else if (sum < total) {
          firstPtr = firstPtr + 1
        }
        else if (sum > total) {
          secondPtr = secondPtr - 1
        }
      }

    }

  }

}
