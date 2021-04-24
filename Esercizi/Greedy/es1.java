public class es1 {
    
    public static int solve(double d[], double k, double c) {
        double res = k * c;
        int i = 0, f = 0;
    
        while(d.length > i) {
            if(res < d[i]) {
                //System.out.println("Ho fatto rifornimento!");
                res = k * c;
                ++f;
            }
            res -= d[i];
            if (res < 0)
                return -1;
            ++i;
        }

        return f;
    }

    public static void main(String args[]) {
        double K = 10, C = 10;
        double []d = {72,23,55,19,99,11};
        
        int s = solve(d, K, C);
        System.out.println(s);
    }
}