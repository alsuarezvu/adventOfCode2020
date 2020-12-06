import scala.collection.mutable
import scala.io.Source

object advent6 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent6.txt"
    val file = Source.fromFile(filename)
    val lines = file.getLines().toList;

    println("Part 1 answer is " + challenge1(lines))
    println("Part 1 answer is " + challenge2(lines))
    file.close()
  }

  //Part 1
  def challenge1(lines: List[String]): Integer = {
    var sumUniqueAnswers = 0;
    var answerHashSet = new mutable.HashSet[Char]
    for(line <- lines) {
      if(line.isEmpty) {
        //add
        sumUniqueAnswers = sumUniqueAnswers + answerHashSet.size
        //reset structures
        answerHashSet.clear()
      }
      else {
        for (c <- line.trim.toCharArray) {
          answerHashSet.add(c)
        }
      }
    }
    //add
    sumUniqueAnswers = sumUniqueAnswers + answerHashSet.size
    //reset structures
    answerHashSet.clear()
    sumUniqueAnswers
  }

  //Part 2
  def challenge2(lines: List[String]): Integer = {
    var sumUniqueAnswers = 0;
    val answerHashMap: mutable.HashMap[Char, Int] = new mutable.HashMap()
    var numLines = 0;
    for(line <- lines) {
      if(line.isEmpty) {
        for(frequency <- answerHashMap)
        {
          if(numLines == frequency._2) {
            sumUniqueAnswers = sumUniqueAnswers + 1
          }
        }

        //reset structures
        answerHashMap.clear()
        numLines = 0
      }
      else {
        numLines = numLines + 1
        for (c <- line.trim.toCharArray) {
          answerHashMap.get(c) match {
            case None => answerHashMap.put(c, 1)
            case _ => {
              val frequency = answerHashMap(c) + 1
              answerHashMap.put(c, frequency)
            }
          }
        }
      }
    }
    for(frequency <- answerHashMap)
    {
      if(numLines == frequency._2) {
        sumUniqueAnswers = sumUniqueAnswers + 1
      }
    }
    sumUniqueAnswers
  }


}
