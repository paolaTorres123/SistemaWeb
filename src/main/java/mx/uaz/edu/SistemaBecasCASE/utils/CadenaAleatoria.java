package mx.uaz.edu.SistemaBecasCASE.utils;

import java.util.Random;

public class CadenaAleatoria {
	private String cadenaAleatoria = "";
	public  String getCadenaAleatoria (int longitud){
		  long milis = new java.util.GregorianCalendar().getTimeInMillis();
		  Random r = new Random(milis);
		  int i = 0;
		  while ( i < longitud){
			  char c = (char)r.nextInt(255);
			  if ( (c >= '0' && c <=9) || (c >='A' && c <='Z') ){
				  cadenaAleatoria += c;
				  i ++;
			  }
		  }
		  return cadenaAleatoria;
	}
}
