package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarAtual extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private ElevatorStatusAndPlan elevatorStatusAndPlan;

	public TaskDesligarLampadaAndarAtual(int andarAtual, ElevatorStatusAndPlan elevatorStatusAndPlan)
	{
		this.andarAtual = andarAtual;
		this.elevatorStatusAndPlan = elevatorStatusAndPlan;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorAndarAtualNoAndar(this.andarAtual);
		
		this.elevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
