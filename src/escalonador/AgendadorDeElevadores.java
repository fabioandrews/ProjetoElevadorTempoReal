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
		while(iteraSobreDiferencaAndaresEntreElevadores.hasNext())
		{
			int diferencaDeAndaresDistanteDoParametro = iteraSobreDiferencaAndaresEntreElevadores.next();
			LinkedList<ElevatorManager> elevadoresComEssaDiferenca = elevadoresEQuantosAndaresDistanteDoAndarParametro.get(diferencaDeAndaresDistanteDoParametro);
			for(int k = 0; k < elevadoresComEssaDiferenca.size(); k++)
			{
				ElevatorManager umElevadorCandidato = elevadoresComEssaDiferenca.get(k);
				String elevadorSobeDesceOuParado = umElevadorCandidato.getSobeOuDesceOuParado();
				if(elevadorSobeDesceOuParado.compareTo("parado") == 0 
						|| elevadorSobeDesceOuParado.compareTo(sobeOuDesce) == 0)
				{
					umElevadorCandidato.adicionarAndarPercorrer(andar);
					return true;
				}
			}
		}
		
		return false;
	}

}
