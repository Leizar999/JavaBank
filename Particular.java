package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class Particular extends Cuenta {
	private boolean tarjeta;
	private boolean sePuede;
	
	public Particular(){
		super();
		tarjeta = false;
		sePuede = true;
	}
	public Particular(String titular, String cuenta, float saldo, boolean tarjeta){
		super(cuenta,saldo);
		this.tarjeta = tarjeta;
	}
	public Particular(Particular copia){
		this.tarjeta = copia.tarjeta;
	}
	public boolean isTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(boolean tarjeta) {
		this.tarjeta = tarjeta;
	}
	public boolean isSePuede() {
		return sePuede;
	}
	public void setSePuede(boolean sePuede) {
		this.sePuede = sePuede;
	}
	public void leerDatos(){
		super.leerDatos();
		String pregunta;
		if(!super.isExiste()){
			
			do{
				pregunta = Teclado.leerString("Tiene tarjeta? (s/n): ");
			}while(pregunta.equals("s") && pregunta.equals("n"));
			
			if(pregunta.equals("s")){
				this.tarjeta = true;
			}else{
				this.tarjeta = false;
			}
		}
	}
	public void retirarDinero(float dinero){
		float aval = 0;
		float disponible;
		aval = aval - (super.calcularAval() / 2);
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
		float comision = (float) ((dinero * 0.2) / 100);
		if(comision > 4){
			comision = 4;
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
		System.out.println("      Cuenta particular: ");
		System.out.println("------------------------------");
		super.mostrarDatos();
		if(this.tarjeta == true){
			System.out.println("La cuenta tiene tarjeta asociada");
		}else{
			System.out.println("La cuenta no tiene tarjeta asociada");
		}
	}
	public void escribirFichero(DataOutputStream filtro)throws IOException{
		super.escribirFichero(filtro);
		filtro.writeBoolean(tarjeta);
	}
	public void leerFichero(DataInputStream filtro2)throws IOException{
		super.leerFichero(filtro2);
		this.tarjeta = filtro2.readBoolean();
	}
}
