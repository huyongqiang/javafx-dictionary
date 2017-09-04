package application.utils;

public class DBTester {

	public static void main(String[] args) {
		SQLiteHelper sqLiteHelper = new SQLiteHelper("dictionary.db");
		//sqLiteHelper.createTable();
		
		//System.out.println(sqLiteHelper.insertRecord(new Word("suppress", "If someone in authority suppresses an activity, they prevent it from continuing, by using force or making it illegal.")));
		//System.out.println(sqLiteHelper.selectRecord("suppr"));
		
		//System.out.println(sqLiteHelper.deleteRecord("suppress"));
	}

}
