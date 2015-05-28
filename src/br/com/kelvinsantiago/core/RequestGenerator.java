package br.com.kelvinsantiago.core;
import java.util.Random;

public class RequestGenerator {
  
  public String dominio = "www.kelvinsantiago.com.br";
  public String tipoQuery;

  private byte[] data;
  
  public RequestGenerator(String dominio, String tipoQuery) {
    this.dominio = dominio;
    this.tipoQuery = tipoQuery;
    this.data = new byte[1024];
  }
  
  
  public byte[] build() {
    /* HEADER DNS */
	// Gerando ID Randomicamente. 
    byte[] messageID = new byte[2];
    new Random().nextBytes(messageID);
    data[0] = messageID[0];
    data[1] = messageID[1];
    
    /* FLAG */
    data[2] = 0x01;
    data[3] = 0x00;

    /* QUESTION */
    data[4] = 0x00;
    data[5] = 0x01;
    
    /* ANSWER */
    data[6] = 0x00;
    data[7] = 0x00;
    
    /* AUTHORITY */
    data[8] = 0x00;
    data[9] = 0x00;
    
    /* ADDITIONAL */
    data[10] = 0x00;
    data[11] = 0x00;
    
    /* QUERY */
    String[] parteDominio = dominio.split("\\.");
    int k = 0;
    
    byte[] bufferDominio = new byte[dominio.length()+2];

    /* Lendo dominio */
    for (int i = 0; i < parteDominio.length; i++) {
      String parte = parteDominio[i];
      bufferDominio[i+k] = (byte) parte.length();
      
      for (char c : parte.toCharArray()) {
        k++;
        bufferDominio[i+k] = (byte) c;
      }
    }
    int bytesBuffer = bufferDominio.length;
    
    for (int i = 0; i < bufferDominio.length-1; i++) {
      data[12+i] = bufferDominio[i];
    }

    /* 2 bytes para tipo da query */
    if (tipoQuery.equalsIgnoreCase("A")) {
      data[12+bytesBuffer] = 0x00;
      data[12+bytesBuffer+1] = 0x01;
    } else if (tipoQuery.equalsIgnoreCase("MX")) {
      data[12+bytesBuffer] = 0x00;
      data[12+bytesBuffer+1] = 0x0F;
    }    
    
    /* QUERY CLASS */
    data[12+bytesBuffer+2] = 0x00;
    data[12+bytesBuffer+3] = 0x01;
    
    return data;
  }
  
  
  
  
}
