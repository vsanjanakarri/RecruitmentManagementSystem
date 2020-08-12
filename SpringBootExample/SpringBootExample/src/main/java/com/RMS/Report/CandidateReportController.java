package com.RMS.Report;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
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

import com.RMS.Bean.CandidateDetails;
import com.RMS.Bean.CandidateMaster;
import com.RMS.Bean.RecruitmentApplicationProcess;
import com.RMS.Model.CandidateAndCompanyModel;
import com.RMS.Model.CandidateDetailsModel;
import com.RMS.Model.JobDetailsModel;
import com.RMS.Model.RecruitmentApplicationProcessModel;
import com.RMS.Model.ReportModel;
import com.RMS.Repository.CandidateDetailsRepository;
import com.RMS.Repository.CandidateRepository;
import com.RMS.Repository.CityRepository;
import com.RMS.Repository.StateRepository;


@Controller
public class CandidateReportController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private CandidateDetailsRepository candidateDetailsRepository;
	
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	
	@RequestMapping(value="/getCandidateReport")
	 public ModelAndView getCandidateReport(HttpSession session,@ModelAttribute("form")ReportModel reportModel){
		 ModelMap map = new ModelMap();
		logger.info("getCandidateDetailsReport method:=");
		try {
			map.addAttribute("form", reportModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidateDetailsReport:= "+e);
		}
		
		return new ModelAndView("CandidateReport",map);
	 }
	
	/* @RequestMapping(value="/getCandidateDetailsReport")
	 public ModelAndView getCandidateDetailsReport(HttpSession session,ReportModel reportModel){
		 ModelMap map = new ModelMap();
		logger.info("getCandidateDetailsReport method:=");
		try {
			List<CandidateDetails> candidateList= candidateDetailsRepository.getCandidateMasterListByMonthAndYear(reportModel.getMonth(), reportModel.getYear());
			map.addAttribute("candidateList", candidateList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCandidateDetailsReport:= "+e);
		}
		
		return new ModelAndView("AjaxPage",map);
	 }
 */
		@RequestMapping(value = "/getCandidateDetailsReport")
		public ModelAndView getCandidateDetailsReport(HttpSession session,ReportModel reportModel) {
			ModelMap map = new ModelMap();
			logger.info("getCandidateDetailsReport Method :-" );
			try {
				/*List<CandidateDetails> candidateList= candidateDetailsRepository.getCandidateMasterListByMonthAndYear(reportModel.getMonth(), reportModel.getYear());
				map.addAttribute("candidateList", candidateList); */
				map.addAttribute("candidateList", candidateRepository.getCandidateList());
				map.addAttribute("stateList", stateRepository.getAllStateList());
				map.addAttribute("cityList", cityRepository.getAllCityList());
				map.addAttribute("form", reportModel);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in getCandidateDetailsReport:= "+e);
			}
			return new ModelAndView("AdminCandidateReport", map);			
		}
		
		@RequestMapping(value = "/getCandidateReportbyfull_name")
		public ModelAndView getCandidateReportbyfull_name(
				@ModelAttribute("form") ReportModel reportModel,BindingResult bindingResult, HttpSession session) {
			logger.info("getCandidateReportbyfull_name method:=");
			ModelMap map = new ModelMap();
			List<ReportModel> candidateReportList = new ArrayList<ReportModel>();
			try {
						
						StringBuffer queryBuffer = new StringBuffer();
						StringBuffer criteriaBuffer = new StringBuffer();

						/*queryBuffer.append(
								" SELECT full_name,city,state,email_id,gender,date_of_birth,active_inactive,mobile_no FROM candidate_master,candidate_deatils,state_master,city_master" + 
								"WHERE candidate_deatils.city_id = city_master.city_id AND candidate_deatils.state_id = state_master.state_id AND" + 
								"candidate_master.deleted='N'");*/
						queryBuffer.append("SELECT cm.full_name,sm.state,cm.email_id,cd.gender,cd.date_of_birth,cm.active_inactive,cd.mobile_no,citym.city ");
						queryBuffer.append("FROM candidate_master cm LEFT JOIN candidate_deatils cd ON cm.candidate_id = cd.candidate_id AND cm.deleted='N' ");
						queryBuffer.append("LEFT JOIN city_master citym ON citym.city_id = cd.city_id AND citym.deleted='N' ");
						queryBuffer.append("LEFT JOIN state_master sm ON sm.state_id = cd.state_id AND sm.deleted='N' ");		
					
						if(reportModel.getCandidate_id() != null && reportModel.getCandidate_id() != 0)
							queryBuffer.append(" where cm.candidate_id= "+reportModel.getCandidate_id());
						if(reportModel.getState_id() != null && reportModel.getState_id() != 0)
							queryBuffer.append(" and sm.state_id="+reportModel.getState_id());
						if(reportModel.getCity_id() != null && reportModel.getCity_id() != 0)
							queryBuffer.append(" and  citym.city_id="+reportModel.getCity_id());
						
						
						logger.info("getCandidateReportbyfull_name query:=" + queryBuffer);
						Query query = entityManager.createNativeQuery(queryBuffer.toString());
						List<Object[]> list = null;
						list = query.getResultList();
						if (list != null && list.size() > 0) {
							for (Object[] objects : list) {
								ReportModel candidateDataList = new ReportModel();
								candidateDataList.setFull_name(objects[0]==null?"":(String)objects[0]);
								candidateDataList.setState(objects[1]==null?"":(String)objects[1]);
								candidateDataList.setEmail_id(objects[2]==null?"":(String) objects[2]);
								candidateDataList.setGender(objects[3]==null?"":(String) objects[3]);
								candidateDataList.setDate_of_birth(objects[4]==null?"":(String.valueOf(objects[4])));
								candidateDataList.setActive_inactive(objects[5]==null?"":(String) objects[5]);
								candidateDataList.setMobile_no(objects[6]==null?"":(String) objects[6]);
								candidateDataList.setCity(objects[7]==null?"":(String)objects[7]);
								candidateReportList.add(candidateDataList);
							}
						}
						map.addAttribute("candidateReportList", candidateReportList);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in getCandidateReportbyfull_name:= " + e);
			}

			return new ModelAndView("AjaxPage", map);
		}
		

		@RequestMapping(value = "/admincandidateList")
		public ModelAndView admincandidateList(HttpSession session) {
			ModelMap map = new ModelMap();
			try {
				if (!(session.getAttribute("admin_id") == null || session.getAttribute("admin_id").equals(""))) {
					map.addAttribute("candidateList", candidateDetailsRepository.getAllCandidateDetails());
					//map.addAttribute("form", new JobDetailsModel());
				} else
					return new ModelAndView("redirect:/login");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in admincandidateList:= " + e);
			}
			return new ModelAndView("AdminCandidateList", map);
		}

		
}
