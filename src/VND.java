import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;

public class VND {
	
	
	

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
		
		s.nextLine(); //pula a linha com EDGE_WEIGHTS_SECTION
		
		int[][] matriz = new int[vertq][vertq]; //cria matriz do tamanho da quantidade dos vértices
		
		//preenche a matriz TODO
		
		//System.out.printf("%4d", Arrays.toString(matriz));
		
		Integer[] sol = new InitialSolution().iSolution(matriz, vertq);
		
		System.out.println(Arrays.toString(sol));
		int value = new InitialSolution().getValue(sol, matriz);
		

	}

	public void vnd(int[] solution){
		
	}
	
}
