
/*
 * This is an example of a CLIENT conversing with a server
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object and receives an Serializable Object
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class UDPEchoClient {

  private static int serverPort = 8009;
  private static InetAddress serverAddress = null;
  private static BufferedReader keyboardInput = null;
  private static clientNetworkUtils networkUtils = null;
  private static String name = null;

  public static void main(String[] args) {
    initializeKeyboard();
    initializeNetwork();
    requestName();
    requestData();
  }

  private static void initializeKeyboard() {
    keyboardInput = new BufferedReader(new InputStreamReader(System.in));
  }

  private static void initializeNetwork() {
    try {
      serverAddress = InetAddress.getByName("127.0.0.1");
      networkUtils = new clientNetworkUtils( serverAddress, serverPort); //create a connection to a server
      networkUtils.createClientSocket();
    } catch(IOException e) {
      System.out.println("Exception thrown trying to initialize network. Address likely already in use." );
      System.out.println(e);
    }
  }

  private static void requestName() {
    try {
      System.out.print("Enter your name: ");
      name = keyboardInput.readLine();
    } catch(IOException e){
      System.out.println("Exception thrown trying to request name." );
      System.out.println(e);
    }

  }

  private static void requestData() {
    String inputLine;
    System.out.print("Enter some data: ");
    try {
      inputLine = keyboardInput.readLine();
      checkSystemExit(inputLine);
      sendData(inputLine);
      requestData();
    } catch(IOException e){
      System.out.println("Exception thrown trying to request data." );
      System.out.println(e);
    }
  }

  private static void checkSystemExit(String inputLine) {
    if (inputLine.equals("exit")){
      System.out.println("Closing socket and exiting function...");
      networkUtils.closeSocket();
      System.exit(0);
    }
  }

  private static void sendData(String inputLine) {
    echoData toSend = new echoData(name, inputLine);
    try {
      System.out.println(toSend.toString());
      networkUtils.sendSerializedEchoData(toSend);
      receiveData();
    } catch(IOException | ClassNotFoundException e){
      System.out.println("Exception thrown trying to send data." );
      System.out.println(e);
    }
  }

  private static void receiveData() {
    try {
      echoData toReceive = networkUtils.getSerializedEchoData();
      printReceivedData(toReceive);
    } catch(IOException | ClassNotFoundException e){
      System.out.println("Exception thrown trying to receive data." );
      System.out.println(e);
    }
  }

  private static void printReceivedData(echoData toReceive) {
    System.out.println("From Server: the size of the string is " + toReceive.getData().length());
    System.out.println("From Server: the data string is--> " + toReceive.getData());
    System.out.println("From Server: the name is--> " + toReceive.getName());
  }

}
