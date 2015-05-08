package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskAcenderLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private String sobeOuDesceOuParado;

	public TaskAcenderLampadaAndarSubindoEDescendo(int andarAtual, String sobeOuDesceOuParado)
	{
		this.andarAtual = andarAtual;
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().ligarVisorSobeEDesceEEmQualDirecao(andarAtual, this.sobeOuDesceOuParado);
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
