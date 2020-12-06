import scala.io.Source

object advent2 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent2.txt"
    val file = Source.fromFile(filename)
    val lines = file.getLines().toList;

    println("There are " + challenge2(lines) + " valid passwords.")

    file.close()
  }

  def challenge1(lines: List[String]): Integer = {
    var validCount = 0
    for (line <- lines) {
      val arrayOfValues = line.split(" ")

      //first 1 is the rule
      val rules = arrayOfValues(0).split("-")
      val minTimes = Integer.parseInt(rules(0))
      val maxTimes = Integer.parseInt(rules(1))

      //2nd is the letter to apply the rule
      val letter = arrayOfValues(1).charAt(0)

      //examine the password, if valid add to count
      var letterCount = 0
      arrayOfValues(2).toCharArray.foreach(x => if (x == letter) letterCount = letterCount + 1);

      if (letterCount >= minTimes && letterCount <= maxTimes) {
        validCount = validCount + 1
        arrayOfValues.foreach(println)
        println
      }
    }

    validCount
  }

  def challenge2(lines: List[String]): Integer = {
    var validCount = 0
    for (line <- lines) {
      val arrayOfValues = line.split(" ")

      //first 1 is the rule
      val rules = arrayOfValues(0).split("-")
      val position1 = Integer.parseInt(rules(0)) - 1
      val position2 = Integer.parseInt(rules(1)) - 1

      //2nd is the letter to apply the rule
      val letter = arrayOfValues(1).charAt(0)

      //examine the password, if valid add to count
      var letterCount = 0

      val password = arrayOfValues(2).toCharArray;

      var isValid = false
      var position1Present = false
      var position2Present = false
      for (i <- 0 until password.length) {
        if (password(i) == letter) {
          if (i == position1) position1Present = true
          if (i == position2) position2Present = true
        }
      }

      if ((position1Present && !position2Present) || (!position1Present && position2Present)) isValid = true

      if (isValid) {
        validCount = validCount + 1
        arrayOfValues.foreach(println)
        println
      }
    }

    validCount
  }
}
