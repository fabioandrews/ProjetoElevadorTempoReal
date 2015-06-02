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
		this.activityTelaElevador.desligarVisorAndarAtual(andarAtual, idElevador);
	}
	public void desligarVisorSobeDesceDoAndar(int numAndarDesligar, int idElevador)
	{
		this.activityTelaElevador.desligarVisorSobeDesceDoAndar(numAndarDesligar, idElevador);
	}
	
	//esse processo acontece assim que o ElevatorControl comeca sua funcao elevadorChegouNoAndar
	public void atualizarInterfaceElevadorChegouNumAndar(int andar,int idElevador, String elevadorSobeOuDesce)
	{
		this.activityTelaElevador.atualizarInterfaceElevadorChegouNumAndar(andar,idElevador, elevadorSobeOuDesce);
	}
	
	
	public void ligarVisorSobeDesceDoAndar(String sobeOuDesceOuParado,int numAndarLigar, int idElevador)
	{
		this.activityTelaElevador.ligarVisorSobeDesceDoAndar(sobeOuDesceOuParado,numAndarLigar, idElevador);
	}
	
	//assim que o elevador para, o background de todos os elevadores volta ao normal
	public void pararElevador(int andar, int idElevador)
	{
		this.activityTelaElevador.pararElevador(andar, idElevador);
	}

}
