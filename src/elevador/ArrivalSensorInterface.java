package elevador;

import android.os.AsyncTask;

public class ArrivalSensorInterface extends AsyncTask<String,String,Void>
{
	protected String result = "";
	private ElevatorControl elevatorControl;
	private int andarDoSensor;
	private int altitudeDoSensor;

	public ArrivalSensorInterface(ElevatorControl elevatorControl, int andarDoSensor, int altitudeDoSensor )
	{
		this.elevatorControl = elevatorControl;
		this.andarDoSensor = andarDoSensor;
		this.altitudeDoSensor = altitudeDoSensor;
	}

	@Override
	protected Void doInBackground(String... string_qualquer) 
	{
		while(true)
		{
			try {
				Thread.sleep(1000);
				int altitudeAtualDoElevador = this.elevatorControl.getAltitudeDoElevador(); 
				if(altitudeAtualDoElevador == altitudeDoSensor)
				{
					publishProgress(string_qualquer);//chama OnProgressUpdate que avisa ao elevador que chegou no andar
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	protected void onProgressUpdate(String... progress)
	{
		this.elevatorControl.elevadorChegouNoAndar(andarDoSensor);
	}
	
	
	@Override
	protected void onPostExecute(Void v) 
	{
		
	}
	

	
}
