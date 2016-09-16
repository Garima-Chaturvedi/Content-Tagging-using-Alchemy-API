package json.filter;

public class Sigma {

	public static void main(String[] args) {
		int a=0;
		int b;
		int sumk=0;
		int sumj=0;
		for(int i = 0;i<=4;i++){
			
			for(int j= 0;j<=4;j++){
				for(int k = 0;k<=4;k++){
					sumk = sumk + (j*k-i);
				}
				sumk = sumk *(i+j);
				sumj = sumj+sumk;
				sumk=0;
				
			}
			 a = a + i* sumj;
			 sumj = 0;
			
		}
		System.out.println("kkk"+ a);
		// TODO Auto-generated method stub

	}

}
