import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Population implements Cloneable{
	
	//Vetor de população.
	String[][] pop;
	int qttCars;
	int qttClients;
	int generation;
	int generationLimit;
	int populationSize;
	int sizeChrmosome;
	int capacity;
	String nextGeneration[][];
	ArrayList<Point> position = new ArrayList<Point>();
	
	public ArrayList<Point> getPosition() {
		return position;
	}

	public void setPosition(ArrayList<Point> position) {
		this.position = position;
	}

	public ArrayList<Integer> getWeight() {
		return weight;
	}

	public void setWeight(ArrayList<Integer> weight) {
		this.weight = weight;
	}

	ArrayList<Integer> weight = new ArrayList<Integer>();
	
	public void readFile(String fileName){
		int nLine = 0;
		try{
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			while (sc.hasNext()){
				if(nLine == 0){
					setQttClients(sc.nextInt());
					setCapacity(sc.nextInt());
					nLine++;
				} else {
					if(nLine <= getQttClients()){
						sc.nextInt();
						position.add(new Point(sc.nextInt(), sc.nextInt()));
						nLine++;
					} else {
						sc.nextInt();
						weight.add(sc.nextInt());
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//Inicializa a população, definindo os critérios de paradas.
	public Population(int generationLimit, int populationSize, int qttCars){
		setGenerationLimit(generationLimit);
		setQttCars(qttCars);
		setPopulationSize(populationSize);
	}
	
	ArrayList<Integer> numberClient = new ArrayList<Integer>();
	
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
		
	/**Função que preenche o vetor população.
	 * @param pop vetor de população.
	 * @return
	 */
	public String[][] fillPop(String[][] pop){
		for(int x = 0; x < getPopulationSize(); x++){
			possibleClientNumber();
			int iChangePosition = getSizeChrmosome() / getQttCars();
			int fChangePosition = 0;
			int clientNumber = 0;
			for(int y = 0; y < getSizeChrmosome(); y++){
				//Insere o primeiro carro.
				if(y == 0){
					pop[x][y] = "*";
					fChangePosition = iChangePosition;
				} else { 
					if(y < fChangePosition){ //Insere os pontos de entrega (Clientes)
						pop[x][y] = numberClient.get(clientNumber).toString();
						clientNumber++;
					} else { 
						pop[x][y] = "*";
						fChangePosition = fChangePosition + iChangePosition + 1;
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
			for(int j = 0; j < newPopulation[0].length; j++){
				this.pop[i][j] = newPopulation[i][j];
			}
		}
		return pop;
	}
	
	/** Função que inicia a população.
	 * @param largeness -> Grandeza da população.
	 * @param sizeChrmosome -> Tamanho do cromossomo
	 * @return
	 */
	public String[][] startPop(){
		
		sizeChrmosome = getQttCars() + getQttClients();
		
		setSizeChrmosome(sizeChrmosome);
		
		pop = new String[getPopulationSize()][getSizeChrmosome()];
		
		setPop(fillPop(this.pop));
		
		return pop;
	}
		
	public void updatePop(int index, String[] chrmosome){
		this.pop[index] = chrmosome;
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

	public String[][] getNextGeneration() {
		return nextGeneration;
	}

	public void setNextGeneration(String[][] nextGeneration) {
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
	
	public ArrayList<Integer> getNumberClient() {
		return numberClient;
	}

	public void setNumberClient(ArrayList<Integer> numberClient) {
		this.numberClient = numberClient;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void printPosition(){
		for (Point p : position) {
		    System.out.println("["+p.x + "," + p.y+"]");
		}
	}
	
	public void printWeight(){
		for (Integer i : weight) {
		    System.out.println(i.intValue());
		}
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