package elevador;

import com.example.projetoelevadortemporeal.MainActivity;

import android.os.AsyncTask;

public class ArrivalSensorInterface extends Thread
{
	protected String result = "";
	private ElevatorControl elevatorControl;
	private ElevatorManager elevatorManager;//precisa dele pra atualizar o andar em que o elevador estah.
	private int andarDoSensor;
	private int altitudeDoSensor;
	private MainActivity telaMain;

	public ArrivalSensorInterface(MainActivity activity, ElevatorControl elevatorControl, int andarDoSensor, int altitudeDoSensor , ElevatorManager elevatorManager)
	{
		this.elevatorControl = elevatorControl;
		this.andarDoSensor = andarDoSensor;
		this.altitudeDoSensor = altitudeDoSensor;
		this.elevatorManager = elevatorManager;
		this.telaMain = activity;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			try {
				Thread.sleep(1000);
				int altitudeAtualDoElevador = this.elevatorControl.getAltitudeDoElevador(); 
				if(altitudeAtualDoElevador == altitudeDoSensor)
				{
					telaMain.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							elevatorManager.setAndarAtual(andarDoSensor);
							elevatorControl.elevadorChegouNoAndar(andarDoSensor);
							
						}
					});
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	

	
}
