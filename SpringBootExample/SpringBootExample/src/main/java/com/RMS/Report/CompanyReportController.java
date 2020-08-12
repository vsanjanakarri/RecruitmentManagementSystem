package com.RMS.Report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import com.RMS.Bean.CompanyBasicDetails;
import com.RMS.Model.ReportModel;
import com.RMS.Repository.CandidateDetailsRepository;
import com.RMS.Repository.CandidateRepository;
import com.RMS.Repository.CityRepository;
import com.RMS.Repository.CompanyBasicDetailsRepository;
import com.RMS.Repository.CompanyRepository;
import com.RMS.Repository.StateRepository;


@Controller
public class CompanyReportController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyBasicDetailsRepository companyBasicDetailsRepository;
	
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	
	/*@RequestMapping(value="/getCompanyReport")
	 public ModelAndView getCandidateReport(HttpSession session,@ModelAttribute("form")ReportModel reportModel){
		 ModelMap map = new ModelMap();
		logger.info("getCompanyBasicDetailsReport method:=");
		try {
			map.addAttribute("form", reportModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCompanyBasicDetailsReport:= "+e);
		}
		
		return new ModelAndView("CompanyReport",map);
	 }
	
	 @RequestMapping(value="/getCompanyDetailsReport")
	 public ModelAndView getCompanyDetailsReport(HttpSession session,ReportModel reportModel){
		 ModelMap map = new ModelMap();
		logger.info("getCompanyDetailsReport method:=");
		try {
			List<CompanyBasicDetails> companyList= companyBasicDetailsRepository.getCompanyMasterListByMonthAndYear(reportModel.getMonth(), reportModel.getYear());
			map.addAttribute("companyList", companyList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCompanyDetailsReport:= "+e);
		}
		
		return new ModelAndView("AjaxPage",map);
	 }   */
	
	@RequestMapping(value = "/getCompanyDetailsReport")
	public ModelAndView getCompanyDetailsReport(HttpSession session,ReportModel reportModel) {
		ModelMap map = new ModelMap();
		logger.info("getCompanyDetailsReport Method :-" );
		try {
			map.addAttribute("companyList", companyRepository.getCompanyList());
			map.addAttribute("stateList", stateRepository.getAllStateList());
			map.addAttribute("cityList", cityRepository.getAllCityList());
			map.addAttribute("form", reportModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCompanyDetailsReport:= "+e);
		}
		return new ModelAndView("AdminCompanyReport", map);			
	}
	
	@RequestMapping(value = "/getCompanyReportbyCompany_name")
	public ModelAndView getCompanyReportbyCompany_name(
			@ModelAttribute("form") ReportModel reportModel,BindingResult bindingResult, HttpSession session) {
		logger.info("getCompanyReportbyCompany_name method:=");
		ModelMap map = new ModelMap();
		List<ReportModel> companyReportList = new ArrayList<ReportModel>();
		try {
					
					StringBuffer queryBuffer = new StringBuffer();
					StringBuffer criteriaBuffer = new StringBuffer();

					/*queryBuffer.append(
							" SELECT full_name,city,state,email_id,gender,date_of_birth,active_inactive,mobile_no FROM candidate_master,candidate_deatils,state_master,city_master" + 
							"WHERE candidate_deatils.city_id = city_master.city_id AND candidate_deatils.state_id = state_master.state_id AND" + 
							"candidate_master.deleted='N'");*/
					queryBuffer.append("SELECT com.company_name,com.company_reg_no,com.email_id,cbd.telephone_no,sm.state,citym.city,com.active_inactive ");
					queryBuffer.append("FROM company_master com LEFT JOIN company_basic_details cbd ON com.company_id = cbd.company_id AND com.deleted='N' ");
					queryBuffer.append("LEFT JOIN city_master citym ON citym.city_id = cbd.city_id AND citym.deleted='N' ");
					queryBuffer.append("LEFT JOIN state_master sm ON sm.state_id = cbd.state_id AND sm.deleted='N'  ");		
				
					if(reportModel.getCompany_id() != null && reportModel.getCompany_id() != 0)
						queryBuffer.append(" where com.company_id= "+reportModel.getCompany_id());
					if(reportModel.getState_id() != null && reportModel.getState_id() != 0)
						queryBuffer.append(" and sm.state_id="+reportModel.getState_id());
					if(reportModel.getCity_id() != null && reportModel.getCity_id() != 0)
						queryBuffer.append(" and  citym.city_id="+reportModel.getCity_id());
					
					
					logger.info("getCompanyReportbyCompany_name query:=" + queryBuffer);
					Query query = entityManager.createNativeQuery(queryBuffer.toString());
					List<Object[]> list = null;
					list = query.getResultList();
					if (list != null && list.size() > 0) {
						for (Object[] objects : list) {
							ReportModel companyReportDataList = new ReportModel();
							companyReportDataList.setCompany_name(objects[0]==null?"":(String)objects[0]);
							companyReportDataList.setCompany_reg_no(objects[1]==null?"":(String)objects[1]);
							companyReportDataList.setEmail_id(objects[2]==null?"":(String) objects[2]);
							companyReportDataList.setTelephone_no(objects[3]==null?"":(Integer) objects[3]);
							companyReportDataList.setState(objects[4]==null?"":(String) objects[4]);
							companyReportDataList.setCity(objects[5]==null?"":(String)objects[5]);
							companyReportDataList.setActive_inactive(objects[6]==null?"":(String) objects[6]);
							
							companyReportList.add(companyReportDataList);
						}
					}
					map.addAttribute("companyReportList", companyReportList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCompanyReportbyCompany_name:= " + e);
		}

		return new ModelAndView("AjaxPage", map);
	}
	

	@RequestMapping(value = "/admincompanyList")
	public ModelAndView admincompanyList(HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			if (!(session.getAttribute("admin_id") == null || session.getAttribute("admin_id").equals(""))) {
				map.addAttribute("companyDataList", companyRepository.getCompanyList());
				//map.addAttribute("form", new JobDetailsModel());
			} else
				return new ModelAndView("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in admincompanyList:= " + e);
		}
		return new ModelAndView("AdminCompanyList", map);
	}


}