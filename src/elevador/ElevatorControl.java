package elevador;

import java.util.LinkedList;
import java.util.UUID;

import com.example.projetoelevadortemporeal.FachadaInterfaceGrafica;
import com.example.projetoelevadortemporeal.MainActivity;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;

public class ElevatorControl 
{
	private InterfaceDaPorta interfaceDaPorta;
	private MotorInterface motorInterface;
	private int altitudeDoElevador;
	private ElevatorStatusAndPlan elevatorStatusAndPlan;
	public static int quantosElevadores = 9;
	private int ultimoAndarQueElevadorParou;//o último andar destino que o elevador parou e abriu porta
	private String elevadorSobeOuDesceAntesDeParar;//antes de parar, ele subia ou descia?
	private int idElevador;
	private MainActivity telaPrincipal;
	private volatile boolean elevadorEsperandoTimerPortaAberta;//o elevador ainda está esperando o timer que abre a porta terminar?
	
	public ElevatorControl(MainActivity telaPrincipal, ElevatorStatusAndPlan elevatorStatusAndPlan, int idElevador)
	{
		this.telaPrincipal = telaPrincipal;
		this.idElevador = idElevador;
		interfaceDaPorta = new InterfaceDaPorta();
		motorInterface = new MotorInterface(this);
		altitudeDoElevador = 0;
		this.elevadorEsperandoTimerPortaAberta = false;
		//os sensores foram removidos desse construtor pq agora precisam conhecer o elevatorManager pra atualizar o andar do elevador.
		
		/*for(int i = 0; i < quantosElevadores; i++)
		{
			ArrivalSensorInterface sensorDeChegadaNoAndar = new ArrivalSensorInterface(this, i, 10 * i);
			sensorDeChegadaNoAndar.execute("");
		}*/
		this.elevatorStatusAndPlan = elevatorStatusAndPlan;
	}
	
	
	
	





	public int getIdElevador()
	{
		return this.idElevador;
	}
	
	public void fazerElevadorSeMecher(String sobeOuDesceOuParado, int andarAtual)
	{	
		Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Porta fechando e" + sobeOuDesceOuParado);
		//os eventos abaixo deveriam ser concorrentes. Por isso as tasks foram criadas
		TaskFechaPorta taskFechaPorta = new TaskFechaPorta(this.telaPrincipal, this, interfaceDaPorta, sobeOuDesceOuParado, andarAtual);
		Status statusThreadRoda = taskFechaPorta.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "").getStatus();
		
