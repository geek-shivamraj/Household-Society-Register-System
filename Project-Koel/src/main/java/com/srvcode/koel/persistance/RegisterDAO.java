package com.srvcode.koel.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.VisitorDetails;
import com.srvcode.koel.models.VisitorResponse;

@Repository
public class RegisterDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public VisitorResponse getVisitorDetail(String governmentId) {
		String sql = "select InsertionOrderId from VisitorMaster where GovernmentId = ?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<VisitorResponse>() {

			@Override
			public VisitorResponse extractData(ResultSet rs) throws SQLException, DataAccessException {
				VisitorResponse visitorResponse = null;
		        while (rs.next()) {
		        	visitorResponse = new VisitorResponse();
		        	visitorResponse.setInsertionOrderId(rs.getString("InsertionOrderId"));
		        }
		        return visitorResponse;
			}
        }, governmentId);
	}

	public int addVisitorDetail(AddVisitorRequest request, String dateTimeIn, String visitorMasterId) {
		return jdbcTemplate.update(
				"INSERT INTO VisitorDetails (VisitorMasterId, FullName, Purpose, DateTimein, DateTimeout, VisitorStatus, VisitorAddress, VisitingAddress, VisitorContactNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				visitorMasterId, request.getFullName(), request.getPurpose(), dateTimeIn, "-", "Enter", request.getVisitorAddress(), request.getVisitingAddress(), request.getVisitorContactNumber());
	}

	public int addToVisitorMaster(AddVisitorRequest request, String dateTimeIn) {
		return jdbcTemplate.update(
				"INSERT INTO VisitorMaster (GovernmentId, DateUpdated, UpdatedBy) VALUES (? ,? ,?)", request.getGovernmentId(), dateTimeIn, request.getUserId());
	}

	public List<VisitorResponse> searchVistorById(String visitorMasterId) {
		String sql = "Select FullName, DateTimein, DateTimeout, VisitorStatus from VisitorDetails where VisitorMasterId = ? ORDER BY DateTimein desc";
        return jdbcTemplate.query(sql, new RowMapper<VisitorResponse>() {
            public VisitorResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            	VisitorResponse visitorResponse = new VisitorResponse();
            	visitorResponse.setFullName(rs.getString("FullName"));
	        	visitorResponse.setDateTimeIn(rs.getString("DateTimein"));
	        	visitorResponse.setDateTimeOut(rs.getString("DateTimeout"));
	        	visitorResponse.setVisitorStatus(rs.getString("VisitorStatus"));
                return visitorResponse;
            }
        }, visitorMasterId);
	}

	public List<VisitorResponse> searchVistorByDate(String date) {
		date = "%" + date + "%";
		String sql = "Select FullName, DateTimein, DateTimeout, VisitorStatus from VisitorDetails where DateTimein like ? or DateTimeout like ? ORDER BY DateTimein desc";
        return jdbcTemplate.query(sql, new RowMapper<VisitorResponse>() {
            public VisitorResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            	VisitorResponse visitorResponse = new VisitorResponse();
            	visitorResponse.setFullName(rs.getString("FullName"));
	        	visitorResponse.setDateTimeIn(rs.getString("DateTimein"));
	        	visitorResponse.setDateTimeOut(rs.getString("DateTimeout"));
	        	visitorResponse.setVisitorStatus(rs.getString("VisitorStatus"));
                return visitorResponse;
            }
        }, date, date);
	}

	public VisitorDetails getVisitorLatestRecord(String visitorMasterId) {
		String sql = "Select Top 1 InsertionOrderId, FullName, Purpose, DateTimein, DateTimeout, VisitorStatus, VisitorAddress, VisitingAddress, VisitorContactNumber from VisitorDetails where VisitorMasterId = ? ORDER BY DateTimein desc";
        return jdbcTemplate.query(sql, new ResultSetExtractor<VisitorDetails>() {

			@Override
			public VisitorDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
				VisitorDetails visitorResponse = null;
		        while (rs.next()) {
		        	visitorResponse = new VisitorDetails();
		        	visitorResponse.setInsertionOrderId(rs.getString("InsertionOrderId"));
		        	visitorResponse.setFullName(rs.getString("FullName"));
		        	visitorResponse.setDateTimeIn(rs.getString("DateTimein"));
		        	visitorResponse.setDateTimeOut(rs.getString("DateTimeout"));
		        	visitorResponse.setVisitorStatus(rs.getString("VisitorStatus"));
		        	visitorResponse.setVisitorAddress(rs.getString("VisitorAddress"));
		        	visitorResponse.setVisitingAddress(rs.getString("VisitingAddress"));
		        	visitorResponse.setVisitorContactNumber(rs.getString("VisitorContactNumber"));
		        }
		        return visitorResponse;
			}
        }, visitorMasterId);
	}

	public void updateVisitorStatus(String insertionOrderId, String dateTimeOut) {
		jdbcTemplate.update("UPDATE VisitorDetails SET DateTimeout = ?, VisitorStatus = ?  WHERE InsertionOrderId = ?", dateTimeOut, "Exit", insertionOrderId);
	}


}
