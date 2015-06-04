package andar;

import escalonador.SingletonInterfaceEscalonador;

public class FloorButtonInterface 
{
	public void usuarioApertouBotaoCimaOuBaixo(int andarQueOUsuarioEstah, String sobeOuDesce)
	{
		SingletonInterfaceEscalonador interfaceEscalonador = 
							SingletonInterfaceEscalonador.getInstancia();
		interfaceEscalonador.solicitarQueUmElevadorPareNoAndar(andarQueOUsuarioEstah, sobeOuDesce);
	}

}
