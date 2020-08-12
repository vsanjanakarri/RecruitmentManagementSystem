package com.RMS.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.jni.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.RMS.Bean.CandidateDetails;
import com.RMS.Bean.CandidateDetails_;
import com.RMS.Bean.CandidateEductionDetails;
import com.RMS.Bean.CandidateLanguageDetails;
import com.RMS.Bean.CandidateMaster;
import com.RMS.Bean.CandidateSkillDetails;
import com.RMS.Bean.CandidateUploadDocument;
import com.RMS.Bean.CompanyMaster;
import com.RMS.Bean.JobDetails;
import com.RMS.Bean.RecruitmentApplicationProcess;
import com.RMS.Common.Common;
import com.RMS.Model.CandidateAndCompanyModel;
import com.RMS.Model.CandidateDetailsModel;
import com.RMS.Model.CandidateEductionDetailsModel;
import com.RMS.Model.CandidateLanguageDetailModel;
import com.RMS.Model.CandidatePersonalDetailsModel;
import com.RMS.Model.CandidateSkillDetailsModel;
import com.RMS.Model.CandidateUploadDocumentModel;
import com.RMS.Model.JobDetailsModel;
import com.RMS.Model.RecruitmentApplicationProcessModel;
import com.RMS.Repository.CandidateDetailsRepository;
import com.RMS.Repository.CandidateEductionDetailsRepository;
import com.RMS.Repository.CandidateLanguageDetailsRepository;
import com.RMS.Repository.CandidateRepository;
import com.RMS.Repository.CandidateSkillDetailsRepository;
import com.RMS.Repository.CandidateUploadDocumentRepository;
import com.RMS.Repository.CityRepository;
import com.RMS.Repository.CompanyRepository;
import com.RMS.Repository.CountryRepository;
import com.RMS.Repository.DistrictRepository;
import com.RMS.Repository.JobDetailsRepository;
import com.RMS.Repository.LanguageRepository;
import com.RMS.Repository.RecruitmentApplicationProcessRepository;
import com.RMS.Repository.StateRepository;
import com.RMS.Repository.TalukaRepository;

