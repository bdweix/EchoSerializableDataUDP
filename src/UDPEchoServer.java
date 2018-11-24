
/*
 * This is the SERVER waiting for Datagram (UDP) packets from a client
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object and receives Serializable Object
 */

import java.io.IOException;

public class UDPEchoServer {

  private static int serverPort = 8009;
  private static serverNetworkUtils networkUtils = null;

  public static void main(String[] args) {
    initializeNetwork();
    receiveRequests();
  }

  public static void initializeNetwork() {
    try {
      networkUtils = new serverNetworkUtils(serverPort);
      networkUtils.createServerSocket();
      networkUtils.setReceivePacket();
      System.out.println("I am an Echo Server and I am waiting on port # " + serverPort);
    } catch(IOException e) {
      System.out.println("Exception thrown trying to initialize network. Address likely already in use." );
      System.out.println(e);
    }
  }

  public static void receiveRequests(){
    try {
      echoData receivedSerializedData = networkUtils.getSerializedEchoData();
      printReceivedData(receivedSerializedData);
      String modifiedData = modifyData(receivedSerializedData);
      sendData(modifiedData, receivedSerializedData);
      receiveRequests();
    } catch(IOException | ClassNotFoundException e){
      System.out.println("Exception thrown trying to receive requests." );
      System.out.println(e);
    }
  }

  public static void sendData(String modifiedData, echoData receivedSerializedData) {
    try {
      echoData echoSerializedData = new echoData(receivedSerializedData.getName(), modifiedData);
      networkUtils.sendSerializedEchoData(echoSerializedData);
    } catch(IOException | ClassNotFoundException e){
      System.out.println("Exception thrown trying to send data." );
      System.out.println(e);
    }
  }

  public static void printReceivedData(echoData receivedSerializedData){
    networkUtils.serverPrint();
    System.out.println("Client name: " + receivedSerializedData.getName() + " Sent: " + receivedSerializedData.getData());
  }

  public static String modifyData(echoData receivedSerializedData){
    return receivedSerializedData.getData().toUpperCase();
  }

}
