package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PruebaCliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PruebaCliente aplicacion = new PruebaCliente();
		aplicacion.run();
	}
	public void run(){
		Cliente cliente;
		FileOutputStream bruto;
		DataOutputStream filtro;
		
		FileInputStream bruto2;
		DataInputStream filtro2;
		
		try{
			cliente = new Cliente();
			bruto = new FileOutputStream("cliente.txt");
			filtro = new DataOutputStream(bruto);
			cliente.leerNif();
			cliente.leerDatos();
			cliente.escribirFichero(filtro);
			
			filtro.close();
			bruto.close();
		}catch(IOException e){
			System.out.println("Error de escritura");
		}
		
		try{
			cliente = new Cliente();
			bruto2 = new FileInputStream("cliente.txt");
			filtro2 = new DataInputStream(bruto2);
			cliente.leerFichero(filtro2);
			cliente.mostrarDatos();
			
		}catch(IOException e){
			System.out.println("Error de lectura");
		}
	}
}
