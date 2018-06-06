package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer, River> ridmap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new ArrayList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				River old = ridmap.get(id);
				if(old == null) {
					old = new River(id, res.getString("name"));
					ridmap.put(id, old);
				}
				rivers.add(old);
			}
			st.close();
			conn.close();
			
			return rivers;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<Flow> getAllFlowsForRiver(River river, Map<Integer, River> ridmap, Map<Integer, Flow> fidmap){
		
		final String sql = "SELECT * FROM flow WHERE river = ? ORDER BY day";

		List<Flow> flows = new ArrayList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				Flow old = fidmap.get(id);
				if(old == null) {
					Date day = res.getDate("day");
					LocalDate date = day.toLocalDate();
					
					old = new Flow(id, date, res.getDouble("flow"), river);
					fidmap.put(id, old);
				}
				flows.add(old);
			}
			st.close();
			conn.close();
			
			return flows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public River getRiverById(int id) {
		String sql = "select id, name from river where id = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				River river = new River(rs.getInt("id"), rs.getString("name"));
				st.close();
				conn.close();
				
				river.updateAllData(this.getAllFlowsForRiverNoMap(river));
				return river;
				
				
			}
			
			st.close();
			conn.close();
			return null;
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException("Errore DB");
		}
	}

	public List<Flow> getAllFlowsForRiverNoMap(River river) {
		final String sql = "SELECT * FROM flow WHERE river = ? ORDER BY day";

		List<Flow> flows = new ArrayList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				
				Date day = res.getDate("day");
				LocalDate date = day.toLocalDate();
					
				Flow old = new Flow(id, date, res.getDouble("flow"), river);
				
				flows.add(old);
			}
			st.close();
			conn.close();
			
			return flows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		}
}
