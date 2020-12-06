import scala.io.Source

object advent6 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/scala/advent6Test.txt"
    val lines = Source.fromFile(filename).getLines().toList;

    println("There are " + challenge1(lines) + " valid passports.")
  }

  def challenge1(lines: List[String]): Integer = {
    0
  }


}
