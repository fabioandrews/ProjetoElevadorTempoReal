package elevador;

public class MotorInterface 
{
	private boolean elevadorEmMovimento;
	private ElevatorControl controleDoElevador;
	

	public MotorInterface(ElevatorControl controleDoElevador)
	{
		this.controleDoElevador = controleDoElevador;
		elevadorEmMovimento = false;
	}
	
	
	
	public synchronized boolean getElevadorEmMovimento() {
		return elevadorEmMovimento;
	}


	


	public void subirElevador()
	{
		elevadorEmMovimento = true;
		TaskMovimentaElevador taskMoveElevador = new TaskMovimentaElevador(controleDoElevador, this, "sobe");
		taskMoveElevador.execute("");
		
	}
	
	public void descerElevador()
	{
		elevadorEmMovimento = true;
		TaskMovimentaElevador taskMoveElevador = new TaskMovimentaElevador(controleDoElevador, this, "desce");
		taskMoveElevador.execute("");
		
	}
	
	public void pararElevador()
	{
		elevadorEmMovimento = false;
	}

}
