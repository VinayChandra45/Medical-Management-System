package com.nrifintech.medico.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nrifintech.medico.entity.Admin;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.repository.AdminRepository;
import com.nrifintech.medico.repository.AppointmentRepository;
import com.nrifintech.medico.repository.DoctorRepository;
import com.nrifintech.medico.request.UserLoginRequest;
import com.nrifintech.medico.response.GraphResponse;
import com.nrifintech.medico.service.AdminService;


@Service
@Transactional
public class AdminServiceImpl extends AbstractBaseServiceImpl<Admin, Long> implements AdminService{
	

	private AdminRepository adminRepository;
	private DoctorRepository doctorRepository;
	private AppointmentRepository apptRepository;

	@Autowired
	public AdminServiceImpl(AdminRepository adminRepository,DoctorRepository doctorRepository,AppointmentRepository apptRepository) {
		super(adminRepository);
		this.adminRepository = adminRepository;
		this.doctorRepository = doctorRepository;
		this.apptRepository = apptRepository;
	}

	@Override
	public boolean isValidUser(UserLoginRequest userLoginRequest) {
		// TODO Auto-generated method stub
		if(Objects.isNull(userLoginRequest.getUsername()) || Objects.isNull(userLoginRequest.getPassword())) {
			return false;
		}
		
		Admin fetchedUser = adminRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());
		System.out.println(fetchedUser);
		if(!Objects.isNull(fetchedUser)) return true;
		else return false;
	}

	@Override
	public List<Object> getSpecializationCount()
	{
		List<GraphResponse> resultSet = doctorRepository.countDoctorsBySpecialization();
		
		List<Map<Object,Object>> data = new ArrayList<>();
		
		Map<Object,Object> map = null;
		
		for(GraphResponse gr: resultSet)
		{
			String label = gr.getLabel();
			int y = gr.getY();
			map = new HashMap<>();
			map.put("label", label);
			map.put("y", y);
			data.add(map);
		}
		
		Gson gsonObj = new Gson();
		String dataPoints = gsonObj.toJson(data);
				
		List<Object> toReturn = new ArrayList<>();
		toReturn.add(resultSet);
		toReturn.add(dataPoints);
		
		return toReturn;
		
	}

	@Override
	public List<Object> getMonthlyCount(String year) {
		
		List<GraphResponse> resultSet = apptRepository.countAppointmentsByMonth(year);
		
		List<Map<Object,Object>> data = new ArrayList<>();
		
		Map<Object,Object> map = null;
		
		for(GraphResponse gr: resultSet)
		{
			String label = gr.getLabel();
			int y = gr.getY();
			map = new HashMap<>();
			map.put("y", y);
			map.put("label", label);
			data.add(map);
		}
		
		Gson gsonObj = new Gson();
		String dataPoints = gsonObj.toJson(data);
		
		List<Object> toReturn = new ArrayList<>();
		toReturn.add(resultSet);
		toReturn.add(dataPoints);
		
		return toReturn;
		
	}
	
	@Override
	public List<Object> getAppointmentsBySpecialization(String year)
	{
		List<GraphResponse> resultSet = adminRepository.countAppointmentsBySpecializaton(year);

		List<Map<Object, Object>> data = new ArrayList<>();

		Map<Object, Object> map = null;

		for (GraphResponse gr : resultSet) {
			String label = gr.getLabel();
			int y = gr.getY();
			map = new HashMap<>();
			map.put("y", y);
			map.put("label", label);
			data.add(map);
		}

		Gson gsonObj = new Gson();
		String dataPoints = gsonObj.toJson(data);

		List<Object> toReturn = new ArrayList<>();
		toReturn.add(resultSet);
		toReturn.add(dataPoints);

		return toReturn;
	}
}