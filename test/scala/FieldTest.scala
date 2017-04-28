

import org.junit.Assert._
import org.junit.Test
import minesweeper.Field

class FieldTest {

  @Test
  def constructor() {
    val f = new Field(2, 2, 0)
  }

  @Test
  def drawEmptyField() {
    val field = new Field(2, 3, 0)
    assertEquals("XXX\nXXX", field.toString)
  }

  @Test
  def drawWithMine() {

    val field = new Field(2, 3, 0)
    field.debugModeOn

    assertEquals("000\n000", field.toString)

    assertTrue(field.putMineAtCoordinate(0, 0))
    assertTrue(field.mineAtCoordinate(0, 0))
    assertFalse(field.putMineAtCoordinate(0, 0))

    val fieldImage = field.toString

    println(fieldImage);
    assertEquals("*10\n110", fieldImage)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def illegalRows() {
    new Field(0, 3, 1)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def illegalcols() {
    new Field(1, -1, 1)
  }

  @Test
  def initStars() {
    val stars = 1;
    val field = new Field(2, 2, stars)
    field.debugModeOn
    val fieldImage = field.toString
    println(fieldImage);
    assertEquals(stars, fieldImage.count(_ == '*'))

  }

  @Test
  def getNearByStars() {
    var field = new Field(3, 3, 0)
    assertEquals("XXX\nXXX\nXXX", field.toString)

    field.putMineAtCoordinate(0, 0)

    println(field.toString)

    assertEquals(1, field.getNearByMines(0, 1));
    assertEquals(0, field.getNearByMines(0, 2));

    assertEquals(1, field.getNearByMines(1, 0));
    assertEquals(1, field.getNearByMines(1, 1));


    field = new Field(3, 3, 0)
    assertEquals("XXX\nXXX\nXXX", field.toString)

    field.putMineAtCoordinate(0, 0)
    field.putMineAtCoordinate(0, 1)
    field.putMineAtCoordinate(1, 1)

    println(field.toString)

    assertEquals(2, field.getNearByMines(0, 2));

    assertEquals(3, field.getNearByMines(1, 0));
    assertEquals(2, field.getNearByMines(1, 2));

    assertEquals(1, field.getNearByMines(2, 0));
    assertEquals(1, field.getNearByMines(2, 1));
    assertEquals(1, field.getNearByMines(2, 2));

  }

}