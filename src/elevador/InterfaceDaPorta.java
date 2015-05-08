package elevador;

public class InterfaceDaPorta 
{
	boolean portaEstaFechada;

	public InterfaceDaPorta()
	{
		portaEstaFechada = true;
	}
	
	public boolean getPortaEstaFechada() {
		return portaEstaFechada;
	}

	public void setPortaEstaFechada(boolean portaEstaFechada) {
		this.portaEstaFechada = portaEstaFechada;
	}
	
	public void fecharPorta()
	{
		this.portaEstaFechada = true;
	}
}
