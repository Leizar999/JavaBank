package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class PruebaCuenta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PruebaCuenta aplicacion = new PruebaCuenta();
		aplicacion.run();
	}
	public void run(){
		int pregunta;
		Cuenta cuenta;
		FileOutputStream bruto;
		DataOutputStream filtro;
		
		FileInputStream bruto2;
		DataInputStream filtro2;
		
		do{
			pregunta = Teclado.leerInt("1. Particular 2. Empresa");

		}while(pregunta < 1 || pregunta > 2);
		
		if(pregunta == 1){
			cuenta = new Particular();
		}else{
			cuenta = new Empresa();
		}
		
		try{
			bruto = new FileOutputStream("cuenta.txt");
			filtro = new DataOutputStream(bruto);
			cuenta.leerDatos();
			cuenta.escribirFichero(filtro);
			
			filtro.close();
			bruto.close();
		}catch(IOException e){
			System.out.println("Error de escritura");
		}
		
		try{
			bruto2 = new FileInputStream("cuenta.txt");
			filtro2 = new DataInputStream(bruto2);
			cuenta.leerFichero(filtro2);
			cuenta.mostrarDatos();
			
		}catch(IOException e){
			System.out.println("Error de lectura");
		}
	}
}
