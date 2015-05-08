package elevador;

import andar.SingletonInterfaceSubsistemaDeAndares;

public class ElevatorControl 
{
	InterfaceDaPorta interfaceDaPorta;
	
	public ElevatorControl()
	{
		interfaceDaPorta = new InterfaceDaPorta();
	}
	
	public void fazerElevadorSeMecher(String sobeOuDesceOuParado, int andarAtual)
	{
		ESSES DOIS METODOS ABAIXO DEVERIAM SER CONCORRENTES. POR ISSO, CRIAMOS JAH UMA TASK P FECHAR PORTA. PARAMOS AQUI!!!! CASO DE USO EH DESPACHAR ELEVADOR PASSO 2
		interfaceDaPorta.fecharPorta();
		SingletonInterfaceSubsistemaDeAndares.getInstancia().desligarVisorSubindoEDescendoNoAndar(andarAtual);
		
	}

}
