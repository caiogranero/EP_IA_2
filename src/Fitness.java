import java.awt.Point;

public class Fitness implements Cloneable{

	Population pop;
	float fitnessVector[]; //Armazena o fitness de cada individuo
	float totalFitness; //Armazena a soma de todos os fitness
	float porcentFitnessVector[]; //Armazena o porcentual dos fitness de cada individuo
	float maxFitness;
	float minFitness;
	float medFitness;
	
	public Fitness(Population pop){
		this.pop= pop;
	}
		
	Point first = null;
	Point second = null;
	boolean needSave = false;
	
	public float overcapacity(int solution, int nClient, String population[][]){
		int sumWeight = 0;
		float over = 0;
		for(int i = nClient; i > 0; i--){
			try{
				sumWeight += pop.weight.get(Integer.parseInt(population[solution][i]));
			} catch (NumberFormatException e){
				over = sumWeight - pop.getCapacity();				
			}
		}
		return over;
	}
	
	public float[] fitnessFunction(){
		fitnessVector = new float[pop.getPopulationSize()];
		for(int nSolution = 0; nSolution < pop.getPopulationSize(); nSolution++){
			for(int nClient = 1; nClient < pop.getSizeChrmosome() - 1; nClient++){
				if(nClient == 1){ //Armazena o primeiro save
					savePoint = pop.getPosition().get(Integer.parseInt(pop.getPop()[nSolution][1]));
				}
				int nextClient = 0;
				try {
					nextClient = nClient+1;
					if(needSave){ 
						savePoint = pop.getPosition().get(Integer.parseInt(pop.getPop()[nSolution][nClient]));
						needSave = false;
					}
					
					first = pop.getPosition().get(Integer.parseInt(pop.getPop()[nSolution][nClient]));
					second = pop.getPosition().get(Integer.parseInt(pop.getPop()[nSolution][nextClient]));
					
					fitnessVector[nSolution] += Point.distance(first.getX(), first.getY(), second.getX(), second.getY());
				} catch(NumberFormatException e) {
					second = savePoint;
					fitnessVector[nSolution] += Point.distance(first.getX(), first.getY(), second.getX(), second.getY()) + overcapacity(nSolution, nClient -1 , pop.getPop());
					needSave = true;
				}
			}			
		}
		return fitnessVector;
	}

	Point savePoint;
		
	/**
	 * Calcula o fitness de cada cromossomo
	 * @param population população que seŕa calculada o fitness
	 * @return
	 */
	public int calcFitness(){

	    setFitnessVector(fitnessFunction());
	    
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
	
	public Fitness clone() {
		try {
			return (Fitness) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}