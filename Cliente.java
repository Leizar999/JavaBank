package sucursalBancaria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class Cliente {
	private String nif;
	private String nombre;
	private String telefono;
	private float aval;
	
	public Cliente(){
		nif = "";
		nombre = "";
		telefono = "";
		aval = 0;
	}
	public Cliente(String nif, String nombre, String telefono, float aval){
		this.nif = nif;
		this.nombre = nombre;
		this.telefono = telefono;
		setAval(aval);
	}
	public Cliente(Cliente copia){
		this.nif = copia.nif;
		this.nombre = copia.nombre;
		this.telefono = copia.telefono;
		this.aval = copia.aval;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public float getAval() {
		return aval;
	}
	public void setAval(float aval) {
		if(aval < 0){
			aval = 0;
		}
		this.aval = aval;
	}
	public void leerNif(){
		this.nif = Teclado.leerString("NIF del cliente: ");
	}
	public void leerDatos(){
		this.nombre = Teclado.leerString("Nombre del cliente: ");
		this.telefono = Teclado.leerString("Teléfono del cliente: ");
		setAval(Teclado.leerFloat("Aval del cliente: "));
	}
	public void mostrarDatos(){
		System.out.println("El NIF del cliente es: " + nif);
		System.out.println("El nombre del cliente es: " + nombre);
		System.out.println("El teléfono del cilente es: " + telefono);
		System.out.println("El aval del cliente es: " + aval);
	}
	public void escribirFichero(DataOutputStream filtro)throws IOException{
		filtro.writeBytes(nif + "\n");
		filtro.writeBytes(nombre + "\n");
		filtro.writeBytes(telefono + "\n");
		filtro.writeFloat(aval);
	}
	@SuppressWarnings("deprecation")
	public void leerFichero(DataInputStream filtro2)throws IOException{
		this.nif = filtro2.readLine();
		this.nombre = filtro2.readLine();
		this.telefono = filtro2.readLine();
		setAval(filtro2.readFloat());
	}
}
