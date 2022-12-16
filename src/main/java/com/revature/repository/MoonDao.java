package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() throws SQLException {
	
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		}
		
	}

	public Moon getMoonByName(String username, String moonName) throws SQLException {
	
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}
	}

	public Moon getMoonById(String username, int moonId) throws SQLException {

		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}
		
	}

	public Moon createMoon(String username, Moon m) throws SQLException {

		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "insert into moons values(default,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int intId = rs.getInt("id");
			Moon moon = new Moon();
			moon.setId(intId);
			moon.setName(m.getName());
			moon.setMyPlanetId(m.getMyPlanetId());
			return moon;
		}
		
	}

	public void deleteMoonById(int moonId) throws SQLException {
		
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			int rowAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowAffected);
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException {
	
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where myplanetid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		}
	}
// 	public static void main(String[] args) {
// 		MoonDao moonDao = new MoonDao();
// 		Moon moon = new Moon();
// 		moon.setName("Moon");
// 		moon.setMyPlanetId(12);
// 		try {
// 			//System.out.println(moonDao.createMoon("username", moon).getId());
// 			//System.out.println(moonDao.getAllMoons());
// 			//System.out.println(moonDao.getMoonByName("user", "Moon"));
// 			//System.out.println(moonDao.getMoonById("null", 1));
// 			//System.out.println(moonDao.getMoonsFromPlanet(12));
// 			//moonDao.deleteMoonById(2);
// 	} catch (SQLException e) {
// 		System.out.println(e.getMessage());
// 	}
// }
}
