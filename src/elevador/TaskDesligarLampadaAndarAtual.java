package elevador;

import com.example.projetoelevadortemporeal.MainActivity;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarAtual extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private ElevatorStatusAndPlan elevatorStatusAndPlan;
	private int idElevador;//necessário pra saber de qual andar de elevador desligamos a lâmpada
	private MainActivity telaPrincipal;

	public TaskDesligarLampadaAndarAtual(MainActivity telaPrincipal, int andarAtual, ElevatorStatusAndPlan elevatorStatusAndPlan, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.elevatorStatusAndPlan = elevatorStatusAndPlan;
		this.idElevador = idElevador;
		this.telaPrincipal = telaPrincipal;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		this.telaPrincipal.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
					SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorAndarAtualNoAndar(andarAtual, idElevador );
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
				
				
			}
		});
		
		this.elevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
