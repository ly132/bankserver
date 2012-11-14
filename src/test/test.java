package test;

public class test {

	public static void main(String[] args){
		
		String[] s = "TIME\t\t\tOPER\tTYPE\t\tOUTCOME\tBALANCE".split("\\t");
		System.out.println(s.length);
		int c = 0;
		for( String ss : s )
		{
			System.out.println(c+":"+ss);
			c++;
		}
		
		System.out.println("2012-11-13".replaceAll("-", ""));
		
		System.out.println(Double.parseDouble(""));
	}
}
