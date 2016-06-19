import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	
	Random random = new Random();
	
	public void makeTest(int START_WITH, int QTT_CARS, int GENERATION_LIMIT,
			int POP_SIZE, double PROB_CROSSOVER, double PROB_MUTATION, boolean fix) throws IOException{
		
		Population pop = new Population(GENERATION_LIMIT, POP_SIZE, QTT_CARS);  //Inicia a população
		Fitness f = new Fitness(pop);
		Selector s = new Selector(f, pop);
		
		//pop.readFile("A-n32-k5.txt");
		//pop.readFile("B-n31-k5.txt");
		pop.readFile("A-n63-k10.txt");
		//pop.readFile("teste.txt");
		
		pop.startPop();
		
		FileWriter arq = new FileWriter("teste-A-high-com-fix.txt");
		Writer write = new Writer(arq);
		write.header(PROB_CROSSOVER, PROB_MUTATION, GENERATION_LIMIT, POP_SIZE, START_WITH, fix, QTT_CARS, pop.getQttClients());
		write.headerExcel();
		
		for(int atualGeneration = 0; atualGeneration < pop.getGenerationLimit(); atualGeneration++){ //Critério de parada I
			System.out.println(atualGeneration+"ª Geração");
			pop.setGeneration(atualGeneration); //Define a geração atual
			f.calcFitness();  //Calcula o fitness individual da população
			f.calcTotalFitness();  //Calculando fitness total da população
			f.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
			
			Population pop_aux = pop.clone();
			Fitness f_aux = f.clone();
			
			for(int count = 0; count < POP_SIZE; count ++){
				if(random.nextDouble() < PROB_CROSSOVER){
					s.makeRoller(); //Criando a seleção por roleta
					s.selectChrmosomeIndex(2, START_WITH);  //Selecionando os individuos através da roleta 
					
					Crossover cros = new Crossover(s.getSelectedChrmosome()); 	//Inicio da operação de Crossover
					cros.simpleCrossover(pop.getQttCars(), pop.getQttClients()); //Crossover de x pontos
					s.updateGeneration(cros.getFinalCrossoverChrmosome()); //Atualiza a geração atual com os novos cromossomos
				}
				count++;
			}
			for(int count = 0; count < POP_SIZE; count++){
				if(random.nextDouble() < PROB_MUTATION){  //Probabilidade de mutação.;
					//Inicio da operação de Mutação
					s.makeRoller(); //Criando a seleção por roleta
					s.selectChrmosomeIndex(1, START_WITH);  //Selecionando os individuos através da roleta 
					
					Mutation mut = new Mutation(s.getSelectedChrmosome(), pop); //Inicio da operação de mutação.
					mut.makeSimpleMutation(fix);
					s.updateGeneration(mut.getMutationChrmosome());					
				}
			}
			
			int i = 0;
			//Concertar infactibilidades
			if(fix){
				pop.fixInfactibility();
				f.calcFitness();
			}
			
			//Eletismo
			if(START_WITH > 1){
				pop.orderPopulation(f); //Ordena a população para realizar o elitismo
				pop.makeEletism(pop_aux, f_aux, f);
			}
		
			pop.setNextGeneration(pop.getPop()); //Define os cromossomos que serão passados para a próxima geração.

			f.calcMedFitness();
			f.calcMaxFitness();
			f.calcMinFitness();
			System.out.println("Fitness Máximo: "+f.getMaxFitness());
			System.out.println("Fitness Médio:  "+f.getMedFitness());
			System.out.println("Fitness Minimo: "+f.getMinFitness());
			
			write.excel(f.getMedFitness(), f.getMaxFitness(), f.getMinFitness(), atualGeneration);
			
			pop.immediateReplacement(pop.getNextGeneration());
			System.out.println("\n-- x --\n");
			
		}
		arq.close();
	}
}
