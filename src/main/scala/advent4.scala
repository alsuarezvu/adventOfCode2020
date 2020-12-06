import scala.collection.immutable.HashSet
import scala.io.Source

object advent4 {
  def main(args: Array[String]): Unit = {
    val filename = "src/main/resources/advent4.txt"
    val lines = Source.fromFile(filename).getLines().toList;

    println("There are " + challenge1(lines) + " valid passports.")
  }

  def challenge1(lines: List[String]): Integer = {
    var validCount = 0
    val passport = new Passport("", "", "", "", "", "", "", "")
    for (line <- lines) {
      if (line.isEmpty) {
        if (passport.isValid())
          validCount = validCount + 1
        passport.resetFields()
      }
      else {
        passport.populate(line.split(" "))
      }
    }
    //need to count the last line
    if (passport.isValid())
      validCount = validCount + 1
    validCount
  }

  class Passport(var byr: String, var iyr: String, var eyr: String, var hgt: String, var hcl: String, var ecl: String, var pid: String, var cid: String) {

    def populate(line: Array[String]) = {
      for (token <- line) {
        //println(token)
        if (token.contains("byr")) {
          byr = token.substring(4)
        }

        if (token.contains("iyr")) {
          iyr = token.substring(4)
        }

        if (token.contains("eyr")) {
          eyr = token.substring(4)
        }

        if (token.contains("hgt")) {
          hgt = token.substring(4)
        }

        if (token.contains("hcl")) {
          hcl = token.substring(4)
        }

        if (token.contains("ecl")) {
          ecl = token.substring(4)
        }

        if (token.contains("pid")) {
          pid = token.substring(4)
        }

        if (token.contains("cid")) {
          cid = token.substring(4)
        }
      }

    }

    def isValid(): Boolean = {
      var result = false
      if (isValidByr(byr) && isValidIyr(iyr) && isValidEyr(eyr) && isValidHgt(hgt) && isValidHcl(hcl) && isValidEcl(ecl) && isValidPid(pid)) {
        result = true
      }
      //if(!result)
      //println("This one is false"+toString)

      result
    }

    def resetFields() = {
      byr = ""
      iyr = ""
      eyr = ""
      hgt = ""
      hcl = ""
      ecl = ""
      pid = ""
      cid = ""
    }

    def isValidByr(byr: String): Boolean = {
      var result = false
      if (!byr.isEmpty && byr.toInt >= 1920 && byr.toInt <= 2002) {
        result = true
      }
      result
    }

    def isValidIyr(iyr: String): Boolean = {
      var result = false
      if (!iyr.isEmpty && (iyr.toInt >= 2010 && iyr.toInt <= 2020)) {
        result = true
      }
      result
    }

    def isValidEyr(eyr: String): Boolean = {
      var result = false
      if (!eyr.isEmpty && (eyr.toInt >= 2020 && eyr.toInt <= 2030)) {
        result = true
      }
      result
    }

    def isValidHgt(hgt: String): Boolean = {
      var result = false
      val cm = hgt.endsWith("cm")
      val in = hgt.endsWith("in")
      if (!hgt.isEmpty && (cm || in)) {
        val height = hgt.substring(0, hgt.length - 2).toInt

        if (cm && height >= 150 && height <= 193)
          result = true

        if (in && height >= 59 && height <= 76)
          result = true

      }
      result
    }

    def isValidHcl(hcl: String): Boolean = {
      var result = false
      if (!hcl.isEmpty && hcl.charAt(0) == '#') {
        val str = hcl.substring(1)
        if (str.length == 6 && str.forall(x => Character.isDigit(x) || Character.isAlphabetic(x)))
          result = true
      }
      result
    }

    def isValidEcl(ecl: String): Boolean = {
      var result = false
      val hashSet: HashSet[String] = HashSet("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
      if (!ecl.isEmpty && hashSet.contains(ecl.trim)) {
        result = true
      }
      result
    }

    def isValidPid(pid: String): Boolean = {
      var result = false

      if (!pid.isEmpty && pid.length == 9 && pid.forall(x => Character.isDigit(x))) {
        result = true
      }
      result
    }

    override def toString = s"Passport($byr, $iyr, $eyr, $hgt, $hcl, $ecl, $pid, $cid)"
  }

}
