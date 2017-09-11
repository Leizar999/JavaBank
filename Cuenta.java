package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import daw.com.Teclado;

public abstract class Cuenta {
	private TreeMap<String,Cliente> clientes;
	private String cuenta;
	private float saldo;
	private boolean existe;
	
	public Cuenta(){
		clientes = new TreeMap<String,Cliente>();
		cuenta = "";
		saldo = 0;
		existe = false;
	}
	public Cuenta(String cuenta, float saldo){
		this.cuenta = cuenta;
		setSaldo(saldo);
	}
	public Cuenta(Cuenta copia){
		this.clientes = (TreeMap<String,Cliente>)copia.clientes.clone();
		this.cuenta = copia.cuenta;
		this.saldo = copia.saldo;
	}
	public void setCuenta(String cuenta){
		this.cuenta = cuenta;
	}
	public String getCuenta(){
		return cuenta;
	}
	public void setSaldo(float saldo){
		this.saldo = saldo;
	}
	public float getSaldo(){
		return saldo;
	}
	public boolean isExiste() {
		return existe;
	}
	public void setExiste(boolean existe) {
		this.existe = existe;
	}
	public void leerCuenta(){
		this.cuenta = Teclado.leerString("Número de cuenta: ");
	}
	public void ingresarDinero(float dinero){
		this.saldo = this.saldo + dinero;
	}
	public void retirarDineroPersonal(float dinero){
		float aval = 0;
		aval = aval - (calcularAval()/ 2);
		saldo = saldo - dinero;
		if(saldo < aval){
			saldo = aval;
			System.out.println("El saldo mínimo que puede haber en la cuenta es: " + aval);
		}
	}
	public abstract void retirarDinero(float dinero);
	
	public abstract boolean hacerTransferenciaOrigen(float dinero);
	
	public abstract void hacerTransferenciaDestino(float dinero);

	public float calcularAval(){
		float aval = 0;
		for(Cliente cliente:clientes.values()){
			aval = aval + cliente.getAval();
		}
		return aval;
	}
	public void leerDatos(){
		Cliente cliente;
		String seguir;
		do{
			cliente = new Cliente();
			cliente.leerNif();
			if(clientes.containsKey(cliente.getNif())){
				System.out.println("El cliente ya existe");
			}else{
				cliente.leerDatos();
				clientes.put(cliente.getNif(), cliente);
			}
			seguir = Teclado.leerString("Desea continuar?(s/n): ");
		}while(seguir.equals("s"));
		
		leerCuenta();
		if(!existe){
			setSaldo(Teclado.leerFloat("Saldo de la cuenta: "));
		}
	}
	public void mostrarDatos(){
		for(Cliente cliente:clientes.values()){
			cliente.mostrarDatos();
		}
		System.out.println("El número de cuenta es: " + cuenta);
		System.out.println("El saldo de la cuenta es: " + saldo);
	}
	public void escribirFichero(DataOutputStream filtro)throws IOException{
		for(Cliente cliente:clientes.values()){
			cliente.escribirFichero(filtro);
		}
		filtro.writeBytes(cuenta + "\n");
		filtro.writeFloat(saldo);
	}
	@SuppressWarnings("deprecation")
	public void leerFichero(DataInputStream filtro2)throws IOException{
		Cliente cliente = new Cliente();
		cliente.leerFichero(filtro2);
		this.cuenta = filtro2.readLine();
		setSaldo(filtro2.readFloat());
		clientes.put(cliente.getNif(), cliente);
	}
}
