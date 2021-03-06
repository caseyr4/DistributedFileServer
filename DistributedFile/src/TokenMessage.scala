/**
 * Created by Ross on 23/12/15.
 */
class TokenMessage(ticket: String, sessionKey: String, serverIP: String, serverPort: String, expirationTime: String) extends ServerMessage {
  override def toString: String = {
    "TICKET: " + ticket + "\n" +
      "SESSION_KEY: " + sessionKey + "\n" +
      "SERVER_IP: " + serverIP + "\n" +
      "SERVER_PORT: " + serverPort + "\n" +
      "EXPIRATION_TIME: " + expirationTime + "\n"
  }

  def getTicket: String = {
    ticket
  }

  def getSessionKey: String = {
    sessionKey
  }

  def getServerIP: String = {
    serverIP
  }

  def getServerPort: String = {
    serverPort
  }

  def getExpirationTime: String = {
    expirationTime
  }
}
