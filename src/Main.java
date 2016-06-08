import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		int START_WITH = 1; //Usado para controlar o elitismo.
		int GENERATION_LIMIT = 1; //Limite de gerações
		int POP_SIZE = 10; //Tamanho da população
		double PROB_CROSSOVER = 0.3; //Probabilidade de crossover
		double PROB_MUTATION = 0.3; //Probabilidade de mutação.
		int QTT_CLIENTS = 8;
		int QTT_CARS = 2;
		
		Test test = new Test();
		test.makeTest(START_WITH, QTT_CLIENTS, QTT_CARS, GENERATION_LIMIT, POP_SIZE, PROB_CROSSOVER, PROB_MUTATION);
	}
}