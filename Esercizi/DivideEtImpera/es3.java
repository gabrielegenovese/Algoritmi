public class es3 {

    public static int solve(int arr[], int a, int b) {
        if (b-a <= 1) {
            if (arr[a] == a) return a;
            else return -1;
        } else {
            int mid = (a+b)/2;
            if (mid == arr[mid])
                return mid;
            if (arr[mid] < mid)
                return solve(arr, mid+1, b);
            else
                return solve(arr, a, mid-1);       
        }
    }

    public static void main(String args[]) {
        int []A = {0,2,5,6,7,8,9,10,11};

        int b = solve(A, 0, A.length-1);

        System.out.println(b);
    }
}

/*

Analisi costo computazionale

Caso base:
    T(1) = 1
Caso n:
    T(n) = T(n/2) + 1

b = 2
a = 1

b > a

Costo: O(log n)

*/