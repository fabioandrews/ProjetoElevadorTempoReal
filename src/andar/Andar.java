package andar;

public class Andar 
{
	private FloorButtonInterface interfaceBotaoAndar;
	private FloorLampInterface interfaceLampadaAndar;
	private DirectionLampInterface interfaceDirecaoSobeDesceAndar;
	private int numeroAndar;
	
	public Andar(int numeroAndar)
	{
		this.numeroAndar = numeroAndar;
		this.interfaceBotaoAndar = new FloorButtonInterface();
		this.interfaceLampadaAndar = new FloorLampInterface();
		this.interfaceDirecaoSobeDesceAndar = new DirectionLampInterface();
	}
	
	public FloorButtonInterface getInterfaceBotaoAndar() {
		return interfaceBotaoAndar;
	}
	public void setInterfaceBotaoAndar(FloorButtonInterface interfaceBotaoAndar) {
		this.interfaceBotaoAndar = interfaceBotaoAndar;
	}
	
	public FloorLampInterface getInterfaceLampadaAndar() {
		return interfaceLampadaAndar;
	}
	public void setInterfaceLampadaAndar(FloorLampInterface interfaceLampadaAndar) {
		this.interfaceLampadaAndar = interfaceLampadaAndar;
	}
	
	public void desligarVisorAndarAtual(int idElevador)
	{
		this.interfaceLampadaAndar.desligarVisorAndarAtual(numeroAndar, idElevador);
	}
	
	public void desligarVisorDirecaoSobeDesceAndar(int idElevador)
	{
		this.interfaceDirecaoSobeDesceAndar.desligarVisorSobeEDesceDoAndar(this.numeroAndar, idElevador);
	}
	
	public void ligarVisorDirecaoSobeDesceDoAndar(String sobeDesceOuParado)
	{
		this.interfaceDirecaoSobeDesceAndar.ligarVisorSobeEDesceEEmQualDirecao(sobeDesceOuParado);
	}

}
