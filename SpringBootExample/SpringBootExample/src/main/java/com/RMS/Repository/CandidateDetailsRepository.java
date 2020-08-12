package com.RMS.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.RMS.Bean.CandidateDetails;
import com.RMS.Bean.JobDetails;
import com.RMS.Bean.RecruitmentApplicationProcess;
import com.RMS.Model.ReportModel;

@Transactional
public interface CandidateDetailsRepository extends CrudRepository<CandidateDetails	, Integer> {
	
	@Query("from CandidateDetails where candidateMaster.candidate_id =:candidate_id")
	CandidateDetails getCandidateDetailsByCandidateId(@Param("candidate_id") Integer candidate_id);
	 
	@Query("from CandidateDetails")
	List<CandidateDetails> getAllCandidateDetails();
	
	@Query("from CandidateDetails where month(candidateMaster.registration_date) =:month and year(candidateMaster.registration_date) =:year and candidateMaster.deleted='N' and candidateMaster.active_inactive='Y' ")
	public List<CandidateDetails> getCandidateMasterListByMonthAndYear(@Param("month") Integer month,@Param("year") Integer year);
	
	@Query("from CandidateDetails where candidateMaster.full_name =:full_name and stateMaster.state=:state and cityMaster.city =:city and candidateMaster.deleted='N' and candidateMaster.active_inactive='Y'  ")
		List<ReportModel> getCandidateDetailsByfull_name(@Param("full_name") String full_name,
				@Param("state") String state,@Param("city") String city);


}
