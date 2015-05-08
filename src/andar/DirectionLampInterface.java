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
	
	public void ligarVisorSobeEDesceEEmQualDirecao(String sobeOuDesceOuParado)
	{
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
		this.visorSobeDesceDoAndarEstahLigado = true;
	}

}
