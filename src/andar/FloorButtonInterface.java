package andar;

import escalonador.SingletonInterfaceEscalonador;

public class FloorButtonInterface 
{
	public void usuarioApertouBotaoCimaOuBaixo(int andarQueOUsuarioEstah, String cimaOuBaixo)
	{
		SingletonInterfaceEscalonador interfaceEscalonador = 
							SingletonInterfaceEscalonador.getInstancia();
		interfaceEscalonador.solicitarQueUmElevadorPareNoAndar(andarQueOUsuarioEstah, cimaOuBaixo);
	}

}
