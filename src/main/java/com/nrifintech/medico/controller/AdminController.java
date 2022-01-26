package com.nrifintech.medico.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.request.AdminRequest;
import com.nrifintech.medico.request.DoctorRegisterRequest;
import com.nrifintech.medico.request.EditRegisterRequest;
import com.nrifintech.medico.response.GraphResponse;
import com.nrifintech.medico.service.AdminService;
import com.nrifintech.medico.service.DoctorService;
import com.nrifintech.medico.utility.ExcelGenerator;

@Controller
@RequestMapping("/admin")
public class AdminController {

	AdminService adminService;
	DoctorService doctorService;
	ExcelGenerator excelGenerator;
	
	@Autowired

	public AdminController(AdminService adminService,DoctorService doctorService,ExcelGenerator excelGenerator) {
		this.adminService = adminService;
		this.doctorService = doctorService;
		this.excelGenerator = excelGenerator;
	}
	
	@PostMapping("/welcome")
	public ModelAndView admin(AdminRequest adminRequest, HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Inside welcomeee");
		HttpSession session = request.getSession();
		//System.out.println(adminRequest);
		ModelAndView mav = new ModelAndView("/admin");
		//Pie-chart portion
		//List<Object> returned1 = adminService.getSpecializationCount();
		//List<GraphResponse> data1 = (List<GraphResponse>) returned1.get(0);
		//String dataPoint1 = (String) returned1.get(1);
		//session.setAttribute("data", data1);
		//session.setAttribute("dataPoint1", dataPoint1);
		
		//Bar-chart portion
		String searchParam = adminRequest.getInlineRadioOptions();
		System.out.println(searchParam);
		
		List<Object> returned2 = null;
		String title = "";
		String year = adminRequest.getYear();
		String axisX = "";
		String axisY = "";
		String chart = "";
		if (year==null)
		{
			DateFormat df = new SimpleDateFormat("yyyy");
			year = df.format(new java.util.Date());
			session.setAttribute("nnyear", year);
		}
		else {
			session.setAttribute("nnyear", year);
		}
		System.out.println(year);
		if(searchParam==null || searchParam.equals("docPerspec"))
		{
			axisX = "Specialization";
			axisY = "No. of Doctors";
			chart = "pie";
			title = axisY + " per " + axisX;
			year ="";
			returned2 = adminService.getSpecializationCount();
		}
		else if(searchParam.equals("month"))
		{	
			axisX = "Month";
			axisY = "No. of Appointments";
			chart = "column";
			title = axisY + " per " + axisX;
			returned2 = adminService.getMonthlyCount(year);
			year = " (" + year + ")";
		}
		else
		{	
			axisX = "Specialization";
			axisY = "No. of Appointments";
			chart = "column";
			title = axisY + " per " + axisX;
			returned2 = adminService.getAppointmentsBySpecialization(year);
			year = " (" + year + ")";
		}
		List<GraphResponse> data2 = (List<GraphResponse>) returned2.get(0);
		String dataPoint2 = (String) returned2.get(1);
		
		System.out.println(data2);
		System.out.println(dataPoint2);
		
		session.setAttribute("data2", data2);
		session.setAttribute("dataPoint2", dataPoint2);
		session.setAttribute("chart", chart);
		session.setAttribute("axisX", axisX);
		session.setAttribute("axisY", axisY);
		session.setAttribute("title",title);
		session.setAttribute("year", year);
		System.out.println(data2);
		System.out.println(dataPoint2);
		
		List<Doctor> allDoctors = doctorService.findAllByNameAndUpvotes();
		session.setAttribute("allDoctors", allDoctors);
		
		return mav;
	}
	
	@PostMapping("/searchDoctor")
	public String searchDoctorByName(HttpServletRequest request, Model model){
		System.out.println("Search Button Clicked");
		HttpSession session = request.getSession();
		String searchParam = (String)request.getParameter("searchParam");
//		System.out.println(searchParam);
		String searchByOption = (String)request.getParameter("searchByOption");
//		System.out.println(searchByOption);
		String radioOption = (String)request.getParameter("radioOption");
//		System.out.println(radioOption);
//		docName = "M";
//		List<Doctor> filterDoctors = new ArrayList<Doctor>();
		List<Doctor> filterDoctors = doctorService.filterDoctor(searchParam, searchByOption, radioOption);
		session.setAttribute("allDoctors", filterDoctors);
		session.setAttribute("radioOption", radioOption);
		session.setAttribute("searchByOption", searchByOption);
//		System.out.println(filterDoctors);
		System.out.println(searchParam + " " + searchByOption +" " + radioOption);
		return "admin";
	}
	 
