public class Crossover {
	
	 String selectedChrmosome[]; //Armazena o código binário do invididuo selecionado
	 String crossoverChrmosome[][]; //Armazena os individuos após o crossover
	 String finalCrossoverChrmosome[];
	 
	public Crossover(String[] selectedChrmosome) {
		setSelectedChrmosome(selectedChrmosome);
	}
	
	public  void divCrossover(int qttPoint, int size){
		int nParts = qttPoint+1;
		crossoverChrmosome = new String[getSelectedChrmosome().length][nParts];
		for(int i = 0; i < getSelectedChrmosome().length; i++){
			int maxSize = size;
			int halfSize = maxSize/(nParts);
			int nextSize = 0;
			for(int j = 0; j < nParts; j++){
				nextSize = nextSize + halfSize;
				if(j == 0){ //Primeiro pedaço do cromossomo
					crossoverChrmosome[i][j] = getSelectedChrmosome()[i].substring(0, halfSize);
				} else {
					if(j == qttPoint) { //Parte final do cromossomo
						crossoverChrmosome[i][j] = getSelectedChrmosome()[i].substring((nextSize - halfSize), maxSize);
					} else { //Meio do cromossomo
						crossoverChrmosome[i][j] = getSelectedChrmosome()[i].substring((nextSize - halfSize), nextSize);
					}
				}
			}
		}
		makeCrossover(nParts);
	}
	
	public  void makeCrossover(int nParts){
		String aux = getCrossoverChrmosome()[0][1];
		getCrossoverChrmosome()[0][1] = getCrossoverChrmosome()[1][1];
		getCrossoverChrmosome()[1][1] = aux;
		setCrossoverChrmosome(getCrossoverChrmosome());
		fixCrossover();
	}
	
	public  void fixCrossover(){
		String[] pop = new String[getCrossoverChrmosome().length];
		for(int j = 0; j < getCrossoverChrmosome().length; j++){
			for(int i = 0; i < getCrossoverChrmosome()[0].length; i++){
				if(i == 0){
					pop[j] = getCrossoverChrmosome()[j][i];
				} else {
					pop[j] = pop[j] + getCrossoverChrmosome()[j][i];
				}
			}
		}
		setFinalCrossoverChrmosome(pop);
	}
	
	public  void printCrossover(){
		for (int i = 0; i < getFinalCrossoverChrmosome().length; i++) {
			System.out.print("Individuo "+ i +" após crossover: "+getFinalCrossoverChrmosome()[i]);
			System.out.println("");
		}
	}
	
	public  String[] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public  void setSelectedChrmosome(String[] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}

	public  String[][] getCrossoverChrmosome() {
		return crossoverChrmosome;
	}

	public  void setCrossoverChrmosome(String[][] crossoverChrmosome) {
		this.crossoverChrmosome = crossoverChrmosome;
	}
	
	public  String[] getFinalCrossoverChrmosome() {
		return finalCrossoverChrmosome;
	}

	public  void setFinalCrossoverChrmosome(String[] finalCrossoverChrmosome) {
		this.finalCrossoverChrmosome = finalCrossoverChrmosome;
	}
}