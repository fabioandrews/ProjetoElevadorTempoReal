package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarAtual extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private ElevatorStatusAndPlan elevatorStatusAndPlan;
	private int idElevador;//necessário pra saber de qual andar de elevador desligamos a lâmpada

	public TaskDesligarLampadaAndarAtual(int andarAtual, ElevatorStatusAndPlan elevatorStatusAndPlan, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.elevatorStatusAndPlan = elevatorStatusAndPlan;
		this.idElevador = idElevador;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorAndarAtualNoAndar(this.andarAtual, this.idElevador );
		
		this.elevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
