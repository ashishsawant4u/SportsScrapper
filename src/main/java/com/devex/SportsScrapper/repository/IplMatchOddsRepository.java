package com.devex.SportsScrapper.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.devex.SportsScrapper.CricketMatchOddsModel;

@Repository("iplMatchOddsRepository")
public interface IplMatchOddsRepository  extends MongoRepository<CricketMatchOddsModel, ObjectId>
{
	public List<CricketMatchOddsModel> findByMatchCode(String matchCode);
	
	public List<CricketMatchOddsModel> findByMatchCodeAndInningsAndOvers(String matchCode,String innings,String overs);
}
