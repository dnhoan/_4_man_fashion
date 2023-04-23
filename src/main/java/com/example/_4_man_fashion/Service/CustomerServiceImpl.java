package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.configs.security.UserDetailsImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.*;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.CustomerRepository;
import com.example._4_man_fashion.repositories.CartRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Value("${4manFashion.app.jwtSecret}")
    private String jwtSecret;

    @Value("${4manFashion.app.jwtExpirationMs}")
    private Long jwtExpiration;

    @Override
    public PageDTO<CustomerDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Customer> page = this.customerRepository.getCustomerByName(pageable, status,
                StringCommon.getLikeCondition(search));
        List<CustomerDTO> customerDTOS = page.stream().map(u -> this.modelMapper.map(u, CustomerDTO.class))
                .collect(Collectors.toList());
        return new PageDTO<CustomerDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                customerDTOS,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Transactional
    public List<Customer> getlistCustomer() {
        List<Customer> customerList = this.customerRepository.findAll();
        return customerList;
    }

    @Transactional
    public Customer create(CustomerDTO customerDTO) {
        if (StringCommon.isNullOrBlank(customerDTO.getCustomerName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(customerDTO.getEmail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(customerDTO.getPhoneNumber())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumberLike(customerDTO.getPhoneNumber().trim());
        if (existsByPhoneNumber) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
        }
        boolean existsByEmail = customerRepository.existsByEmailLike(customerDTO.getEmail().trim());
        if (existsByEmail) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
        }

        boolean existsByPhoneNumberAccount = accountRepository.existsByPhoneNumber(customerDTO.getPhoneNumber().trim());
        if (existsByPhoneNumberAccount) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
        }
        boolean existsByEmailAccount = accountRepository.existsByEmail(customerDTO.getEmail().trim());
        if (existsByEmailAccount) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
        }

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        Set<Role> roles = new HashSet<>();
        String passwordEncrypt = this.passwordEncoder.encode("4ManFashion");

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Error: Role is not found.")));
        roles.add(userRole);
        Account account = new Account();
        Cart cart = new Cart();
        account.setRoles(roles);
        account.setPhoneNumber(customerDTO.getPhoneNumber());
        account.setEmail(customerDTO.getEmail());
        account.setStatus(Constant.Status.ACTIVE);
        account.setPassword(passwordEncrypt);
        account.setCustomer(customer);
        cart.setCustomer(customer);
        System.out.println("id. " + customer.getId());
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lỗi lưu vào db"));
        }
        // try {
        // cartRepository.save(cart);
        // } catch (Exception e) {
        // throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format( "Lỗi lưu vào
        // db"));
        // }

        customerDTO.setCtime(LocalDate.now());
        customerDTO.setStatus(Constant.Status.ACTIVE);

        return this.customerRepository.save(Customer.fromDTO(customerDTO));

    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
         Optional<Customer> customer = this.customerRepository.findById(id);
         if(customer.isPresent()) {
             return this.modelMapper.map(customer.get(), CustomerDTO.class);
         }
         return new CustomerDTO();
    }

    @Transactional
    public Cart createCustomer(CustomerDTO customerDTO) {
        if (StringCommon.isNullOrBlank(customerDTO.getCustomerName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(customerDTO.getEmail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (StringCommon.isNullOrBlank(customerDTO.getPhoneNumber())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumberLike(customerDTO.getPhoneNumber().trim());
        if (existsByPhoneNumber) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
        }
        boolean existsByEmail = customerRepository.existsByEmailLike(customerDTO.getEmail().trim());
        if (existsByEmail) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
        }

        boolean existsByPhoneNumberAccount = accountRepository.existsByPhoneNumber(customerDTO.getPhoneNumber().trim());
        if (existsByPhoneNumberAccount) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
        }
        boolean existsByEmailAccount = accountRepository.existsByEmail(customerDTO.getEmail().trim());
        if (existsByEmailAccount) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
        }

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        Set<Role> roles = new HashSet<>();
        String passwordEncrypt = this.passwordEncoder.encode("4ManFashion");

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Error: Role is not found.")));
        roles.add(userRole);
        Account account = new Account();
        Cart cart = new Cart();
        account.setRoles(roles);
        account.setPhoneNumber(customerDTO.getPhoneNumber());
        account.setEmail(customerDTO.getEmail());
        account.setStatus(Constant.Status.ACTIVE);
        account.setPassword(passwordEncrypt);
        account.setCustomer(customer);

        System.out.println("id. " + customer.getId());
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lỗi lưu vào db"));
        }
        customerDTO.setCtime(LocalDate.now());
        customerDTO.setStatus(Constant.Status.ACTIVE);

        try {
            Customer customer1 = customerRepository.save(Customer.fromDTO(customerDTO));
            System.out.println("id. " + customer1.getId());
            cart.setCustomer(customer1);
            return this.cartRepository.save(cart);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lỗi lưu vào db 2"));
        }

    }
