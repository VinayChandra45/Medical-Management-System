package com.nrifintech.medico.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.response.GraphResponse;

@Repository
public interface DoctorRepository extends IAbstractBaseRepository<Doctor, Long> {
	Doctor findByUsernameAndPassword(String username, String password);

	@Query(value = "select * from doctor order by name asc, upvotes desc", nativeQuery = true)
	List<Doctor> findByOrderByNameAscOrderByUpvotesDesc();

	@Query(value = "select * from doctor where name like %?% order by upvotes desc, name asc", nativeQuery = true)
	List<Doctor> findByNameOrderByUpvotesDesc(String name);

	@Query(value = "select * from doctor where name like %?% order by fees asc, name asc", nativeQuery = true)
	List<Doctor> findByNameOrderByFeesAsc(String name);

	@Query(value = "select * from doctor where specialization like %?% order by fees asc,name asc ", nativeQuery = true)
	List<Doctor> findBySpecializationOrderByFeesAsc(String name);

	@Query(value = "select * from doctor where specialization like %?% order by upvotes desc, name asc", nativeQuery = true)
	List<Doctor> findBySpecializationOrderByUpvotesDesc(String name);

	@Query(value = "SELECT specialization AS label, COUNT(*) AS y FROM doctor GROUP BY specialization ORDER BY y DESC", nativeQuery = true)
	List<GraphResponse> countDoctorsBySpecialization();

	@Modifying
	@Query(value = "UPDATE doctor SET name=?,fees=?,practice_started=?,degree=?,specialization=?,password=?,descr=? WHERE email=?", nativeQuery = true)
	void updateDetails(String name, int fees, Date practice_started, String degree, String specialization,
			String password, String descr, String username);

}
