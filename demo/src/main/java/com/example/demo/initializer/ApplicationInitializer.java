package com.example.demo.initializer;

import com.example.demo.dataproviders.entities.*;
import com.example.demo.dataproviders.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentsRepository departmentsRepository;
    private final EmployeesRepository employeesRepository;
    private final ProjectsRepository projectsRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationInitializer(UserRepository userRepository, RoleRepository roleRepository, DepartmentsRepository departmentsRepository, EmployeesRepository employeesRepository, ProjectsRepository projectsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentsRepository = departmentsRepository;
        this.employeesRepository = employeesRepository;
        this.projectsRepository = projectsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0 && userRepository.count() == 0) {
            User user1 = new User();
            user1.setFirstname("Milo");
            user1.setLastname("Allom");
            user1.setEmail("user@a.com");
            user1.setPassword(passwordEncoder.encode("12345678"));
            userRepository.save(user1); // Save user1 first

            User admin1 = new User();
            admin1.setFirstname("Milo");
            admin1.setLastname("Allom");
            admin1.setEmail("admin@a.com");
            admin1.setPassword(passwordEncoder.encode("12345678"));
            userRepository.save(admin1); // Save admin1 first

            Role admin = new Role();
            admin.setRoleName("ADMIN");
            admin.setUsers(Collections.singletonList(admin1));
            roleRepository.save(admin);

            Role user = new Role();
            user.setRoleName("USER");
            user.setUsers(Collections.singletonList(user1));
            roleRepository.save(user);

            admin1.setRoles(Collections.singletonList(admin));
            userRepository.save(admin1); // Update admin1 with roles

            user1.setRoles(Collections.singletonList(user));
            userRepository.save(user1); // Update user1 with roles
        }

        Departments departments = new Departments();
        if (departmentsRepository.count() == 0) {
            departments.setDepartment_name("Algorhythm");
            departmentsRepository.save(departments);
        }

        Employees employees = new Employees();
        if (employeesRepository.count() == 0) {
            employees.setFirst_name("Milo");
            employees.setLast_name("Molla");
            employees.setJob_title("Intern");
            employees.setSalary(5000);
            employees.setDepartment(departments);
            employeesRepository.save(employees);
        }

        if (projectsRepository.count() == 0) {
            Projects projects = new Projects();
            projects.setProject_name("SpringProject");
            projects.setStart_date(new Date()); // Use LocalDate for better clarity
            projects.setEnd_date(new Date());
            projects.setEmployees(Collections.singletonList(employees));
            projectsRepository.save(projects);
        }
    }
}
