import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InitialSolution {
	
	
	public int getValue(Integer[] sol, int[][]matriz){
		
		int value = 0;
		
		for(int i = 0; i < sol.length; i++){
			int atual = sol[i];
			int prox = 0;
			if (atual != sol.length) {
				prox = sol[i+1];
			} else if(atual == sol.length){
				prox = sol[0];
			}
			value += matriz[atual][prox];
		
		}
		
		System.out.println(value);
		return value;
		
	}
	
	
	public Integer[] iSolution(int[][] matriz, int size){
		
		Integer [] solution = new Integer[size];
		
		for(int i = 0; i < matriz.length; i++){
			solution[i] = i+1;
		}
		List<Integer> l = Arrays.asList(solution);
		Collections.shuffle(l);
		//int[] aRandom = new int[size];
		//System.out.println(l);
		
		solution = (Integer[]) l.toArray();
		System.out.println(Arrays.toString(solution));
			
		return solution;
		
		
		
		
	}
	

}
