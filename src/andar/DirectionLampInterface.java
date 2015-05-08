package andar;

public class DirectionLampInterface 
{
	private String sobeOuDesceOuParado;
	private boolean visorSobeDesceDoAndarEstahLigado;
	
	public DirectionLampInterface()
	{
		this.sobeOuDesceOuParado = "parado";
		this.visorSobeDesceDoAndarEstahLigado = false;
	}
	
	public void desligarVisorSobeEDesceDoAndar()
	{
		this.visorSobeDesceDoAndarEstahLigado = false;
	}

}
