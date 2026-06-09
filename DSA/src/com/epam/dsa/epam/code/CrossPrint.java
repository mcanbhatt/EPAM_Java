package 
com.epam.dsa.epam.code;

public class CrossPrint {

	public static void main(String[] args) {
		print(3);
		
	}
	
	public static void print(int n) {
		 // Upper part		
        for (int i = 1; i <= n; i++) {
        	printHelper(i,n);            
        }

        // Lower part
        for (int i = n; i >= 1; i--) {
        	printHelper(i,n);        	
        }

		//updward
	 /*for(int i=0; i<(size+1)/2 ; i++) {
		 //space
		 int j =0;
		 for(;j<i;j++)
			 System.out.print(" ");
		//System.out.print( j);
		 for(j=0 ;j<=i;j++)
			 System.out.print("#");
		// System.out.print("j--"+j +(i+j));
		 int k =j+i;
		 int n = 2*size-(j+i);
		 //System.out.print("n--"+n);
		 for(;k<n;k++)
			 System.out.print(" ");
		// System.out.print(k);
		 
		 n=k;
		// System.out.print("n--"+n +"i+n"+(n+i));
		 for(;k<=n+i;k++) {
			//System.out.print(2*size-(2*i+1) );
			// System.out.print(2*size-(i+1));
			System.out.print("#");
		 }
		 
		 System.out.println();
		 
	 }
	 
		//Down
	 for(int i=(size+1)/2 -1; i>=0 ; i--) {
		 //space
		 int j =0;
		 for(;j<i;j++)
			 System.out.print(" ");
		//System.out.print( j);
		 for(j=0 ;j<=i;j++)
			 System.out.print("#");
		// System.out.print("j--"+j +(i+j));
		 int k =j+i;
		 int n = 2*size-(j+i);
		 //System.out.print("n--"+n);
		 for(;k<n;k++)
			 System.out.print(" ");
		// System.out.print(k);
		 
		 n=k;
		// System.out.print("n--"+n +"i+n"+(n+i));
		 for(;k<=n+i;k++) {
			//System.out.print(2*size-(2*i+1) );
			// System.out.print(2*size-(i+1));
			System.out.print("#");
		 }
		 
		 System.out.println();
		 
	 }*/
		
	}

	private static void printHelper(int i, int n) {
		// Left spaces
        for (int s = 1; s < i; s++) {
            System.out.print(" ");
        }
        

        // Left #
        for (int j = 1; j <= i; j++) {
            System.out.print("#");
        }

        // Middle spaces
        for (int k = 1; k <= 4 * (n - i); k++) {
            System.out.print(" ");
        }

        // Right #
        for (int j = 1; j <= i; j++) {
            System.out.print("#");
        }

        System.out.println();
	}

}
