import java.io.PrintWriter;
import java.io.FileWriter;

public class Writer {

	PrintWriter gravarArq;
	
	public Writer(FileWriter arq){
		this.gravarArq = new PrintWriter(arq);
	}
	
	public void header(double probCross, double probMutation, int generationLimit, int popSize, int chrmoSize, int eletism, int crossPoint, int typeSub, int function){
		this.gravarArq.printf("+---------------------------------------------+%n");
		gravarArq.printf("PARAMETROS UTILIZADOS: %n");
		switch(function){
    	case 1:
    		gravarArq.printf("Função de otimização: Função Gold %n");
    		break;
    	case 2:
    		gravarArq.printf("Função de otimização: Função Rastrigin %n");
    		break;
    	case 3:
    		gravarArq.printf("Função de otimização: Função Bump %n");
    		break;
		}
		if(typeSub == 1){
			gravarArq.printf("Substituição: por inclusão %n");
		} else {
			gravarArq.printf("Substituição: imediata %n");
		}
		gravarArq.printf("Crossover de: "+crossPoint+" ponto%n");
		gravarArq.printf("Taxa de Crossover: "+probCross+"%n");
		gravarArq.printf("Taxa de Mutação: "+probMutation+"%n");
		gravarArq.printf("Quantidade de Gerações: "+generationLimit+"%n");
		gravarArq.printf("Tamanho da População: "+popSize+"%n");
		gravarArq.printf("Tamanho do Cromossomo: "+chrmoSize+"%n");
		gravarArq.printf("Taxa de elitismo: "+eletism+"%n");
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
		gravarArq.printf("Geração, Fitness Total, Fitness Médio, Fitness Máximo, Fitness Minimo%n");
	}
	
	public void excel(float totalFitness, float medFitness, float maxFitness, float minFitness, float atualGeneration){
		gravarArq.printf(atualGeneration+","+totalFitness+","+medFitness+","+maxFitness+","+minFitness+"%n");
	}
}
