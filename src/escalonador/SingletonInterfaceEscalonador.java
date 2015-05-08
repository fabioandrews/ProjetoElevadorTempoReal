package escalonador;

public class SingletonInterfaceEscalonador 
{
	private static SingletonInterfaceEscalonador instancia;
	private AgendadorDeElevadores agendadorDeElevadores;
	
	private SingletonInterfaceEscalonador()
	{
		agendadorDeElevadores = new AgendadorDeElevadores();
	}
	
	public static SingletonInterfaceEscalonador getInstancia()
	{
		if(instancia == null)
		{
			
			instancia = new SingletonInterfaceEscalonador();
		}
		
		return instancia;
	}
	
	public void solicitarQueUmElevadorPareNoAndar(int andar, String desceOuSobe)
	{
		this.agendadorDeElevadores.escolherElevadorParaEnviarParaOAndar(andar, desceOuSobe);
	}

}
