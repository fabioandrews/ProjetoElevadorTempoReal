package elevador;

import com.example.projetoelevadortemporeal.MainActivity;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private int idElevador;//precisamos dele para saber de qual andar de elevador desligar a lâmpada
	private MainActivity telaPrincipal;

	public TaskDesligarLampadaAndarSubindoEDescendo(MainActivity telaPrincipal, int andarAtual, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.idElevador = idElevador;
		this.telaPrincipal = telaPrincipal;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		telaPrincipal.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
					SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorSobeDesceNoAndar(andarAtual, idElevador);
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
				
				
			}
		});
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
