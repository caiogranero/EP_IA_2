import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	
	Random random = new Random();
	
	public void makeTest(int START_WITH, int QTT_CLIENTS, int QTT_CARS, int GENERATION_LIMIT,
			int POP_SIZE, double PROB_CROSSOVER, double PROB_MUTATION) throws IOException{
		
		Population pop = new Population(GENERATION_LIMIT, POP_SIZE, QTT_CLIENTS, QTT_CARS);  //Inicia a população
		Fitness f = new Fitness(pop);
		Selector s = new Selector(f, pop);
		
		pop.startPop();
		
		for(int atualGeneration = 0; atualGeneration < pop.getGenerationLimit(); atualGeneration++){ //Critério de parada I
			System.out.println(atualGeneration+"ª Geração");
			pop.setGeneration(atualGeneration); //Define a geração atual
			f.calcFitness();  //Calcula o fitness individual da população
			f.calcTotalFitness();  //Calculando fitness total da população
			f.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
			s.orderPopulation(); //Ordena a população para realizar o elitismo
			
			Population pop_aux = pop.clone();
			Fitness f_aux = f.clone();
			
			for(int count = 0; count < GENERATION_LIMIT; count ++){
				if(random.nextDouble() < PROB_CROSSOVER){
					s.makeRoller(); //Criando a seleção por roleta
					s.selectChrmosomeIndex(2, START_WITH);  //Selecionando os individuos através da roleta 
					
					Crossover cros = new Crossover(s.getSelectedChrmosome()); 	//Inicio da operação de Crossover
					cros.simpleCrossover(pop.getQttCars(), pop.getQttClients()); //Crossover de x pontos
					s.updateGeneration(cros.getFinalCrossoverChrmosome()); //Atualiza a geração atual com os novos cromossomos
				}
				if(random.nextDouble() < PROB_MUTATION){  //Probabilidade de mutação.;
					//Inicio da operação de Mutação
					s.makeRoller(); //Criando a seleção por roleta
					s.selectChrmosomeIndex(1, START_WITH);  //Selecionando os individuos através da roleta 
					
					Mutation mut = new Mutation(s.getSelectedChrmosome(), QTT_CLIENTS); //Inicio da operação de mutação.
					mut.makeSimpleMutation();
					s.updateGeneration(mut.getMutationChrmosome());
				}
			}
			
			//Elitismo
			try{
				for(int i = 0; i < pop.getPopulationSize(); i++){
					if(f_aux.getFitnessVector()[i]> f.getFitnessVector()[i]){
						pop.updatePop(i, pop_aux.getPop()[i]);
					}
				}
				f.calcFitness();  //Calculando fitness de cada individuo
				f.calcTotalFitness();  //Calculando fitness total da população
				f.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
			}catch(Exception e){}
		
			pop.setNextGeneration(pop.getPop()); //Define os cromossomos que serão passados para a próxima geração.
			
			f.calcTotalFitness();	
			f.calcMedFitness();
			f.calcMaxFitness();
			f.calcMinFitness();
			System.out.println("Fitness Total:  "+f.getTotalFitness());
			System.out.println("Fitness Máximo: "+f.getMaxFitness());
			System.out.println("Fitness Médio:  "+f.getMedFitness());
			System.out.println("Fitness Minimo: "+f.getMinFitness());
			
			//pop.immediateReplacement(pop.getNextGeneration());
			System.out.println("\n-- x --\n");
			
		}
	}
}
