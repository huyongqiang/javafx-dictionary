package application.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteHelper {
	private final String TABLE_NAME = "Dictionary";
	private Connection conne;
	private Statement stmt;
	private String dbName;

	public SQLiteHelper(String dbName) {
		this.dbName = dbName;
	}

	public void createTable() {
		try {
			Class.forName("org.sqlite.JDBC");
			String db = "jdbc:sqlite:" + dbName;
			conne = DriverManager.getConnection(db);
			stmt = conne.createStatement();
			String sql = "CREATE TABLE " + TABLE_NAME
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Word Char(50) NOT NULL, Meaning Text NOT NULL);";
			stmt.executeUpdate(sql);
			stmt.close();
			conne.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String selectRecord(String word) {
		String meaning = "-1";

		try {
			Class.forName("org.sqlite.JDBC");
			String db = "jdbc:sqlite:" + dbName;
			conne = DriverManager.getConnection(db);
			stmt = conne.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE_NAME + " Where word = '" + word + "';");
			while (rs.next()) {
				meaning = rs.getString("meaning");
			}
			rs.close();
			stmt.close();
			conne.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return meaning;
	}

	public boolean insertRecord(Word word) {
		boolean isOperationSuccess = true;
		int num = 0;

		// check whether duplicate record
		try {
			Class.forName("org.sqlite.JDBC");
			String db = "jdbc:sqlite:" + dbName;
			conne = DriverManager.getConnection(db);
			stmt = conne.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE_NAME + " Where word = '" + word.getWord() + "';");
			while (rs.next()) {
				num++;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (num > 0) {
			return false;
		}

		// insert record
		try {
			String sql = "INSERT INTO " + TABLE_NAME + " (Word, Meaning) " + "VALUES ('" + word.getWord() + "', '"
					+ word.getMeaning() + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			conne.close();
		} catch (Exception e) {
			isOperationSuccess = false;
			e.printStackTrace();
		}

		return isOperationSuccess;
	}

	public boolean deleteRecord(String word) {
		boolean isOperationSuccess = true;
		
		try {
			Class.forName("org.sqlite.JDBC");
			String db = "jdbc:sqlite:" + dbName;
			conne = DriverManager.getConnection(db);
			stmt = conne.createStatement();
			String sql = "DELETE from " + TABLE_NAME + " where word = '" + word + "';";
			stmt.executeUpdate(sql);
			stmt.close();
			conne.close();
		} catch (Exception e) {
			isOperationSuccess = false;
			e.printStackTrace();
		}
		
		return isOperationSuccess;
	}
}
