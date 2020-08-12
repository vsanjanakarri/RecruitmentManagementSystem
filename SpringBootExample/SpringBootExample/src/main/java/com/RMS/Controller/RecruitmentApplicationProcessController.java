package com.RMS.Controller;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.Query;

import com.RMS.Bean.JobDetails;
import com.RMS.Bean.RecruitmentApplicationProcess;
import com.RMS.Model.CandidateAndCompanyModel;
import com.RMS.Model.CandidateLanguageDetailModel;
import com.RMS.Model.JobDetailsModel;
import com.RMS.Model.RecruitmentApplicationProcessModel;
import com.RMS.Model.ReportModel;
import com.RMS.Repository.CandidateRepository;
import com.RMS.Repository.CityRepository;
import com.RMS.Repository.CompanyBasicDetailsRepository;
import com.RMS.Repository.CompanyRepository;
import com.RMS.Repository.JobDetailsRepository;
import com.RMS.Repository.RecruitmentApplicationProcessRepository;
import com.RMS.Repository.StateRepository;


@Controller
public class RecruitmentApplicationProcessController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyBasicDetailsRepository companyBasicDetailsRepository;

	@Autowired
	private JobDetailsRepository jobDetailsRepository;
	
	

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private RecruitmentApplicationProcessRepository recruitmentApplicationProcessRepository;

	@RequestMapping(value = "/addRecruitmentApplication")
	public ModelAndView addRecruitmentApplication(
			@ModelAttribute("form") RecruitmentApplicationProcessModel recruitmentApplicationProcessModel,
			BindingResult bindingResult, HttpSession session) {
		logger.info("addRecruitmentApplication method:=");
		ModelMap map = new ModelMap();
		try {
			RecruitmentApplicationProcess applicationProcess = new RecruitmentApplicationProcess();
			applicationProcess.setCandidateMaster(candidateRepository.getCandidateMasterById((Integer) session.getAttribute("candidate_id")));
			applicationProcess.setCompanyMaster(companyRepository.getCompanyMasterById(recruitmentApplicationProcessModel.getCompany_id()));
			applicationProcess.setJobDetails(jobDetailsRepository.getJobDetailsById(recruitmentApplicationProcessModel.getJob_details_id()));
			applicationProcess.setStatus("P");
			applicationProcess.setApplication_date(new Date());
			recruitmentApplicationProcessRepository.save(applicationProcess);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in addRecruitmentApplication:= " + e);
		}

		return new ModelAndView("redirect:/getJobSearch");
	}
	
	@RequestMapping(value = "/getJobDetails")
	public ModelAndView  getJobDetails(HttpSession session) {
		ModelMap map = new ModelMap();
		map.addAttribute("jobDetailsList", jobDetailsRepository.getAllJobDetails());
		return new ModelAndView("JobDetailsList", map);
	}
	
	@RequestMapping(value = "/getJobTitleByCompanyId")
	public ModelAndView getJobTitleByCompanyId(HttpSession session,
			@ModelAttribute("form") JobDetailsModel jobDetailsModel) {
		ModelMap map = new ModelMap();
		logger.info("getJobTitleByCompanyId method:=");
		try {
			
			map.addAttribute("jobTitleList",jobDetailsRepository.getJobTitleByCompanyId(jobDetailsModel.getCompany_id()));

			map.addAttribute("form", jobDetailsModel);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidatePersonalDetails:= " + e);
		}

		return new ModelAndView("AjaxPage", map);
	}

	@RequestMapping(value = "/getJobApplication")
	public ModelAndView getJobSearch(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			logger.info("getJobApplication Method :-" );
			map.addAttribute("companyList", companyRepository.getCompanyList());
			JobDetailsModel jobDetailsModel = new JobDetailsModel();
			
			map.addAttribute("form", jobDetailsModel);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("SearchJob", map);			
	}
	
	@RequestMapping(value = "/getJobDetailsWithStatus")
	public ModelAndView getJobDetailsWithStatus(
			@ModelAttribute("form") RecruitmentApplicationProcessModel recruitmentApplicationProcessModel,
			BindingResult bindingResult, HttpSession session) {
		logger.info("getJobDetailsWithStatus method:=");
		ModelMap map = new ModelMap();
		List<RecruitmentApplicationProcessModel> recruitmentApplicationProcessModelsList = new ArrayList<RecruitmentApplicationProcessModel>();
		try {
			if (recruitmentApplicationProcessModel != null
					&& !recruitmentApplicationProcessModel.getStatus().equals("")) {
				if (recruitmentApplicationProcessModel.getStatus() != null
						&& (recruitmentApplicationProcessModel.getStatus().equals("P")
								|| recruitmentApplicationProcessModel.getStatus().equals("A")
								|| recruitmentApplicationProcessModel.getStatus().equals("R"))) {

					List<RecruitmentApplicationProcess> recruitmentApplicationProcessesList = recruitmentApplicationProcessRepository.getRecruitmentApplicationProcessDetailsByCandidateId((Integer) session.getAttribute("candidate_id"),
									recruitmentApplicationProcessModel.getCompany_id(),
									recruitmentApplicationProcessModel.getJob_details_id(),
									recruitmentApplicationProcessModel.getStatus());
					if (recruitmentApplicationProcessesList != null)
						map.addAttribute("recruitmentApplicationProcessesList", recruitmentApplicationProcessesList);
					else
						session.setAttribute("message", "Data Not Found.");

				} else {
					
					StringBuffer queryBuffer = new StringBuffer();
					StringBuffer criteriaBuffer = new StringBuffer();

					queryBuffer.append(
							" SELECT rap.status,jd.job_details_id,jd.company_id,cm.company_name,jd.job_name,jd.maximum_salary,"
									+ " jd.minimum_salary,jd.employment_type,jd.experience,rap.candidate_id FROM job_details jd LEFT JOIN recruitment_application_process rap ON ");
					queryBuffer.append(
							" jd.company_id = rap.company_id AND jd.job_details_id = rap.job_details_id left join company_master cm on cm.company_id = jd.company_id and cm.deleted='N' WHERE STATUS IS NULL ");
					logger.info("getJobDetailsWithStatus query:=" + queryBuffer);
					Query query = entityManager.createNativeQuery(queryBuffer.toString());
					List<Object[]> list = null;
					list = query.getResultList();
					if (list != null && list.size() > 0) {
						for (Object[] objects : list) {
							RecruitmentApplicationProcessModel recruitmentApplicationProcess = new RecruitmentApplicationProcessModel();
							recruitmentApplicationProcess.setStatus((String)objects[0]);
							recruitmentApplicationProcess.setJob_details_id((Integer) objects[1]);
							recruitmentApplicationProcess.setCompany_id((Integer) objects[2]);
							recruitmentApplicationProcess.setCompany_name((String) objects[3]);
							recruitmentApplicationProcess.setJob_name((String) objects[4]);
							recruitmentApplicationProcess.setMaximum_salary((Integer) objects[5]);
							recruitmentApplicationProcess.setMinimum_salary((Integer) objects[6]);
							recruitmentApplicationProcess.setEmployment_type((String) objects[7]);
							recruitmentApplicationProcess.setExperience((String)objects[8]);
							recruitmentApplicationProcess.setCandidate_id((Integer) objects[9]);
							recruitmentApplicationProcessModelsList.add(recruitmentApplicationProcess);
						}
					}
					map.addAttribute("recruitmentApplicationProcessModelsList", recruitmentApplicationProcessModelsList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getJobDetailsWithStatus:= " + e);
		}

		return new ModelAndView("AjaxPage", map);
	}
	
	/*@RequestMapping(value = "/getCandidateJobDetails")
	public ModelAndView getCompanyDetails(HttpSession session, CandidateAndCompanyModel candidateAndCompanyModel) {
		ModelMap map = new ModelMap();
		JobDetails jobDetails = new JobDetails();
		map.addAttribute("candidateDetailsList", recruitmentApplicationProcessRepository.getJobApplicationBYCandidateId_CompanyId_JobDetailId(
				Integer.parseInt(String.valueOf(session.getAttribute("candidate_id"))),
				jobDetails.getCompanyMaster().getCompany_id(), jobDetails.getJob_details_id()));
	
		return new ModelAndView("AdminCandidateDetails", map);
	}
	
	*/
	@RequestMapping(value = "/getRecruitmentDetails")
	public ModelAndView getRecruitmentDetails(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			logger.info("getRecruitmentDetails Method :-" );
			map.addAttribute("candidateList", candidateRepository.getCandidateList());
			map.addAttribute("companyList", companyRepository.getCompanyList());
			map.addAttribute("jobList", jobDetailsRepository.getAllJobDetails());
			JobDetailsModel jobDetailsModel = new JobDetailsModel();
			
			map.addAttribute("form", jobDetailsModel);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("AdminRecruitmentReports", map);			
	}
	
	@RequestMapping(value = "/getRecruitmentReport")
	public ModelAndView getRecruitmentReport(
			@ModelAttribute("form") ReportModel reportModel,
			BindingResult bindingResult, HttpSession session) {
		logger.info("getRecruitmentReport method:=");
		ModelMap map = new ModelMap();
		List<ReportModel> recruitmentReportList = new ArrayList<ReportModel>();
		try {
					
					StringBuffer queryBuffer = new StringBuffer();
					StringBuffer criteriaBuffer = new StringBuffer();

					queryBuffer.append(
							" SELECT cd.full_name,cm.company_name,jd.job_name,jd.employment_type,jd.location FROM job_details jd "
									+ " LEFT JOIN recruitment_application_process rap ON jd.company_id = rap.company_id AND jd.job_details_id = rap.job_details_id LEFT JOIN company_master cm ");
					queryBuffer.append(
							"ON cm.company_id = jd.company_id AND cm.deleted='N' LEFT JOIN candidate_master cd ON cd.candidate_id=rap.candidate_id AND cd.deleted='N' WHERE STATUS IS NOT NULL ");
					
					if(reportModel.getCandidate_id() != null && reportModel.getCandidate_id() != 0)
						queryBuffer.append(" where cm.candidate_id= "+reportModel.getCandidate_id());
					if(reportModel.getCompany_id() != null && reportModel.getCompany_id() != 0)
						queryBuffer.append(" and cm.Company_id="+reportModel.getCompany_id());
					if(reportModel.getJob_details_id() != null && reportModel.getJob_details_id() != 0)
						queryBuffer.append(" and jd.Job_details_id="+reportModel.getJob_details_id());
				
					
					logger.info("getRecruitmentReport query:=" + queryBuffer);
					Query query = entityManager.createNativeQuery(queryBuffer.toString());
					List<Object[]> list = null;
					list = query.getResultList();
					if (list != null && list.size() > 0) {
						for (Object[] objects : list) {
							ReportModel recruitmentReportDataList = new ReportModel();
							recruitmentReportDataList.setFull_name((String) objects[0]);
							recruitmentReportDataList.setCompany_name((String) objects[1]);
							recruitmentReportDataList.setJob_name((String) objects[2]);
							recruitmentReportDataList.setEmployment_type((String) objects[3]);
							recruitmentReportDataList.setLocation((String) objects[4]);
							
							recruitmentReportList.add(recruitmentReportDataList);
						}
					}
					map.addAttribute("recruitmentReportList", recruitmentReportList);
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getRecruitmentReport:= " + e);
		}

		return new ModelAndView("AjaxPage", map);
	}
	
	@RequestMapping(value = "/adminrecruitmentList")
	public ModelAndView adminrecruitmentList(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			if (!(session.getAttribute("admin_id") == null || session.getAttribute("admin_id").equals(""))) {
				map.addAttribute("recruitmentDataList", recruitmentApplicationProcessRepository.getRecruitmentList());
			} else
				return new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in adminrecruitmentList:= " + e);
		}
		return new ModelAndView("AdminRecruitmentList", map);
	}

}
	
	

