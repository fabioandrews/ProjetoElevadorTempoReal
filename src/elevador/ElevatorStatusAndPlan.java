package elevador;

import java.util.LinkedList;

public class ElevatorStatusAndPlan 
{
	private String sobeOuDesceOuParado; //valores: sobe ou desce ou parado
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

	public String getSobeOuDesceOuParado() {
		return sobeOuDesceOuParado;
	}

	public void setSobeOuDesceOuParado(String sobeOuDesceOuParado) {
		this.sobeOuDesceOuParado = sobeOuDesceOuParado;
	}


}
