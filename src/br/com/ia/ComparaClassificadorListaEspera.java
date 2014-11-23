package br.com.ia;

import java.io.FileReader;


import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author
 * Classe que compara o resultado da utilização do NaiveBayes e do Id3 (Arvore de decisao)
 *
 */
public class ComparaClassificadorListaEspera {

	/**
	 * Exibe o resultado de classificacao correto atraves do lista-espera-gabarito,
	 * utilizando uma base de teste para exibir os resultados aplicando o NaiveBayes e o Id3.
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		FileReader arffTreino = new FileReader("lista-espera-treino.arff");
		Instances conjuntoTreino = new Instances(arffTreino);
		conjuntoTreino.setClassIndex(4);
		
		FileReader arffTeste = new FileReader("lista-espera-teste.arff");
		Instances conjuntoTeste = new Instances(arffTeste);
		conjuntoTeste.setClassIndex(4);
		
		FileReader arffGabarito = new FileReader("lista-espera-gabarito.arff");
		Instances conjuntoGabarito = new Instances(arffGabarito);
		conjuntoGabarito.setClassIndex(4);
		
		
		Id3 arvore = new Id3();
		arvore.buildClassifier(conjuntoTreino);
		
		NaiveBayes naive = new NaiveBayes();
		naive.buildClassifier(conjuntoTreino);
		
		System.out.println("Classe Real;Predição Árvore;Predição Naive");
		for(int i = 0; i < 33; i++){
			Instance exemploTeste = conjuntoTeste.instance(i);
			double resultArvore = arvore.classifyInstance(exemploTeste);
			double resultNaive = naive.classifyInstance(exemploTeste);
		
			Instance gabarito = conjuntoGabarito.instance(i);
			
			double rotuloReal = gabarito.value(4);
			double rotuloArvore = resultArvore;
			double rotuloNaive = resultNaive;
			System.out.println(rotuloReal + ";" + rotuloArvore + ";" + rotuloNaive);
			
		}
	}
}	