package com.example.projetoelevadortemporeal;

import java.util.HashMap;
import java.util.LinkedList;

import elevador.ArrivalSensorInterface;
import elevador.ElevatorControl;
import elevador.ElevatorManager;
import elevador.ElevatorStatusAndPlan;
import elevador.ElevatorButtonInterface;
import elevador.InterfaceDaPorta;
import escalonador.SingletonInterfaceEscalonador;

import andar.FloorButtonInterface;
import andar.SingletonInterfaceSubsistemaDeAndares;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	private HashMap<Integer,LinkedList<Boolean>> elevadoresESeBotoesInternosEstaoApertados; //uma lista de x componentes(depende de quantos andares) que diz se o botao para o andar N está apertado ou nao 
	private int quantosElevadores = 2; //quantos elevadores a aplicacao deveria ter																						//a chave eh o elevador
	private int quantosAndares = 9;
	
	private LinkedList<ElevatorControl> elevatorControls;
	private LinkedList<ElevatorManager> elevatorManagers;
	private LinkedList<ElevatorButtonInterface> elevatorButtonInterfaces;
	private FloorButtonInterface interfaceBotoesDoAndar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FachadaInterfaceGrafica.getInstance().setActivityTelaElevador(this);
		
		this.elevatorControls = new LinkedList<ElevatorControl>();
		this.elevatorManagers = new LinkedList<ElevatorManager>();
		this.interfaceBotoesDoAndar = new FloorButtonInterface();
		this.elevatorButtonInterfaces = new LinkedList<ElevatorButtonInterface>();
		this.elevadoresESeBotoesInternosEstaoApertados = new HashMap<Integer, LinkedList<Boolean>>();
		
		for(int i = 0; i < quantosElevadores; i++)
		{
			//vamos preparar os botoes internos dos elevadores
			LinkedList<Boolean> botoesInternosElevadorQueEstaoApertados = new LinkedList<Boolean>();
			for(int j = 0; j < quantosAndares; j++)
			{
				botoesInternosElevadorQueEstaoApertados.add(false);
			}
			
			this.elevadoresESeBotoesInternosEstaoApertados.put(i + 1, botoesInternosElevadorQueEstaoApertados);
			
			//agora vamos criar os elevadores e etc - parte fora da interface
			
			ElevatorStatusAndPlan umElevatorStatusAndPlan = new ElevatorStatusAndPlan();
			umElevatorStatusAndPlan.setSobeOuDesceOuParado("parado");
			
			ElevatorControl umElevatorControl = new ElevatorControl(this, umElevatorStatusAndPlan, i + 1);
			this.elevatorControls.add(umElevatorControl);
			
			ElevatorManager umElevatorManager = new ElevatorManager(umElevatorStatusAndPlan, umElevatorControl, 0);
			this.elevatorManagers.add(umElevatorManager);
			
			ElevatorButtonInterface umElevatorButtonInterface = new ElevatorButtonInterface(umElevatorManager);
			this.elevatorButtonInterfaces.add(umElevatorButtonInterface);
			
			for(int k = 0; k < quantosAndares; k++)
			{
				ArrivalSensorInterface umArrivalSensorInterface = new ArrivalSensorInterface(this, umElevatorControl, k, k * 10, umElevatorManager);
				umArrivalSensorInterface.start();
				int idElevador = i + 1;
				String nomeIdBotaoCimaDoAndar = "botao_subir_parte_fora_andar" + k + "_" + idElevador;
				int idBotaoSobe = getResources().getIdentifier(nomeIdBotaoCimaDoAndar, "id", getPackageName());
				ImageView botaoSobeDoAndar = (ImageView) findViewById(idBotaoSobe);
				botaoSobeDoAndar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						apertarBotaoCimaBaixoAndar(v);
					}
				});
				//fazer isso pros botoes de descer tb
				String nomeIdBotaoDesceDoAndar = "botao_descer_parte_fora_andar" + k + "_" + idElevador;
				int idBotaoDesce = getResources().getIdentifier(nomeIdBotaoDesceDoAndar, "id", getPackageName());
				ImageView botaoDesceDoAndar = (ImageView) findViewById(idBotaoDesce);
				botaoDesceDoAndar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						apertarBotaoCimaBaixoAndar(v);
					}
				});
			}
			
			
			//fim da criação dos elevadores
			
			//comeco de abrir porta elevadores do andar zero
			this.elevatorControls.get(i).abrirPortaDoElevadorInicial();
			//fim de abrir porta elevadores andar zero
		}
		
		SingletonInterfaceEscalonador escalonador = SingletonInterfaceEscalonador.getInstancia();
		escalonador.setElevatorManagers(elevatorManagers);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void apertarBotaoInternoElevador(View v)
	{
		String elevadorEAndar = (String) v.getTag(); 
		String[] elevadorEAndarArray = elevadorEAndar.split(";"); //ex: elevador=1;andar=0
		String elevadorEmString = elevadorEAndarArray[0].split("=")[1];
		String andarEmString = elevadorEAndarArray[1].split("=")[1];
		int elevador = Integer.valueOf(elevadorEmString);
		int andar = Integer.valueOf(andarEmString);
		
		LinkedList<Boolean> elevadoresDesseAndarEstaoApertados = this.elevadoresESeBotoesInternosEstaoApertados.get(elevador);
		boolean botaoEstahAceso = elevadoresDesseAndarEstaoApertados.get(andar);
		
		if(botaoEstahAceso == false)
		{
			elevadoresDesseAndarEstaoApertados.set(andar, true);
			this.elevadoresESeBotoesInternosEstaoApertados.put(elevador, elevadoresDesseAndarEstaoApertados);
			
			//REALIZAR PROCEDIMENTO CLICOU BOTAO ANDAR
			if(v instanceof Button)
			{
				Button botaoV = (Button) v;
				String idDoBotaoComoString = v.getResources().getResourceName(v.getId());
				idDoBotaoComoString = idDoBotaoComoString.replace("com.example.projetoelevadortemporeal:id/", "");
				int numElevadorAssociado = Integer.valueOf(idDoBotaoComoString.substring(idDoBotaoComoString.length() - 1));
				String idDoBotaoComoStringSemUltimoDigito = idDoBotaoComoString.substring(0, idDoBotaoComoString.length() - 1);
				String numAndarPorExtenso = idDoBotaoComoStringSemUltimoDigito.replace("botao", "");
				String nomeImagemNovaBotao = idDoBotaoComoStringSemUltimoDigito + "_aceso";
				int idImagemNovaBotao = getResources().getIdentifier(nomeImagemNovaBotao, "drawable", getPackageName());
				botaoV.setBackgroundResource(idImagemNovaBotao);
				
				ElevatorButtonInterface interfaceBotoesDoElevadorChamado = this.elevatorButtonInterfaces.get(numElevadorAssociado - 1); 
				int numeroAndarSolicitado = 0;
				if(numAndarPorExtenso.compareTo("zero") == 0)
				{
					numeroAndarSolicitado = 0;
				}
				else if(numAndarPorExtenso.compareTo("um") == 0)
				{
					numeroAndarSolicitado = 1;
				}
				else if(numAndarPorExtenso.compareTo("dois") == 0)
				{
					numeroAndarSolicitado = 2;
				}
				else if(numAndarPorExtenso.compareTo("tres") == 0)
				{
					numeroAndarSolicitado = 3;
				}
				else if(numAndarPorExtenso.compareTo("quatro") == 0)
				{
					numeroAndarSolicitado = 4;
				}
				else if(numAndarPorExtenso.compareTo("cinco") == 0)
				{
					numeroAndarSolicitado = 5;
				}
				else if(numAndarPorExtenso.compareTo("seis") == 0)
				{
					numeroAndarSolicitado = 6;
				}
				else if(numAndarPorExtenso.compareTo("sete") == 0)
				{
					numeroAndarSolicitado = 7;
				}
				else if(numAndarPorExtenso.compareTo("oito") == 0)
				{
					numeroAndarSolicitado = 8;
				}
				interfaceBotoesDoElevadorChamado.pressionouBotaoDoAndar(numeroAndarSolicitado);
				
			}
			
		}
	}
	public void apertarBotaoCimaBaixoAndar(View v)
	{
		if(v instanceof ImageView)
		{
			ImageView botaoCimaDesce = (ImageView) v;
			String idDoBotaoComoString = v.getResources().getResourceName(v.getId());
			idDoBotaoComoString = idDoBotaoComoString.replace("com.example.projetoelevadortemporeal:id/", "");
			String usuarioSobeOuDesce = "sobe";
			String idDoBotaoSohAndarEElevador;
			if(idDoBotaoComoString.contains("subir"))
			{
				usuarioSobeOuDesce = "sobe";
				botaoCimaDesce.setImageResource(R.drawable.botao_cima_aceso);
				idDoBotaoSohAndarEElevador = idDoBotaoComoString.replace("botao_subir_parte_fora_andar", "");
			}
			else
			{
				usuarioSobeOuDesce = "desce";
				botaoCimaDesce.setImageResource(R.drawable.botao_baixo_aceso);
				idDoBotaoSohAndarEElevador = idDoBotaoComoString.replace("botao_descer_parte_fora_andar", "");
			}
			
			String sohONumeroDoAndar = "";
			/*int percorredoridDoBotaoSohAndarEElevador = 0;
			while(idDoBotaoSohAndarEElevador.charAt(percorredoridDoBotaoSohAndarEElevador) == '_')
			{
				sohONumeroDoAndar = sohONumeroDoAndar + String.valueOf(idDoBotaoSohAndarEElevador.charAt(percorredoridDoBotaoSohAndarEElevador));
				percorredoridDoBotaoSohAndarEElevador = percorredoridDoBotaoSohAndarEElevador + 1;
			}*/
			String [] idNumeroDoAndarEElevadorSplitado = idDoBotaoSohAndarEElevador.split("_");
			sohONumeroDoAndar = idNumeroDoAndarEElevadorSplitado[0];
			int numeroDoAndarInteiro = Integer.valueOf(sohONumeroDoAndar);
			this.interfaceBotoesDoAndar.usuarioApertouBotaoCimaOuBaixo(numeroDoAndarInteiro, usuarioSobeOuDesce);
			
		}
	}
	
	//metodo chamado pela FachadaInterfaceGrafica assim que o elevador comeca a andar (quem manda requisicao ao Fachada eh o InterfaceDaPorta)
	public void fecharPortaElevadorEAndar(int andarAtual, int idElevador)
	{
		String nomeIdAndarPraFecharPorta = "porta_parte_fora_andar" + andarAtual + "_" + idElevador;
		Resources res = getResources();
		int idPortaAndarPraFechar = res.getIdentifier(nomeIdAndarPraFecharPorta, "id", this.getPackageName());
		ImageView imageviewPortaAndar = (ImageView) findViewById(idPortaAndarPraFechar);
		imageviewPortaAndar.setImageResource(R.drawable.portaelevador_maior);
		//agora, iremos fechar a porta que fica na visao de dentro do elevador
		String nomeIdPortaDentroElevadorFechar = "porta_dentro_elevador" + idElevador;
		int idPortaDentroElevadorFechar = res.getIdentifier(nomeIdPortaDentroElevadorFechar, "id", this.getPackageName());
		ImageView imageviewPortaDentroElevadorFechar = (ImageView) findViewById(idPortaDentroElevadorFechar);
		imageviewPortaDentroElevadorFechar.setImageResource(R.drawable.portaelevador);
	}
	public void desligarVisorSobeDesceDoAndar(int numAndarDesligar, int idElevador)
	{
		String nomeIdVisorSobeDescePraDesligar = "visor_parte_fora_andar" + numAndarDesligar + "_" + idElevador;
		Resources res = getResources();
		int idVisorSobeDescePraDesligar = res.getIdentifier(nomeIdVisorSobeDescePraDesligar, "id", this.getPackageName());
		ImageView imageviewVisorSobeDescePraDesligar = (ImageView) findViewById(idVisorSobeDescePraDesligar);
		imageviewVisorSobeDescePraDesligar.setImageResource(R.drawable.visor_elevador_parado);
	}
	
	//metodo chamado pela FachadaInterfaceGrafica
	public void atualizarInterfaceElevadorChegouNumAndar(int andar,int idElevador, String elevadorSobeOuDesce)
	{
		for(int i = 0; i < this.quantosAndares; i++)
		{
			String nomeIdRelativeLayoutDeUmAndar = "parte_fora_andar" + i + "_" + idElevador;
			Resources res = getResources();
			int idRelativeLayoutDeUmAndar = res.getIdentifier(nomeIdRelativeLayoutDeUmAndar, "id", this.getPackageName());
			RelativeLayout relativeLayoutUmAndar = (RelativeLayout) findViewById(idRelativeLayoutDeUmAndar);
			
			if(i == andar)
			{
				//eh o andar que o elevador acabou de chegar
				relativeLayoutUmAndar.setBackgroundResource(R.drawable.parte_fora_elevador_com_elevador_movendo_no_andar);
			}
			else
			{
				relativeLayoutUmAndar.setBackgroundResource(R.drawable.parte_fora_elevador);
			}
			
			//alterar visor do andar atual do elevador 
			String nomeIdTextViewVisorDeUmAndar = "numero_andar_visor_parte_fora_andar" + i + "_" + idElevador;
			Resources res2 = getResources();
			int idTextViewVisorDeUmAndar = res2.getIdentifier(nomeIdTextViewVisorDeUmAndar, "id", this.getPackageName());
			TextView textViewVisorDeUmAndar = (TextView) findViewById(idTextViewVisorDeUmAndar);
			textViewVisorDeUmAndar.setText(String.valueOf(andar));
			
			//falta alterar o visor do sobe ou desce do elevador
			String nomeIdImageViewVisorParteForaAndar = "visor_parte_fora_andar" + i + "_" + idElevador;
			Resources res3 = getResources();
			int idImageViewVisorParteForaAndar = res3.getIdentifier(nomeIdImageViewVisorParteForaAndar, "id", this.getPackageName());
			ImageView imageViewVisorParteForaAndar = (ImageView) findViewById(idImageViewVisorParteForaAndar);
			if(elevadorSobeOuDesce.compareTo("sobe") == 0)
			{
				imageViewVisorParteForaAndar.setImageResource(R.drawable.visor_elevador_subindo);
			}
			else
			{
				
				imageViewVisorParteForaAndar.setImageResource(R.drawable.visor_elevador_descendo);
			}
		}
	}
	
	//funcao chamada pela Fachada, para ligar a direcao. Acontece quando um elevador para num andar
	public void ligarVisorSobeDesceDoAndar(String sobeOuDesceOuParado,int numAndarLigar, int idElevador)
	{
		String nomeIdImageViewVisorParteForaAndar = "visor_parte_fora_andar" + numAndarLigar + "_" + idElevador;
		Resources res3 = getResources();
		int idImageViewVisorParteForaAndar = res3.getIdentifier(nomeIdImageViewVisorParteForaAndar, "id", this.getPackageName());
		ImageView imageViewVisorParteForaAndar = (ImageView) findViewById(idImageViewVisorParteForaAndar);
		if(sobeOuDesceOuParado.compareTo("sobe") == 0)
		{
			imageViewVisorParteForaAndar.setImageResource(R.drawable.visor_elevador_subindo);
		}
		else
		{
			
			imageViewVisorParteForaAndar.setImageResource(R.drawable.visor_elevador_descendo);
		}
	}
	
	//funcao chamada pela Fachada. Acontece quando um elevador parou num andar
	public void desligarVisorAndarAtual(int andarAtual, int idElevador)
	{
		String nomeIdTextViewVisorDeUmAndar = "numero_andar_visor_parte_fora_andar" + andarAtual + "_" + idElevador;
		Resources res2 = getResources();
		int idTextViewVisorDeUmAndar = res2.getIdentifier(nomeIdTextViewVisorDeUmAndar, "id", this.getPackageName());
		TextView textViewVisorDeUmAndar = (TextView) findViewById(idTextViewVisorDeUmAndar);
		textViewVisorDeUmAndar.setText("");
	}
	
	//metodo chamado pela fachada. Eh para assim que o elevador parar, o backgorund dos elevadores ficar normal
	public void pararElevador(int andar, int idElevador)
	{
		String nomeIdRelativeLayoutDeUmAndar = "parte_fora_andar" + andar + "_" + idElevador;
		Resources res = getResources();
		int idRelativeLayoutDeUmAndar = res.getIdentifier(nomeIdRelativeLayoutDeUmAndar, "id", this.getPackageName());
		RelativeLayout relativeLayoutUmAndar = (RelativeLayout) findViewById(idRelativeLayoutDeUmAndar);
		
		relativeLayoutUmAndar.setBackgroundResource(R.drawable.parte_fora_elevador);
	}
	
	//metodo chamado pela fachada. Eh para assim que o elevador parar, abrir sua porta e desligar o botão de dentro do elevador
	public void abrirPortaNaInterfaceEDesligarLampadaBotaoDentroElevador(int andarAtual, int idElevador)
	{
		String nomeIdAndarPraAbrirPorta = "porta_parte_fora_andar" + andarAtual + "_" + idElevador;
		Resources res = getResources();
		int idPortaAndarPraAbrir = res.getIdentifier(nomeIdAndarPraAbrirPorta, "id", this.getPackageName());
		ImageView imageviewPortaAndar = (ImageView) findViewById(idPortaAndarPraAbrir);
		imageviewPortaAndar.setImageResource(R.drawable.portaelevador_maior_aberta);
		//agora, iremos abrir a porta que fica na visao de dentro do elevador
		String nomeIdPortaDentroElevadorAbrir = "porta_dentro_elevador" + idElevador;
		int idPortaDentroElevadorAbrir = res.getIdentifier(nomeIdPortaDentroElevadorAbrir, "id", this.getPackageName());
		ImageView imageviewPortaDentroElevadorAbrir = (ImageView) findViewById(idPortaDentroElevadorAbrir);
		imageviewPortaDentroElevadorAbrir.setImageResource(R.drawable.portaelevador_aberta);
		
		//agora, iremos fechar a porta que fica na visao de dentro do elevador
		String nomeIdBotaoDesligarDentroElevador = "botao";
		if(andarAtual  == 0)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "zero";
		}
		else if(andarAtual  == 1)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "um";
		}
		else if(andarAtual  == 2)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "dois";
		}
		else if(andarAtual  == 3)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "tres";
		}
		else if(andarAtual  == 4)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "quatro";
		}
		else if(andarAtual  == 5)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "cinco";
		}
		else if(andarAtual  == 6)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "seis";
		}
		else if(andarAtual  == 7)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "sete";
		}
		else if(andarAtual  == 8)
		{
			nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + "oito";
		}
		String nomeParaImaqemBotaoApagado = nomeIdBotaoDesligarDentroElevador;
		int idImagemNovaBotao = getResources().getIdentifier(nomeParaImaqemBotaoApagado, "drawable", getPackageName());
		nomeIdBotaoDesligarDentroElevador = nomeIdBotaoDesligarDentroElevador + String.valueOf(idElevador);
		int idBotaoDesligarDentroElevador = res.getIdentifier(nomeIdBotaoDesligarDentroElevador, "id", this.getPackageName());
		Button botaoDesligarDentroElevador = (Button) findViewById(idBotaoDesligarDentroElevador);
		botaoDesligarDentroElevador.setBackgroundResource(idImagemNovaBotao);
		
		//vamos agora desligar os botoes sobe/desce do andar que o elevador parou.
		for(int i = 0; i < quantosElevadores; i++)
		{
			int idElevadorDesligarSobeDesce = i + 1; 
			String nomeIdBotaoSobeDoAndarElevador1 = "botao_subir_parte_fora_andar" + String.valueOf(andarAtual) + "_" + String.valueOf(idElevadorDesligarSobeDesce);
			int idBotaoSobeDoAndarElevador1 = res.getIdentifier(nomeIdBotaoSobeDoAndarElevador1, "id", this.getPackageName());
			ImageView imageviewBotaoSobeDoAndarElevador1 = (ImageView) findViewById(idBotaoSobeDoAndarElevador1);
			imageviewBotaoSobeDoAndarElevador1.setImageResource(R.drawable.botao_cima_apagado);
			
			String nomeIdBotaoDesceDoAndarElevador1 = "botao_descer_parte_fora_andar" + String.valueOf(andarAtual) + "_" + String.valueOf(idElevadorDesligarSobeDesce);
			int idBotaoDesceDoAndarElevador1 = res.getIdentifier(nomeIdBotaoDesceDoAndarElevador1, "id", this.getPackageName());
			ImageView imageviewBotaoDesceDoAndarElevador1 = (ImageView) findViewById(idBotaoDesceDoAndarElevador1);
			imageviewBotaoDesceDoAndarElevador1.setImageResource(R.drawable.botao_baixo_apagado);
		}
		
		
		
	}
}
