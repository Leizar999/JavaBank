package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class Empresa extends Cuenta{
	private String nombre;
	private String cif;
	private String local;
	private boolean sePuede;
	
	public Empresa(){
		nombre = "";
		cif = "";
		local = "Propio";
		sePuede = true;
	}
	public Empresa(String titular, String cuenta, float saldo, String nombre, String cif, String local){
		super(cuenta,saldo);
		this.nombre = nombre;
		this.cif = cif;
		this.local = local;
	}
	public Empresa(Empresa copia){
		this.nombre = copia.nombre;
		this.cif = copia.cif;
		this.local = copia.local;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public void leerCif(){
		this.cif = Teclado.leerString("CIF de la empresa: ");
	}
	public boolean isSePuede() {
		return sePuede;
	}
	public void setSePuede(boolean sePuede) {
		this.sePuede = sePuede;
	}
	public void leerDatos(){
		
		super.leerDatos();
		if(!super.isExiste()){
			leerCif();
			this.nombre = Teclado.leerString("Nombre de la empresa: ");
			do{
				this.local = Teclado.leerString("Local? (1. Propio 2. Alquilado): ");
			}while(local.equals("1") && local.equals("2"));
			if(local.equals("1")){
				this.local = "Propio";
			}else{
				this.local = "Alquilado";
			}
		}
	}
	public void retirarDinero(float dinero){
		float aval = 0;
		float disponible;
		aval = aval - (super.calcularAval() * 2);
		disponible = super.getSaldo() - aval;
		if(disponible < dinero){
			sePuede = false;
			System.out.println("Saldo insuficiente en cuenta!");
		}else{
			sePuede = true;
			super.setSaldo(super.getSaldo() - dinero);
			System.out.println("La cantidad que puedes disponer es: " + (disponible - dinero));
		}
	}
	public boolean hacerTransferenciaOrigen(float dinero){
		boolean sePuede = true;
		float comision = (float) ((dinero * 0.1) / 100);
		if(comision > 6){
			comision = 6;
		}
		retirarDinero(dinero);
		super.setSaldo(super.getSaldo()-comision);
		sePuede = this.sePuede;
		System.out.println("La comisión cobrada es: " + comision);
		System.out.println("El nuevo saldo en cuenta es: " + super.getSaldo());
		return sePuede;
	}
	public void hacerTransferenciaDestino(float dinero){
		super.setSaldo(super.getSaldo() + dinero);
	}
	public void mostrarDatos(){
		System.out.println("------------------------------");
		System.out.println("     Cuenta de Empresa: ");
		System.out.println("------------------------------");
		super.mostrarDatos();
		System.out.println("El CIF de la empresa es: " + cif);
		System.out.println("El nombre de la empresa es: " + nombre);
		System.out.println("El local es: " + local);
	}
	public void escribirFichero(DataOutputStream filtro)throws IOException{
		super.escribirFichero(filtro);
		filtro.writeBytes(cif + "\n");
		filtro.writeBytes(nombre + "\n");
		filtro.writeBytes(local + "\n");
	}
	@SuppressWarnings("deprecation")
	public void leerFichero(DataInputStream filtro2)throws IOException{
		super.leerFichero(filtro2);
		this.cif = filtro2.readLine();
		this.nombre = filtro2.readLine();
		this.local = filtro2.readLine();
	}
}
