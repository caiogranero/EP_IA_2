import java.util.Random;

public class Fitness {

	Population population;
	float fitnessVector[]; //Armazena o fitness de cada individuo
	float totalFitness; //Armazena a soma de todos os fitness
	float porcentFitnessVector[]; //Armazena o porcentual dos fitness de cada individuo
	float maxFitness;
	float minFitness;
	float medFitness;
	
	public Fitness(Population pop){
		this.population = pop;
	}
	
	public float[] fitnessFunction(){
		float totalCost[] = new float[population.getPopulationSize()];
		for(int nSolution = 0; nSolution < population.getPopulationSize(); nSolution++){
			for(int nCar = 0; nCar < population.getQttCars(); nCar++){
				totalCost[nSolution] = totalCost(carCost(population.getPop()[nSolution]));
			}
		}
		return totalCost;
	}
	
	//Calcula o custo total de todas as viagens, para todos os carros de uma solução.
	public int totalCost(int[] countCar){
		int totalCost = 0;
		for(int i = 0; i < countCar.length; i++){
			totalCost = countCar[i] - totalCost;
		}
		return totalCost;
	}
	
	//Calcula o custo de ir em um cliente para o outro, seguindo toda a rota.
	public int[] carCost(String[] vetor){
		int countCar[] = new int[population.getQttCars()];
		int m = 0;
		for(int j = 1; j < vetor.length; j++){
			if(vetor[j] != "*"){
				countCar[m] -= Integer.parseInt(vetor[j]);
			} else {
				j++;
				m++;
			}
		}
		return countCar; 
	} 
	
	/**
	 * Calcula o fitness de cada cromossomo
	 * @param population população que seŕa calculada o fitness
	 * @return
	 */
	public int calcFitness(){
		
		String pop[] = population.getPop()[0];

		float fitness[] = new float[population.getPopulationSize()];
		
	    for(int nChrmosome = 0; nChrmosome < population.getPopulationSize(); nChrmosome++){
	    	fitness[nChrmosome] = fitnessFunction()[nChrmosome];
	    }
	    setFitnessVector(fitness);
		return 0;
	}

	/**
	 * Calcula o maior fitness da população.
	 */
	public void calcMaxFitness(){
		float maxFitness = getFitnessVector()[0];		
		for (int i = 0; i < getFitnessVector().length; i++) {
			if(getFitnessVector()[i] > maxFitness){
				maxFitness = getFitnessVector()[i];
			}
		}
		setMaxFitness(maxFitness);
	}
	
	/**
	 * Calcula o menor fitness da população.
	 */
	public void calcMinFitness(){
		float minFitness = getFitnessVector()[0]; 
		for (int i = 0; i < getFitnessVector().length; i++) {
			if(getFitnessVector()[i] < minFitness){
				minFitness = getFitnessVector()[i];
			}
		}
		setMinFitness(minFitness);
	}
	
	/*
	 * Calcula a media de todos os fitness da população
	 */
	public void calcMedFitness(){
		float medFitness = getTotalFitness()/getFitnessVector().length;
		setMedFitness(medFitness);
	}
	
	/**
	 * Calcula o porcentual do fitness, (fitness individual/fitness total) e armazena em um vetor
	 */
	public void calcPorcentFitness(){
		float porcentFitness[] = new float[getFitnessVector().length];
		for(int nChrmosome = 0; nChrmosome < getFitnessVector().length; nChrmosome++){
			porcentFitness[nChrmosome] = ((getFitnessVector()[nChrmosome] * 100) / (getTotalFitness()));
		}
		setPorcentFitnessVector(porcentFitness);
	}
	
	/**
	 * Calcula o fitness total da população.
	 */
	public void calcTotalFitness(){
		float totalFitness = 0;
		for (int i = 0; i < getFitnessVector().length; i++) {
			totalFitness += getFitnessVector()[i];
		}
		setTotalFitness(totalFitness);
	}
	
	/**
	 * Imprime o fitness de cada cromossomo.
	 */
	public void printFitness(){
		for (int i = 0; i < getFitnessVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness: "+ fitnessVector[i]);
		}
	}
	
	/**
	 * Imprime a porcentagem do fitness de cada cromossomo.
	 */
	public void printFitnessPorcent(){
		for (int i = 0; i < getPorcentFitnessVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness porcentual: "+ porcentFitnessVector[i]);
		}
	}
	
	public float[] getFitnessVector() {
		return fitnessVector;
	}

	public void setFitnessVector(float[] fitness) {
		this.fitnessVector = fitness;
	}
	
	public float getTotalFitness() {
		return totalFitness;
	}

	public void setTotalFitness(float totalFitness) {
		this.totalFitness = totalFitness;
	}
	
	public float[] getPorcentFitnessVector() {
		return porcentFitnessVector;
	}

	public void setPorcentFitnessVector(float[] porcentFitnessVector) {
		this.porcentFitnessVector = porcentFitnessVector;
	}
	
	public float getMaxFitness() {
		return maxFitness;
	}

	public void setMaxFitness(float maxFitness) {
		this.maxFitness = maxFitness;
	}

	public float getMinFitness() {
		return minFitness;
	}

	public void setMinFitness(float minFitness) {
		this.minFitness = minFitness;
	}

	public float getMedFitness() {
		return medFitness;
	}

	public void setMedFitness(float medFitness) {
		this.medFitness = medFitness;
	}
}