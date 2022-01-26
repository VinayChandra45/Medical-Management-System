package com.nrifintech.medico.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.repository.AppointmentRepository;
import com.nrifintech.medico.response.GraphResponse;
import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.service.DoctorService;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.net.SyslogOutputStream;


@Controller
public class PdfDownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private AppointmentService apptService;
	
	@Autowired
	private DoctorService doctorService;
		
	private final String[] stringMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October" ,"November", "December"};
	
	public PdfDownloadController() {
		super();
	}

	
	@PostMapping("/pdf/admin/{criteria}")
	protected void generateAdminExcel(@PathVariable String criteria, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		String rowhead1 = (String)session.getAttribute("axisX");
		String rowhead2 = (String) session.getAttribute("axisY");
		List<GraphResponse> data = (List<GraphResponse>) session.getAttribute("data2");	
		
		try {
			OutputStream out = response.getOutputStream();

			Document document = new Document(PageSize.A4);
			/* Basic PDF Creation inside servlet */
			PdfWriter.getInstance(document, out);
			// PdfPTable table = new PdfPTable(noOfColumn);

			//int lenght = patientList.size();
			if (!data.isEmpty()) {
				PdfPTable table = new PdfPTable(2);

				this.createHeaderDate(table, rowhead1, rowhead2);

				for (GraphResponse g : data) {
					//System.out.println(s.get(0) + " " + s.get(1));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(g.getLabel()))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(g.getY()))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					

				}
				document.open();
				Paragraph title = new Paragraph("Report for " + criteria);
				title.setAlignment(Paragraph.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);
				document.add(table);
				System.out.println("Message printed");
				document.close();
				out.close();
			}

		} catch (DocumentException exc) {
			System.out.println("Exception occured in doPost");
			throw new IOException(exc.getMessage());
		} finally {
			System.out.println("output stream closed");
		}
	}

	@PostMapping("/pdf/range/{date1}/{date2}")
	protected void generateRangePdf(@PathVariable String date1, @PathVariable String date2, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside pdf controller for range");
		response.setContentType("application/pdf");
		List<Appointment> apptList = apptService.rangeAppointmentByDoctor(doctor_id, date1, date2);
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
		//System.out.println(apptList);
		System.out.println(patientList);
		// Get the output stream for writing PDF object
		
		try {
			//System.out.println("output stream : " + out);
			OutputStream out = response.getOutputStream();
			Document document = new Document(PageSize.A4);
			/* Basic PDF Creation inside servlet */
			PdfWriter.getInstance(document, out);
			// PdfPTable table = new PdfPTable(noOfColumn);

			if (!patientList.isEmpty()) {
				PdfPTable table = new PdfPTable(3);

				this.createHeaderMonth(table);

				for (ArrayList<String> s : patientList) {
					//System.out.println(s.get(0) + " " + s.get(1));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(0)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(1)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(2)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				}
				document.open();
				Paragraph title = new Paragraph("Report from " + date1 + " to " + date2 + " generated by " + currDoctor.getName());
				title.setAlignment(Paragraph.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);
				document.add(table);
				System.out.println("Message printed");
				document.close();
				out.close();
			}

		} catch (DocumentException exc) {
			System.out.println("Exception occured in doPost");
			throw new IOException(exc.getMessage());
		} finally {
			System.out.println("output stream closed");
		}

	}
	

	@PostMapping("/pdf/month/{month}/{year}")
	protected void generateMonthlyPdf(@PathVariable String month,@PathVariable String year , HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		// Set content type to application / pdf
		// browser will open the document only if this is set
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside pdf controller for month");
		response.setContentType("application/pdf");
		
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
		//System.out.println(apptList);
		System.out.println(patientList);
		// Get the output stream for writing PDF object
		
		try {
			OutputStream out = response.getOutputStream();

			Document document = new Document(PageSize.A4);
			/* Basic PDF Creation inside servlet */
			PdfWriter.getInstance(document, out);
			// PdfPTable table = new PdfPTable(noOfColumn);

			//int lenght = patientList.size();
			if (!patientList.isEmpty()) {
				PdfPTable table = new PdfPTable(3);

				this.createHeaderMonth(table);

				for (ArrayList<String> s : patientList) {
					//System.out.println(s.get(0) + " " + s.get(1));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(0)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(1)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(2)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				}
				document.open();
				Paragraph title = new Paragraph("Report for month " + stringMonth[Integer.parseInt(month)-1] + "("+ year +")" + " generated by " + currDoctor.getName());
				title.setAlignment(Paragraph.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);
				document.add(table);
				System.out.println("Message printed");
				document.close();
				out.close();
			}

		} catch (DocumentException exc) {
			System.out.println("Exception occured in doPost");
			throw new IOException(exc.getMessage());
		} finally {
			//out.flush();
			System.out.println("output stream closed");
		}
	}
	
	
	@PostMapping("/pdf/date/{date}")
	protected void generateDatePdf(@PathVariable String date,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		
		// Set content type to application / pdf
		// browser will open the document only if this is set
		HttpSession session = request.getSession();
		Long doctor_id = (Long)session.getAttribute("currentDoctorID"); 
		Doctor currDoctor = doctorService.getCurrentEntity((String)session.getAttribute("username"));
		System.out.println("inside pdf controller date");
		response.setContentType("application/pdf");
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
		//System.out.println(apptList);
		//System.out.println(patientList);
		// Get the output stream for writing PDF object
		
		try {
			OutputStream out = response.getOutputStream();

			Document document = new Document(PageSize.A4);
			/* Basic PDF Creation inside servlet */
			PdfWriter.getInstance(document, out);
			// PdfPTable table = new PdfPTable(noOfColumn);

			//int lenght = patientList.size();
			if (!patientList.isEmpty()) {
				PdfPTable table = new PdfPTable(2);

				this.createHeaderDate(table);

				for (ArrayList<String> s : patientList) {
					//System.out.println(s.get(0) + " " + s.get(1));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(0)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					table.addCell(new PdfPCell(new Phrase(String.valueOf(s.get(1)))))
							.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					

				}
				document.open();
				Paragraph title = new Paragraph("Report for " + date + " generated by " + currDoctor.getName());
				title.setAlignment(Paragraph.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);
				document.add(table);
				System.out.println("Message printed");
				document.close();
				out.close();
			}

		} catch (DocumentException exc) {
			System.out.println("Exception occured in doPost");
			throw new IOException(exc.getMessage());
		} finally {
			System.out.println("output stream closed");
		}
	}
	
	private void createHeaderDate(PdfPTable table) {
		table.addCell(new PdfPCell(new Phrase("Patient Name"))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase("Appointment Time"))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}
	
	private void createHeaderDate(PdfPTable table, String head1,String head2){
		table.addCell(new PdfPCell(new Phrase(head1))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase(head2))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}
	
	private void createHeaderMonth(PdfPTable table) {
		table.addCell(new PdfPCell(new Phrase("Patient Name"))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase("Appointment Date"))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase("Appointment Time"))).setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}

}
