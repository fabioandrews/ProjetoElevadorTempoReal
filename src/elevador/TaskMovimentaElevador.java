package elevador;

import java.util.LinkedList;

import android.os.AsyncTask;

public class TaskMovimentaElevador extends AsyncTask<String,String,Void>
{
	protected String result = "";
	private ElevatorControl elevatorControl;
	private MotorInterface motorElevador;
	private String sobeOuDesce;

	public TaskMovimentaElevador(ElevatorControl elevatorControl, MotorInterface motorElevador, String sobeOuDesce)
	{
		this.elevatorControl = elevatorControl;
		this.motorElevador = motorElevador;
		this.sobeOuDesce = sobeOuDesce;
	}

	@Override
	protected Void doInBackground(String... string_qualquer) 
	{
		
		while(this.motorElevador.getElevadorEmMovimento() == true)
		{
			try {
				Thread.sleep(1000);
				if(this.motorElevador.getElevadorEmMovimento() == true)
				{
					int antigaAltitudeDoElevador = elevatorControl.getAltitudeDoElevador();
					if(sobeOuDesce.compareTo("sobe") == 0)
					{
						this.elevatorControl.setAltitudeDoElevador(antigaAltitudeDoElevador + 10);
						
					}
					else
					{
						this.elevatorControl.setAltitudeDoElevador(antigaAltitudeDoElevador - 10);
						
					}
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	
	
	@Override
	protected void onPostExecute(Void v) 
	{
		
	}

}
