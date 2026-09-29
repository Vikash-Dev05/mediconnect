package com.mediconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.mediconnect.entity.User;
import com.mediconnect.entity.Doctor;
import com.mediconnect.repository.UserRepository;
import com.mediconnect.repository.DoctorRepository;

@SpringBootApplication
public class MediconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediconnectApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, DoctorRepository doctorRepository) {
		return args -> {
			// Update or Create Admin
			userRepository.findByEmail("admin@example.com").ifPresentOrElse(
				admin -> { admin.setRole("ROLE_ADMIN"); userRepository.save(admin); },
				() -> {
					User admin = new User();
					admin.setEmail("admin@example.com");
					admin.setPassword("{noop}admin123");
					admin.setFullName("Default Admin");
					admin.setRole("ROLE_ADMIN");
					userRepository.save(admin);
				}
			);

			// Update or Create Patient
			userRepository.findByEmail("patient@example.com").ifPresentOrElse(
				patient -> { patient.setRole("ROLE_PATIENT"); userRepository.save(patient); },
				() -> {
					User patient = new User();
					patient.setEmail("patient@example.com");
					patient.setPassword("{noop}patient123");
					patient.setFullName("Sohan Lal");
					patient.setRole("ROLE_PATIENT");
					userRepository.save(patient);
				}
			);

			
			if (userRepository.findByEmail("doctor@example.com").isEmpty()) {
				User docUser = new User();
				docUser.setEmail("doctor@example.com");
				docUser.setPassword("{noop}doctor123");
				docUser.setFullName("Dr. Ajay Sharma");
				docUser.setRole("DOCTOR");
				userRepository.save(docUser);
				System.out.println("Default doctor user created: doctor@example.com / doctor123");
			}
			
			if (doctorRepository.count() == 0) {
				User docUser = userRepository.findByEmail("doctor@example.com").get();
				Doctor d1 = new Doctor();
				d1.setName("Dr. Ajay Sharma");
				d1.setQualification("MBBS, MD");
				d1.setSpecialization("Fever, Cough, Cold");
				d1.setVerified(true);
				d1.setUser(docUser);
				doctorRepository.save(d1);

				Doctor d2 = new Doctor();
				d2.setName("Dr. Priya Verma");
				d2.setQualification("MBBS, MS");
				d2.setSpecialization("Heart, BP, Chest Pain");
				d2.setVerified(true);
				doctorRepository.save(d2);

				Doctor d3 = new Doctor();
				d3.setName("Dr. Rahul Gupta");
				d3.setQualification("BDS, MDS");
				d3.setSpecialization("Tooth Ache, Cavity, Dental");
				d3.setVerified(false); // Testing unverified
				doctorRepository.save(d3);
				
				System.out.println("Sample doctors created for search testing.");
			}
		};
	}
}
