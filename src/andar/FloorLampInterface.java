package andar;

import com.example.projetoelevadortemporeal.FachadaInterfaceGrafica;

public class FloorLampInterface 
{
	private boolean visorAndarAtualEstahLigado;
	private int andar; //o que o visor estah mostrando? um numero do andar atual em que ele esta parado
	
	public FloorLampInterface()
	{
		visorAndarAtualEstahLigado = false;
		andar = 0;
	}
	
	public void desligarVisorAndarAtual(int andarAtual, int idElevador)
	{
		visorAndarAtualEstahLigado = false;
		FachadaInterfaceGrafica.getInstance().desligarVisorAndarAtual(andarAtual, idElevador);
	}
}
