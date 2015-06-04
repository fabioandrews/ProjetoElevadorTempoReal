package elevador;

import android.os.AsyncTask;

public class DoorTimer extends AsyncTask<String,String,String>
{
	//DoorTimer é task pq vai esperar um tempo e vc sabe que espera em android só pode ser feito com tasks
	
	private ElevatorControl controleDoElevador;
	private static int timeoutDoorTimer = 10000;  

	public DoorTimer(ElevatorControl controleDoElevador)
	{
		this.controleDoElevador = controleDoElevador;
	}
	@Override
	protected String doInBackground(String... params) 
	{
		this.controleDoElevador.pararElevadorNoStatusAndPlan();
		this.controleDoElevador.setElevadorEsperandoTimerPortaAberta(true);
		try {
			Thread.sleep(timeoutDoorTimer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) 
	{
		this.controleDoElevador.setElevadorEsperandoTimerPortaAberta(false);
		this.controleDoElevador.checarProximoDestino();
	}

}
