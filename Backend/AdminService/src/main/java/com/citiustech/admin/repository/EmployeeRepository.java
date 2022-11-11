package com.citiustech.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.admin.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String>{
//@Query("select * from dbo.person inner Join dbo.employee on dbo.person.personID=dbo.employee.personID where dbo.person.first_name=:firstName")
//	Optional<Employee> FindByFirstName(@Param("firstName")String firstName);

Optional<Employee> findByEmailID(String emailID);

boolean existsByEmailID(String emailID);

}
