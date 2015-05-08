package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;

public class ElevatorControl 
{
	private InterfaceDaPorta interfaceDaPorta;
	private MotorInterface motorInterface;
	
	public ElevatorControl()
	{
		interfaceDaPorta = new InterfaceDaPorta();
		motorInterface = new MotorInterface();
	}
	
	public void fazerElevadorSeMecher(String sobeOuDesceOuParado, int andarAtual)
	{	
		//os eventos abaixo deveriam ser concorrentes. Por isso as tasks foram criadas
		TaskFechaPorta taskFechaPorta = new TaskFechaPorta(this, interfaceDaPorta, sobeOuDesceOuParado, andarAtual);
		taskFechaPorta.execute("");
		
		TaskDesligarLampadaAndarSubindoEDescendo taskDesligaLampada = new TaskDesligarLampadaAndarSubindoEDescendo(andarAtual);
		taskDesligaLampada.execute("");	
	}
	
	//esse processo soh eh iniciado apos a taskfechaporta terminar de fechar a porta
	public void realizarProcedimentoIniciarMovimento(String sobeOuDesceOuParado, int andarAtual)
	{
		if(sobeOuDesceOuParado.compareTo("sobe") == 0)
		{
			this.motorInterface.subirElevador();
		}
		else if(sobeOuDesceOuParado.compareTo("desce") == 0)
		{
			this.motorInterface.descerElevador();
		}
		else
		{
			this.motorInterface.pararElevador();
		}
		
		SingletonInterfaceSubsistemaDeAndares singletonConheceAndares = 
							SingletonInterfaceSubsistemaDeAndares.getInstancia();
		singletonConheceAndares.desligarVisorSobeDesceNoAndar(andarAtual);
	}

}
