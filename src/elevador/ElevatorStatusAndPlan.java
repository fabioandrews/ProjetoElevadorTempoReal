package elevador;

import java.util.LinkedList;

public class ElevatorStatusAndPlan 
{
	private String sobeOuDesce; //valores: sobe ou desce ou parado
	private boolean elevadorEstahParado;
	private LinkedList<Integer> andaresAPercorrer;
	
	
	public boolean andarPrecisaPercorrer(int numAndar)
	{
		for(int i = 0; i < andaresAPercorrer.size(); i++)
		{
			int umAndar = andaresAPercorrer.get(i);
			if(umAndar == numAndar)
			{
				return true;
			}
		}
		
		return false;
	}

	//alem de adicionar um novo andar(se necessario), ele ordena a lista de andares
	public void adicionarNovoAndarAPercorrer(int numeroAndar)
	{
		LinkedList<Integer> novoAndaresAPercorrer = new LinkedList<Integer>();
		
		if(andarPrecisaPercorrer(numeroAndar) == false)
		{
			//o andar eh novo e precisa ser colocado na lista, mas na ordem do menor para o maior
			this.andaresAPercorrer.add(numeroAndar);
			int menorAndar = this.andaresAPercorrer.getFirst();
			int posicaoMenorAndar = 0;
			
			while(andaresAPercorrer.size() > 0)
			{
				for(int i = 0; i < andaresAPercorrer.size(); i++)
				{
					int umAndar = this.andaresAPercorrer.get(i);
					
					if(umAndar < menorAndar)
					{
						menorAndar = umAndar;
						posicaoMenorAndar = i;
					}
				}
				
				novoAndaresAPercorrer.add(menorAndar);
				this.andaresAPercorrer.remove(posicaoMenorAndar);
			}
			
			for(int j = 0; j < novoAndaresAPercorrer.size(); j++)
			{
				this.andaresAPercorrer.add(novoAndaresAPercorrer.get(j));
			}
		}
	}

	public String getSobeOuDesce() {
		return sobeOuDesce;
	}
	
	public boolean getElevadorEstahParado()
	{
		return this.elevadorEstahParado;
	}

	public void setSobeOuDesceOuParado(String sobeOuDesceOuParado) 
	{
		if(sobeOuDesceOuParado.compareTo("parado") == 0)
		{
			this.elevadorEstahParado = true;
		}
		else
		{
			this.sobeOuDesce = sobeOuDesceOuParado;
			this.elevadorEstahParado =false;
		}
	}
	
	public boolean removerAndarAPercorrer(int numAndar)
	{
		for(int i = 0; i < andaresAPercorrer.size(); i++)
		{
			int umAndar = andaresAPercorrer.get(i);
			if(umAndar == numAndar)
			{
				andaresAPercorrer.remove(i);
				return true;
			}
		}
		return false;
	}
	public int checarProximoDestino(int andarAtual, String elevadorSobeOuDesceAntesDeParar)
	{
		int proximoDestino = -1;
		if(andaresAPercorrer.size() > 0)
		{
			if(elevadorSobeOuDesceAntesDeParar.compareTo("sobe") == 0)
			{
				//elevador subindo, o próximo andar é em cima, se tiver
				int andarMaisAcima = andaresAPercorrer.getLast();//o andar mais em cima de todos que o elevador precisa percorrer
				if(andarMaisAcima < andarAtual)
				{
					//o andar atual é o mais em cima, logo agora o elevador vai descer...
					proximoDestino = escolherProximoAndar(andarAtual, "desce");
				}
				else
				{
					proximoDestino = escolherProximoAndar(andarAtual, "sobe");
				}
			}
			else
			{
				//elevador descendo, o próximo andar é embaixo, se tiver
				int andarMaisAbaixo = andaresAPercorrer.getFirst();//o andar mais embaixo de todos que o elevador precisa percorrer
				if(andarMaisAbaixo > andarAtual)
				{
					//o andar atual é o mais embaixo, logo agora o elevador vai subir...
					proximoDestino = escolherProximoAndar(andarAtual, "sobe");
				}
				else
				{
					proximoDestino = escolherProximoAndar(andarAtual, "desce");
				}
			}
		}
		
		return proximoDestino;
	}
	
	/**
	 * aqui, assume-se que vc já sabe a direção exata se o elevador vai subir ou descer, ou seja,
	 * TEM(E JÁ FOI CHECADO ANTES) um andar acima/abaixo para ele subir/descer 
	 * @param andarAtual
	 * @param sobeOuDesce
	 * @return o número do próximo andar a percorrer. aqui, SEMPRE VAI TER UM ANDAR 
	 */
	public int escolherProximoAndar(int andarAtual, String sobeOuDesce)
	{
		int proximoAndarPercorrer = -1;
		if(sobeOuDesce.compareTo("sobe") == 0)
		{
			//é um andar acima
			for(int i = 0; i < andaresAPercorrer.size(); i++)
			{
				int umAndarPercorrer = andaresAPercorrer.get(i);
				if((proximoAndarPercorrer == -1 && umAndarPercorrer > andarAtual) || (umAndarPercorrer > andarAtual && umAndarPercorrer < proximoAndarPercorrer))
				{
					/*resumindo: o proximo andar a percorrer vai ser atualizado se:
					 * 1)ainda não achamos um próximo andar pra percorrer(-1)
					 * 2)achamos um andar acima do andar atual e que está mais próximo dele(ele é < o atual proximoAndarPercorrer)
					 */
					proximoAndarPercorrer = umAndarPercorrer;
				}
			}
			
		}
		else
		{
			//é um andar abaixo
			//é um andar acima
			for(int i = 0; i < andaresAPercorrer.size(); i++)
			{
				int umAndarPercorrer = andaresAPercorrer.get(i);
				if((proximoAndarPercorrer == -1 && umAndarPercorrer < andarAtual) || (umAndarPercorrer < andarAtual && umAndarPercorrer > proximoAndarPercorrer))
				{
					/*resumindo: o proximo andar a percorrer vai ser atualizado se:
					 * 1)ainda não achamos um próximo andar pra percorrer(-1)
					 * 2)achamos um andar abaixo do andar atual e que está mais próximo dele(ele é > o atual proximoAndarPercorrer)
					 */
					proximoAndarPercorrer = umAndarPercorrer;
				}
			}
		}
		
		return proximoAndarPercorrer;
	}
	


}
