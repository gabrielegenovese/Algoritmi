public class es4 {
    
    public static int searchNext(int arr[], int a, int b, int n) {
        if(a >= b) {
            if(arr[a] >= n)
                return a;
            else
                return -1;
        } else {
            int mid = (a+b)/2;
            if(arr[mid] == n)
                return searchNext(arr, a, mid, n);
            if(arr[mid] > n)
                return searchNext(arr, a, mid, n);
            else
                return searchNext(arr, mid+1, b, n);
        }
    }

    public static int searchPrec(int arr[], int a, int b, int n) {
        if(a >= b) {
            if(arr[b] <= n)
                return b;
            else
                return -1;
        } else {
            int mid = (a+b)/2;
            if(arr[mid] == n)
                return searchPrec(arr, mid+1, b, n);
            if(arr[mid] < n)
                return searchPrec(arr, mid+1, b, n);
            else
                return searchPrec(arr, a, mid-1, n);
        }
    }

    public static int solve(int arr[], int low, int up) {
        int x = searchNext(arr, 0, arr.length-1, low);
        int y = searchPrec(arr, 0, arr.length-1, up);
        return y - x + 1;
    }

    public static void main(String args[]) {
        int []A = {-20,2,5,11,12,12,12,12,12,13,13,15};

        // assumo che low > up
        int b = solve(A, 6, 12);
        System.out.println(b);
    }
}

/*

Analisi costo computazionale

Caso n:
    T(n) = O(2log n) + 1

Costo: O(log n)

*/