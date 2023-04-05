package com.example._4_man_fashion.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.EmployeeDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Employee;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.EmployeeRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PageDTO<EmployeeDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Employee> page = this.employeeRepository.getAll(pageable);
        List<EmployeeDTO> employeeDTOList = page.stream().map(u -> this.modelMapper.map(u, EmployeeDTO.class))
                .collect(Collectors.toList());
        return new PageDTO<EmployeeDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                employeeDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    public PageDTO<EmployeeDTO> findByKey(Pageable pageable, String valueSearch) {
        Page<Employee> page = this.employeeRepository.findByKey(pageable, valueSearch);
        List<EmployeeDTO> employeeDTOList = page.stream().map(u -> this.modelMapper.map(u, EmployeeDTO.class))
                .collect(Collectors.toList());
        return new PageDTO<EmployeeDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                employeeDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    public PageDTO<EmployeeDTO> findByStatus(Pageable pageable, Integer status) {
        Page<Employee> page = this.employeeRepository.findEmployeeByStatus(pageable, status);
        List<EmployeeDTO> employeeDTOList = page.stream().map(u -> this.modelMapper.map(u, EmployeeDTO.class))
                .collect(Collectors.toList());
        return new PageDTO<EmployeeDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                employeeDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Transactional
    public Employee create(EmployeeDTO employeeDto) throws SQLException {
        if (StringCommon.isNullOrBlank(employeeDto.getEmail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(employeeDto.getPhoneNumber())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        employeeDto.setTimeOnboard(LocalDate.now());
        employeeDto.setStatus(Constant.Status.ACTIVE);

        String passwordEncrypt = this.passwordEncoder.encode("4ManFashion");
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Error: Role is not found.")));
        roles.add(userRole);
        Account account = new Account();
        account.setRoles(roles);
        account.setPhoneNumber(employeeDto.getPhoneNumber());
        account.setEmail(employeeDto.getEmail());
        account.setStatus(Constant.Status.ACTIVE);
        account.setPassword(passwordEncrypt);

        try {
            this.accountRepository.save(account);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lỗi lưu vào db"));
        }
        
        employeeDto.setAccount(account);

        return this.employeeRepository.saveAndFlush(Employee.fromDTO(employeeDto));
    }

    @Transactional
    public Employee update(EmployeeDTO employeeDto) throws SQLException {
        if (StringCommon.isNullOrBlank(employeeDto.getEmail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(employeeDto.getPhoneNumber())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
        return this.employeeRepository.saveAndFlush(Employee.fromDTO(employeeDto));
    }

    @Transactional
    public void delete(Integer id) {
        Optional<Employee> optionalEmp = this.employeeRepository.findById(id);
        if (optionalEmp.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Employee Id"));

        Employee emp = optionalEmp.get();
        emp.setMtime(LocalDateTime.now());
        emp.setStatus(Constant.Status.INACTIVE);
        emp.setDayOff(LocalDate.now());

        Optional<Account> optionalAccount = this.accountRepository.findById(emp.getAccount().getId());
        Account account = optionalAccount.get();
        account.setStatus(Constant.Status.INACTIVE);
        this.accountRepository.save(account);

        this.employeeRepository.save(emp);
    }

    @Transactional
    public void restore(Integer id) {
        Optional<Employee> optionalEmp = this.employeeRepository.findById(id);
        if (optionalEmp.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Employee Id"));

        Employee emp = optionalEmp.get();
        emp.setMtime(LocalDateTime.now());
        emp.setStatus(Constant.Status.ACTIVE);

        Optional<Account> optionalAccount = this.accountRepository.findById(emp.getAccount().getId());
        Account account = optionalAccount.get();
        account.setStatus(Constant.Status.ACTIVE);
        this.accountRepository.save(account);

        this.employeeRepository.save(emp);
    }

    public Employee findEmployeeById(Integer id) {
        return this.employeeRepository.findEmployeeById(id);
    }

    public EmployeeDTO getById(Integer id) throws Exception {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if (employee.isPresent()) {
            return this.modelMapper.map(employee.get(), EmployeeDTO.class);
        }
        throw new Exception("Employee not found");
    }

}
