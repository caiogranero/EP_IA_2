import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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
	float capacity;
	int sizeCar;
	String nextGeneration[][];
	ArrayList<Point> position = new ArrayList<Point>();
	ArrayList<Integer> weight = new ArrayList<Integer>();
	ArrayList<Integer> numberClient = new ArrayList<Integer>();
	ArrayList<String> tempSolution = new ArrayList<String>();
	ArrayList<String> tempSolution2 = new ArrayList<String>();
	
	public void printWeightSolution(){
		for(int i = 0; i < getPopulationSize(); i++){
			int sumWeight = 0;
			for(int j = 1; j < getSizeChrmosome(); j++){
				try{
					sumWeight += weight.get(Integer.parseInt(getPop()[i][j]));
				} catch (NumberFormatException e){
				System.out.println("Solução: "+"["+i+"]"+"Capacidade do caminhão: ["+j+"] - "+sumWeight);
				sumWeight = 0;
				}
			}
			
		}
	}
	
	public boolean isInfactibility(int elitism){
		tempSolution2.clear();
		for(int i = elitism; i < getPopulationSize(); i++){
			//Copia todos uma solução para o ArrayList
			for(int j = 1; j < getSizeChrmosome(); j++){
				tempSolution2.add(getPop()[i][j]);
			}
			//Verifica
			for(Integer k = 0; k < getNumberClient().size(); k++){
				if(Collections.frequency(tempSolution2, k.toString()) > 1){
					return true;
				} else {
					if(Collections.frequency(tempSolution2, k.toString()) == 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void lookInfactibility(int elitism){
		while(isInfactibility(elitism)){
			fixInfactibility(elitism);
		}
	}
	
	public void fixInfactibility(int elitism){
		for(int i = elitism; i < getPopulationSize(); i++){
			Integer emptyValue= 0;
			Integer doubleValue = 0;
			boolean flag1 = false; 
			boolean flag2 = false;
			int mm = 0;
			
			//Copia todos uma solução para o ArrayList
			for(int j = 1; j < getSizeChrmosome(); j++){
				tempSolution.add(getPop()[i][j]);
			}
			
			//Procura no arrayList infactibilidade
			for(Integer k = 0; k < getNumberClient().size(); k++){
				if(Collections.frequency(tempSolution, k.toString()) > 1){
					doubleValue = k;
					flag1 = true;
					mm++;
				} else {
					if(Collections.frequency(tempSolution, k.toString()) == 0){
						emptyValue = k;
						flag2 = true;
					}
				}
				if(flag1 && flag2){ //Corrige as infactibilidades
					int index = tempSolution.indexOf(doubleValue.toString());
					tempSolution.remove(index);
					tempSolution.add(index, emptyValue.toString());
					if(mm > 1){
						k = 0;
						mm = 0;
					} else {
						k = getNumberClient().size();
					}
				}
			}
			
			int iArray = 0;
			for(int j = 1; j < getSizeChrmosome(); j++){
				getPop()[i][j] = tempSolution.get(iArray);
				iArray++;
			}
			
			tempSolution.clear();
		}
	}
	
	int save;
	
	public boolean isSolutionOverweight(int nSolution){
		for(int i = 1; i < getSizeChrmosome(); i++){
			if(isCarOverweight(nSolution, i, getPop())){
				return true;
			}
			i = save;
		}
		return false;
	}

	public boolean isCarOverweight(int solution, int carPosition, String population[][]){
		int sumWeight = 0;
		int k = 0;
		for(int i = 1; i < getSizeCar()+1; i++){
			try{
				if(carPosition != population[0].length){
					sumWeight += weight.get(Integer.parseInt(population[solution][carPosition]));
					k++;
					carPosition++;
				}
			} catch (NumberFormatException e){
				save += k+1;
				if(sumWeight > getCapacity()){
					return true;
				} else {
					return false;
				}
			}
		}
		save += k+1;
		if(sumWeight > getCapacity()){
			return true;
		}
		return false;
	}
	
	public void findSizeCar(){
		int sizeCar = ((getQttCars() + getQttClients())/getQttCars());
		setSizeCar(sizeCar);
	}
	
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
	
	
	//Vetor de valor para os clientes
	public void possibleClientNumber(){
		numberClient.clear();
		for(int i = 0; i < getQttClients(); i++){
			int k = randInt(0, getQttClients() - 1);
			while(numberClient.contains(k)){
				k = randInt(0, getQttClients() - 1);
			}
			numberClient.add(i, k);
		}
	}
	
	int clientNumber;
	/**Função que preenche o vetor população.
	 * @param pop vetor de população.
	 * @return
	 */
	public String[][] fillPop(String[][] pop){
		int saveSolution;
		int x = 0;
		while(x < getPopulationSize()){
			possibleClientNumber();
			int iChangePosition = getSizeChrmosome() / getQttCars();
			int fChangePosition = 0;
			clientNumber = 0;
			
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
			saveSolution = x;
			x++;
			if(isSolutionOverweight(saveSolution)){
				x = saveSolution;
			}
		}
		return pop;
	}
		
	public void fixSolution(){
		
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
		
		findSizeCar();
		
		pop = new String[getPopulationSize()][getSizeChrmosome()];
		
		setPop(fillPop(this.pop));
		
		return pop;
	}
	
	
	public void makeEletism(Population pop_aux, Fitness f_aux, Fitness f){
		try{
			for(int i = 0; i < getPopulationSize(); i++){
				if(f_aux.getFitnessVector()[i] < f.getFitnessVector()[i]){
					updatePop(i, pop_aux.getPop()[i]);
				}
			}
			
			f.calcFitness();  //Calculando fitness de cada individuo
		}catch(Exception e){}
	}
		
	public void updatePop(int index, String[] chrmosome){
		this.pop[index] = chrmosome;
	}
	
	//Função que copia uma solução para um vetor temporário
	public void copyChrmosome(String[][] to, String[][] from){
		for(int j = 0; j < to.length; j++){
			for(int i = 0; i < to[0].length; i++){
				from[0][i] = to[0][i];
			}
		}
	}
	
	public void orderPopulation(Fitness f){
		String aux[] = new String[getSizeChrmosome()];
		for(int i = 0; i < getPopulationSize(); i++){
			for(int j = 0; j < getPopulationSize(); j++){
				f.calcFitness();
				if(f.getFitnessVector()[i] < f.getFitnessVector()[j]){
					aux = getPop()[i];
					updatePop(i, getPop()[j]);
					updatePop(j, aux);
				}
			}
		}
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
	
	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}
	
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
	
	public int getSizeCar() {
		return sizeCar;
	}

	public void setSizeCar(int sizeCar) {
		this.sizeCar = sizeCar;
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
		for (i=0; i < vector.length; i++) {
			for(int j = 0; j < vector[i].length; j++){
			   System.out.printf(" "+vector[i][j]);
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