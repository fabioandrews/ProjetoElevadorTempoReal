package escalonador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import elevador.ElevatorManager;

public class AgendadorDeElevadores 
{
	private LinkedList<ElevatorManager> elevadores;
	
	public AgendadorDeElevadores(LinkedList<ElevatorManager> elevadores)
	{
		this.elevadores = elevadores;
		System.out.println("pra debugar");
	}
	
	public boolean escolherElevadorParaEnviarParaOAndar(int andar, String sobeOuDesce)
	{
		TreeMap<Integer, LinkedList<ElevatorManager>> elevadoresEQuantosAndaresDistanteDoAndarParametro 
							= new TreeMap<Integer, LinkedList<ElevatorManager>>(); //precisamos escolher os elevadores que estao mais pertos e na mesma direcao que o solicitado 
		for(int i = 0; i < elevadores.size(); i++)
		{
			ElevatorManager umElevador = elevadores.get(i);
			int andarAtualUmElevador = umElevador.getAndarAtual();
			int quantosAndaresDistanteDoAndarParametro;
			if(andarAtualUmElevador > andar)
			{
				quantosAndaresDistanteDoAndarParametro = andarAtualUmElevador - andar;
			}
			else
			{
				quantosAndaresDistanteDoAndarParametro = andar - andarAtualUmElevador;
			}
			LinkedList<ElevatorManager> elevadoresDessaMesmaDistancia = 
					elevadoresEQuantosAndaresDistanteDoAndarParametro.get(quantosAndaresDistanteDoAndarParametro);
			if(elevadoresDessaMesmaDistancia == null)
			{
				elevadoresDessaMesmaDistancia = new LinkedList<ElevatorManager>();
			}
			//de qqr forma iremos colocar o elevador na linkedList
			elevadoresDessaMesmaDistancia.addLast(umElevador);
			elevadoresEQuantosAndaresDistanteDoAndarParametro.put(quantosAndaresDistanteDoAndarParametro, elevadoresDessaMesmaDistancia);
		}
		Set<Integer> chavesHashmapElevadoresEAndares = elevadoresEQuantosAndaresDistanteDoAndarParametro.keySet();
		Iterator<Integer> iteraSobreDiferencaAndaresEntreElevadores = chavesHashmapElevadoresEAndares.iterator();
		boolean achouElevadorPraAdicionarAndar = false;
		while(iteraSobreDiferencaAndaresEntreElevadores.hasNext())
		{
			int diferencaDeAndaresDistanteDoParametro = iteraSobreDiferencaAndaresEntreElevadores.next();
			LinkedList<ElevatorManager> elevadoresComEssaDiferenca = elevadoresEQuantosAndaresDistanteDoAndarParametro.get(diferencaDeAndaresDistanteDoParametro);
			for(int k = 0; k < elevadoresComEssaDiferenca.size(); k++)
			{
				ElevatorManager umElevadorCandidato = elevadoresComEssaDiferenca.get(k);
				boolean elevadorEstahParado = umElevadorCandidato.getElevadorEstahParado();
				String elevadorSobeOuDesce = umElevadorCandidato.getSobeOuDesce();
				if(elevadorEstahParado == true 
						|| (elevadorSobeOuDesce.compareTo(sobeOuDesce) == 0 && 
								(sobeOuDesce.compareTo("sobe") == 0 && umElevadorCandidato.getAndarAtual() < andar) ||
								(sobeOuDesce.compareTo("desce") == 0 && umElevadorCandidato.getAndarAtual() > andar))
						)
				{
					achouElevadorPraAdicionarAndar = true;
					umElevadorCandidato.adicionarAndarPercorrer(andar);
					return true;
				}
			}
		}
		if(achouElevadorPraAdicionarAndar == false)
		{
			//ainda não achou elevador compatível com o que o usuário solicitou
			//vamos pegar então o elevador mais próximo do andar solicitado
			Iterator<Integer> iteraSobreDiferencaAndaresEntreElevadoresRestantes = chavesHashmapElevadoresEAndares.iterator();
			int chaveDosElevadoresMaisProximos = iteraSobreDiferencaAndaresEntreElevadoresRestantes.next();
			LinkedList<ElevatorManager> elevadoresMaisProximos = elevadoresEQuantosAndaresDistanteDoAndarParametro.get(chaveDosElevadoresMaisProximos);
			ElevatorManager elevadorMaisProximo = elevadoresMaisProximos.getFirst();
			elevadorMaisProximo.adicionarAndarPercorrer(andar);
		}
		
		return false;
	}

}
