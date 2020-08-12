package com.RMS.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.RMS.Bean.CandidateLanguageDetails;

@Transactional
public interface CandidateLanguageDetailsRepository extends CrudRepository<CandidateLanguageDetails, Integer> {

	@Query("from CandidateLanguageDetails where candidateMaster.candidate_id = :candidate_id")
	List<CandidateLanguageDetails> getCandidateLanguageDetailsByCandidateId(@Param("candidate_id") Integer candidate_id);

	@Query("from CandidateLanguageDetails where candidate_language_detail_id = :candidate_language_detail_id")
	CandidateLanguageDetails getCandidateLanguageDetailsById(@Param("candidate_language_detail_id") Integer candidate_language_detail_id);
}
