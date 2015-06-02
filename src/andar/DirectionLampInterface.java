package andar;

import com.example.projetoelevadortemporeal.FachadaInterfaceGrafica;

public class DirectionLampInterface 
{
	private String sobeOuDesceOuParado;
	private boolean visorSobeDesceDoAndarEstahLigado;
	
	public DirectionLampInterface()
	{
		this.sobeOuDesceOuParado = "parado";
		this.visorSobeDesceDoAndarEstahLigado = false;
	}
	
	public void desligarVisorSobeEDesceDoAndar(int numAndarDesligar, int idElevador)
	{
		this.visorSobeDesceDoAndarEstahLigado = false;
		FachadaInterfaceGrafica.getInstance().desligarVisorSobeDesceDoAndar(numAndarDesligar, idElevador);
	}
	
	public void ligarVisorSobeEDesceEEmQualDirecao(String sobeOuDesceOuParado,int numAndarLigar, int idElevador)
	{
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
		this.visorSobeDesceDoAndarEstahLigado = true;
		
		FachadaInterfaceGrafica.getInstance().ligarVisorSobeDesceDoAndar(sobeOuDesceOuParado,numAndarLigar, idElevador);
	}

}
