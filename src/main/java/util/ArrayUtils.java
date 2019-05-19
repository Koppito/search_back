package util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	
	public static <T> List<T> convertArrayToList(T array[]) 
    { 
  
        // Create an empty List 
        List<T> list = new ArrayList<>(); 
  
        // Iterate through the array 
        for (T t : array) { 
            // Add each element into the list 
            list.add(t); 
        } 
  
        // Return the converted List 
        return list; 
    } 

}
