package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Mutter;

public class MutterDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	//共通接続処理
	public void connect() throws SQLException, NamingException {
		Context context = new InitialContext();
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/jsp");
		this.db = ds.getConnection();
	}
	//共通切断処理
	public void disconnect() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(db != null) {
				db.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//テーブル全データ抽出
	public List<Mutter> findall(){
		List<Mutter> list = new ArrayList();
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM mutter ORDER BY id DESC");
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String text = rs.getString("text");
				Mutter mutter = new Mutter(id,name,text);
				list.add(mutter);
			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return list;
	}

	//つぶやきをDBに一件インサートする
	public boolean createOne(Mutter mutter) {
		try {
			this.connect();
			ps = db.prepareStatement("INSERT INTO mutter(name,text) VALUES(?,?)");
			ps.setString(1, mutter.getUserName());
			ps.setString(2, mutter.getText());
			int result = ps.executeUpdate();//追加された行数が代入
			if(result != 1) {
				return false;
			}
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}finally {
			this.disconnect();
		}
		return true;
	}
}
