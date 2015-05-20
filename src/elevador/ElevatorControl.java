package elevador;

import java.util.LinkedList;
import java.util.UUID;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.util.Log;

public class ElevatorControl 
{
	private InterfaceDaPorta interfaceDaPorta;
	private MotorInterface motorInterface;
	private int altitudeDoElevador;
	private ElevatorStatusAndPlan elevatorStatusAndPlan;
	public static int quantosElevadores = 9;
	private UUID idControleDoElevador;//um ID único para elevatorController
	private int ultimoAndarQueElevadorParou;//o último andar destino que o elevador parou e abriu porta
	private String elevadorSobeOuDesceAntesDeParar;//antes de parar, ele subia ou descia?
	
	
	public ElevatorControl(ElevatorStatusAndPlan elevatorStatusAndPlan, LinkedList<ArrivalSensorInterface> sensoresDosAndares)
	{
		interfaceDaPorta = new InterfaceDaPorta();
		motorInterface = new MotorInterface(this);
		altitudeDoElevador = 0;
		//os sensores foram removidos desse construtor pq agora precisam conhecer o elevatorManager pra atualizar o andar do elevador.
		
		/*for(int i = 0; i < quantosElevadores; i++)
		{
			ArrivalSensorInterface sensorDeChegadaNoAndar = new ArrivalSensorInterface(this, i, 10 * i);
			sensorDeChegadaNoAndar.execute("");
		}*/
		this.elevatorStatusAndPlan = elevatorStatusAndPlan;
		this.idControleDoElevador = UUID.randomUUID();
	}
	
	public void fazerElevadorSeMecher(String sobeOuDesceOuParado, int andarAtual)
	{	
		Log.i("ElevatorControl", "Elevador id=" + idControleDoElevador + ";Porta fechando e" + sobeOuDesceOuParado);
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

	public synchronized int getAltitudeDoElevador() {
		return altitudeDoElevador;
	}

	public synchronized void setAltitudeDoElevador(int altitudeDoElevador) {
		this.altitudeDoElevador = altitudeDoElevador;
	}

	public void elevadorChegouNoAndar(int andarDoSensor) 
	{
		boolean precisaPercorrerEsseAndar = this.elevatorStatusAndPlan.andarPrecisaPercorrer(andarDoSensor);
		if(precisaPercorrerEsseAndar == true)
		{
			
			this.elevatorStatusAndPlan.removerAndarAPercorrer(andarDoSensor);//chegamos num andar que precisa percorrer, logo remove ele da lista de andares pra percorrer
			this.ultimoAndarQueElevadorParou = andarDoSensor;
			this.elevadorSobeOuDesceAntesDeParar = this.elevatorStatusAndPlan.getSobeOuDesceOuParado();
			
			String sobeDesceOuParado = elevatorStatusAndPlan.getSobeOuDesceOuParado();
			
			//essa task foi criada porque o processo precisava acontecer em paralelo com parar elevador
			TaskAcenderLampadaAndarSubindoEDescendo taskAcenderLampadaAndarSubindoEDescendo =
							new TaskAcenderLampadaAndarSubindoEDescendo(andarDoSensor, sobeDesceOuParado);
			taskAcenderLampadaAndarSubindoEDescendo.execute("");
			
			
			//essa task foi criada porque o processo precisava acontecer em paralelo com parar elevador
			TaskDesligarLampadaAndarAtual taskDesligarLampadaAndarAtual 
					= new TaskDesligarLampadaAndarAtual(andarDoSensor, elevatorStatusAndPlan);
			taskDesligarLampadaAndarAtual.execute("");
			
			//agora em paralelo vamos parar o elevador
			this.motorInterface.pararElevador();
			
			this.abrirPortaElevador();
		}
	}
	
	public void abrirPortaElevador()
	{
		
		boolean portaEstahFechada = this.interfaceDaPorta.abrirPorta();
		if(portaEstahFechada == false)
		{
			Log.i("ElevatorControl", "Elevador id=" + idControleDoElevador + ";Elevador no andar");
			DoorTimer timerDaPorta = new DoorTimer(this);
			timerDaPorta.execute("");
		}
	}
	
	public void desligarLampadaAndarElevador(int qualAndarDesligar)
	{
		TaskDesligarLampadaAndarAtual taskDesligarLampadaAndarAtual 
						= new TaskDesligarLampadaAndarAtual(qualAndarDesligar, elevatorStatusAndPlan);
		taskDesligarLampadaAndarAtual.execute("");
	}
	
	public void checarProximoDestino()
	{
		Log.i("ElevatorControl", "Elevador id=" + idControleDoElevador + ";Checando próximo destino");
		int proximoAndarPercorrer = 
				elevatorStatusAndPlan.checarProximoDestino(ultimoAndarQueElevadorParou, elevadorSobeOuDesceAntesDeParar);
		if(proximoAndarPercorrer < 0)
		{
			//elevador não tem mais o que percorrer e ficará ocioso
			Log.i("ElevatorControl", "Elevador id=" + idControleDoElevador + ";Elevador oscioso");
		}
		else
		{
			//COMEÇA CASO DE USO DESPACHAR ELEVADOR//
			if(this.ultimoAndarQueElevadorParou > proximoAndarPercorrer)
			{
				fazerElevadorSeMecher("desce", this.ultimoAndarQueElevadorParou);
			}
			else
			{
				fazerElevadorSeMecher("sobe", this.ultimoAndarQueElevadorParou);
			}
			
		}
	}
	
	

}
