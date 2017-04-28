package minesweeper

import scala.util.Random

import Cell.emptytCell
import Cell.minedCell

class Field(val rows: Int, val columns: Int, val mines: Int) {

  var cellDebug = false

  def cellDebugOn = cellDebug = true
  def cellDebugOff = cellDebug = false
  def isCellDebug = cellDebug;

  var fieldResult = new Result(rows, columns, mines)

  def getFieldResult = fieldResult

  private var _gameOver = false
  private var _debug = false

  def debugModeOn = _debug = true
  def debugModeOff = _debug = false

  require(rows > 0)
  require(columns > 0)
  require(mines < rows * columns)

  var cells = Array.ofDim[Cell](rows, columns)

  init
  def init =
    for (rowIndex <- 0 until rows)
      for (colIndex <- 0 until columns)
        cells(rowIndex)(colIndex) = emptytCell

  initMines
  def initMines =
    for (iteration <- 1 to mines)
      while (!(putMineAtCoordinate _).tupled(randomMineCoords)) ()

  def upper = (col: Int, row: Int) => if (row > 0) (true, row - 1, col) else (false, -1, -1)

  def right = (col: Int, row: Int) => if (col < columns - 1) (true, row, col + 1) else (false, -1, -1)

  def upperRight = (col: Int, row: Int) => if (row > 0 && col < columns - 1) (true, row - 1, col + 1) else (false, -1, -1)

  def lowerRight = (col: Int, row: Int) => if (row < rows - 1 && col < columns - 1) (true, row + 1, col + 1) else (false, -1, -1)

  def lower = (col: Int, row: Int) => if (row < rows - 1) (true, row + 1, col) else (false, -1, -1)

  def lowerLeft = (col: Int, row: Int) => if (row < rows - 1 && col > 0) (true, row + 1, col - 1) else (false, -1, -1)

  def left = (col: Int, row: Int) => if (col > 0) (true, row, col - 1) else (false, -1, -1)

  def upperLeft = (col: Int, row: Int) => if (row > 0 && col > 0) (true, row - 1, col - 1) else (false, -1, -1)

  private def locationsArround = Array(upper, upperRight, right, lowerRight, lower, lowerLeft, left, upperLeft)

  def getNearByMines(row: Int, col: Int): Int = {
    require(!(mineAtCoordinate(row, col)))

    locationsArround.count(nextNearByLocation => {
      val nearByData = nextNearByLocation(col, row)
      if (nearByData._1) mineAtCoordinate(nearByData._2, nearByData._3) else false
    })
  }

  def mineAssumed(row: Int, column: Int): Boolean = {

    return true;
  }

  def play(row: Int, column: Int, putMine: Boolean = false, removeMine: Boolean = false): Result = {
    var result = new Result(rows, columns, mines)

    if (row < 0 || row >= rows || column < 0 || column >= columns || (removeMine == true && putMine == true)) {
      result.wrongArgument
      fieldResult = result
      return result
    }

    if (putMine) {
      println("putting mine");
      cells(row)(column).asssumeMine
    } else if (removeMine) {
      println("removing mine");
      cells(row)(column).removeAsssumedMine
    } else {
      println("opening");
      openAt(row: Int, column: Int)
    }

    result.cells = new Array[Cell](rows * columns)

    if (isGameOver) result.gameOver

    var fullIndex = 0;
    for (rowIndex <- 0 until rows) {
      for (colIndex <- 0 until columns) {

        val cell = emptytCell;
        cell.row = rowIndex
        cell.column = colIndex

        if (isGameOver) {
          if (cells(rowIndex)(colIndex).getBlasted) cell.blast
          else if (cells(rowIndex)(colIndex).getMined) cell.putMine
          else if (cells(rowIndex)(colIndex).isOpened) {
            cell.open
            cell.setNeighbours(cells(rowIndex)(colIndex).getNeighbours)
          } else if (cells(rowIndex)(colIndex).isMineAssumed) {
            cell.asssumeMine
          }
        } else {
          if (cells(rowIndex)(colIndex).isOpened) {
            cell.open
            cell.setNeighbours(cells(rowIndex)(colIndex).getNeighbours)
          } else if (cells(rowIndex)(colIndex).isMineAssumed) {
            cell.asssumeMine
          }
        }

        result.cells(fullIndex) = cell
        if (isCellDebug) println("cell: " + cell)

        fullIndex += 1;
      }
    }

    fieldResult = result

    result
  }

  def openAt(row: Int, column: Int): Boolean = {

    cells(row)(column).open

    if (cells(row)(column).getMined) {
      cells(row)(column).blast
      gameOver
    } else {

      val nearByMines = getNearByMines(row, column)

      if (nearByMines == 0) {
        locationsArround.foreach(nextNearByLocation => {
          val nearByData = nextNearByLocation(column, row)
          if (nearByData._1)
            if (cells(nearByData._2)(nearByData._3).closed)
              openAt(nearByData._2, nearByData._3)
        })
      }

      cells(row)(column).setNeighbours(nearByMines)
    }

    return true

  }

  def randomMineCoords: (Int, Int) = (Random.nextInt(rows), Random.nextInt(columns))

  def putMineAtCoordinate(row: Int, column: Int) = {
    require(row >= 0 && row < rows)
    require(column >= 0 && column < columns)

    if (mineAtCoordinate(row, column)) false
    else {
      cells(row)(column) = minedCell
      true
    }

  }

  def mineAtCoordinate(row: Int, column: Int): Boolean = {
    return cells(row)(column).getMined
  }

  override def toString = {
    val lines: StringBuffer = new StringBuffer
    for (rowIndex <- 0 until rows) lines.
      append(drawLine(rowIndex)).
      append(if (rowIndex != rows - 1) "\n" else "")

    lines.toString
  }

  def drawLine(rowIndex: Integer): String = {
    val line: StringBuffer = new StringBuffer
    for (columnIndex <- 0 until columns) line.
      append(drowCell(rowIndex, columnIndex))

    line.toString
  }

  def gameOver = _gameOver = true
  def isGameOver = _gameOver

  def drowCell(row: Int, column: Int) =
    if (_debug) {
      if (cells(row)(column).getMined) "*"
      else if (cells(row)(column).isMineAssumed) "M"
      else getNearByMines(row, column)
    } else if (isGameOver) {
      if (cells(row)(column).getBlasted) "!"
      else if (cells(row)(column).getMined) "*"
      else if (cells(row)(column).isMineAssumed) "M"
      else if (cells(row)(column).isOpened) cells(row)(column).getNeighbours
      else "X"
    } else {
      if (cells(row)(column).isOpened) cells(row)(column).getNeighbours
      else if (cells(row)(column).isMineAssumed) "M"
      else "X"
    }

}



