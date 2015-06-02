package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private int idElevador;//precisamos dele para saber de qual andar de elevador desligar a lâmpada

	public TaskDesligarLampadaAndarSubindoEDescendo(int andarAtual, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.idElevador = idElevador;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorSobeDesceNoAndar(andarAtual, idElevador);
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
