package com.RMS.Bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="candidate_language_details")
public class CandidateLanguageDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="candidate_language_detail_id",columnDefinition="int")
	private Integer candidate_language_detail_id;
	
	@OneToOne
	@JoinColumn(name = "candidate_id",columnDefinition="int")
	private CandidateMaster candidateMaster;
	
	@Column(name="wr")
	private String wr;
	
	@Column(name="rd")
	private String rd;
	
	@Column(name="speak")
	private String speak;
	
	/*
	 * @Column(name="language_proficiency_level") private String
	 * language_proficiency_level;
	 */
	@OneToOne
	@JoinColumn(name = "language_id",columnDefinition="int")
	private LanguageMaster languageMaster;

	public Integer getCandidate_language_detail_id() {
		return candidate_language_detail_id;
	}

	public void setCandidate_language_detail_id(Integer candidate_language_detail_id) {
		this.candidate_language_detail_id = candidate_language_detail_id;
	}

	public CandidateMaster getCandidateMaster() {
		return candidateMaster;
	}

	public void setCandidateMaster(CandidateMaster candidateMaster) {
		this.candidateMaster = candidateMaster;
	}

	public String getWr() {
		return wr;
	}

	public void setWr(String wr) {
		this.wr = wr;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getSpeak() {
		return speak;
	}

	public void setSpeak(String speak) {
		this.speak = speak;
	}

	public LanguageMaster getLanguageMaster() {
		return languageMaster;
	}

	public void setLanguageMaster(LanguageMaster languageMaster) {
		this.languageMaster = languageMaster;
	}
	

	
}