	@PostMapping("/add")
	public String add(DoctorRegisterRequest doctorRegisterRequest, HttpServletRequest request, Model model, @RequestParam("file") MultipartFile img,
			RedirectAttributes redirectAttributes) throws IOException {
		System.out.println("Register request"+doctorRegisterRequest);
		String result = doctorService.isValidRegistration(doctorRegisterRequest);
		boolean isValidRegistration = result.charAt(0)=='1';
        String fileName = StringUtils.cleanPath(img.getOriginalFilename());
		if(isValidRegistration)
		{
			Doctor doc = new Doctor();
			doc.setName(doctorRegisterRequest.getName().toUpperCase());
			doc.setUsername(doctorRegisterRequest.getUsername().toLowerCase());
			doc.setPassword(doctorRegisterRequest.getPassword());
			doc.setDegree(doctorRegisterRequest.getDegree());
			doc.setSpecialization(doctorRegisterRequest.getSpecialization().toUpperCase());
			doc.setFees(doctorRegisterRequest.getFees());
//			if(doctorRegisterRequest.getPractice_started()==null)
//				doc.setPractice_started(java.time.LocalDate.now());
			doc.setPractice_started(doctorRegisterRequest.getPractice_started());
			doc.setDescr(doctorRegisterRequest.getDescr());
			doc.setProfile_pic(fileName);
			Doctor savedDoc = doctorService.save(doc);
			String uploadDir = "src/main/webapp/docImages/" + savedDoc.getDoctor_id();
			FileUploadUtil.saveFile(uploadDir, fileName, img);
			model.addAttribute("error", "");
			model.addAttribute("addition","added");
		}
		else
		{
			model.addAttribute("error", result.substring(2));
		}
		
		return "forward:/admin/welcome";
	}
	
//	@GetMapping("/downloadExcel")
//	public String downloadExcel(HttpServletRequest request,Model model) throws IOException
//	{
//		System.out.println("inside downloadExcel");
//		HttpSession session = request.getSession();
//		String label = (String)session.getAttribute("axisX");
//		String y = (String) session.getAttribute("axisY");
//		String filename = (String)session.getAttribute("title")+(String)session.getAttribute("year") + " Report.xls";
//		excelGenerator.createFile(filename,label,y);
//		List<GraphResponse> data = (List<GraphResponse>) session.getAttribute("data2");
//		excelGenerator.createFile(filename, label, y);
//		excelGenerator.createData(data);
//		System.out.println("data created");
//		excelGenerator.saveFile();
//		System.out.println("saved");
//		model.addAttribute("downloadSuccess","Downloaded Successfully!");
//		return "admin";		
//	}
	
	@PostMapping("/edit")
	public String edit(EditRegisterRequest editRegisterRequest, HttpServletRequest request, Model model)  {
		System.out.println("Register request"+editRegisterRequest);
		String result = doctorService.isValidEdit(editRegisterRequest);
		boolean isValidEdit = result.charAt(0)=='1';
		
		if(isValidEdit)
		{
			String name = editRegisterRequest.getName().toUpperCase();
			String username = editRegisterRequest.getUsername().toLowerCase() ;
			int fees = editRegisterRequest.getFees();
		    String password = editRegisterRequest.getPassword();
			String specialization = editRegisterRequest.getSpecialization();
			Date practice_started = editRegisterRequest.getPractice_started();
			String degree = editRegisterRequest.getDegree();
			String descr = editRegisterRequest.getDescr();
			doctorService.editDetails(name, fees, practice_started, degree, specialization, password,descr,username); 
			model.addAttribute("error", "");
			model.addAttribute("addition","added");
		}
		else
		{
			model.addAttribute("error", result.substring(2));
		}
		
		return "forward:/admin/welcome";
	}
}
