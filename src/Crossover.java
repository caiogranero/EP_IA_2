import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Crossover {
	
	 String selectedChrmosome[][]; //Armazena o código binário do invididuo selecionado
	 String crossoverChrmosome[][]; //Armazena os individuos após o crossover
	 String finalCrossoverChrmosome[][];
	 
	 
	public Crossover(String[][] strings) {
		setSelectedChrmosome(strings);
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
	
	public int cost(String temp[], int start, int finish){
		int cost = 0;
		for(int i = start; i < finish-1; i++){
			cost += Integer.parseInt(temp[i]);
		}
		return cost;
	}

	public void randomCrossover(int qttCars, int qttClients) {
		int selectSubRoute = randInt(1, qttCars);
		int aux=0;
		int beginRoute = 0;
		int finishRoute = 0;
		for(int i = 0; this.selectedChrmosome[0].length>i; i++){
			if (this.selectedChrmosome[0][i].equals("*")){
				aux++;
				if (aux == selectSubRoute){
					beginRoute = i + 1;
				}
				if (aux == selectSubRoute+1){
					finishRoute = i - 1;
					break;
				}
			}			
		}
		
		if(finishRoute == 0){finishRoute = this.selectedChrmosome[0].length-1;}
		int positionCrossover = ((qttCars + qttClients)/qttCars) * randInt(1, qttCars-1);
		for(int i = beginRoute; i <= finishRoute; i++){
			if(this.selectedChrmosome[1][positionCrossover].equals("*")) positionCrossover++;
			String temp = this.getSelectedChrmosome()[1][positionCrossover];								
			this.selectedChrmosome[1][positionCrossover] = this.selectedChrmosome[0][i];
			this.selectedChrmosome[0][i] = temp;
		}
		setFinalCrossoverChrmosome(getSelectedChrmosome());
	}
	
	
	public void simpleCrossover(int qttCars, int qttClients){
		int selectedCar = randInt(0, qttCars - 1);
		int range = getSelectedChrmosome()[0].length/qttCars;
		int index = 0;
		
		int size = (getSelectedChrmosome()[0].length - (selectedCar*range));
		String temp[] = new String[size];
		
		//Pega o index do carrinho
		for(int i = 0; i != selectedCar; i++){
			index += range;
		}
		
		
		
		//Altera a posição dos carrinhos
		int m = 0;
		for(int k = index; k < getSelectedChrmosome()[0].length; k++){
			temp[m] = getSelectedChrmosome()[0][k];
			m++;
		}
		
		for(int k = index; k < getSelectedChrmosome()[0].length; k++){
			getSelectedChrmosome()[0][k] = getSelectedChrmosome()[1][k];
		}
		m = 0;
		for(int k = index; k < getSelectedChrmosome()[0].length; k++){
			getSelectedChrmosome()[1][k] = temp[m];
			m++;
		}
		
		setFinalCrossoverChrmosome(getSelectedChrmosome());
	}
	
	public int bestInsertion(String temp[], int range, int sizeSubRoute, int i){
		int bestCost = 0;
		bestCost = cost(temp, i, sizeSubRoute)- cost(temp, i, i+1) - cost(temp, range, sizeSubRoute);
		return bestCost;
	}
	
	public  void fuckerCrossover(int qttCars, int qttClients){
		int sizeSubRoute = (qttCars + qttClients)/qttCars;
		int sizeChrmosome = qttCars + qttClients;
		
		String[] temp = new String[sizeChrmosome];
		for(int j = 0; j < sizeChrmosome; j++){
			temp[j] = selectedChrmosome[0][j];
		}
		
		int selectedRoute = sizeSubRoute * (randInt(0, qttCars-1)); //Seleciona o veículo que sofrerá o crossover
		int selectedSubRouteStart = randInt(1, sizeSubRoute-1); //Seleciona o range de clientes
		int selectedSubRouteEnd;
		
		if(sizeSubRoute != selectedSubRouteStart){
			selectedSubRouteEnd = randInt(selectedSubRouteStart, sizeSubRoute-1);
		} else {
			selectedSubRouteEnd = selectedSubRouteStart;
		}
		
		int qttSelectedClients = (selectedSubRouteEnd - selectedSubRouteStart)+1;
		int[] selectedCustomers = new int[qttSelectedClients];
		
		int m = 0;
		for(int i = selectedRoute+selectedSubRouteStart; i < qttSelectedClients; i++){
			selectedCustomers[m] = Integer.parseInt(getSelectedChrmosome()[1][i]);
			m++;
		}
		
		int value = 0;
		
		for(int i = qttSelectedClients; i < sizeChrmosome; i++){
			int value2 = bestInsertion(temp, qttSelectedClients, sizeSubRoute, i);
			if(value < value2){
				value = value2;
			}
		}
	}
	
	public  void printCrossover(){
		for (int i = 0; i < getFinalCrossoverChrmosome().length; i++) {
			System.out.print("Individuo "+ i +" após crossover: "+getFinalCrossoverChrmosome()[i]);
			System.out.println("");
		}
	}
	
	public  String[][] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public  void setSelectedChrmosome(String[][] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}

	public  String[][] getCrossoverChrmosome() {
		return crossoverChrmosome;
	}

	public  void setCrossoverChrmosome(String[][] crossoverChrmosome) {
		this.crossoverChrmosome = crossoverChrmosome;
	}
	
	public  String[][] getFinalCrossoverChrmosome() {
		return finalCrossoverChrmosome;
	}

	public  void setFinalCrossoverChrmosome(String[][] finalCrossoverChrmosome) {
		this.finalCrossoverChrmosome = finalCrossoverChrmosome;
	}
}