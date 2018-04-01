package com.kemery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class MemberServiceJdbcTxImplWithSpring implements MemberService {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	@Transactional
	public void renewMember(String memid, String success) {
		
		Connection connection = DataSourceUtils.getConnection(dataSource);
		Statement statement = null;
		
		try { 
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE tblmembers SET ExpDt = '2018-01-01' WHERE memid = '" + memid + "'");
			
			if(success.equalsIgnoreCase("Y")) {
				// at 43:20 in video
				statement.executeUpdate("INSERT INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2017-03-23', 'D', '01', 100.00)" ); 
			} else {
				// failure (no success)
				statement.executeUpdate("INSET INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2017-03-23', 'D', '01', 100.00)" ); 
			}
			System.out.println("Transactions for " + memid + " committed.");
		} catch(SQLException e) {
			// From text page 191
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			throw new RuntimeException(e);
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
		
	}
	
}
