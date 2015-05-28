package br.com.kelvinsantiago.core;

import java.util.Scanner;

public class DNSClient {
  
  public static String SERVIDOR_DNS = "";
  public static String dominio = "";
  public static String tipoQuery = "";
  public static int opcaoMenu;
  public static int port = 53; /* port for sending DNS requests */
  public static Scanner inputOpcao;
  public static Scanner inputDominio;
  
  public static void main (String[] args) {
	  
	  inputOpcao = new Scanner(System.in);
	  inputDominio = new Scanner(System.in);
	  
      SERVIDOR_DNS = "8.8.8.8"; // IP do servidor de DNS
      
      System.out.println("--------------- DNS --------------");
      System.out.println("1.Obter IP a partir de um domínio ");
      System.out.println("2.Obter MX a partir de um domínio ");
      System.out.print("Escolha a opcao: ");
      opcaoMenu = inputOpcao.nextInt();
      
      System.out.print("Insira o dominio: ");
      
      dominio = inputDominio.nextLine();
      
      if(opcaoMenu == 1){
    	  tipoQuery = "A";
      }else{
    	  tipoQuery = "MX";
      }
      
      ClienteUDP client = new ClienteUDP(SERVIDOR_DNS, port, dominio, tipoQuery);
      RequestGenerator request = new RequestGenerator(dominio, tipoQuery);
      
      try {
        byte[] responseData = client.conectar(request.build());
        new ResponseHandler(responseData, dominio, tipoQuery);
      } catch (Exception e) {
        e.printStackTrace();
      }
      
}
  

  
  
  
  
  
}
