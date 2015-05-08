package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskDesligarLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;

	public TaskDesligarLampadaAndarSubindoEDescendo(int andarAtual)
	{
		this.andarAtual = andarAtual;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorAndarAtualNoAndar(andarAtual);
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
