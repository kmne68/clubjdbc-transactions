package com.kemery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class MemberServiceJdbcTxImpl implements MemberService {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public void renewMember(String memid, String success) {
		
		Connection connection = null;
		Statement statement = null;
		
		try { 
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE tblmembers SET ExpDt = '2018-01-01' WHERE memid = '" + memid + "'");
			
			if(success.equalsIgnoreCase("Y")) {
				// at 43:20 in video
				statement.executeUpdate("INSERT INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2017-03-23', 'D', '01', 100.00)" ); 
			} else {
				// failure (no success)
				statement.executeUpdate("INSET INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2017-03-23', 'D', '01', 100.00)" ); 
			}
			connection.commit();
			System.out.println("Transactions for " + memid + " committed.");
		} catch(SQLException e) {
			try {
				connection.rollback();
				System.out.println("Transaction rolled back.");
			} catch(SQLException ex) {
				
			} finally {
				try {
					connection.close();
				} catch(SQLException ex) {
					
				}
			}
			
			
		}
	}
	
}
