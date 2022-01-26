package com.nrifintech.medico.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.response.GraphResponse;
import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.service.DoctorService;


@Controller
public class ExcelDownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private AppointmentService apptService;
	
	private final String[] stringMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October" ,"November", "December"};

	
	public ExcelDownloadController() {
		super();
	}

	@PostMapping("/excel/admin/{criteria}")
	protected void generateAdminExcel(@PathVariable String criteria, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		String rowhead1 = (String)session.getAttribute("axisX");
		String rowhead2 = (String) session.getAttribute("axisY");
		List<GraphResponse> data = (List<GraphResponse>) session.getAttribute("data2");	
		OutputStream out = response.getOutputStream();
		
		// creating the excel file with the fetched data
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int rowCounter = 0;
		Row currentRow = sheet.createRow(rowCounter);
		Cell topCell = currentRow.createCell(rowCounter);
		String year = (String)session.getAttribute("year");
		String header;
		if (criteria.equals("month")) header = "No. of appointments per month " + year;
		else if (criteria.equals("docPerspec")) header = "No. of doctors per specializtion";
		else header = "No. of appointments per specialization " + "(" + year + ")";
		topCell.setCellValue("Report for " + header);
		int firstRow = rowCounter;
		int lastRow = rowCounter;
		int firstCol = 0;
		int lastCol = 2;
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		rowCounter ++;
		rowCounter ++;
		createHeaderDate(sheet, rowCounter, rowhead1, rowhead2);
		rowCounter ++;
		
		for (GraphResponse g : data) {
			currentRow = sheet.createRow(rowCounter);
			Cell nameCell = currentRow.createCell(0);
			Cell timeCell = currentRow.createCell(1);
			nameCell.setCellValue(g.getLabel());
			timeCell.setCellValue(g.getY());
			rowCounter ++;
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		// Set content type to application / excel
		response.setContentType("application/ms-excel");
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xls", criteria));
		out.write(outArray);
		out.close();
		wb.close();
		
	}
	
	@PostMapping("/excel/date/{date}")
	protected void generateDatePdf(@PathVariable String date,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside excel controller date");
		OutputStream out = response.getOutputStream();
		
		// fetching the data
		
		List<Appointment> apptList = apptService.findAppointmentByDate(doctor_id, date);
		ArrayList<ArrayList<String>> patientList = new ArrayList<ArrayList<String>>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		for(Appointment appt : apptList) {
			ArrayList<String> temp = new ArrayList<String>();
			Date cDate = sdf2.parse(appt.getAppt_date().toString());
			String mins = Integer.toString(cDate.getMinutes());
			if (mins.length() < 2) mins = "0"+mins;
			String time = cDate.getHours() + ":" + mins + ":" + cDate.getSeconds() + "0";
			temp.add(appt.getPatient().getName());
			temp.add(time);
			patientList.add(temp);
		}
		
		// creating the excel file with the fetched data
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int rowCounter = 0;
		Row currentRow = sheet.createRow(rowCounter);
		Cell topCell = currentRow.createCell(rowCounter);
		topCell.setCellValue("Report for " + date + " generated by " + currDoctor.getName());
		int firstRow = rowCounter;
		int lastRow = rowCounter;
		int firstCol = 0;
		int lastCol = 2;
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		rowCounter ++;
		rowCounter ++;
		createHeaderDate(sheet, rowCounter);
		rowCounter ++;
		
		for (ArrayList<String> p : patientList) {
			currentRow = sheet.createRow(rowCounter);
			Cell nameCell = currentRow.createCell(0);
			Cell timeCell = currentRow.createCell(1);
			nameCell.setCellValue(p.get(0));
			timeCell.setCellValue(p.get(1));
			rowCounter ++;
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		// Set content type to application / excel
		response.setContentType("application/ms-excel");
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xls", date));
		out.write(outArray);
		out.close();
		wb.close();
	}
	
	@PostMapping("/excel/month/{month}/{year}")
	protected void generateMonthlyExcel(@PathVariable String month, @PathVariable String year, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside excel controller date");
		OutputStream out = response.getOutputStream();
		
		// fetching the data
		
		List<Appointment> apptList = apptService.findAppointmentByMonth(doctor_id, month, year);
		ArrayList<ArrayList<String>> patientList = new ArrayList<ArrayList<String>>();
		//System.out.println("No.of appointments fetched : " + apptList.size());
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		for(Appointment appt : apptList) {
			ArrayList<String> temp = new ArrayList<String>();
			Date cDate = sdf2.parse(appt.getAppt_date().toString());
			String mins = Integer.toString(cDate.getMinutes());
			if (mins.length() < 2) mins = "0"+mins;
			String time = cDate.getHours() + ":" + mins + ":" + cDate.getSeconds() + "0";
			temp.add(appt.getPatient().getName());
			String day = Integer.toString(cDate.getDate());
			String mon = Integer.toString(cDate.getMonth() + 1);
			String yr = Integer.toString(cDate.getYear());
			if (day.length() == 1) {
				day = "0" + day;
			}
			if (mon.length() == 1) {
				mon = "0" + mon;
			}
//			if (yr.length() == 1) {
//				yr = "0" + yr;
//			}
			String date = day + "/" + mon + "/" + year;
			temp.add(date);
			temp.add(time);
			patientList.add(temp);
			
		}
		
		// creating the excel file with the fetched data
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int rowCounter = 0;
		Row currentRow = sheet.createRow(rowCounter);
		Cell topCell = currentRow.createCell(rowCounter);
		topCell.setCellValue("Report for month " + stringMonth[Integer.parseInt(month)-1] + "("+ year +")" + " generated by " + currDoctor.getName());
		int firstRow = rowCounter;
		int lastRow = rowCounter;
		int firstCol = 0;
		int lastCol = 2;
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		rowCounter ++;
		rowCounter ++;
		createHeaderMonth(sheet, rowCounter);
		rowCounter ++;
		
		for (ArrayList<String> p : patientList) {
			currentRow = sheet.createRow(rowCounter);
			Cell nameCell = currentRow.createCell(0);
			Cell dateCell = currentRow.createCell(1);
			Cell timeCell = currentRow.createCell(2);
			nameCell.setCellValue(p.get(0));
			dateCell.setCellValue(p.get(1));
			timeCell.setCellValue(p.get(2));
			rowCounter ++;
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		// Set content type to application / excel
		response.setContentType("application/ms-excel");
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xls", month));
		out.write(outArray);
		out.close();
		wb.close();
	}
	
	@PostMapping("/excel/range/{date1}/{date2}")
	protected void generateRangePdf(@PathVariable String date1, @PathVariable String date2, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside excel controller date");
		OutputStream out = response.getOutputStream();
		
		// fetching the data
		List<Appointment> apptList = apptService.rangeAppointmentByDoctor(doctor_id, date1, date2);
		ArrayList<ArrayList<String>> patientList = new ArrayList<ArrayList<String>>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		for(Appointment appt : apptList) {
			ArrayList<String> temp = new ArrayList<String>();
			Date cDate = sdf2.parse(appt.getAppt_date().toString());
			String mins = Integer.toString(cDate.getMinutes());
			if (mins.length() < 2) mins = "0"+mins;
			String time = cDate.getHours() + ":" + mins + ":" + cDate.getSeconds() + "0";
			temp.add(appt.getPatient().getName());
			String date = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
//			String day = Integer.toString(cDate.getDate());
//			String mon = Integer.toString(cDate.getMonth() + 1);
//			String yr = Integer.toString(cDate.getYear());
//			if (day.length() == 1) {
//				day = "0" + day;
//			}
//			if (mon.length() == 1) {
//				mon = "0" + mon;
//			}
//			if (yr.length() == 1) {
//				yr = "0" + yr;
//			}
//			String date = day + "/" + mon + "/" + yr;
			temp.add(date);
			temp.add(time);
			patientList.add(temp);
			
		}
		
		// creating the excel file with the fetched data
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int rowCounter = 0;
		Row currentRow = sheet.createRow(rowCounter);
		Cell topCell = currentRow.createCell(rowCounter);
		topCell.setCellValue("Report from " + date1 + " to " + date2 + " generated by " + currDoctor.getName());
		int firstRow = rowCounter;
		int lastRow = rowCounter;
		int firstCol = 0;
		int lastCol = 2;
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		rowCounter ++;
		rowCounter ++;
		createHeaderMonth(sheet, rowCounter);
		rowCounter ++;
		
		for (ArrayList<String> p : patientList) {
			currentRow = sheet.createRow(rowCounter);
			Cell nameCell = currentRow.createCell(0);
			Cell dateCell = currentRow.createCell(1);
			Cell timeCell = currentRow.createCell(2);
			nameCell.setCellValue(p.get(0));
			dateCell.setCellValue(p.get(1));
			timeCell.setCellValue(p.get(2));
			rowCounter ++;
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		// Set content type to application / excel
		response.setContentType("application/ms-excel");
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s%s.xls", date1,date2));
		out.write(outArray);
		out.close();
		wb.close();
	}
	
	private void createHeaderDate(HSSFSheet sheet, int rowCounter){
		Row currentRow = sheet.createRow(rowCounter);
		Cell nameCell = currentRow.createCell(0);
		Cell timeCell = currentRow.createCell(1);
		nameCell.setCellValue("Patient Name");
		timeCell.setCellValue("Appointment Time");
	}
	
	private void createHeaderDate(HSSFSheet sheet, int rowCounter, String head1,String head2){
		Row currentRow = sheet.createRow(rowCounter);
		Cell nameCell = currentRow.createCell(0);
		Cell timeCell = currentRow.createCell(1);
		nameCell.setCellValue(head1);
		timeCell.setCellValue(head2);
	}
	
	private void createHeaderMonth(HSSFSheet sheet, int rowCounter){
		Row currentRow = sheet.createRow(rowCounter);
		Cell nameCell = currentRow.createCell(0);
		Cell dateCell = currentRow.createCell(1);
		Cell timeCell = currentRow.createCell(2);
		nameCell.setCellValue("Patient Name");
		dateCell.setCellValue("Appointment Date");
		timeCell.setCellValue("Appointment Time");
	}

}
