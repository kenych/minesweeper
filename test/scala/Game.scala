
import scala.util.Random
import minesweeper.Field

object Game extends App {

  //  sets

  main

  def main2 {
    println(Random.nextInt(3))
  }

  def main {

    //TODO show that put mine
	  //TODO win mechanism
    //TODO calc steps and time
    

    val field = new Field(3, 3, 2)

    field.debugModeOn

    println(field.toString)

    field.debugModeOff

    while (!field.isGameOver) {

      println("enter row:")
      val r = readLine()

      println("enter column:")
      val c = readLine()

      if (field.play(r.toInt, c.toInt).isWrongArgument) {
        println("wrong col/line");
      } else {
        println(field.toString)
      }

    }

    println("game over!")

  }

}