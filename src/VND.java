import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VND {
	
	static int quant_h = 2;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String vert;
		//List<Integer> goal = new ArrayList<Integer>();
		//lendo o arquivo e criando a matriz
		String path = "C:/Users/Anderson/workspace/VND/swiss42.txt";
		
		//path = System.getProperty("user.dir") + args[0];
		
		File file = new File(path);
		Scanner s = null;
		s = new Scanner(file);
		s.nextLine(); //pula uma linha
		
		vert = s.nextLine(); //pega a linha que contem as dimensões
		
		int vertq = Integer.parseInt(vert.replaceAll("[\\D]", "")); //pega apenas os numeros da linha
		//vertq--; //pois a contagem começa de 0
		//System.out.println("vertq: " + vertq);
		s.nextLine();
		int[][] matriz = new int[vertq][vertq]; //cria matriz do tamanho da quantidade dos vértices
		
		
		
		//preenche a matriz
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		while(s.hasNextInt()){
			numeros.add(s.nextInt());	
			//System.out.println(numeros);
		}
//		System.out.println(numeros);
//		System.out.println("primeiro numeros: " + numeros.get(1));
		int total = 0;
		for (int i = 0; i < vertq; i++) {
			for (int j = 0; j < vertq; j++) {
//				System.out.println(total);
//				System.out.println("i: " + i + " j: " + j);
				matriz[i][j] = numeros.get(total);
//				System.out.println("matriz:" + matriz[i][j]);
				total++;
			}
		}
		s.close();
		Integer[] sol = new InitialSolution().iSolution(matriz, vertq); //salva array de solução a
		
		int values = new InitialSolution().getValue(sol, matriz, vertq); // salva valor da solução
		System.out.println("resultado aleatório: " + values);
		int solut = Vnd(sol, 1, values, matriz);
		System.out.println("Menor valor do vnd: " + solut);
		
		

	}
	// solution :array com o caminho para a solução encontrada aleatóriamente
	// k = quantidade de algoritmos
	// values = valor da solução anterior encontrada
	//matriz = matriz de adjascencia
	public static int Vnd(Integer[] solution, int k, int values, int[][]matriz){ 
		
		while(k <= quant_h){ //quantidade de algoritmos 
			//System.out.println("entrou vnd");
			Integer[] swapSol;
			int optSol = Integer.MAX_VALUE;
			int reinSol = Integer.MAX_VALUE;
			int swapVal = Integer.MAX_VALUE;
			//System.out.println("vetor de entrada: " + Arrays.toString(solution));
			switch (k) {
			case 1: //swap
				swapSol = swap(solution, matriz , solution.length, values);
				
				swapVal = new InitialSolution().getValue(swapSol, matriz, swapSol.length);
				//System.out.println("valor encontrado no swap: " + swapVal); 
				if(swapVal < values){
					values = swapVal;
					
					//System.out.println("menor valor encontrado do swap: " + swapVal);
					//System.out.println("para o vetor: \n" + Arrays.toString(swapSol));
				}
				k++;
				break;
/*			case 2: //2opt
				
				optSol = opt(solution);
				if (optSol < values) {
					values = optSol;
				}	
				
				k++;
				break;*/
				
			case 2: //reinsertion
				//System.out.println("entrou rein");
				reinSol = reinsertion(solution, matriz, values, solution.length);
				if(reinSol < values){
					values = reinSol;
					//System.out.println("menor valor encontrado rein: " + values);
				}
				//System.out.println("valor encontrado no reinsertion: " + reinSol);
				k++;
				break;
				
			default:
				System.out.println("fim do vnd, rip;");
				
			}
			
		}
		return values;
	}
	
	
	
	public static int reinsertion(Integer[] solution, int[][]matriz, int values, int size){
		long startTime = System.currentTimeMillis();
		size--;
		int start = 1;
		int menorSol = values;
		int novaSol = Integer.MAX_VALUE;
		int i, j;
		int vert1 = 0, vert2 = 0;
		i = j = 1;
		int rnode = 0;
		//System.out.println("entrou reinsertion");
		//System.out.println("size: " + size);
		while(i < size){
			while(j < size){
				if(i == j || i == j-1){
					//System.out.println("foi igual");
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
					//System.out.println(menorSol);
				}
				j++;
			}
			j = 1;
			i++;
		}
		//return menorSol;
		
		
		

		if(menorSol < values){
			//System.out.println("entrou menor");
			rnode = solution[vert1];
			if(vert1 < vert2){
				for(i = vert1; i <= vert2; i++){
					if(i == vert2){
						solution[i] = rnode;
						break;
					}
					solution[i] = solution[i+1];
				}
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
		int valor = new InitialSolution().getValue(solution, matriz, size);
		long endTime = System.currentTimeMillis() - startTime;
		System.out.printf("Reinsertion Levou %.8f segundos %n", endTime/1e3);
		return valor;
		
	}
	
	
	
	
	private static Integer[] swap(Integer[] solution, int[][]matriz, int size, int valuesIn) {
		long startTime = System.currentTimeMillis();
		int vert1 = 0;
		int vert2 = 0;
		// TODO Auto-generated method stub
		//System.out.println("entrou swap");
		int pivot = 0;
		int menorValor = Integer.MAX_VALUE;
		int value;
		//System.out.println(Arrays.toString(solution));
		
  //enquanto houver melhoras
			
		boolean best =  false;
			
		while (best != true) {
			//System.out.println("entrou while");
			
			for (int i = 0; i < solution.length - 1; i++) { //pivot
				//System.out.println(solution.length - 1);
				for (int j = 0; j < solution.length - 1; j++) { //resto do array 
					//swap entre o pivot e todos 
					if(j == i){
						//j++;
						//System.out.println("continuou");
						continue;
	
					}
					//swap solution[i] com solution[j] via  xor
					//System.out.println("swap i: " + i + "com j: " + j);
					solution[i] ^= solution[j];
					solution[j] ^= solution[i];
					solution[i] ^= solution[j];
					//System.out.println(Arrays.toString(solution));
					value = new InitialSolution().getValue(solution, matriz, size);
					//System.out.println("value: " + value);
					if(value < valuesIn){
						//System.out.println("melhor solução: \n" + Arrays.toString(solution));
						menorValor = value;
						vert1 = i;
						vert2 = j;
					}
				}
			}
			
			if (menorValor < valuesIn) {
				best = true;
			} 
			
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.printf("Swap Levou %.8f segundos %n", endTime/1e3);
		return solution;
	}

	
	private int opt(Integer[] solution) {
		// TODO Auto-generated method stub
		
		return 0;
	}
	
}
