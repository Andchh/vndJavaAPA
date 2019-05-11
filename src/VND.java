import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VND {
	
	static int quant_h = 2;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		/*
		 * String vert; //salvar quantidade de vertices //lendo o arquivo e criando a
		 * matriz String path = "C:/Users/Anderson/Documents/workspace/VND/swiss42.txt";
		 * 
		 * File file = new File(path); Scanner s = null; s = new Scanner(file);
		 * s.nextLine(); //pula uma linha
		 * 
		 * vert = s.nextLine(); //pega a linha que contem as dimensões
		 * 
		 * int vertq = Integer.parseInt(vert.replaceAll("[\\D]", "")); //pega apenas os
		 * numeros da linha //vertq--; //pois a contagem começa de 0 s.nextLine();
		 * //pula linha int[][] matriz = new int[vertq][vertq]; //cria matriz do tamanho
		 * da quantidade dos vértices
		 * 
		 * 
		 * 
		 * //preenche a matriz ArrayList<Integer> numeros = new ArrayList<Integer>();
		 * while(s.hasNextInt()){ numeros.add(s.nextInt()); } int total = 0; for (int i
		 * = 0; i < vertq; i++) { //linhas for (int j = 0; j < vertq; j++) { //colunas
		 * matriz[i][j] = numeros.get(total); total++; } } s.close();
		 */
		String vert; //salvar quantidade de vertices
		//lendo o arquivo e criando a matriz
		String path = "C:/Users/Anderson/Documents/workspace/VND/tsp3.txt";
				
		File file = new File(path);
		Scanner s = null;
		s = new Scanner(file);
		s.nextLine(); //pula uma linha
		
		vert = s.nextLine(); //pega a linha que contem as dimensões
		
		int vertq = Integer.parseInt(vert.replaceAll("[\\D]", "")); //pega apenas os numeros da linha
		//vertq--; //pois a contagem começa de 0
		System.out.println("vertq: " + vertq);
		System.out.println(s.next()); //dysplay data section
		s.nextLine(); //pula linha
		//System.out.println(s.next() + " " + s.next() + " "+ s.next());
		
		
		int[] index = new int[vertq];
		double[] x = new double[vertq];
		double[] y =  new double[vertq];
		int i = 0;
		
		while (s.hasNextLine()) {
				if(i == vertq) {
					break;
				}
				//System.out.println(index[j] + x[j] + y[j]);
				index[i] = Integer.parseInt(s.next());
				
				x[i] = Double.parseDouble(s.next());
				y[i] = Double.parseDouble(s.next());
				//System.out.println(index[i] + " " + x[i] + " " + y[i]);
				i++;
				
				s.hasNextLine();
			 
		}
		
		int[][] matriz = new int[vertq][vertq]; 
		
		coordRead(vertq, matriz, x, y);  //começando daqui já tenho uma matriz

		s.close();
		new InitialSolution();
		Integer[] sol = InitialSolution.iSolution(matriz, vertq); //salva array de solução a
		
		new InitialSolution();
		int values = InitialSolution.getValue(sol, matriz, vertq); // salva valor da solução
		//System.out.println("resultado aleatório: " + values);
		Vnd(sol, 1,  values, matriz);
		System.out.println("best Sol3 main: " + Arrays.toString(sol));
		//int solut = Vnd(sol, 1, values, matriz);
		//System.out.println("Menor valor do vnd: " + solut);
		
		

	}
	// solution :array com o caminho para a solução encontrada aleatóriamente
	// k = quantidade de algoritmos
	// values = valor da solução anterior encontrada
	//matriz = matriz de adjascencia
	
	public static void Vnd(Integer[] solution, int k, int values, int[][]matriz){
		long startTime = System.currentTimeMillis();
		
		Integer[] bestSol = solution;
		Integer[] reinSol;
		Integer[] swapSol;
		Integer[] tSol;
		int twSol;
		int bestValue = values; //melhor custo encontrado
		int sSol; //custo encontrado pelo swap
		int rSol; //custo encontrado pelo reinsertion
		int r = 0; //quantidade de vezes que pode errar
		
		
			while (r <= 10) { 
				//System.out.println("entrou swap");
				

				while (true) {
					
					swapSol = swap(bestSol, matriz, bestSol.length, values);
					sSol = InitialSolution.getValue(swapSol, matriz, swapSol.length);
					//System.out.println("sSol: " + sSol);
					if (sSol < bestValue) { //se a solução do swap for melhor do que a que temos
						bestSol = swapSol;
						bestValue = sSol;
						//System.out.println("Valor do swap dentro do if: " + sSol);
						continue; //roda novamente o swap
					} else { //se não for melhor entra no rein
						break;
					} 
				}
				
				reinSol = reinsertion(bestSol, matriz, bestValue, bestSol.length);
				rSol = InitialSolution.getValue(reinSol, matriz, reinSol.length);
				//System.out.println("Solução rein: " + rSol);
				if(rSol < bestValue) {
					bestValue = rSol;
					bestSol = reinSol;
					continue;
				}
				tSol = tOpt(bestSol, matriz, bestSol.length, bestValue);
				twSol = InitialSolution.getValue(tSol, matriz, tSol.length);
				if(twSol < bestValue) {
					bestValue = twSol;
					bestSol = tSol;
					continue;
				}
				
				//System.out.println("best SOl: "  +  Arrays.deepToString(bestSol));
				
				
				r++;
			}
			
		solution = bestSol;
		System.out.println("best valu at all: " + bestValue);
		System.out.println("bestSol length: " + bestSol.length);
		int valorFinal = InitialSolution.getValue(bestSol, matriz, bestSol.length);
		System.out.println("valorFinal: " + valorFinal);
		long endTime = System.currentTimeMillis() - startTime;
		System.out.printf("vnd Levou %.8f segundos %n", endTime/1e3);	
			
	}


	
	public static Integer[] reinsertion(Integer[] solution, int[][]matriz, int values, int size){
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

		}	

		return solution;
		
	}
	
	
	
	
	private static Integer[] swap(Integer[] solution, int[][]matriz, int size, int valuesIn) {
		
		int i, j;
		i = j = 1;
		int start = 1;
		int novaSol = Integer.MAX_VALUE;
		int vertice1 = 0;
		int vertice2 = 0;
		int menorValue = valuesIn;
		
		while(i < size - 1) {
			while(j < size -1) {
				if(i == j) {
					j++;
					continue;
				}
				if(j == i+1) {
					novaSol = valuesIn
							- matriz[solution[i-1]][solution[i]]
							- matriz[solution[j]][solution[j+1]]
							+ matriz[solution[i-1]][solution[j]]
							+ matriz[solution[i]][solution[j+1]];
				} else {
					novaSol = valuesIn
							- matriz[solution[i-1]][solution[i]]
							- matriz[solution[j]][solution[j+1]]
							+ matriz[solution[i-1]][solution[j]]
							+ matriz[solution[i]][solution[j+1]]
							- matriz[solution[j]][solution[i+1]]
							- matriz[solution[j-1]][solution[j]]
							+ matriz[solution[j]][solution[i+1]]
							+ matriz[solution[j-1]][solution[i]];
				}
				if(novaSol < menorValue){
					menorValue = novaSol;
					vertice1 = i;
					vertice2 = j;
				}
				j++;
			}
			j = ++start;
			i++;
		}
		if(menorValue < valuesIn) {
			int aux = solution[vertice1];
			solution[vertice1] = solution[vertice2];
			solution[vertice2] = aux;
			
		}
		return solution;
	}

		
	
	private static Integer[] tOpt(Integer[] solution, int[][]matriz, int size, int values) {
		size--;
		int i, j, menorSol, novaSol, v1 = 0, v2 = 0, aux;
		//Integer[] solu = solution;
		i = j = 1;
		menorSol = values;
		novaSol = Integer.MAX_VALUE;
		
		while(i < size-2) {
			for(j = i + 3; j < size; j++) {
				novaSol = values
						- matriz[solution[i-1]][solution[i]]
						- matriz[solution[j+1]][solution[j]]
						+ matriz[solution[j+1]][solution[i]]
						+ matriz[solution[i-1]][solution[j]];
				if(novaSol < menorSol) {
					menorSol = novaSol;
					v1 = i;
					v2 = j;
				}
			}
			++i;
		}
		j = v2;
		if(menorSol < values) {
			for(i = v1; i < j;i++) {
				aux = solution[i];
				solution[i] = solution[j];
				solution[j] = aux;
				j--;
			}
			values = InitialSolution.getValue(solution, matriz, size);
		}
		
		return solution;
		
	}
	
	
	
	public static int distance(double x1, double x2, double y1, double y2) {
		double dist = ((x2-x1) * (x2-x1) ) + ( (y2-y1) * (y2-y1) );
		return (int) Math.round(Math.sqrt(dist));
	}
	
	public static void coordRead(int vertq, int[][] matriz, double[] x, double[] y) {
		vertq--;
		int i, j;
		int start = 0;
		System.out.println(vertq);
		for(i =0; i < vertq; i++) {
			for(j=start; j < vertq; j++) {
				if(i == j) {
					matriz[i][j] = matriz[j][i] = 0;
					continue;
				}
				matriz[i][j] = matriz[j][i] = distance(x[i], x[j], y[i], y[j]);
			}
			start++;
		}
		
	}
	
	
	
}