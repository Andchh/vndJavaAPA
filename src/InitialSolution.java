import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class InitialSolution {
	
	
	public static int getValue(Integer[] sol, int[][]matriz, int size){
		size--;  //subtrair 2x pois -1 da matriz começando em 0
		//size--;  // e -1 pois são n-1 operações para somar os valores
		int value = 0; //valor total do caminho
		//System.out.println("size: " + size);
		//System.out.println(Arrays.toString(sol));
		for(int i = 0; i < size; i++){

			int atual = sol[i];

			int prox = sol[i+1];

			if(i == (size)){

				prox = sol[0];
				value += matriz[atual][prox]; //soma o ultimo para o primeiro
			} else {
				value += matriz[atual][prox];
			}

		}
		return value;
	}
	
	
	public static Integer[] iSolution(int[][] matriz, int size){
		//size--;
		long startTime = System.currentTimeMillis();

		int custoF = 0;
		LinkedList<Integer> solucao = new LinkedList<Integer>();
		boolean[] verified = new boolean[size];
		solucao.addLast(0);
		int[] caminho = new int[size +1];
		
		caminho[0] = 0;  //
		verified[0] = true;  // marca indice inicial como visitado
		
		for (int i = 0; i < size; i++) {
			int custo = Integer.MAX_VALUE;
			int index = 0;
			for (int j = 0; j < size; j++) {
				if((!verified[j]) && custo > matriz[i][j]) {
					index = j;
					custo = matriz[i][j];
				}
			}
			//System.out.println("i + 1; " + (i+1));
			caminho[i+1] = index;
			verified[index] = true;
			solucao.addLast(index);
		}
		
		caminho[size] = 0;
		//solucao.addLast(0);
		
		for (int i = 0; i < size; i++) {
			custoF  += matriz[caminho[i]][caminho[i+1]];
		}
		int[] nSol = new int[solucao.size()];
		solucao.stream();
		nSol = solucao.stream().mapToInt(Integer::intValue).toArray();
		Integer[] nSolu = Arrays.stream(nSol).boxed().toArray(Integer[]::new);
		
		
		
		System.out.println("Valor da Construtiva: " + InitialSolution.getValue(nSolu, matriz, size));
		long endTime = System.currentTimeMillis() - startTime;
		System.out.printf("vnd Levou %.8f segundos %n", endTime/1e3);
		return nSolu;
		
//		size--;
//		Integer [] solution = new Integer[size];
//		//System.out.println(size);
//		
//		for(int i = 0; i < size; i++){ //preenche o array com os numeros de 1-tamanho n da matriz  nxm 
//			solution[i] = i+1;
//
//		}
//		List<Integer> l = Arrays.asList(solution);
//		Collections.shuffle(l);
//		
//		//solution = (Integer[]) l.toArray();
//		long endTime = System.currentTimeMillis() - startTime;
//		System.out.printf("CONSTRUTIVA %.8f segundos %n", endTime/1e3);
			
		
	}
	

}
