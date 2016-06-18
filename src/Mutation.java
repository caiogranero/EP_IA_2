import java.util.Random;

public class Mutation {

	 String selectedChrmosome[][]; //Armazena o código binário do invididuo selecionado
	 int mutationPoint; //Ponto de mutação
	 String mutationChrmosome[][]; //Cromossomo após mutação
	 int qttClients;
	 
	public Mutation(String[][] selectedChrmosome, int qttClients) {
		setSelectedChrmosome(selectedChrmosome);
		setQttClients(qttClients);
	}
	
	//Realiza a mutação simples
	public  void makeSimpleMutation(){
		mutationPoint();
		Integer newValue = randInt(1, qttClients - 1);
		getSelectedChrmosome()[0][getMutationPoint()] = newValue.toString();
		setMutationChrmosome(getSelectedChrmosome());
	}
	
	//Define o ponto de mutação.
	public void mutationPoint(){
		int point = randInt(1, getSelectedChrmosome()[0].length - 1);
		while(getSelectedChrmosome()[0][point] == "*"){
			point = randInt(1, getSelectedChrmosome()[0].length - 1);
		}
		setMutationPoint(point);
	}
	
	/**
	 * Função que gera números aleatórios.
	 * @param min -> Valor minimo do range
	 * @param max -> Valor maximo do range
	 * @return 
	 */
	public  int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public  String[][] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public  void setSelectedChrmosome(String[][] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}
	
	public  int getMutationPoint() {
		return mutationPoint;
	}

	public  void setMutationPoint(int mutationPoint) {
		this.mutationPoint = mutationPoint;
	}
	
	public  String[][] getMutationChrmosome() {
		return mutationChrmosome;
	}

	public  void setMutationChrmosome(String[][] mutationChrmosome) {
		this.mutationChrmosome = mutationChrmosome;
	}
	
	public int getQttClients() {
		return qttClients;
	}

	public void setQttClients(int qttClients) {
		this.qttClients = qttClients;
	}

}
