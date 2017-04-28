package minesweeper

class Result(val rows: Int = 0, val columns: Int = 0, val mines: Int = 0) {

  private var _wrongArgumet: Boolean = false

  def wrongArgument = _wrongArgumet = true

  var cells = Array[Cell]()

  def getCells = cells
  
  def isWrongArgument = _wrongArgumet;

  var _gameOver = false
  def gameOver = _gameOver = true
  def isGameOver = _gameOver
  
  def getRows = rows
  def getCols = columns
  def getMines = mines
  
  var _gameId = 0;
  
  def getGameId = _gameId
  
  def setGameId(id: Int) = _gameId = id

}


