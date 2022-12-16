package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    public static Logger logger = LoggerFactory.getLogger(PlanetDao.class);

    public List<Planet> getAllPlanets() throws SQLException {
	
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Planet> planets = new ArrayList<>();
			while(rs.next()){ 
				//the resultset next method returns a boolean, so we use it in our loop
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		}
	}

	public Planet getPlanetByName(String owner, String planetName) throws SQLException {

		try(Connection connection = ConnectionUtil.createConnection()) {
			//UserDao user = new UserDao();
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			// ps.setInt(2, user.getUserByUsername(owner).getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		}
		
	}

	public Planet getPlanetById(String username, int planetId) throws SQLException{
	
		try(Connection connection = ConnectionUtil.createConnection()) {
			// UserDao user = new UserDao();
			String sql = "select * from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			//ps.setInt(2, user.getUserByUsername(username).getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		}
	}

	public Planet createPlanet(String username, Planet p) throws SQLException {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "insert into planets values(default,?,?)";
			// UserDao user = new UserDao();
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());
			ps.setInt(2, p.getOwnerId());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			Planet newPlanet = new Planet();
			int newId = rs.getInt("id");
			newPlanet.setId(newId);
			newPlanet.setName(p.getName());
			newPlanet.setOwnerId(p.getOwnerId());
			return newPlanet;
		}
	}

	public void deletePlanetById(int planetId) throws SQLException{
	
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int rowAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowAffected);
		 } //catch(SQLException e) {
		// 	System.out.println(e.getMessage()); // Good spot to add some logging?
		// }
	}

	// public static void main(String[] args) {
	// 	PlanetDao planetDao = new PlanetDao();
	// 	Planet planet = new Planet();
	// 	planet.setName("Earth");
	// 	planet.setOwnerId(4);
	// 	try {
	// 		//System.out.println(planetDao.getAllPlanets());
	// 		 //System.out.println(planetDao.getPlanetByName("username", "Earth"));
	// 		// System.out.println(planetDao.getPlanetById("username", 12));
	// 		System.out.println(planetDao.createPlanet("username", planet).getId());
	// 	}catch (SQLException e) {
	// 		
	// 		logger.error(e.getMessage());
			
	// 	}
	// 	//planetDao.deletePlanetById(11);
	// }

}
