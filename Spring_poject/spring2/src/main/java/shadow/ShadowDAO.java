package shadow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cosmetic.Cosmetic;

public class ShadowDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb;MODE=LEGACY";
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL,"jwbook","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public List<Shadow> getAll() throws Exception {
		Connection conn = open();
		List<Shadow> CosmeticList = new ArrayList<>();
		
		String sql = "select cosmetic_id, cosmetic_name, cosmetic_price, cosmetic_img from cosmetic where cosmetic_type='Eye Shadow'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
				Shadow n = new Shadow();
				n.setCosmetic_id(rs.getInt("cosmetic_id"));
				n.setCosmetic_name(rs.getString("cosmetic_name"));
				n.setCosmetic_price(rs.getInt("cosmetic_price")); 
				n.setCosmetic_img(rs.getString("cosmetic_img"));
				
				CosmeticList.add(n);
			}
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		return CosmeticList;
	}
	
	
	public Shadow getShadow(int cosmetic_id) throws SQLException {
		Connection conn = open();
		
		Shadow n = new Shadow();
		String sql = "select cosmetic_id, cosmetic_name, cosmetic_price, cosmetic_img from cosmetic where cosmetic_id=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, cosmetic_id);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		try {
			n.setCosmetic_id(rs.getInt("cosmetic_id"));
			n.setCosmetic_name(rs.getString("cosmetic_name"));
			n.setCosmetic_price(rs.getInt("cosmetic_price"));
			n.setCosmetic_img(rs.getString("cosmetic_img"));

			pstmt.executeQuery();
			return n;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return n;
	}
}
