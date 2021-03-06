import java.net.{InetAddress, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}
import java.nio.file.{Files, Paths}
import java.io.File
import scala.collection.mutable.ArrayBuffer


/**
 * Created by Ross on 10/11/15.
 */

//functionality interface offered by server
trait ServerUtility {
  def getIP: String
  def getPort: String
  def execute(x: Runnable): Unit
  def killServer(): Unit
  def getAuthenticationServer: NodeAddress
  def getKey: String
  def getUsername: String
  def getPassword: String
}



object DirectoryServer extends ServerUtility {

  var serverSocket: ServerSocket = null
  var threadPool: ExecutorService = null
  var portNumber: Int = -1

  var authenticationServer: NodeAddress = null

  var password: String = "12345678"
  var username: String = "rcasey"
  var key: String = "SuperSecretPassphrase"


  def main(args: Array[String]) {
    //attempt to create a server socket, exit otherwise
    startServer(args)
    LookupHandler.serverUtility = this


    var exit = false
    while(!exit) {
      try {
        val s = serverSocket.accept()
        println("Connection accepted")


        val newConnection = new Connection(computeNextId(), s)
        execute(new ConnectionInputListener(newConnection, this))

      } catch {
        case e: Exception => {
          exit = true
          threadPool.shutdown()
          println("Killing server...")
        }
      }
    }
    System.exit(0)
  }


  /**
   * Attempts to start server with specified parameters
   * @param args - command line arguments
   */
  def startServer(args: Array[String]): Unit = {
    try {
      portNumber = Integer.parseInt(args(0))
      serverSocket = new ServerSocket(portNumber)
      threadPool = Executors.newFixedThreadPool(256)

      username = args(1)
      password = args(2)


      authenticationServer = new NodeAddress(args(3), args(4))

      println("Directory server listening on port " + portNumber + ": ")
    } catch {
      case e: Exception => {
        println("SERVER ERROR: " + e.getClass + ": " + e.getMessage)
        System.exit(0)
      }
    }
  }


  /**
   * @return IP address of server
   */
  def getIP: String = {
    "178.62.123.87" //server cannot get its own IP due to NAT
  }


  /**
   * @return Port server is running on
   */
  def getPort: String = {
    serverSocket.getLocalPort.toString
  }


  /**
   * Executes a thread on server's thread pool
   * @param job - thread to execute
   */
  def execute(job: Runnable): Unit = {
    threadPool.execute(job)
  }


  /**
   * Kills the server
   */
  def killServer(): Unit = {
    serverSocket.close()
  }


  /**
   * Computes the next unique ID for a connection
   */
  var connectionId = 0
  private def computeNextId(): Int = {
    val newId = connectionId
    connectionId += 1
    newId
  }


  def getAuthenticationServer: NodeAddress = {
    authenticationServer
  }


  def getPassword: String = {
    password
  }


  def getUsername: String = {
    username
  }


  def getKey: String = {
    key
  }
}
