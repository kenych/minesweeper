

import org.junit.Assert._
import org.junit.Test
import minesweeper.Cell
import minesweeper.Field

class CellTest {

  @org.junit.Test
  def constructor() {

    val cell = new Cell
    assertFalse(cell.getMined)

    cell.putMine
    assertTrue(cell.getMined)

    assertEquals(0, cell.getNeighbours)
  }

  @org.junit.Test(expected = classOf[IllegalArgumentException])
  def illegalRows() {
    new Field(0, 3, 1)
  }

  @org.junit.Test(expected = classOf[IllegalArgumentException])
  def illegalcols() {
    new Field(1, -1, 1)
  }

}