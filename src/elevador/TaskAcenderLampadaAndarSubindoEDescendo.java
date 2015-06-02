package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskAcenderLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private String sobeOuDesceOuParado;
	private int idElevador;

	public TaskAcenderLampadaAndarSubindoEDescendo(int andarAtual, String sobeOuDesceOuParado, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
		this.idElevador = idElevador;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		SingletonInterfaceSubsistemaDeAndares.getInstancia().ligarVisorSobeEDesceEEmQualDirecao(andarAtual, this.sobeOuDesceOuParado,this.idElevador);
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		
	}

}
