package escalonador;

import java.util.LinkedList;

import elevador.ElevatorManager;

public class SingletonInterfaceEscalonador 
{
	private static SingletonInterfaceEscalonador instancia;
	private AgendadorDeElevadores agendadorDeElevadores;
	private LinkedList<ElevatorManager> elevatorManagers;
	
	private SingletonInterfaceEscalonador()
	{
		agendadorDeElevadores = new AgendadorDeElevadores(elevatorManagers);
	}
	
	
	



	public void setElevatorManagers(LinkedList<ElevatorManager> elevatorManagers) {
		this.elevatorManagers = elevatorManagers;
		agendadorDeElevadores.setElevadores(elevatorManagers);
	}



	public static SingletonInterfaceEscalonador getInstancia()
	{
		if(instancia == null)
		{
			
			instancia = new SingletonInterfaceEscalonador();
		}
		
		return instancia;
	}
	
	public void solicitarQueUmElevadorPareNoAndar(int andar, String desceOuSobe)
	{
		this.agendadorDeElevadores.escolherElevadorParaEnviarParaOAndar(andar, desceOuSobe);
	}

}
