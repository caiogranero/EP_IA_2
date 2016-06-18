import java.util.Random;

public class Mutation {
	
	Population pop; 
	private String selectedChrmosome[][]; //Armazena o código binário do invididuo selecionado
	private int mutationPoint; //Ponto de mutação
	private String mutationChrmosome[][]; //Cromossomo após mutação
	private int mutationCar[]; //Carros que contem clientes que sofreram mutação
	private String temp[][]; //Vetor temporário que armazena a solução
	private String savedValue; //Valor anterior a mutação
	private boolean isNotOk = true; //Usado para verificar o peso após o crossover
	private int vec[] = new int[2]; //Vetor que armazena os carros que sofreram mutação 
	private Integer newValue; //Novo valor do cromossomo que sofrerá mutação
	private int newPosition; //Posição do vetor que não sofreu mutação, mas que precisa ser mudado.
	
	public Mutation(String[][] selectedChrmosome, Population pop) {
		setSelectedChrmosome(selectedChrmosome);
		this.pop = pop;
		temp = new String[1][pop.getSizeChrmosome()];
	}
	
	//Encontra o carro que irá realizar a operação
	public void findMutationCar(int point, String[][] vetor, int number){
		for(int i = point; i > -1; i--){
			if(vetor[0][i] == "*"){
				vec[number]  = i;
				setMutationCar(vec);
				return;
			}
		}
	}
	
	//Verifica se o carrinho com o novo cliente tem mais peso do que deveria
	public void fixMutationWeight(int number){
		while(pop.isCarOverweight(0, getMutationCar()[number]+1, temp)){
			System.out.println("looping fucker");
			newValue = randInt(1, pop.getQttClients() - 1);
			System.out.println(newValue);
			temp[0][getMutationPoint()] = newValue.toString();
		}
	}
	
	public void changeOld(){
		for(int i = 0; i < temp[0].length; i++){
			if((getSelectedChrmosome()[0][i].compareTo(newValue.toString())) == 0){
				temp[0][i] = savedValue;
				newPosition = i;
				return;
			}
		}
	}
	
	//Realiza a mutação simples
	public void makeSimpleMutation(boolean fix){
		pop.copyChrmosome(getSelectedChrmosome(), temp);
		mutationPoint();
		savedValue = getSelectedChrmosome()[0][getMutationPoint()];
		newValue = randInt(1, pop.getQttClients() - 1);
		temp[0][getMutationPoint()] = newValue.toString();
		/*if(fix){
			while(isNotOk){
				fixMutationWeight(0);
				changeOld();
				findMutationCar(newPosition, temp, 1);
				isNotOk = pop.isCarOverweight(0, getMutationCar()[1]+1, temp);
			}
			pop.copyChrmosome(temp, getSelectedChrmosome());
		} else {*/
			pop.copyChrmosome(temp, getSelectedChrmosome());
		//}
		setMutationChrmosome(getSelectedChrmosome());
	}
	
	//Define o ponto de mutação.
	public void mutationPoint(){		
		int point = randInt(1, getSelectedChrmosome()[0].length - 1);
		while(getSelectedChrmosome()[0][point] == "*"){
			point = randInt(1, getSelectedChrmosome()[0].length - 1);
		}
		findMutationCar(point, getSelectedChrmosome(), 0);
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

	public int[] getMutationCar() {
		return mutationCar;
	}

	public void setMutationCar(int mutationCar[]) {
		this.mutationCar = mutationCar;
	}

}
