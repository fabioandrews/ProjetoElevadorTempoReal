package andar;

import java.util.LinkedList;

public class SingletonInterfaceSubsistemaDeAndares 
{
	private LinkedList<Andar> andares;
	private static SingletonInterfaceSubsistemaDeAndares instancia;
	private static int quantosAndaresTerao = 9;
	
	private SingletonInterfaceSubsistemaDeAndares()
	{
		this.andares = new LinkedList<Andar>();
		
		for(int i = 0; i < quantosAndaresTerao; i++)
		{
			andares.add(new Andar(i));
		}
	}
	
	public static SingletonInterfaceSubsistemaDeAndares getInstancia()
	{
		if(instancia == null)
		{
			instancia = new SingletonInterfaceSubsistemaDeAndares();
		}
		
		return instancia;
	}
	
	
	public void desligarVisorSubindoEDescendoNoAndar(int andar)
	{
		Andar andarDesligar = this.andares.get(andar);
		andarDesligar.desligarVisorSubindoEDescendoNoAndar();
	}
	
	

}
