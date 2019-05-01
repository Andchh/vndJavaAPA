import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;

public class VND {
	
	static int quant_h = 1;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String vert;
		List<Integer> goal = new ArrayList<Integer>();
		//lendo o arquivo e criando a matriz
		String path = "C:/Users/Anderson/workspace/VND/bayg29.txt";
		
		//path = System.getProperty("user.dir") + args[0];
		
		File file = new File(path);
		Scanner s = null;
		s = new Scanner(file);
		s.nextLine(); //pula uma linha
		
		vert = s.nextLine(); //pega a linha que contem as dimensões
		
		int vertq = Integer.parseInt(vert.replaceAll("[\\D]", "")); //pega apenas os numeros da linha
		
		s.nextLine();
		int[][] matriz = new int[vertq][vertq]; //cria matriz do tamanho da quantidade dos vértices
		
		
		
		//preenche a matriz
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		while(s.hasNextInt()){
			numeros.add(s.nextInt());	
		}
		int total = 0;
		for (int i = 0; i < vertq; i++) {
			for (int j = 0; j < vertq; j++) {
				matriz[i][j] = numeros.get(total);
				total++;
			}
		}
		

		
		Integer[] sol = new InitialSolution().iSolution(matriz, vertq); //salva array de solução a
		
		int values = new InitialSolution().getValue(sol, matriz, vertq); // salva valor da solução
		System.out.println("resultado aleatório: " + values);
		int solut = Vnd(sol, 1, values, matriz);
		System.out.println(solut);
		
		

	}

	public static int Vnd(Integer[] solution, int k, int values, int[][]matriz){
		while(k <= quant_h){ //quantidade de algoritmos 
			System.out.println("entrou vnd");
			int swapSol = Integer.MAX_VALUE;
			int optSol = Integer.MAX_VALUE;
			
			switch (k) {
			case 1: //swap
				swapSol = swap(solution,matriz , solution.length);
				if(swapSol < values){
					values = swapSol;
					System.out.println("menor valor encontrado");
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
				
			default:
				//System.out.println("fim do vnd, rip;");
				break;
			}
			
		}
		return values;
	}

	private static int swap(Integer[] solution, int[][]matriz, int size) {
		// TODO Auto-generated method stub
		//System.out.println("entrou swap");
		int pivot = 0;
		int menorValor = Integer.MAX_VALUE;
		int value;
		while (pivot < (solution.length)) {
			for (int i = 0; i <= (solution.length - 1); i++) {
				//System.out.println("entrou no for");
				if (i != pivot) {
					solution[pivot] ^= solution[i];   //swap através de  XOR
					solution[i] ^= solution[pivot];
					solution[pivot] ^= solution[i];
					
					//System.out.println("fez swap");
				} else{
					continue;
				}
				
				System.out.println("solução: " + Arrays.toString(solution));
				
				value = new InitialSolution().getValue(solution, matriz, solution.length);
				System.out.println("Novo valor: " + value);
				if(value < menorValor) {
					menorValor = value;
				}
//				if (pivot != (solution.length)){
					System.out.println(i);
					System.out.println("pivot: " + pivot);
//					pivot++;
//				}
			}
			pivot++;

		}
		return menorValor;
	}

	private int opt(Integer[] solution) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
