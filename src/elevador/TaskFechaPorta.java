package elevador;

import android.os.AsyncTask;


public class TaskFechaPorta extends AsyncTask<String,String,String>
{
	protected String result = "";
	private ElevatorControl elevatorControl;
	private InterfaceDaPorta interfaceDaPorta;
	private String sobeOuDesceOuParado;
	private int andarAtual;

	public TaskFechaPorta(ElevatorControl elevatorControl, InterfaceDaPorta interfaceDaPorta, String sobeOuDesceOuParado, int andarAtual)
	{
		this.elevatorControl = elevatorControl;
		this.interfaceDaPorta = interfaceDaPorta;
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
		this.andarAtual = andarAtual;
	}

	@Override
	protected String doInBackground(String... string_qualquer) 
	{
		this.interfaceDaPorta.fecharPorta(andarAtual, this.elevatorControl.getIdElevador());
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		this.elevatorControl.realizarProcedimentoIniciarMovimento(sobeOuDesceOuParado,andarAtual);
	}

}
