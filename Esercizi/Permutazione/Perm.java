public class Perm {

	static int missing(int[] a, int length) {
		int max = length+1;
		int sum = max*(max+1)/2;
		
		for(int i = 0; i < length; ++i) {
			sum -= a[i];
		}
		
		return sum;
	}
		
	public static void main(String args[]) {
		try {
			
			int[] arr = {3,1,5,2,4,6,7,8,9,10,11,13,14,15};
			int missingNumber = missing(arr,14);
			System.out.println(missingNumber);

		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(0);
		}
	}
}