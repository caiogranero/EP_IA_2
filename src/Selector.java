import java.util.Random;

public class Selector implements Cloneable{
	
	Fitness f;
	Population pop;
	float rollerVector[]; //Armazena a roleta
	int selectedIndex[]; //Armazena o indice do individuo selecionados
	String selectedChrmosome[][]; //Armazena o código binário do invididuo selecionado
	
	public Selector(Fitness fitness, Population pop){
		this.f = fitness;
		this.pop = pop;
	}
	
	//Simula a criação de uma roleta com as porcentagens dos fitness
	public void makeRoller(){
		float roller[] = new float[f.getPorcentFitnessVector().length];
		for(int i = 0; i < f.getPorcentFitnessVector().length; i++){
			if(i > 0){
				roller[i] = f.getPorcentFitnessVector()[i] + roller[i-1];
			} else if(i == 0){
				roller[i] = f.getPorcentFitnessVector()[i];
			}
		}
		setRollerVector(roller);
	}
	
	//Seleciona o individuo através da roleta
	public void selectChrmosomeIndex(int qttChrmosome, int startWith){
		int selectedIndex[] = new int[qttChrmosome];
		String selectedChrmosome[][] = new String[qttChrmosome][pop.getSizeChrmosome()]; 
		for(int nChrmosome = 0; nChrmosome < qttChrmosome; nChrmosome++){
			int random = randInt(startWith, 99); //Sorteia um número de 0 a 99
			for(int i = startWith; i < getRollerVector().length; i++){
				if(random < getRollerVector()[i]){
					selectedIndex[nChrmosome] = i; //Insere o indice do individuo no vetor
					for(int k = 0; k < pop.getSizeChrmosome(); k++){
						selectedChrmosome[nChrmosome][k] = pop.getPop()[i][k]; //Insere o código binário do individuo
					}
					break;
				}	
			}
		}
		setSelectedIndex(selectedIndex);
		setSelectedChrmosome(selectedChrmosome);
	}	
	
	public void updateGeneration(String[][] updateChrmossome){
		for(int i = 0; i < getRollerVector().length; i++){
			for (int j = 0; j < getSelectedIndex().length; j++){
				if(i == getSelectedIndex()[j]){
					for(int k = 0; k < pop.getSizeChrmosome(); k++){
						pop.getPop()[i][k] = updateChrmossome[j][k];
					}
				}
			}
		}
	}
	
	/**
	 * Imprime a roleta.
	 */
	public void printRoller(){
		for (int i = 0; i < getRollerVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness %: "+ rollerVector[i]);
		}
	}
	
	public float[] getRollerVector() {
		return rollerVector;
	}

	public void setRollerVector(float[] rollerVector) {
		this.rollerVector = rollerVector;
	}
	
	public int[] getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String[][] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public void setSelectedChrmosome(String[][] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}

	public Selector clone() {
		try {
			return (Selector) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Função que gera números aleatórios.
	 * @param min -> Valor minimo do range
	 * @param max -> Valor maximo do range
	 * @return 
	 */
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
