package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtils {

	public static boolean isNullOrEmpty(Collection c){
		return c == null || c.isEmpty();
	}
	
	public static boolean isNotNullOrEmpty(Collection c){
		return !isNullOrEmpty(c);
	}
	
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }
	
	public static <T> List<List<T>> allCombinations(List<T> list) {
		int n = list.size();
		long combinations = 1 << n;
		List<List<T>> aResult = new ArrayList<List<T>>();
		List<T> localCombination;
		for (int setNumber = 0; setNumber < combinations; setNumber++) {
			localCombination = new ArrayList<>();
			for (int digit = 0; digit < n; digit++) {
				if ((setNumber & (1 << digit)) > 0) {
					localCombination.add(list.get(digit));
				}
			}
			aResult.add(localCombination);
		}
		return aResult;
	}
	
	public static <T> List<T> clone(List<T> list){
		return list.stream().collect(Collectors.toList());
	}
}
