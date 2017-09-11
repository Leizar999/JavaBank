package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import daw.com.Teclado;

public class JavaBank {
	private TreeMap<String,Cuenta>cuentas;
	private static Temporizador tempus;
	public JavaBank(){
		cuentas = new TreeMap<String,Cuenta>();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JavaBank aplicacion = new JavaBank();
		tempus = new Temporizador(aplicacion);
		aplicacion.run(tempus);
	}
	public void run(Temporizador tempus){
		tempus.start();
		leerFichero();
		int opc;
		do{
			opc = mostrarMenu();
			evaluarOpc(opc);
			tempus.guardarFichero();
		}while(opc!=7);
	}
	public int mostrarMenu(){
		int opc;
		System.out.println("------------------------------");
		System.out.println("    Bienvenido a JavaBank");
		System.out.println("------------------------------");
		System.out.println("1. Crear cuenta");
		System.out.println("2. Ingresar dinero");
		System.out.println("3. Retirar dinero");
		System.out.println("4. Realizar transferencia");
		System.out.println("5. Consultar saldo de cuenta");
		System.out.println("6. Consultar saldo del banco");
		System.out.println("9. Mostrar TreeMap");
		System.out.println("7. Salir");
		System.out.println();
		opc = Teclado.leerInt("Elige una opción: ");
		return opc;
	}
	public void evaluarOpc(int opc){
		switch(opc){
		case 1:
			crearCuenta();
			break;
		case 2:
			ingresarDinero();
			break;
		case 3:
			retirarDinero();
			break;
		case 4:
			hacerTransferencia();
			break;
		case 5:
			consultarSaldo();
			break;
		case 6:
			saldoBanco();
			break;
		case 7:
			salir();
			break;
		case 9:
			mostrarTree();
		}
	}
	public void crearCuenta(){
		int pregunta;
		Cuenta cuenta;
		
		do{
			pregunta = Teclado.leerInt("1. Particular 2. Empresa");

		}while(pregunta < 1 || pregunta > 2);
		
		if(pregunta == 1){
			cuenta = new Particular();
		}else{
			cuenta = new Empresa();
		}

		do{
			cuenta.leerDatos();
			if(cuentas.containsKey(cuenta.getCuenta())){
				cuenta.setExiste(true);
				System.out.println("La cuenta ya existe! repita por favor: ");
			}else{
				cuenta.setExiste(false);
				cuentas.put(cuenta.getCuenta(), cuenta);
			}
		}while(cuenta.isExiste());
	}
	public void ingresarDinero(){
		String cuenta;
		float dinero;
		
		cuenta = Teclado.leerString("Número de cuenta para ingresar: ");
		if(cuentas.containsKey(cuenta)){
			dinero = Teclado.leerFloat("Dinero a ingresar?: ");
			cuentas.get(cuenta).ingresarDinero(dinero);
		}else{
			System.out.println("La cuenta no existe!");
		}
		System.out.println("El saldo actual es: " + cuentas.get(cuenta).getSaldo());
	}
	public void retirarDinero(){
		String cuenta;
		float dinero;
		
		cuenta = Teclado.leerString("Número de cuenta para retirar: ");
		if(cuentas.containsKey(cuenta)){
			dinero = Teclado.leerFloat("Dinero a retirar?: ");
			cuentas.get(cuenta).retirarDinero(dinero);
		}else{
			System.out.println("La cuenta no existe!");
		}
	}
	public void hacerTransferencia(){
		String origen = Teclado.leerString("Cuenta de origen: ");
		String destino;
		float dinero;
		if(cuentas.containsKey(origen)){
			destino = Teclado.leerString("Cuenta de destino: ");
			if(cuentas.containsKey(destino)){
				dinero = Teclado.leerFloat("Dinero a transferir: ");
				if(cuentas.get(origen).hacerTransferenciaOrigen(dinero)){
					cuentas.get(destino).hacerTransferenciaDestino(dinero);
				}else{
					System.out.println("La transferencia no se pudo realizar!");
				}
			}else{
				System.out.println("Cuenta de destino no existe!");
			}
		}else{
			System.out.println("Cuenta de origen no existe!");
		}
	}
	public void consultarSaldo(){
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy/hh:mm");
		String cuenta = Teclado.leerString("Número de cuenta a consultar: ");
		if(cuentas.containsKey(cuenta)){
			cuentas.get(cuenta).mostrarDatos();
		}
		System.out.println("La fecha es: " + fecha.format(new Date()));
	}
	public void saldoBanco(){
		float saldo = 0;
		for(Cuenta cuenta:cuentas.values()){
			System.out.println("Nº: " + cuenta.getCuenta() + " saldo: " + cuenta.getSaldo());
			saldo = saldo + cuenta.getSaldo();
		}
		System.out.println("El saldo del banco es: " + saldo);
	}
	public void salir(){
		guardarFichero();
		System.out.print("Tiempo de uso del programa: ");
		tempus.stop();
		tempus.escribirTiempo();
	}
	public void mostrarTree(){
		for(Cuenta cuenta:cuentas.values()){
			cuenta.mostrarDatos();
		}
	}
	public void guardarFichero(){
		FileOutputStream bruto;
		DataOutputStream filtro;
		
		try{
			bruto = new FileOutputStream("cuentas.dat");
			filtro = new DataOutputStream(bruto);
			for(Cuenta cuenta:cuentas.values()){
				if(cuenta.getClass().getSimpleName().equals("Particular")){
					filtro.writeBytes(cuenta.getClass().getSimpleName() + "\n");
				}else{
					filtro.writeBytes(cuenta.getClass().getSimpleName() + "\n");
				}
				cuenta.escribirFichero(filtro);
			}
			
			filtro.close();
			bruto.close();
		}catch(IOException e){
			System.out.println("Error de escritura");
		}
	}
	public void leerFichero(){
		FileInputStream bruto;
		DataInputStream filtro;
		Cuenta cuenta;
		
		try{
			bruto = new FileInputStream("cuentas.dat");
			filtro = new DataInputStream(bruto);
			while(filtro.available()>0){
				if(filtro.readLine().equals("Particular")){
					cuenta = new Particular();
				}else{
					cuenta = new Empresa();
				}
				cuenta.leerFichero(filtro);
				cuentas.put(cuenta.getCuenta(), cuenta);
			}
			
			filtro.close();
			bruto.close();
		}catch(IOException  e){
			e.printStackTrace();
		}
	}
}
