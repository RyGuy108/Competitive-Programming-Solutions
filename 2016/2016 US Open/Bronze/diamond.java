import java.io.*;
import java.util.*;
public class diamond {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("diamond.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("diamond.out")));
		// read in N and K
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		// read in sizes of all the diamonds
		int[] list = new int[n];
		for(int i = 0; i < n; i++) {
			list[i] = Integer.parseInt(br.readLine());
		}
		int ans = 0;
		for(int i = 0; i < n; i++) {
			// list[i] will be the size of the smallest diamond in the case
			int amt = 0;
			for(int j = 0; j < n; j++) {
				// loop over all diamonds, see if this diamond can be arranged with the selected one
				if(list[j] >= list[i] && list[j] <= list[i] + k) {
					amt++;
				}
			}
			// update our answer
			if(amt > ans) {
				ans = amt;
			}
		}
		// print the answer
		pw.println(ans);
		pw.close();
	}
}