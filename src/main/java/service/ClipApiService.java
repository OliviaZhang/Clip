package service;

public class ClipApiService {
	
	private static String ALPHANUMERICAL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private static final int BASE = ALPHANUMERICAL.length();
	
	public static String idToString(long id) {
		
		StringBuilder code = new StringBuilder();
		
		while (id > 0) {
			
			code.insert(0, ALPHANUMERICAL.charAt((int) (id % BASE)));
			
			id = (int)(id / BASE);
		}
		
		return code.toString();
	}

	public static long stringToId(String str) {
		
		long id = 0;
				
		for (int i=0; i < str.length(); i++) {
			
			id = ALPHANUMERICAL.indexOf(str.charAt(i)) + (BASE * id);
		}
		
		return id;
	}
}