@Transactional
    public Customer update(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerDTO.getId());
        if (optionalCustomer.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Khách hàng Id"));

        if (StringCommon.isNullOrBlank(customerDTO.getCustomerName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên khách hàng"));

        if (StringCommon.isNullOrBlank(customerDTO.getEmail()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Email khách hàng"));

        if (StringCommon.isNullOrBlank(customerDTO.getPhoneNumber()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Số điện thoại khách hàng"));

        Customer customer = optionalCustomer.get();
        if (!customer.getEmail().equals(customerDTO.getEmail())) {
            boolean isExistEmail = customerRepository.existsByEmailLike(customerDTO.getEmail().trim());
            if (isExistEmail) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
            }
        }
        if (!customer.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
            boolean isExitsPhoneNumber = customerRepository
                    .existsByPhoneNumberLike(customerDTO.getPhoneNumber().trim());
            if (isExitsPhoneNumber) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
            }
        }
        System.out.println("hihiahah" + customerDTO);
        Optional<Account> optionalAccount = this.accountRepository.findById(customer.getAccount().getId());
        Account account = optionalAccount.get();
        if (!account.getEmail().equals(customerDTO.getEmail())) {
            boolean isExistEmail = accountRepository.existsByEmail(customerDTO.getEmail().trim());
            if (isExistEmail) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
            }
        }
        if (!account.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
            boolean isExitsPhoneNumber = accountRepository.existsByPhoneNumber(customerDTO.getPhoneNumber().trim());
            if (isExitsPhoneNumber) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
            }
        }

        account.setEmail(customerDTO.getEmail());
        account.setPhoneNumber(customerDTO.getPhoneNumber());
        account.setStatus(customer.getStatus());
        accountRepository.save(account);

        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setAvatar(customerDTO.getAvatar());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setNote(customerDTO.getNote());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setGender(customerDTO.getGender());
        customer.setMtime(LocalDate.now());
        customer.setStatus(customerDTO.getStatus());
        return this.customerRepository.save(customer);

    }

    @Transactional
    public JwtResponse updateOnline(CustomerOnlineDTO customerDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerDTO.getId());
        if (optionalCustomer.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Khách hàng Id"));

        if (StringCommon.isNullOrBlank(customerDTO.getEmail()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Email khách hàng"));

        if (StringCommon.isNullOrBlank(customerDTO.getPhoneNumber()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Số điện thoại khách hàng"));

        Customer customer = optionalCustomer.get();
        if (!customer.getEmail().equals(customerDTO.getEmail())) {
            boolean isExistEmail = customerRepository.existsByEmailLike(customerDTO.getEmail().trim());
            if (isExistEmail) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Email"));
            }
        }
        if (!customer.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
            boolean isExitsPhoneNumber = customerRepository
                    .existsByPhoneNumberLike(customerDTO.getPhoneNumber().trim());
            if (isExitsPhoneNumber) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Số điện thoại"));
            }}

        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setAvatar(customerDTO.getAvatar());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setNote(customerDTO.getNote());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setGender(customerDTO.getGender());
        customer.setMtime(LocalDate.now());
        customer.setStatus(customerDTO.getStatus());
        this.customerRepository.save(customer);
        Account account = this.customerRepository.getAccountByCustomerId(customerDTO.getId(), customerDTO.getStatus());
        UserDetailsImpl userDetails = UserDetailsImpl.build(account);

        String jwt = Jwts.builder()
                .claim("info", userDetails)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + this.jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return JwtResponse
                .builder()
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getUsername())
                .token(jwt)
                .build();
    }

    public void delete(Integer id) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Customer Id"));

        Customer customer = optionalCustomer.get();
        customer.setMtime(LocalDate.now());
        customer.setStatus(Constant.Status.INACTIVE);
        Optional<Account> optionalAccount = this.accountRepository.findById(customer.getAccount().getId());
        Account account = optionalAccount.get();
        account.setStatus(Constant.Status.INACTIVE);
        accountRepository.save(account);
        this.customerRepository.save(customer);
    }

    private CustomerDTO customerMapToCustomerDto(Customer customer) {

        return CustomerDTO
                .builder()
                .id(customer.getId())
                .customerName(customer.getCustomerName())
                .birthday(customer.getBirthday())
                .address(customer.getAddress())
                .avatar(customer.getAvatar())
                .email(customer.getEmail())
                .gender(customer.getGender())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .note(customer.getNote())
                .ctime(customer.getCtime())
                .mtime((customer.getMtime()))
                .status(customer.getStatus())
                .build();
    }
}
