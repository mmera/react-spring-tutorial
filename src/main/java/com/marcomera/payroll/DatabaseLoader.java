package com.marcomera.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner{

	private final EmployeeRepository employees;
	private final ManagerRepository managers;

	@Autowired
	public DatabaseLoader(EmployeeRepository employeeRepository,
						  ManagerRepository managerRepository) {

		this.employees = employeeRepository;
		this.managers = managerRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		Manager marco = this.managers.save(new Manager("marco", "goodvibes",
				"ROLE_MANAGER"));
		Manager ruben = this.managers.save(new Manager("ruben", "peru",
				"ROLE_MANAGER"));

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("marco", "doesn't matter",
						AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Frodo", "Baggins", "ring bearer", marco));
		this.employees.save(new Employee("Bilbo", "Baggins", "burglar", marco));
		this.employees.save(new Employee("Gandalf", "the Grey", "wizard", marco));

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("ruben", "doesn't matter",
						AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Samwise", "Gamgee", "gardener", ruben));
		this.employees.save(new Employee("Merry", "Brandybuck", "pony rider", ruben));
		this.employees.save(new Employee("Peregrin", "Took", "pipe smoker", ruben));

		SecurityContextHolder.clearContext();
	}
	
}
