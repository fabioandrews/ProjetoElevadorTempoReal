package andar;

public class FloorLampInterface 
{
	private boolean visorSubindoEDescendoNoAndarEstahLigado;
	private String sobeOuDesce; //o que o visor estah mostrando? uma setinha de sobe ou desce?
	
	public FloorLampInterface()
	{
		visorSubindoEDescendoNoAndarEstahLigado = false;
		sobeOuDesce = "sobe";
	}
	
	public void desligarVisorSubindoEDescendoNoAndar()
	{
		visorSubindoEDescendoNoAndarEstahLigado = false;
	}
}
