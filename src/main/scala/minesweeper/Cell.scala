package minesweeper

class Cell(var _mined: Boolean = false, var _neighbours: Int = 0) {

  var row: Int = -1
  var column: Int = -1

  def getRow = row;
  def getColumn = column;

  var _opened = false
  var _blasted = false
  var _mineAssumed = false

  def asssumeMine = _mineAssumed = true
  def removeAsssumedMine = _mineAssumed = false
  def isMineAssumed = _mineAssumed

  def blast = _blasted = true
  def getBlasted = _blasted

  def getMined = _mined
  def putMine = _mined = true

  def setNeighbours(n: Int) = { _neighbours = n }
  def getNeighbours = _neighbours

  def isOpened = _opened
  def closed = !_opened
  def open = _opened = true

  override def toString = {
    val str: StringBuffer = new StringBuffer
    str.append("column: " + column).append("\n")
    str.append("row: " + row).append("\n")
    str.append("opened: " + _opened).append("\n")
    str.append("blast: " + _blasted).append("\n")
    str.append("mined: " + _mineAssumed).append("\n")
    str.append("_neighbours: " + _neighbours).append("\n")

    str.toString()

  }

}

object Cell {

  def minedCell = new Cell(true)
  def emptytCell = new Cell()

}