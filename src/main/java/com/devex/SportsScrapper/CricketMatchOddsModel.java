package com.devex.SportsScrapper;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "CricketMatchOdds")
public class CricketMatchOddsModel 
{
	@MongoId
	private ObjectId _id;
	
	//create fields team1Name,team2Name,team1Score,team2Score,innings,overs,team1Back,team1Lay,team2Back,team2Lay
	private String team1Name;
	private String team2Name;
	private String team1Score;
	private String team2Score;
	private String innings;
	private String overs;
	private String team1Back;
	private String team1Lay;
	private String team2Back;
	private String team2Lay;
	private String league;
	private String matchCode;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
	@CreatedDate
	private Date createDateTime;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getTeam1Name() {
		return team1Name;
	}
	public void setTeam1Name(String team1Name) {
		this.team1Name = team1Name;
	}
	public String getTeam2Name() {
		return team2Name;
	}
	public void setTeam2Name(String team2Name) {
		this.team2Name = team2Name;
	}
	public String getTeam1Score() {
		return team1Score;
	}
	public void setTeam1Score(String team1Score) {
		this.team1Score = team1Score;
	}
	public String getTeam2Score() {
		return team2Score;
	}
	public void setTeam2Score(String team2Score) {
		this.team2Score = team2Score;
	}
	public String getInnings() {
		return innings;
	}
	public void setInnings(String innings) {
		this.innings = innings;
	}
	public String getOvers() {
		return overs;
	}
	public void setOvers(String overs) {
		this.overs = overs;
	}
	public String getTeam1Back() {
		return team1Back;
	}
	public void setTeam1Back(String team1Back) {
		this.team1Back = team1Back;
	}
	public String getTeam1Lay() {
		return team1Lay;
	}
	public void setTeam1Lay(String team1Lay) {
		this.team1Lay = team1Lay;
	}
	public String getTeam2Back() {
		return team2Back;
	}
	public void setTeam2Back(String team2Back) {
		this.team2Back = team2Back;
	}
	public String getTeam2Lay() {
		return team2Lay;
	}
	public void setTeam2Lay(String team2Lay) {
		this.team2Lay = team2Lay;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getLeague() {
		return league;
	}
	public void setLeague(String league) {
		this.league = league;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CricketMatchOdds [_id=");
		builder.append(_id);
		builder.append(", team1Name=");
		builder.append(team1Name);
		builder.append(", team2Name=");
		builder.append(team2Name);
		builder.append(", team1Score=");
		builder.append(team1Score);
		builder.append(", team2Score=");
		builder.append(team2Score);
		builder.append(", innings=");
		builder.append(innings);
		builder.append(", overs=");
		builder.append(overs);
		builder.append(", team1Back=");
		builder.append(team1Back);
		builder.append(", team1Lay=");
		builder.append(team1Lay);
		builder.append(", team2Back=");
		builder.append(team2Back);
		builder.append(", team2Lay=");
		builder.append(team2Lay);
		builder.append(", league=");
		builder.append(league);
		builder.append(", matchCode=");
		builder.append(matchCode);
		builder.append(", createDateTime=");
		builder.append(createDateTime);
		builder.append("]");
		return builder.toString();
	}
	
	
}
