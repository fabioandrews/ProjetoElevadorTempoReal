package elevador;

import escalonador.SingletonInterfaceEscalonador;

public class ElevatorManager 
{
	private ElevatorStatusAndPlan andaresPercorrerESobreOuDesce;
	private ElevatorControl controleDoElevador;
	private int andarAtual;
	
	public ElevatorManager(ElevatorStatusAndPlan andaresPercorrerESobreOuDesce, ElevatorControl controleDoElevador, int andarAtual)
	{
		this.andaresPercorrerESobreOuDesce = andaresPercorrerESobreOuDesce;
		this.andarAtual = andarAtual;
		this.controleDoElevador = controleDoElevador;
	}
	
	public ElevatorStatusAndPlan getAndaresPercorrerESobreOuDesce() {
		return andaresPercorrerESobreOuDesce;
	}
	public void setAndaresPercorrerESobreOuDesce(
			ElevatorStatusAndPlan andaresPercorrerESobreOuDesce) {
		this.andaresPercorrerESobreOuDesce = andaresPercorrerESobreOuDesce;
	}
	public int getAndarAtual() {
		return andarAtual;
	}
	public void setAndarAtual(int andarAtual) 
	{
		this.andarAtual = andarAtual;
	}
	
	public void adicionarAndarPercorrer(int andar)
	{
		this.andaresPercorrerESobreOuDesce.adicionarNovoAndarAPercorrer(andar);
		boolean elevadorEstahParado = this.andaresPercorrerESobreOuDesce.getElevadorEstahParado();
		String elevadorSobeDesceOuParado = this.andaresPercorrerESobreOuDesce.getSobeOuDesce();
		if(elevadorEstahParado == true)
		{
			String elevadorSobeOuDesceOuParado = "";
			if(andarAtual > andar)
			{
				elevadorSobeOuDesceOuParado = "desce";
			}
			else if(andarAtual < andar)
			{
				elevadorSobeOuDesceOuParado = "sobe";
			}
			else
			{
				elevadorSobeOuDesceOuParado = "parado";
			}
			
			this.controleDoElevador.fazerElevadorSeMecher(elevadorSobeOuDesceOuParado,andarAtual);
			this.andaresPercorrerESobreOuDesce.setSobeOuDesceOuParado(elevadorSobeOuDesceOuParado);
			
		}
	}
	
	public String getSobeOuDesce()
	{
		return this.andaresPercorrerESobreOuDesce.getSobeOuDesce();
	}
	
	public boolean getElevadorEstahParado()
	{
		return this.andaresPercorrerESobreOuDesce.getElevadorEstahParado();
	}
	
	/*essa funcao ocorre no caso de uso Selecionar destino, apos o ElevatorButtonInterface executar pressionouBotaoDoAndar*/
	public void agendarAndarApospressionouBotaoDoAndar(int numAndar)
	{
		this.adicionarAndarPercorrer(numAndar);
		
	}
	
	public int getIdElevador()
	{
		return this.controleDoElevador.getIdElevador();
	}

}
