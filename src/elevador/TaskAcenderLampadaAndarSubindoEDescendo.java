package elevador;

import com.example.projetoelevadortemporeal.MainActivity;

import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;


public class TaskAcenderLampadaAndarSubindoEDescendo extends AsyncTask<String,String,String>
{
	private int andarAtual;
	private String sobeOuDesceOuParado;
	private int idElevador;
	private MainActivity telaPrincipal;

	public TaskAcenderLampadaAndarSubindoEDescendo(MainActivity telaPrincipal, int andarAtual, String sobeOuDesceOuParado, int idElevador)
	{
		this.andarAtual = andarAtual;
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
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
					SingletonInterfaceSubsistemaDeAndares.getInstancia().ligarVisorSobeEDesceEEmQualDirecao(andarAtual, sobeOuDesceOuParado, idElevador);
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
