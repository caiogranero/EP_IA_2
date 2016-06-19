import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		int START_WITH = 10; //Usado para controlar o elitismo.
		int GENERATION_LIMIT = 100; //Limite de gerações
		int POP_SIZE = 50; //Tamanho da população
		double PROB_CROSSOVER = 0.7; //Probabilidade de crossover
		double PROB_MUTATION = 0.7; //Probabilidade de mutação.
		int QTT_CARS = 7;
		boolean fix = true; //Variável que controla de verifica ou não infactibilidade
		
		Test test = new Test();
		test.makeTest(START_WITH, QTT_CARS, GENERATION_LIMIT, POP_SIZE, PROB_CROSSOVER, PROB_MUTATION, fix);
	}
}