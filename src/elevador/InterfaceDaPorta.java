package elevador;

import com.example.projetoelevadortemporeal.FachadaInterfaceGrafica;

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
	
	public void fecharPorta(int andarAtual, int idElevador)
	{
		this.portaEstaFechada = true;
		FachadaInterfaceGrafica.getInstance().fecharPortaElevadorEAndar(andarAtual, idElevador);
		
	}
	
	public boolean abrirPorta()
	{
		this.portaEstaFechada = false;
		return this.portaEstaFechada;
	}
}