@Controller
public class CandidateController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private CandidateDetailsRepository candidateDetailsRepository;

	@Autowired
	private CandidateEductionDetailsRepository candidateEductionDetailsRepository;

	@Autowired
	private CandidateLanguageDetailsRepository candidateLanguageDetailsRepository;

	@Autowired
	private CandidateSkillDetailsRepository candidateSkillDetailsRepository;

	@Autowired
	private CandidateUploadDocumentRepository candidateUploadDocumentRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private TalukaRepository talukaRepository;

	@Autowired
	private JobDetailsRepository jobDetailsRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private RecruitmentApplicationProcessRepository recruitmentApplicationProcessRepository;

	@RequestMapping(value = "/getCandidatePersonalDetails")
	public ModelAndView getCandidatePersonalDetails(HttpSession session,
			@ModelAttribute("form") CandidateAndCompanyModel candidateAndCompanyModel) {
		ModelMap map = new ModelMap();
		logger.info("getCandidatePersonalDetails method:=");
		try {
			CandidateMaster candidatemaster = candidateRepository
					.getCandidateMasterById((Integer) session.getAttribute("candidate_id"));
			if (candidatemaster != null) {
				CandidateDetailsModel candidateDetailsModel = new CandidateDetailsModel();
				candidateDetailsModel.setEmail_id(candidatemaster.getEmail_id());
				candidateDetailsModel.setFull_name(candidatemaster.getFull_name());
				CandidateDetails candidateDetails = candidateDetailsRepository
						.getCandidateDetailsByCandidateId((Integer) session.getAttribute("candidate_id"));
				if (candidateDetails != null) {
					candidateDetailsModel.setAadhar_card_no(candidateDetails.getAadhar_card_no());
					candidateDetailsModel.setAddress(candidateDetails.getAddress());
					candidateDetailsModel.setCandidate_details_id(candidateDetails.getCandidate_details_id());
					candidateDetailsModel.setCandidate_id(candidateDetails.getCandidateMaster().getCandidate_id());
					if (candidateDetails.getCityMaster() != null)
						candidateDetailsModel.setCity_id(candidateDetails.getCityMaster().getCity_id());
					if (candidateDetails.getCountryMaster() != null)
						candidateDetailsModel.setCountry_id(candidateDetails.getCountryMaster().getCountry_id());
					candidateDetailsModel.setDate_of_birth(
							Common.ddMMMyyyySDF.parse(Common.ddMMMyyyySDF.format(candidateDetails.getDate_of_birth())));
					if (candidateDetails.getDistrictMaster() != null)
						candidateDetailsModel.setDistrict_id(candidateDetails.getDistrictMaster().getDistrict_id());
					candidateDetailsModel.setEmail_id(candidateDetailsModel.getEmail_id());
					candidateDetailsModel.setFacebook_profile_url(candidateDetails.getFacebook_profile_url());
					candidateDetailsModel.setGender(candidateDetails.getGender());
					candidateDetailsModel.setLinkedln_profile_url(candidateDetails.getLinkedln_profile_url());
					candidateDetailsModel.setMobile_no(candidateDetails.getMobile_no());
					candidateDetailsModel.setPan_card_no(candidateDetails.getPan_card_no());
					candidateDetailsModel.setPersonal_email_id(candidateDetails.getPersonal_email_id());
					candidateDetailsModel.setPincode(candidateDetails.getPincode());
					if (candidateDetails.getStateMaster() != null)
						candidateDetailsModel.setState_id(candidateDetails.getStateMaster().getState_id());
					if (candidateDetails.getTalukaMaster() != null)
						candidateDetailsModel.setTaluka_id(candidateDetails.getTalukaMaster().getTaluka_id());
					candidateDetailsModel.setTelephone_no(candidateDetails.getTelephone_no());
					candidateDetailsModel.setTwitter_profile_url(candidateDetails.getTwitter_profile_url());
				}
				map.addAttribute("countryList", countryRepository.getAllCountryList());
				map.addAttribute("stateList", stateRepository.getAllStateList());
				map.addAttribute("cityList", cityRepository.getAllCityList());
				map.addAttribute("districtList", districtRepository.getAllDistrictList());
				map.addAttribute("talukaList", talukaRepository.getAllTalukaList());
				map.addAttribute("form", candidateDetailsModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidatePersonalDetails:= " + e);
		}

		return new ModelAndView("CandidatePersonalDetails", map);
	}

	@RequestMapping(value = "/saveCandidatePersonalDetails")
	public ModelAndView saveCandidatePersonalDetails(
			@ModelAttribute("form") CandidateDetailsModel candidateDetailsModel, BindingResult bindingResult) {
		logger.info("saveCandidatepersonaldetails method:=");
		ModelMap map = new ModelMap();
		try {
			CandidateDetails candidateDetails = new CandidateDetails();
			candidateDetails.setCandidate_details_id(candidateDetailsModel.getCandidate_details_id());
			candidateDetails.setAadhar_card_no(candidateDetailsModel.getAadhar_card_no());
			candidateDetails.setAddress(candidateDetailsModel.getAddress());
			candidateDetails.setDate_of_birth(candidateDetailsModel.getDate_of_birth());
			candidateDetails.setFacebook_profile_url(candidateDetailsModel.getFacebook_profile_url());
			candidateDetails.setGender(candidateDetailsModel.getGender());
			candidateDetails.setLinkedln_profile_url(candidateDetailsModel.getLinkedln_profile_url());
			candidateDetails.setMobile_no(candidateDetailsModel.getMobile_no());
			candidateDetails.setPan_card_no(candidateDetailsModel.getPan_card_no());
			candidateDetails.setTelephone_no(candidateDetailsModel.getTelephone_no());
			candidateDetails.setPincode(candidateDetailsModel.getPincode());
			candidateDetails.setTwitter_profile_url(candidateDetailsModel.getTwitter_profile_url());
			candidateDetails.setCandidateMaster(
					candidateRepository.getCandidateMasterById(candidateDetailsModel.getCandidate_id()));
			candidateDetails.setStateMaster(stateRepository.getStateById(candidateDetailsModel.getState_id()));
			candidateDetails.setCountryMaster(countryRepository.getCountryById(candidateDetailsModel.getCountry_id()));
			candidateDetails
					.setDistrictMaster(districtRepository.getDistrictById(candidateDetailsModel.getDistrict_id()));
			candidateDetails.setTalukaMaster(talukaRepository.getTalukaById(candidateDetailsModel.getTaluka_id()));
			candidateDetails.setPersonal_email_id(candidateDetailsModel.getPersonal_email_id());
			candidateDetailsRepository.save(candidateDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveCandidatePersonalDetails:= " + e);
		}

		return new ModelAndView("CandidatePersonalDetails", map);
	}

	@RequestMapping(value = "/getCandidateEductionDetails")
	public ModelAndView getCandidateEductionDetails(HttpSession session,
			@ModelAttribute("form") CandidateEductionDetailsModel candidateEductionDetailsModel) {
		ModelMap map = new ModelMap();
		logger.info("getCandidateEductionDetailsModel method:=");
		try {
			List<CandidateEductionDetails> candidateEductionDetails = candidateEductionDetailsRepository
					.getCandidateEductionDetailsByCandidateId((Integer) session.getAttribute("candidate_id"));
			List<CandidateEductionDetailsModel> candidateEductionModelList = new ArrayList<CandidateEductionDetailsModel>();
			CandidateEductionDetailsModel candidateEduction = new CandidateEductionDetailsModel();
			if (candidateEductionDetails != null) {
				for (CandidateEductionDetails eduction : candidateEductionDetails) {
					CandidateEductionDetailsModel candidateEductionModel = new CandidateEductionDetailsModel();
					candidateEductionModel.setCandidate_eduction_detail_id(eduction.getCandidate_eduction_detail_id());
					candidateEductionModel.setCollage_name(eduction.getCollage_name());
					candidateEductionModel.setEduction(eduction.getEduction());
					candidateEductionModel.setPassing_year(eduction.getPassing_year());
					candidateEductionModel.setPercantage(eduction.getPercantage());
					candidateEductionModel.setUniversity_inititute(eduction.getUniversity_inititute());
					candidateEductionModelList.add(candidateEductionModel);
				}
				if (candidateEductionModelList.size() == 0)
					candidateEductionModelList.add(new CandidateEductionDetailsModel());

				candidateEduction.setCandidateEductionDetailsModelList(candidateEductionModelList);
			}
			map.addAttribute("form", candidateEduction);
			map.addAttribute("candidate_id", (Integer) session.getAttribute("candidate_id"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidateEductionDetails:= " + e);
		}

		return new ModelAndView("Candidate_EducationalDetailsPage", map);
	}

	@RequestMapping(value = "/saveCandidateEductionDetails")
	public ModelAndView saveCandidateEductionDetails(
			@ModelAttribute("form") CandidateEductionDetailsModel candidateEductionDetailsModel,
			BindingResult bindingResult, HttpSession session) {
		logger.info("saveCandidateEductionDetails method:=");
		ModelMap map = new ModelMap();
		try {
			if (candidateEductionDetailsModel.getCandidateEductionDetailsModelList() != null
					&& candidateEductionDetailsModel.getCandidateEductionDetailsModelList().size() > 0) {
				for (CandidateEductionDetailsModel eductionDetails : candidateEductionDetailsModel
						.getCandidateEductionDetailsModelList()) {
					CandidateEductionDetails candidateEduction = candidateEductionDetailsRepository
							.getCandidateEductionDetailId(eductionDetails.getCandidate_eduction_detail_id());
					if (candidateEduction == null)
						candidateEduction = new CandidateEductionDetails();
					candidateEduction.setCollage_name(eductionDetails.getCollage_name());
					candidateEduction.setUniversity_inititute(eductionDetails.getUniversity_inititute());
					candidateEduction.setPassing_year(eductionDetails.getPassing_year());
					candidateEduction.setPercantage(eductionDetails.getPercantage());
					candidateEduction.setEduction(eductionDetails.getEduction());
					candidateEduction.setCandidateMaster(candidateRepository.getCandidateMasterById(
							Integer.parseInt(String.valueOf(session.getAttribute("candidate_id")))));
					candidateEductionDetailsRepository.save(candidateEduction);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveCandidateEducationDetails:= " + e);
		}
		return new ModelAndView("redirect:/getCandidateEductionDetails");
	}

	@RequestMapping(value = "/getCandidateSkillDetails")
	public ModelAndView getCandidateSkillDetails(HttpSession session,
			@ModelAttribute("form") CandidateSkillDetailsModel candidateSkillDetailsModel) {
		ModelMap map = new ModelMap();
		logger.info("getCandidateSkillDetailsModel method:=");
		try {
			List<CandidateSkillDetails> candidateSkillDetails = candidateSkillDetailsRepository
					.getCandidateSkillDetailsByCandidateId((Integer) session.getAttribute("candidate_id"));
			List<CandidateSkillDetailsModel> candidateSkillModelList = new ArrayList<>();
			CandidateSkillDetailsModel candidateSkillModel = new CandidateSkillDetailsModel();

			if (candidateSkillDetails != null) {
				for (CandidateSkillDetails skill : candidateSkillDetails) {
					CandidateSkillDetailsModel candidateSkill = new CandidateSkillDetailsModel();
					candidateSkill.setCandidate_skill_detail_id(skill.getCandidate_skill_detail_id());
					candidateSkill.setDescription(skill.getDescription());
					candidateSkill.setSkill(skill.getSkill());
					candidateSkill.setSkillProficiency(skill.getSkillProficiency());
					candidateSkill.setCandidate_id(skill.getCandidateMaster().getCandidate_id());
					candidateSkillModelList.add(candidateSkill);
				}
				if (candidateSkillModelList.size() == 0)
					candidateSkillModelList.add(new CandidateSkillDetailsModel());
				candidateSkillModel.setCandidateSkillDetailsModelsList(candidateSkillModelList);
			}
			map.addAttribute("form", candidateSkillModel);
			map.addAttribute("candidate_id", (Integer) session.getAttribute("candidate_id"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidateSkillDetails:= " + e);
		}

		return new ModelAndView("Candidate_SkillDetailsPage", map);
	}

	@RequestMapping(value = "/saveCandidateSkillDetails")
	public ModelAndView saveCandidateSkillDetails(
			@ModelAttribute("form") CandidateSkillDetailsModel candidateSkillDetailsModel, BindingResult bindingResult,
			HttpSession session) {
		logger.info("saveCandidateSkillDetails method:=");
		ModelMap map = new ModelMap();
		try {
			if (candidateSkillDetailsModel.getCandidateSkillDetailsModelsList() != null
					&& candidateSkillDetailsModel.getCandidateSkillDetailsModelsList().size() > 0) {

				for (CandidateSkillDetailsModel skillDetails : candidateSkillDetailsModel
						.getCandidateSkillDetailsModelsList()) {
					CandidateSkillDetails candidateSkill = candidateSkillDetailsRepository
							.getCandidateSkillDetailsById(skillDetails.getCandidate_skill_detail_id());
					if (candidateSkill == null)
						candidateSkill = new CandidateSkillDetails();
					candidateSkill.setSkill(skillDetails.getSkill());
					candidateSkill.setDescription(skillDetails.getDescription());
					candidateSkill.setSkillProficiency(skillDetails.getSkillProficiency());
					candidateSkill.setCandidateMaster(candidateRepository.getCandidateMasterById(
							Integer.parseInt(String.valueOf(session.getAttribute("candidate_id")))));
					candidateSkillDetailsRepository.save(candidateSkill);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveCandidateSkillDetails:= " + e);
		}

		return new ModelAndView("redirect:/getCandidateSkillDetails");
	}

	@RequestMapping(value = "/getCandidateLanguageDetails")
	public ModelAndView getCandidateLanguageDetails(
			@ModelAttribute("form") CandidateLanguageDetailModel candidateLanguageDetailModel,
			BindingResult bindingResult, HttpSession session) {
		ModelMap map = new ModelMap();
		map.addAttribute("candidateLanguageDetailsList",
				candidateLanguageDetailsRepository.getCandidateLanguageDetailsByCandidateId(
						Integer.parseInt(String.valueOf(session.getAttribute("candidate_id")))));
		return new ModelAndView("Candidate_LanguageDetailList", map);
	}

	@RequestMapping(value = "/addLanguageDetails")
	public ModelAndView addLanguageDetails(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			if (!(session.getAttribute("candidate_id") == null || session.getAttribute("candidate_id").equals(""))) {

				map.addAttribute("form", new CandidateLanguageDetailModel());
				map.addAttribute("languageList", languageRepository.getAllLanguageList());
			} else
				return

				new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in addLanguageDetails:= " + e);
		}
		return new ModelAndView("CandidateAddLanguagePage", map);
	}

	@RequestMapping(value = "/saveLanguageDetails", method = RequestMethod.POST)
	public ModelAndView saveLanguageDetails(
			@ModelAttribute("form") @Valid CandidateLanguageDetailModel candidateLanguageDetailModel,
			BindingResult bindingResult, HttpSession session) {
		logger.info("saveJobDetails method:=");
		ModelMap map = new ModelMap(); 
		CandidateLanguageDetails candidateLanguageDetails = null;
		try {
			if (candidateLanguageDetailModel.getCandidate_language_detail_id() == null || candidateLanguageDetailModel.getCandidate_language_detail_id() == 0) {
				candidateLanguageDetails = new CandidateLanguageDetails();
			} else {
				candidateLanguageDetails = candidateLanguageDetailsRepository.getCandidateLanguageDetailsById(candidateLanguageDetailModel.getCandidate_language_detail_id());
			}
			candidateLanguageDetails.setCandidateMaster(candidateRepository.getCandidateMasterById(Integer.parseInt(String.valueOf(session.getAttribute("candidate_id")))));
			// candidateLanguageDetails.setLanguage_proficiency_level(candidateLanguageDetailModel.getLanguage_proficiency_level());
			candidateLanguageDetails.setLanguageMaster(languageRepository.getLanguageById(candidateLanguageDetailModel.getLanguage_id()));
			candidateLanguageDetails.setRd(candidateLanguageDetailModel.isRd()?"Y":"N");
			candidateLanguageDetails.setWr(candidateLanguageDetailModel.isWr()?"Y":"N");
			candidateLanguageDetails.setSpeak(candidateLanguageDetailModel.isSpeak()?"Y":"N");
			candidateLanguageDetailsRepository.save(candidateLanguageDetails);
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveLanguageDetails:= " + e);

		}

		return new ModelAndView("redirect:/getCandidateLanguageDetails");
	}
	/*
	 * @RequestMapping(value="/getCandidateLanguageDetails") public ModelAndView
	 * getCandidateLanguageDetails(HttpSession
	 * session,@ModelAttribute("form")CandidateLanguageDetailModel
	 * candidateLanguageDetailModel){ ModelMap map = new ModelMap();
	 * logger.info("getCandidateLanguageDetailModel method:="); try {
	 * CandidateLanguageDetails
	 * candidateLanguageDetails=candidateLanguageDetailsRepository.
	 * getCandidateLanguageDetailsByCandidateId((Integer)session.getAttribute(
	 * "candidate_id")); List<CandidateLanguageDetailModel> candidateLanguageModel =
	 * new ArrayList<>();
	 * 
	 * if (candidateLanguageDetails != null) { } map.addAttribute("form",
	 * candidateLanguageModel); } catch (Exception e) { e.printStackTrace();
	 * logger.error("Error in getCandidateLanguageDetails:= "+e); }
	 * 
	 * return new ModelAndView("Candidate_LanguageDetailsPage",map); }
	 * 
	 * 
	 * @RequestMapping(value="/saveCandidateLanguageDetail") public ModelAndView
	 * saveCandidateLanguageDetail(@ModelAttribute("form")
	 * CandidateLanguageDetailModel candidateLanguageDetailModel,BindingResult
	 * bindingResult,HttpSession session ){
	 * logger.info("saveCandidateLanguageDetail method:="); ModelMap map = new
	 * ModelMap(); try {
	 * if(candidateLanguageDetailModel.getCandidateLanguageDetailsModelList() !=
	 * null &&
	 * candidateLanguageDetailModel.getCandidateLanguageDetailsModelList().size() >
	 * 0) { for(CandidateLanguageDetailModel languageDetails :
	 * candidateLanguageDetailModel.getCandidateLanguageDetailsModelList()) {
	 * CandidateLanguageDetails candidateLanguageDetails =
	 * candidateLanguageDetailsRepository.getCandidateLanguageDetailsById(
	 * languageDetails.getCandidate_language_detail_id());
	 * if(candidateLanguageDetails == null) candidateLanguageDetails = new
	 * CandidateLanguageDetails();
	 * candidateLanguageDetails.setEnglish_read(languageDetails.isEnglish_read()?"Y"
	 * :"N");
	 * candidateLanguageDetails.setEnglish_speak(languageDetails.isEnglish_speak()?
	 * "Y":"N");
	 * candidateLanguageDetails.setEnglish_write(languageDetails.isEnglish_write()?
	 * "Y":"N");
	 * candidateLanguageDetails.setHindi_read(languageDetails.isHindi_read()?"Y":"N"
	 * );
	 * candidateLanguageDetails.setHindi_speak(languageDetails.isHindi_speak()?"Y":
	 * "N");
	 * candidateLanguageDetails.setHindi_write(languageDetails.isHindi_write()?"Y":
	 * "N");
	 * candidateLanguageDetails.setGujarati_read(languageDetails.isGujarati_read()?
	 * "Y":"N");
	 * candidateLanguageDetails.setGujarati_speak(languageDetails.isGujarati_speak()
	 * ?"Y":"N");
	 * candidateLanguageDetails.setGujarati_write(languageDetails.isGujarati_write()
	 * ?"Y":"N");
	 * 
	 * candidateLanguageDetails.setCandidateMaster(candidateRepository.
	 * getCandidateMasterById(Integer.parseInt(String.valueOf(session.getAttribute(
	 * "candidate_id")))));
	 * candidateLanguageDetailsRepository.save(candidateLanguageDetails);
	 * 
	 * } }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * logger.error("Error in saveCandidateLanguageDetail:= "+e); }
	 * 
	 * return new ModelAndView("redirect:/getCandidateLanguageDetails"); }
	 */

	@RequestMapping(value = "/candidateUploadDocument", method = RequestMethod.GET)
	public ModelAndView candidateUploadDocument(
			@ModelAttribute("form") CandidateUploadDocumentModel candidateUploadDocumentModel,
			BindingResult bindingResult, HttpSession session) {
		ModelMap map = new ModelMap();
		logger.info("Method candidateUploadDocument");
		try {
			if (!(session.getAttribute("candidate_id") == null || session.getAttribute("candidate_id").equals(""))) {
				candidateUploadDocumentModel.setCandidate_upload_document_id(1);
				candidateUploadDocumentModel.setCandidate_id(4);
				map.addAttribute("form", candidateUploadDocumentModel);
			} else
				return new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in candidateUploadDocument= " + e);
		}
		return new ModelAndView("CandidateUploadDocument", map);
	}

	@RequestMapping(value = "/saveCandidateUploadDocument", method = RequestMethod.POST)
	public ModelAndView saveCandidateUploadDocument(
			@ModelAttribute("form") CandidateUploadDocumentModel candidateUploadDocumentModel,
			BindingResult bindingResult, @RequestParam("candidate_photo_path") MultipartFile importPhoto,
			@RequestParam("id_proof_path") MultipartFile importId,
			@RequestParam("resume_path") MultipartFile importResume, HttpSession session) {
		logger.info("Method saveCandidateUploadDocument");

		ModelMap map = new ModelMap();
		CandidateUploadDocument master = null;
		CandidateUploadDocument candidateUploadDocument = null;

		try {
			if (!(session.getAttribute("candidate_id") == null || session.getAttribute("candidate_id").equals(""))) {
				if (candidateUploadDocumentModel.getCandidate_upload_document_id() == null
						|| candidateUploadDocumentModel.getCandidate_upload_document_id() == 0) {
					candidateUploadDocument = new CandidateUploadDocument();
				} else {
					candidateUploadDocument = candidateUploadDocumentRepository
							.getCandidateUploadDocumentByCandidateId(candidateUploadDocumentModel.getCandidate_id());
				}
				candidateUploadDocument.setCandidateMaster(
						candidateRepository.getCandidateMasterById(candidateUploadDocumentModel.getCandidate_id()));
				if (!importPhoto.isEmpty()) {
					String originalFileName = importPhoto.getOriginalFilename();
					String fileName = session.getAttribute("candidate_id") + "_photo"
							+ originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
					File dirFile = new File("D:/New_Workspace/CandidatePhoto");
					if (!dirFile.exists())
						dirFile.mkdirs();
					Path rootlocation = Paths.get(dirFile.getPath());
					File file = new File(dirFile.getPath() + "\\" + fileName);
					if (file.exists())
						file.delete();
					Files.copy(importPhoto.getInputStream(), rootlocation.resolve(fileName));
					candidateUploadDocument.setCandidate_photo_path(fileName);
				}
				if (!importId.isEmpty()) {
					String originalFileName = importId.getOriginalFilename();
					String fileName = session.getAttribute("candidate_id") + "_Id"
							+ originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
					File dirFile = new File("D:/New_Workspace/CandidateId");
					if (!dirFile.exists())
						dirFile.mkdirs();
					Path rootlocation = Paths.get(dirFile.getPath());
					File file = new File(dirFile.getPath() + "\\" + fileName);
					if (file.exists())
						file.delete();
					Files.copy(importId.getInputStream(), rootlocation.resolve(fileName));
					candidateUploadDocument.setId_proof_path(fileName);
				}
				if (!importResume.isEmpty()) {
					String originalFileName = importResume.getOriginalFilename();
					String fileName = session.getAttribute("candidate_id") + "_resume"
							+ originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
					File dirFile = new File("D:/New_Workspace/CandidateResume");
					if (!dirFile.exists())
						dirFile.mkdirs();
					Path rootlocation = Paths.get(dirFile.getPath());
					File file = new File(dirFile.getPath() + "\\" + fileName);
					if (file.exists())
						file.delete();
					Files.copy(importResume.getInputStream(), rootlocation.resolve(fileName));
					candidateUploadDocument.setResume_path(fileName);
				}
				master = candidateUploadDocumentRepository.save(candidateUploadDocument);
			} else
				return new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveCandidateUploadDocument= " + e);
		}
		return new ModelAndView("CandidateUploadDocument", map);
	}

	@SuppressWarnings("unused")
	@RequestMapping(path = "/downloadResume", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> downloadResume(
			@ModelAttribute("form") CandidateUploadDocumentModel candidateUploadDocumentModel, HttpSession session) {
		File file = null;
		Path path = null;
		ByteArrayResource resource = null;
		String fileName = null;
		CandidateUploadDocument candidateUploadDocument = null;
		try {
			candidateUploadDocument = candidateUploadDocumentRepository
					.getCandidateUploadDocumentByCandidateId(candidateUploadDocumentModel.getCandidate_id());
			if (candidateUploadDocument != null) {
				if (candidateUploadDocument.getResume_path() != null
						&& !candidateUploadDocument.getResume_path().equals("")) {
					File fileDir = new File("D:\\New_Workspace\\CandidateResume\\"
							+ candidateUploadDocument.getCandidateMaster().getCandidate_id() + "_resume.pdf");
					if (fileDir.exists()) {
						file = new File(fileDir.getPath());
						if (file.exists()) {
							path = Paths.get(file.getAbsolutePath());
							resource = new ByteArrayResource(Files.readAllBytes(path));
						}
					} else {
						session.setAttribute("message", "Your resume/CV has not been uploaded");
					}
				}
			} else {
				session.setAttribute("message", "Your resume/CV has not been uploaded");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);

	}

	@RequestMapping(value = "/getJobSearch")
	public ModelAndView getJobSearch(HttpSession session) {
		ModelMap map = new ModelMap();
		map.addAttribute("companyList", companyRepository.getCompanyList());
		JobDetailsModel jobDetailsModel = new JobDetailsModel();
		List<JobDetails> jobDetailsList = new ArrayList<JobDetails>();
		map.addAttribute("form", jobDetailsModel);
		RecruitmentApplicationProcess recruitmentApplicationProcess = null;
		List<JobDetails> jobDetailslist = jobDetailsRepository.getAllJobDetails();
		if (jobDetailslist != null) {
			for (JobDetails jobdetails : jobDetailslist) {
				if (jobdetails.getCompanyMaster() != null) {
				 recruitmentApplicationProcess = recruitmentApplicationProcessRepository
						.getJobApplicationBYCandidateId_CompanyId_JobDetailId(
								Integer.parseInt(String.valueOf(session.getAttribute("candidate_id"))),
								jobdetails.getCompanyMaster().getCompany_id(), jobdetails.getJob_details_id());
				}
				if (recruitmentApplicationProcess == null) {
					jobDetailsList.add(jobdetails);
					map.addAttribute("jobDetailsList", jobDetailsList);
				} else {
					logger.info("Job Applied");
				}
			}
		}
		return new ModelAndView("JobDetailsList", map);
	}

	@RequestMapping(value = "/addCandidateDetails")
	public ModelAndView addCandidateDetails(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			if (!(session.getAttribute("candidate_id") == null || session.getAttribute("candidate_id").equals(""))) {
				map.addAttribute("form", new CandidateDetailsModel());
			} else
				return new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in addJobDetails:= " + e);
		}
		return new ModelAndView("CandidateDetails", map);
	}

}
