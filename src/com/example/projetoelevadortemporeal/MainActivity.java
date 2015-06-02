package com.example.projetoelevadortemporeal;

import java.util.HashMap;
import java.util.LinkedList;

import elevador.ArrivalSensorInterface;
import elevador.ElevatorControl;
import elevador.ElevatorManager;
import elevador.ElevatorStatusAndPlan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity 
{
	private HashMap<Integer,LinkedList<Boolean>> elevadoresESeBotoesInternosEstaoApertados; //uma lista de x componentes(depende de quantos andares) que diz se o botao para o andar N está apertado ou nao 
	private int quantosElevadores = 2; //quantos elevadores a aplicacao deveria ter																						//a chave eh o elevador
	private int quantosAndares = 9;
	
	private LinkedList<ElevatorControl> elevatorControls;
	private LinkedList<ElevatorManager> elevatorManagers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.elevatorControls = new LinkedList<ElevatorControl>();
		this.elevatorManagers = new LinkedList<ElevatorManager>();
		
		for(int i = 0; i < quantosElevadores; i++)
		{
			//vamos preparar os botoes internos dos elevadores
			LinkedList<Boolean> botoesInternosElevadorQueEstaoApertados = new LinkedList<Boolean>();
			for(int j = 0; j < quantosAndares; j++)
			{
				botoesInternosElevadorQueEstaoApertados.add(false);
			}
			
			this.elevadoresESeBotoesInternosEstaoApertados.put(i + 1, botoesInternosElevadorQueEstaoApertados);
			
			//agora vamos criar os elevadores e etc - parte fora da interface
			
			ElevatorStatusAndPlan umElevatorStatusAndPlan = new ElevatorStatusAndPlan();
			umElevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
			
			ElevatorControl umElevatorControl = new ElevatorControl(umElevatorStatusAndPlan);
			this.elevatorControls.add(umElevatorControl);
			
			ElevatorManager umElevatorManager = new ElevatorManager(umElevatorStatusAndPlan, umElevatorControl, 0);
			this.elevatorManagers.add(umElevatorManager);
			
			for(int k = 0; k < quantosAndares; k++)
			{
				ArrivalSensorInterface umArrivalSensorInterface = new ArrivalSensorInterface(umElevatorControl, k, k * 10, umElevatorManager);
				umArrivalSensorInterface.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
			}
			
			//fim da criação dos elevadores
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void apertarBotaoInternoElevador(View v)
	{
		String elevadorEAndar = (String) v.getTag(); 
		String[] elevadorEAndarArray = elevadorEAndar.split(";"); //ex: elevador=1;andar=0
		String elevadorEmString = elevadorEAndarArray[0].split("=")[1];
		String andarEmString = elevadorEAndarArray[1].split("=")[1];
		int elevador = Integer.valueOf(elevadorEmString);
		int andar = Integer.valueOf(andarEmString);
		
		LinkedList<Boolean> elevadoresDesseAndarEstaoApertados = this.elevadoresESeBotoesInternosEstaoApertados.get(elevador);
		boolean botaoEstahAceso = elevadoresDesseAndarEstaoApertados.get(andar);
		
		if(botaoEstahAceso == false)
		{
			elevadoresDesseAndarEstaoApertados.set(andar, true);
			this.elevadoresESeBotoesInternosEstaoApertados.put(elevador, elevadoresDesseAndarEstaoApertados);
			
			//REALIZAR PROCEDIMENTO CLICOU BOTAO ANDAR
			
		}
		
		
	}
}
