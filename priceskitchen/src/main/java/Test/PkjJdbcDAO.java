package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PkjJdbcDAO implements PkDAO {
	@Autowired
	private DataSource dataSource;

	public PkjJdbcDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static final String INSERT = "INSERT INTO Wholesale_price values(?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public boolean insert(WPbean bean) {
		int count = 0;
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement pst = conn.prepareStatement(INSERT)) {
			pst.setInt(1, bean.getId());
			pst.setString(2, bean.getIngre_name());
			pst.setString(3, bean.getClassName());
			pst.setDate(4, bean.getUpdate_date());
			pst.setBigDecimal(5, bean.getPrice_upper());
			pst.setBigDecimal(6, bean.getPrice_middle());
			pst.setBigDecimal(7, bean.getPrice_bottom());
			pst.setBigDecimal(8, bean.getPrice_average());
			pst.setString(9, bean.getMarket());
			pst.setNull(10, java.sql.Types.BLOB);
			pst.setNull(11, java.sql.Types.INTEGER);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count == 0) {
			return false;
		} else {
			return true;
		}

	}

}
