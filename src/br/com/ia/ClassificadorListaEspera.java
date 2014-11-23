package br.com.ia;

import java.io.FileReader;

import javax.swing.JOptionPane;

import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author 
 * Executa classifica��o de uma crian�a informando se ela j� deve receber vaga,
 * continuar aguardando na lista, ou se j� deve ser descartada de receber a vaga.
 *
 */
public class ClassificadorListaEspera {

	/**
	 * Executa a classificacao utilizando o Id3 (�rvore de decisao) da biblioteca Weka.
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		FileReader arffTreino = new FileReader("lista-espera.arff");
		Instances conjuntoTreino = new Instances(arffTreino);
		conjuntoTreino.setClassIndex(4);
				
		Id3 arvore = new Id3();
		arvore.buildClassifier(conjuntoTreino);
		
		Instance exemploTeste = obtemInstancia(conjuntoTreino);
		double result = arvore.classifyInstance(exemploTeste);
		JOptionPane.showMessageDialog(null, convertClasse(result));
	}
	
	/**
	 * Converte o valor double retornado da classificacao em seu texto correspondente.
	 * @param value valor retornado pelo algoritmo de classificacao.
	 * @return
	 */
	private static String convertClasse(double value) {
		if (value == 0) {
			return "Exemplo j� recebe vaga!";
		} else if (value == 1) {
			return "Exemplo n�o recebe vaga!";
		} else {
			return "Exemplo deve continuar aguardando!";
		}
	}
	
	/**
	 * Controi uma instancia com os dados recebidos da tela.
	 * @param conjuntoDados base de dados de treino
	 * @param maeTrabalha parametro binario se mae trabalha ou nao
	 * @param possuiDef parametro binario se crian�a possui deficiencia ou nao
	 * @param ehAbrig parametro binario se crian�a mora em abrigo ou nao
	 * @param renda parametro de faixas de rendas da casa
	 * @return
	 */
	public static Instance constroiInstancia(Instances conjuntoDados, int maeTrabalha, int possuiDef, int ehAbrig, int renda) {
		
		Instance instancia = new Instance(5);
		instancia.setDataset(conjuntoDados);

		instancia.setValue(0, maeTrabalha == 1 ? "sim" : "nao");
		instancia.setValue(1, possuiDef == 1 ? "sim" : "nao");
		instancia.setValue(2, ehAbrig == 1 ? "sim" : "nao");
		instancia.setValue(3, converteRenda(renda));
		return instancia;
	}
	
	private static String converteRenda(int value) {
		if (value == 1) {
			return "ate-1-salario";
		} else if (value == 2) {
			return "ate-2-salarios";
		} else {
			return "maior-2-salarios";
		}
	}
	
	/**
	 * Solicita o preenchimento de dados e retorna uma instancia carregada com eles.
	 * @param conjuntoDados base de dados de treino.
	 * @return instancia que sera classificada.
	 */
	public static Instance obtemInstancia(Instances conjuntoDados) {
		int maeTrabalha = constroiPerguntasBinarias("A m�e trabalha? Digite: \n1 - Sim\n2 - N�o");
		int possuiDeficiencia = constroiPerguntasBinarias("A crian�a possui defici�ncia? Digite: \n1 - Sim\n2 - N�o");
		int ehAbrigado = constroiPerguntasBinarias("A crian�a/familia moram em abrigo? Digite: \n1 - Sim\n2 - N�o");

		int renda = 0;
		while (renda == 0 || renda > 3) {
			try {
				renda = Integer.parseInt(JOptionPane.showInputDialog(null, "A renda da casa �: \n1 - At� 1 sal�rio.\n2 - At� 2 sal�rios.\n3- Mais que 2 sal�rios.", "", JOptionPane.PLAIN_MESSAGE));
				if (renda == JOptionPane.CLOSED_OPTION) {
					System.exit(0);
				}
			} catch (NumberFormatException ne) {
				JOptionPane.showMessageDialog(null, "Op��es s�o apenas n�meros!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Digite uma op��o valida!");
			}
		}

		return constroiInstancia(conjuntoDados, maeTrabalha, possuiDeficiencia, ehAbrigado, renda);
		
	}
	
	/**
	 * Constroi tela de pergunta para parametros que dependem de respostas binarias.
	 * @param texto mensagem da pergunta
	 * @return resposta enviada pelo usuario
	 */
	public static int constroiPerguntasBinarias(String texto) {
		int valor = 0;
		while (valor == 0 || valor > 2) {
			try {
				valor = Integer.parseInt(JOptionPane.showInputDialog(null, texto, "", JOptionPane.PLAIN_MESSAGE));
				if (valor == JOptionPane.CLOSED_OPTION) {
					System.exit(0);
				}
			} catch (NumberFormatException ne) {
				JOptionPane.showMessageDialog(null, "Op��es s�o apenas n�meros!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Digite uma op��o valida!");
			}
		}
		return valor;
	}
}
