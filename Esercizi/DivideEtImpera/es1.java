public class es1 {

    public static boolean solve(int arr[], int a, int b) {
        if (a >= b) 
            return false;
        else {
            int mid = (a+b)/2;
            return arr[mid] == arr[mid+1] || solve(arr, a, mid) || solve(arr, mid+1, b);
        }
    }

    public static void main(String args[]) {
        int []A = {1,2,3,4,5,6,7,8,9,10};

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

In base al Master Theorem
a = b = 2

Costo: O(n)

*/