		TaskDesligarLampadaAndarSubindoEDescendo taskDesligaLampada = new TaskDesligarLampadaAndarSubindoEDescendo(this.telaPrincipal,andarAtual, this.idElevador);
		taskDesligaLampada.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");	
	}
	
	//esse processo soh eh iniciado apos a taskfechaporta terminar de fechar a porta
	public void realizarProcedimentoIniciarMovimento(String sobeOuDesceOuParado, int andarAtual)
	{
		this.elevatorStatusAndPlan.setSobeOuDesceOuParado(sobeOuDesceOuParado);
		if(sobeOuDesceOuParado.compareTo("sobe") == 0)
		{
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Iniciando subida");
			this.motorInterface.subirElevador();
		}
		else if(sobeOuDesceOuParado.compareTo("desce") == 0)
		{
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Iniciando descida");
			this.motorInterface.descerElevador();
		}
		else
		{
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Iniciando parada");
			this.motorInterface.pararElevador();
		}
		
		SingletonInterfaceSubsistemaDeAndares singletonConheceAndares = 
							SingletonInterfaceSubsistemaDeAndares.getInstancia();
		singletonConheceAndares.desligarVisorSobeDesceNoAndar(andarAtual, this.idElevador);
	}

	public synchronized int getAltitudeDoElevador() {
		return altitudeDoElevador;
	}

	public synchronized void setAltitudeDoElevador(int altitudeDoElevador) {
		this.altitudeDoElevador = altitudeDoElevador;
	}

	public void elevadorChegouNoAndar(int andarDoSensor) 
	{
		//primeiro, precisamos alterar na interface grafica que um elevador chegou num determinado andar, seja ele para parar ou nao
		String elevadorSobeOuDesce = this.elevatorStatusAndPlan.getSobeOuDesce();
		FachadaInterfaceGrafica.getInstance().atualizarInterfaceElevadorChegouNumAndar(andarDoSensor,idElevador,elevadorSobeOuDesce);
		
		boolean precisaPercorrerEsseAndar = this.elevatorStatusAndPlan.andarPrecisaPercorrer(andarDoSensor);
		if(precisaPercorrerEsseAndar == true)
		{
			
			this.elevatorStatusAndPlan.removerAndarAPercorrer(andarDoSensor);//chegamos num andar que precisa percorrer, logo remove ele da lista de andares pra percorrer
			this.ultimoAndarQueElevadorParou = andarDoSensor;
			this.elevadorSobeOuDesceAntesDeParar = this.elevatorStatusAndPlan.getSobeOuDesce();
			
			String sobeDesceOuParado = elevatorStatusAndPlan.getSobeOuDesce();
			
			//essa task foi criada porque o processo precisava acontecer em paralelo com parar elevador
			TaskAcenderLampadaAndarSubindoEDescendo taskAcenderLampadaAndarSubindoEDescendo =
							new TaskAcenderLampadaAndarSubindoEDescendo(this.telaPrincipal, andarDoSensor, sobeDesceOuParado,this.idElevador);
			taskAcenderLampadaAndarSubindoEDescendo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
			
			
			//essa task foi criada porque o processo precisava acontecer em paralelo com parar elevador
			TaskDesligarLampadaAndarAtual taskDesligarLampadaAndarAtual 
					= new TaskDesligarLampadaAndarAtual(this.telaPrincipal, andarDoSensor, elevatorStatusAndPlan, this.idElevador);
			taskDesligarLampadaAndarAtual.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");;
			
			//agora em paralelo vamos parar o elevador
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Elevador muda de movendo para parando");
			this.motorInterface.pararElevador();
			
			FachadaInterfaceGrafica.getInstance().pararElevador(andarDoSensor, this.idElevador);
			
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Abrindo porta elevador");
			
			this.abrirPortaElevador(andarDoSensor);
		}
	}
	
	public void abrirPortaElevador(int andarDoSensor)
	{
		
		boolean portaEstahFechada = this.interfaceDaPorta.abrirPorta();
		FachadaInterfaceGrafica.getInstance().abrirPortaNaInterfaceEDesligarBotaoDentroElevador(andarDoSensor, this.idElevador);
		if(portaEstahFechada == false)
		{
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Elevador no andar");
			DoorTimer timerDaPorta = new DoorTimer(this);
			timerDaPorta.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");;
		}
	}
	
	public synchronized void setElevadorEsperandoTimerPortaAberta(boolean novoValor)
	{
		this.elevadorEsperandoTimerPortaAberta = novoValor;
	}
	
	public synchronized boolean getElevadorEsperandoTimerPortaAberta()
	{
		return this.elevadorEsperandoTimerPortaAberta;
	}
	
	public void desligarLampadaAndarElevador(int qualAndarDesligar)
	{
		TaskDesligarLampadaAndarAtual taskDesligarLampadaAndarAtual 
						= new TaskDesligarLampadaAndarAtual(this.telaPrincipal, qualAndarDesligar, elevatorStatusAndPlan, this.idElevador);
		taskDesligarLampadaAndarAtual.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");;
	}

	
	public void checarProximoDestino()
	{
		Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Checando próximo destino");
		int proximoAndarPercorrer = 
				elevatorStatusAndPlan.checarProximoDestino(ultimoAndarQueElevadorParou, elevadorSobeOuDesceAntesDeParar);
		if(proximoAndarPercorrer < 0)
		{
			//elevador não tem mais o que percorrer e ficará ocioso
			Log.i("ElevatorControl", "Elevador id=" + idElevador + ";Elevador oscioso");
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
	
	
	//funcao chamada so para uma porta ser aberta sem timer(a interface grafica da MainActivity quem chama)
	public void abrirPortaDoElevadorInicial()
	{
		this.interfaceDaPorta.abrirPorta();
	}
	
	//esse método é chamado apenas pelo DoorTimer
	public synchronized void pararElevadorNoStatusAndPlan()
	{
		Log.i("ElevatorControl", "Elevador id=" + idElevador + ";DoorTimer Chamou pararElevadorNoStatusAndPlan");
		this.elevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
	}
	

}
