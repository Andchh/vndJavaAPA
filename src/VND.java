import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VND {
	
	static int quant_h = 2;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String vert; //salvar quantidade de vertices
		//lendo o arquivo e criando a matriz
		String path = "C:/Users/Anderson/Documents/workspace/VND/swiss42.txt";
				
		File file = new File(path);
		Scanner s = null;
		s = new Scanner(file);
		s.nextLine(); //pula uma linha
		
		vert = s.nextLine(); //pega a linha que contem as dimensões
		
		int vertq = Integer.parseInt(vert.replaceAll("[\\D]", "")); //pega apenas os numeros da linha
		//vertq--; //pois a contagem começa de 0
		s.nextLine(); //pula linha
		int[][] matriz = new int[vertq][vertq]; //cria matriz do tamanho da quantidade dos vértices
		
		
		
		//preenche a matriz
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		while(s.hasNextInt()){
			numeros.add(s.nextInt());	
		}
		int total = 0;
		for (int i = 0; i < vertq; i++) { //linhas
			for (int j = 0; j < vertq; j++) { //colunas
				matriz[i][j] = numeros.get(total);
				total++;
			}
		}
		s.close();
		new InitialSolution();
		Integer[] sol = InitialSolution.iSolution(matriz, vertq); //salva array de solução a
		
		new InitialSolution();
		int values = InitialSolution.getValue(sol, matriz, vertq); // salva valor da solução
		//System.out.println("resultado aleatório: " + values);
		Vnd(sol, 10000,  values, matriz);
		//int solut = Vnd(sol, 1, values, matriz);
		//System.out.println("Menor valor do vnd: " + solut);
		
		

	}
	// solution :array com o caminho para a solução encontrada aleatóriamente
	// k = quantidade de algoritmos
	// values = valor da solução anterior encontrada
	//matriz = matriz de adjascencia
	
	public static void Vnd(Integer[] solution, int k, int values, int[][]matriz){
		int loop = 0;
		Integer[] bestSol = solution;
		Integer[] swapSol = null;
		int reinVal;
		int bestValue = values;
		// roda cada busca por vizinhança k vezes
		while(loop < k){ //swap
			swapSol = swap(bestSol, matriz, bestSol.length, bestValue);  //vetor apóswap
			int sSol = InitialSolution.getValue(swapSol, matriz, swapSol.length);  //salva o custo
			if(sSol < bestValue){
				bestValue = sSol;
				bestSol = swapSol;
				loop = 0;
				//System.out.println("valor do swap: " + sSol);

			}else  if(sSol > bestValue){ //ainda repete 10x caso não ache melhor solução
				loop++;
			}
		}
		//System.out.println("best value on swap: " + bestValue);
		loop = 0;
		while(loop < k){ //rein
			//System.out.println("entrou no reinsertion");
			reinVal = reinsertion(bestSol, matriz, bestValue, bestSol.length); //caminho gerado pelo reinsertion
			//int reinSol = InitialSolution.getValue(reinVal, matriz, reinVal.length);
			if(reinVal < bestValue){
				System.out.println("encontrou menor pelo reinsertion");
				bestValue = reinVal;
				//bestSol = reinVal;
				loop = 0;
				//System.out.println("best sol: " + bestSol);
			} else if(reinVal > bestValue){
				loop++;
			}
		}
		System.out.println("best value after rein and at all: " + bestValue);
		
		
	}

	
	public static int reinsertion(Integer[] solution, int[][]matriz, int values, int size){
		long startTime = System.currentTimeMillis();
		size--;
		int menorSol = values;
		int novaSol = Integer.MAX_VALUE;
		int i, j;
		int vert1 = 0, vert2 = 0;
		i = j = 1;
		int valor = 0;
		int rnode = 0;

		while(i < size){
			while(j < size){
				if(i == j || i == j-1){
					j++;
					continue;
				}
				novaSol = values 
						- matriz[solution[i-1]][solution[i]] //aresta até o vertice 
						- matriz[solution[i]][solution[i+1]]  //aresta do vertice até o próximo
						- matriz[solution[j]][solution[j+1]] // 
						+ matriz[solution[i-1]][solution[i+1]]
						+ matriz[solution[j]][solution[i]]
						+ matriz[solution[i]][solution[j+1]];
				
				if(novaSol < menorSol){
					
					vert1 = i;
					vert2 = j;
					menorSol = novaSol;
				}
				j++;
			}
			j = 1;
			i++;
		}
		//return menorSol;
		
		
		

		if(menorSol < values){
			rnode = solution[vert1];
			if(vert1 < vert2){
				for(i = vert1; i <= vert2; i++){
					if(i == vert2){
						solution[i] = rnode;
						break;
					}
					solution[i] = solution[i+1];
				}
			}else{
				for(i = vert1; i >= vert2; i--){
					if(i == vert2){
						solution[i] = rnode;
						break;
					}
					solution[i] = solution[i-1];
				}
			}
			new InitialSolution();
			valor = InitialSolution.getValue(solution, matriz, size);
			long endTime = System.currentTimeMillis() - startTime;
			//System.out.printf("R  einsertion Levou %.8f segundos %n", endTime/1e3);
			//System.out.println("Array do reinsertion: " + Arrays.deepToString(solution));
		}	

		return valor;
		
	}
	
	
	
	
	private static Integer[] swap(Integer[] solution, int[][]matriz, int size, int valuesIn) {
		long startTime = System.currentTimeMillis();
		Integer[] temporario =  solution;
		int valoresDentro = valuesIn;
		int valorTemporario = 0;
		
		//System.out.println("Solution: " + Arrays.deepToString(solution));
		for (int i = 1; i < size; i++) {
			temporario = solution;
			for (int j = i+1; j < size; j++) {
				int aux = temporario[i];
				temporario[i] = temporario[j];
				temporario[j] = aux;
			
				valorTemporario = InitialSolution.getValue(temporario, matriz, size);  
				
				if (valorTemporario < valoresDentro) {
					valoresDentro = valorTemporario;
					//System.out.println("array temporario: " +  Arrays.toString(temporario));
					solution = temporario;
					valorTemporario = InitialSolution.getValue(solution, matriz, size);
					//System.out.println("Valor encontrado no swap: " + valorTemporario);
				}
			}				
		}
		//System.out.println("Array retornado: " + Arrays.toString(solution));
		return solution;
	}
}