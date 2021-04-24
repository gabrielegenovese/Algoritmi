public class es2 {

    public static boolean solve(int arr[], int a, int b) {
        if (a >= b) 
            return true;
        else {
            int mid = (a+b)/2;
            boolean first = solve(arr, a, mid);
            boolean second = solve(arr, mid+1, b);
            return arr[mid] < arr[mid+1] && first && second;
        }
    }

    public static void main(String args[]) {
        int []A = {1,2,3,6,5,6,7,8,9,10};

        boolean b = solve(A, 0, A.length-1);

        System.out.println(b);
    }
}

/*

Analisi costo computazionale

Caso base:
    T(1) = 1
Caso n:
    T(n) = 2T(n/2) + 1

a = b = 2

Costo: O(n)

*/