package sucursalBancaria;

public class Temporizador extends Thread{
	private int seconds;
	private int minutes;
	private int hours;
	private JavaBank aplicacion;
	
	public Temporizador(JavaBank aplicacion){
		seconds = 0;
		minutes = 0;
		hours = 0;
		this.aplicacion = aplicacion;
	}
	public void run(){
		while(true){
			try{
				sleep(1000);
				seconds++;
			}catch(InterruptedException e){
				System.out.println("Se paró");
			}	
		}
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public void escribirTiempo(){
		while(seconds > 59){
			minutes ++;
			seconds -= 60;
		}
		while(minutes > 59){
			hours++;
			minutes -= 60;
		}
		System.out.print(hours + ":" + minutes + ":" + seconds);
	}
	public void guardarFichero(){
		if(seconds % 10 == 0){
			aplicacion.guardarFichero();
			System.err.println("Guardando...");
		}
	}
}
