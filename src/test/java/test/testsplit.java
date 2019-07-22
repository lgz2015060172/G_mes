package test;

import java.util.Arrays;

public class testsplit {

	
	public static void main(String[] args) {
		
		int a [] = new int[]{18,62,68,82,65,9};
		for (int j = 0; j < a.length-1; j++) {
            for (int i = j+1; i < a.length; i++) {
                if(a[i]<a[j]){  
                    int temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                }
            }
        }
         
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(" ");       
	}
}
