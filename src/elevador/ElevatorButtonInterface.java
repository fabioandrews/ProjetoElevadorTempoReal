package elevador;

public class ElevatorButtonInterface 
{
	private ElevatorManager elevatorManager;
	
	public ElevatorButtonInterface(ElevatorManager elevatorManager)
	{
		this.elevatorManager = elevatorManager;
	}
	
	/*ESSE EH O COMECO DO CASO DE USO SELECIONAR DESTINO*/
	public void pressionouBotaoDoAndar(int numAndar)
	{
		elevatorManager.agendarAndarApospressionouBotaoDoAndar(numAndar);
		
	}

}
