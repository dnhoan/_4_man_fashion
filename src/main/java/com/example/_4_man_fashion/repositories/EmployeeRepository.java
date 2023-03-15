package com.example._4_man_fashion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example._4_man_fashion.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e order by e.id")
    Page<Employee> getAll(Pageable pageable);

    @Query("select emp from Employee emp where emp.status = :status ")
    Page<Employee> findEmployeeByStatus(Pageable pageable, @Param("status") int status);

    @Query("select e from Employee e where e.phoneNumber = :valueSearch" +
            " or (lower(e.email) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(e.employeeCode) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(e.employeeName) like  '%' || lower(:valueSearch) || '%')")
    Page<Employee> findByKey(Pageable pageable, @Param("valueSearch") String valueSearch);

    Employee findEmployeeById(Integer id);

    @Modifying
    @Query("update Employee emp set emp.status = 1 where emp.id = :id")
    void restoreEmp(@Param("id") Integer id);
}
