import java.util.ArrayList;
import java.util.Random;

public class Population implements Cloneable{
	
	//Vetor de população.
	String[][] pop;
	int qttCars;
	int qttClients;
	int generation;
	int generationLimit;
	int populationSize;
	int sizeChrmosome;
	int newSizeChrmosome;
	String nextGeneration[];

	//Inicializa a população, definindo os critérios de paradas.
	public Population(int generationLimit, int populationSize, int qttClients, int qttCars){
		setGenerationLimit(generationLimit);
		setQttCars(qttCars);
		setQttClients(qttClients);
		setPopulationSize(populationSize);
		
		sizeChrmosome = (qttCars) + qttClients;
		setSizeChrmosome(sizeChrmosome);
		
		newSizeChrmosome = (2*qttCars) + qttClients;
		setNewSizeChrmosome(newSizeChrmosome);
	}
	
	ArrayList<Integer> numberClient = new ArrayList<Integer>();
	ArrayList<Integer> numberCars = new ArrayList<Integer>();
	
	//Vetor de valor para os clientes
	public int possibleClientNumber(){
		numberClient.clear();
		for(int i = 0; i < getQttClients(); i++){
			int k = randInt(0, getQttClients() - 1);
			while(numberClient.contains(k)){
				k = randInt(0, getQttClients() - 1);
			}
			numberClient.add(i, k);
		}
		return 1;
	}
	
	//Vetor de valores para os carros
	public int possibleCarsNumber(){
		numberCars.clear();
		for(int i = 0; i < getQttCars(); i++){
			int k = randInt(getQttClients(), getSizeChrmosome() - 1);
			while(numberCars.contains(k)){
				k = randInt(getQttClients(), getSizeChrmosome() - 1);
			}
			numberCars.add(i, k);
		}
		return 1;
	}
	
	/**Função que preenche o vetor população.
	 * @param pop vetor de população.
	 * @return
	 */
	public String[][] fillPop(String[][] pop){
		for(int x = 0; x < getPopulationSize(); x++){
			possibleCarsNumber();
			possibleClientNumber();
			int iChangePosition = getSizeChrmosome() / getQttCars();
			int fChangePosition = 0;
			int carNumber = 0;
			int clientNumber = 0;
			for(int y = 0; y < newSizeChrmosome; y++){
				//Insere o primeiro carro.
				if(y == 0){
					pop[x][y] = numberCars.get(carNumber).toString();
					carNumber++;
					fChangePosition = iChangePosition;
				} else { 
					if(y < fChangePosition){ //Insere os pontos de entrega (Clientes)
						pop[x][y] = numberClient.get(clientNumber).toString();
						clientNumber++;
					} else { 
						if(y == fChangePosition){
							pop[x][y] = "*";
						} else {
							pop[x][y] = numberCars.get(carNumber).toString();
							carNumber++;
							fChangePosition = fChangePosition + iChangePosition + 1;
						}
					}
				}
			}
		}
		return pop;
	}
	
	/**Função que preenche o vetor população após a segunda geração.
	 * @param pop vetor de população.
	 * @return
	 */
	public String[][] immediateReplacement(String[][] newPopulation){
		for(int i = 0; i < newPopulation.length; i++){
			this.pop[i][i] = newPopulation[i][i];
		}
		return pop;
	}
	
	/** Função que inicia a população.
	 * @param largeness -> Grandeza da população.
	 * @param sizeChrmosome -> Tamanho do cromossomo
	 * @return
	 */
	public String[][] startPop(){
		pop = new String[getPopulationSize()][getNewSizeChrmosome()];
		
		setPop(fillPop(this.pop));
		
		return pop;
	}
	
	
	public void updatePop(int index, String chrmosome){
		this.pop[index][1] = chrmosome;
	}
	
	public String[][] getPop() {
		return pop;
	}

	public void setPop(String[][] pop) {
		this.pop = pop;
	}

	public  int getGenerationLimit() {
		return generationLimit;
	}

	public  void setGenerationLimit(int generationLimit) {
		this.generationLimit = generationLimit;
	}
	
	public  int getPopulationSize() {
		return populationSize;
	}

	public  void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public  int getSizeChrmosome() {
		return sizeChrmosome;
	}

	public  void setSizeChrmosome(int sizeChrmosome) {
		this.sizeChrmosome = sizeChrmosome;
	}
	
	public  int getGeneration() {
		return generation;
	}

	public  void setGeneration(int generation) {
		this.generation = generation;
	}

	public String[] getNextGeneration() {
		return nextGeneration;
	}

	public void setNextGeneration(String[] nextGeneration) {
		this.nextGeneration = nextGeneration;
	}
	
	public int getQttCars() {
		return qttCars;
	}

	public void setQttCars(int qttCars) {
		this.qttCars = qttCars;
	}

	public int getQttClients() {
		return qttClients;
	}

	public void setQttClients(int qttClients) {
		this.qttClients = qttClients;
	}
	
	public int getNewSizeChrmosome() {
		return newSizeChrmosome;
	}

	public void setNewSizeChrmosome(int newSizeChrmosome) {
		this.newSizeChrmosome = newSizeChrmosome;
	}

	public ArrayList<Integer> getNumberClient() {
		return numberClient;
	}

	public void setNumberClient(ArrayList<Integer> numberClient) {
		this.numberClient = numberClient;
	}

	public ArrayList<Integer> getNumberCars() {
		return numberCars;
	}

	public void setNumberCars(ArrayList<Integer> numberCars) {
		this.numberCars = numberCars;
	}
	
	/**
	 * Função que gera números aleatórios.
	 * @param min -> Valor minimo do range
	 * @param max -> Valor maximo do range
	 * @return 
	 */
	public Integer randInt(int min, int max) {
	    Random rand = new Random();
	    Integer randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public  void printChrmosome(int index){
		String vector[] = getPop()[1];
		System.out.printf(index+"º cromossomo: "+ vector[index]);
		System.out.printf("\n");
	}
	
	public  void printPopulation(){
		String vector[][] = getPop();

		int i;
		for (i=0; i<vector.length; i++) {
			for(int j = 0; j<vector[i].length; j++){
			   System.out.printf(vector[i][j]);
			}
			System.out.printf("\n");
		}
	}
	
	public Population clone(){
		try {
			return (Population) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}