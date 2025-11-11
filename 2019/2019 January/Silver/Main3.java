import java.io.*;
import java.util.*;

public class Main3 {
    static class M implements Comparable<M>{
        long neg, pos;
        M(long n,long p){neg=n;pos=p;}
        public int compareTo(M o){
            if(neg!=o.neg) return neg<o.neg?-1:1;
            if(pos!=o.pos) return pos>o.pos?-1:1;
            return 0;
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer(br.readLine());
        int N=Integer.parseInt(st.nextToken());
        M[] a=new M[N];
        for(int i=0;i<N;i++){
            st=new StringTokenizer(br.readLine());
            long x=Long.parseLong(st.nextToken());
            long y=Long.parseLong(st.nextToken());
            a[i]=new M(x-y,x+y);
        }
        Arrays.sort(a);
        long best = Long.MIN_VALUE;
        int ans=0;
        for(int i=0;i<N;i++){
            if(a[i].pos>best){
                ans++;
                best=a[i].pos;
            }
        }
        System.out.println(ans);
    }
}