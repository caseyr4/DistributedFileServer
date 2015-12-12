import java.io.PrintStream
import java.net._

import scala.io.BufferedSource

/**
 * Created by Ross on 10/11/15.
 */
class Connection() {

  private var in: Iterator[String] = null
  private var out: PrintStream = null
  private val id: Int = -1
  private var socket: Socket = null


  def this(id: Int, socket: Socket) {
    this()
    this.socket = socket

    in = new BufferedSource(socket.getInputStream()).getLines()
    out = new PrintStream(socket.getOutputStream())
  }

  /**
   * Gets the next line of input from the user
   * @return the next line of input sent by the user
   */
  def nextLine(): String = {
    val message = in.next()
    println("RECEIVED on "  + id + ": " + message)
    message
  }


  /**
   * Send a message to the user
   * @param message - message to send
   */
  def sendMessage(message: ServerMessage): Unit = {
    out.print(message.toString)
    out.flush()
    println("SENDING on " + id + ": " + message)
  }


  /**
   * Send error to user, remove from its from server records and close connection.
   * @param error - error to send
   */
  def sendError(error: Error): Unit = {
    out.print(error.toString)
    out.flush()
  }


  /**
   * Determines whether it is possible to read a message from the user
   * @return whether or not a message exists
   */
  def hasMessage: Boolean = {
    in.hasNext
  }

  /**
   * Returns the ID of the connection
   */
  def getId: Int = {
    id
  }


  /**
   * Returns whether the TCP connection is still active
   */
  def isConnected: Boolean = {
    socket.isConnected
  }


  def sendBytes(bytes: Array[Byte]): Unit = {
    out.write(bytes)
    out.flush()
  }
}
