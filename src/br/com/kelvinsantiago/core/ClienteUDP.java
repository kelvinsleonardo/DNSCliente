package br.com.kelvinsantiago.core;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClienteUDP {
  public DatagramSocket clienteSocket;
  public InetAddress IPAddress;
  public byte[] resposta;
  
  public String host = "0.0.0.0";
  public String dominio = "www.kelvinsantiago.com.br";
  public String tipoQuery;
  public int porta;
  
  public ClienteUDP(String host, int porta, String dominio, String tipoQuery) {
    try {
      clienteSocket = new DatagramSocket();
    } catch (SocketException e) {
      e.printStackTrace();
    }
    this.host = host;
    this.porta = porta;
    this.dominio = dominio;
    this.tipoQuery = tipoQuery;
    this.resposta = new byte[1024];
    
    try {
      IPAddress = InetAddress.getByName(host);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
  
  
  public byte[] conectar(byte[] sendData) throws Exception {
     
     byte[] receiveData = new byte[1024];   
     
     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
     clienteSocket.send(sendPacket);
     
     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
     clienteSocket.receive(receivePacket);
     
     resposta = receivePacket.getData();
     clienteSocket.close();
     
     return resposta;
     
  }
}
