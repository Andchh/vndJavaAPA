import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InitialSolution {
	
	
	public int getValue(Integer[] sol, int[][]matriz, int size){
		size--;  //subtrair 2x pois -1 da matriz começando em 0
		size--;  // e -1 pois são n-1 operações para somar os valores
		int value = 0; //valor total do caminho
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
	
	
	public Integer[] iSolution(int[][] matriz, int size){
		long startTime = System.currentTimeMillis();

		size--;
		Integer [] solution = new Integer[size];
		//System.out.println(size);
		
		for(int i = 0; i < size; i++){ //preenche o array com os numeros de 1-tamanho n da matriz  nxm 
			solution[i] = i+1;

		}
		List<Integer> l = Arrays.asList(solution);
		Collections.shuffle(l);
		
		solution = (Integer[]) l.toArray();
		long endTime = System.currentTimeMillis() - startTime;
		System.out.printf("CONSTRUTIVA %.8f segundos %n", endTime/1e3);
			
		return solution;
		
		
		
		
	}
	

}
