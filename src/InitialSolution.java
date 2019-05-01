import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InitialSolution {
	
	
	public int getValue(Integer[] sol, int[][]matriz, int size){
		
		int value = 0;
		for(int i = 0; i < (size); i++){
			int prox = 0;
			int atual = sol[i];
			atual--; //gambiarra pra entrar na matriz direito pois ela está iniciando em index 0
			
			if (i < (size-1)) {
				prox = sol[i+1];
				prox--;
			} else if(i == (size-1)){
				prox = sol[0];
			}
			value += matriz[atual][prox];
		}
		return value;
	}
	
	
	public Integer[] iSolution(int[][] matriz, int size){
		
		Integer [] solution = new Integer[size];
		
		for(int i = 0; i < size; i++){ //preenche o array com os numeros de 1-tamanho n da matriz  nxm 
			solution[i] = i+1;
			//System.out.printf("%3d", solution[i]);
			//System.out.println();
		}
		List<Integer> l = Arrays.asList(solution);
		Collections.shuffle(l);
		
		solution = (Integer[]) l.toArray();
		//System.out.println(Arrays.toString(solution));
			
		return solution;
		
		
		
		
	}
	

}
