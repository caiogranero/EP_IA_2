import java.io.PrintWriter;
import java.io.FileWriter;

public class Writer {

	PrintWriter gravarArq;
	
	public Writer(FileWriter arq){
		this.gravarArq = new PrintWriter(arq);
	}
	
	public void header(double probCross, double probMutation, int generationLimit, int popSize, int eletism, boolean fix, int qtt_cars, int qtt_stops){
		this.gravarArq.printf("+---------------------------------------------+%n");
		gravarArq.printf("PARAMETROS UTILIZADOS: %n");
		gravarArq.printf("Substituição: imediata %n");
		gravarArq.printf("Taxa de Crossover: "+probCross+"%n");
		gravarArq.printf("Taxa de Mutação: "+probMutation+"%n");
		gravarArq.printf("Quantidade de Gerações: "+generationLimit+"%n");
		gravarArq.printf("Tamanho da População: "+popSize+"%n");
		gravarArq.printf("Taxa de elitismo: "+eletism+"%n");
		gravarArq.printf("Quantidade de veiculos: "+qtt_cars+"%n");
		gravarArq.printf("Quantidade de paradas: "+qtt_stops+"%n");
		gravarArq.printf("Corrigindo infactibilidades: "+fix+"%n");
		gravarArq.printf("+---------------------------------------------+%n");
	}
	
	public void aboutFitness(float totalFitness, float medFitness, float maxFitness, float minFitness, float atualGeneration){
		gravarArq.printf("- "+atualGeneration+"ª Geração%n");
		gravarArq.printf("Fitness Total: "+totalFitness+"%n");
		gravarArq.printf("Fitness Médio: "+medFitness+"%n");
		gravarArq.printf("Fitness Máximo: "+maxFitness+"%n");
		gravarArq.printf("Fitness Minimo: "+minFitness+"%n");
		gravarArq.printf("%n-------- x ----------%n");
	}
	
	public void headerExcel(){
		gravarArq.printf("Geração, Fitness Médio, Fitness Máximo, Fitness Minimo%n");
	}
	
	public void excel(float medFitness, float maxFitness, float minFitness, float atualGeneration){
		gravarArq.printf(atualGeneration+","+medFitness+","+maxFitness+","+minFitness+"%n");
	}
}
