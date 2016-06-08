import java.util.Random;
import java.util.regex.Pattern;

public class Selector implements Cloneable{
	
	Fitness f;
	Population population;
	float rollerVector[]; //Armazena a roleta
	int selectedIndex[]; //Armazena o indice do individuo selecionados
	String selectedChrmosome[]; //Armazena o código binário do invididuo selecionado
	
	public Selector(Fitness fitness, Population pop){
		this.f = fitness;
		this.population = pop;
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
			//System.out.println(roller[i]);
		}
		setRollerVector(roller);
	}
	
	//Seleciona o individuo através da roleta
	public void selectChrmosomeIndex(int qttChrmosome, int startWith){
		int selectedIndex[] = new int[qttChrmosome];
		String selectedChrmosome[] = new String[qttChrmosome]; 
		for(int nChrmosome = 0; nChrmosome < qttChrmosome; nChrmosome++){
			int random = randInt(startWith, 99); //Sorteia um número de 0 a 99
			boolean get = false;
			for(int i = startWith; i < getRollerVector().length; i++){
				if(!get){
					if(random < getRollerVector()[i]){
						selectedIndex[nChrmosome] = i; //Insere o indice do individuo no vetor
						selectedChrmosome[nChrmosome] = population.getPop()[i]; //Insere o código binário do individuo
						get = true;
					}
				}	
			}
		}
		setSelectedIndex(selectedIndex);
		setSelectedChrmosome(selectedChrmosome);
	}
	
	public void orderPopulation(){
		for(int i = 0; i < population.getPopulationSize(); i++){
			for(int j = 0; j < population.getPopulationSize(); j++){
				f.calcFitness();
				if(f.getFitnessVector()[i] < f.getFitnessVector()[j]){
					String aux = population.getPop()[i];
					population.updatePop(i, population.getPop()[j]);
					population.updatePop(j, aux);
				}
			}
		}
	}
	
	public void updateGeneration(String[] updateChrmossome){
		for(int i = 0; i < getRollerVector().length; i++){
			for (int j = 0; j < getSelectedIndex().length; j++){
				if(i == getSelectedIndex()[j]){
					population.getPop()[i] = updateChrmossome[j];
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

	public String[] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public void setSelectedChrmosome(String[] selectedChrmosome) {
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
