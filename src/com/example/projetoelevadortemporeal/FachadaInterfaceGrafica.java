package com.example.projetoelevadortemporeal;

public class FachadaInterfaceGrafica {
	private static FachadaInterfaceGrafica instanciaFachada;
	private MainActivity activityTelaElevador;
	
	private FachadaInterfaceGrafica()
	{
		
	}
	
	public static FachadaInterfaceGrafica getInstance()
	{
		if(instanciaFachada == null)
		{
			instanciaFachada = new FachadaInterfaceGrafica();
		}
		return instanciaFachada;
	}
	
	public void fecharPortaElevadorEAndar(int andarAtual, int idElevador)
	{
		this.activityTelaElevador.fecharPortaElevadorEAndar(andarAtual, idElevador);
	}
	
	public void desligarVisorAndarAtual(int andarAtual, int idElevador)
	{
		
	}
	public void desligarVisorSobeDesceDoAndar(int numAndarDesligar, int idElevador)
	{
		this.activityTelaElevador.desligarVisorSobeDesceDoAndar(numAndarDesligar, idElevador);
	}

}
