package elevador;

import com.example.projetoelevadortemporeal.MainActivity;

import android.os.AsyncTask;
import android.util.Log;


public class TaskFechaPorta extends AsyncTask<String,String,String>
{
	protected String result = "";
	private ElevatorControl elevatorControl;
	private InterfaceDaPorta interfaceDaPorta;
	private String sobeOuDesceOuParado;
	private int andarAtual;
	private MainActivity telaPrincipal;

	public TaskFechaPorta(MainActivity telaPrincipal, ElevatorControl elevatorControl, InterfaceDaPorta interfaceDaPorta, String sobeOuDesceOuParado, int andarAtual)
	{
		this.elevatorControl = elevatorControl;
		this.interfaceDaPorta = interfaceDaPorta;
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
		this.andarAtual = andarAtual;
		this.telaPrincipal = telaPrincipal;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		telaPrincipal.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				try
				{
					// TODO Auto-generated method stub
					//PAREI AKI PARECE QUE ESSA TASK NÃO EXECUTA. PROBLEMA DO EXECUTE ON EXECUTOR?
					Log.i("ElevatorControl", "Elevador id=" + elevatorControl.getIdElevador() + ";fechando porta");
					interfaceDaPorta.fecharPorta(andarAtual, elevatorControl.getIdElevador());
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
		this.elevatorControl.realizarProcedimentoIniciarMovimento(sobeOuDesceOuParado,andarAtual);
	}

}
