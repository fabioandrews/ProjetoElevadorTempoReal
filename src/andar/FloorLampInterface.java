package andar;

public class FloorLampInterface 
{
	private boolean visorAndarAtualEstahLigado;
	private int andar; //o que o visor estah mostrando? um numero do andar atual em que ele esta parado
	
	public FloorLampInterface()
	{
		visorAndarAtualEstahLigado = false;
		andar = 0;
	}
	
	public void desligarVisorAndarAtual()
	{
		visorAndarAtualEstahLigado = false;
	}
}
