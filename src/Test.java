import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	
	Random random = new Random();
	
	public void makeTest(int START_WITH, int QTT_CLIENTS, int QTT_CARS, int GENERATION_LIMIT,
			int POP_SIZE, double PROB_CROSSOVER, double PROB_MUTATION) throws IOException{
		
		Population pop = new Population(GENERATION_LIMIT);  //Inicia a população
		Selector bi = new Selector();
		
		pop.startPop(POP_SIZE, QTT_CLIENTS, QTT_CARS);  //Starta a população
		
		for(int atualGeneration = 0; atualGeneration < pop.getGenerationLimit(); atualGeneration++){ //Critério de parada I
			System.out.println(atualGeneration+"ª Geração");
			pop.setGeneration(atualGeneration); //Define a geração atual				
			Population pop_aux = pop.clone();
			Selector bi_aux = bi.clone();		
			
			bi.calcFitness(pop);  //Calculando fitness de cada individuo
			bi.calcTotalFitness();  //Calculando fitness total da população
			bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
			bi.orderPopulation(pop); //Ordena a população para realizar o elitismo
				
			for(int count = 0; count < GENERATION_LIMIT; count ++){
				if(random.nextDouble() < PROB_CROSSOVER){
					bi.makeRoller(); //Criando a seleção por roleta
					bi.selectChrmosomeIndex(2, pop, START_WITH);  //Selecionando os individuos através da roleta 
				
					Crossover cros = new Crossover(bi.getSelectedChrmosome()); 	//Inicio da operação de Crossover
					cros.divCrossover(1, pop.getSizeChrmosome()); //Crossover de x pontos
					bi.updateGeneration(cros.getFinalCrossoverChrmosome(), pop); //Atualiza a geração atual com os novos cromossomos
				}
					
				if(random.nextDouble() < PROB_MUTATION){  //Probabilidade de mutação.;
					//Inicio da operação de Mutação
					bi.makeRoller(); //Criando a seleção por roleta
					bi.selectChrmosomeIndex(1, pop, START_WITH);  //Selecionando os individuos através da roleta 
					
					Mutation mut = new Mutation(bi.getSelectedChrmosome()); //Inicio da operação de mutação.
					mut.makeSimpleMutation();
					bi.updateGeneration(mut.getMutationChrmosome(), pop);
				}
			}
				
			//Pega os melhores
			try{
				for(int i = 0; i < pop.getPopulationSize(); i++){
					if(bi_aux.getFitnessVector()[i]> bi.getFitnessVector()[i]){
						pop.updatePop(i, pop_aux.getPop()[i]);
					}
				}
				bi.calcFitness(pop);  //Calculando fitness de cada individuo
				bi.calcTotalFitness();  //Calculando fitness total da população
				bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
			}catch(Exception e){}
		}
		
		pop.setNextGeneration(pop.getPop()); //Define os cromossomos que serão passados para a próxima geração.
		pop.immediateReplacement(pop.getNextGeneration());
		System.out.println("\n-- x --\n");
	}
}
