package com.winciak.medicFacility.services;



import com.winciak.medicFacility.dto.CustomUserDetails;
import com.winciak.medicFacility.dto.DtoUser;
import com.winciak.medicFacility.entities.*;
import com.winciak.medicFacility.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final PatientUserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PatientsProjectsRepository patientsProjectsRepository;

	private final ConsentFileRepository consentFileRepository;

	private final PatientTestRepository patientTestRepository;

	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(PatientUserRepository theUserRepository, RoleRepository theRoleRepository,
						   PatientsProjectsRepository patientsProjectsRepository, ConsentFileRepository consentFileRepository, PatientTestRepository patientTestRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = theUserRepository;
		this.roleRepository = theRoleRepository;
		this.patientsProjectsRepository = patientsProjectsRepository;
		this.consentFileRepository = consentFileRepository;
		this.patientTestRepository = patientTestRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public List<PatientUser> findAll() {
		return userRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public PatientUser findById(int theId) {
		Optional<PatientUser> result = userRepository.findById(theId);

		PatientUser theUser;

		if (result.isPresent()) {
			theUser = result.get();
		}
		else {
			throw new RuntimeException("Did not find user id - " + theId);
		}

		return theUser;
	}

	@Override
	public void save(PatientUser theUser) {
		userRepository.save(theUser);
	}

	@Override
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
	}

	@Override
	public List<Role> findRoles(){
		return roleRepository.findAll();
	}

	@Override
	public PatientUser findByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Override
	public void save(DtoUser dtoUser) {

		PatientUser user = new PatientUser();

		user.setLogin(dtoUser.getLogin());
		user.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
		user.setFirstName(dtoUser.getFirstName());
		user.setLastName(dtoUser.getLastName());
		user.setPhoneNumber(dtoUser.getPhoneNumber());
		user.setEmail(dtoUser.getEmail());

		changeRoles(dtoUser, user);

		userRepository.save(user);
	}

	@Override
	public void update(DtoUser dtoUser) {

		Optional<PatientUser> optionalPatientUser = userRepository.findById(dtoUser.getWhatId());

		if(optionalPatientUser.isPresent()) {

			PatientUser user = optionalPatientUser.get();

			user.setLogin(dtoUser.getLogin());

			user.setFirstName(dtoUser.getFirstName());
			user.setLastName(dtoUser.getLastName());
			user.setPhoneNumber(dtoUser.getPhoneNumber());
			user.setEmail(dtoUser.getEmail());

			String checkIfRoleChanged = "";
			if(user.getRoles().size()==1){
				checkIfRoleChanged="ROLE_USER";
			} else
			if(user.getRoles().size()==2){
				checkIfRoleChanged="ROLE_EMPLOYEE";
			} else
			if(user.getRoles().size()==3){
				checkIfRoleChanged="ROLE_ADMIN";
			}


			if (!Objects.equals(dtoUser.getRole(), checkIfRoleChanged)) {
				changeRoles(dtoUser, user);
			}

			userRepository.save(user);
		}
	}


	//------------------------------------------------------------------------------------------------------------------

	@Override
	public PatientsProjects findPatientsProjectsById(int id) {
		Optional<PatientsProjects> result = patientsProjectsRepository.findById(id);

		PatientsProjects patientsProjects;

		if (result.isPresent()) {
			patientsProjects = result.get();
		}
		else {
			throw new RuntimeException("Did not find patientsProjects id - " + id);
		}

		return patientsProjects;
	}

	@Override
	public PatientsProjects findPatientsProjectsByPatientUserIdAndResearchProjectId(int userId, int projectId) {
		return patientsProjectsRepository.findPatientsProjectsByPatientUserIdAndResearchProjectId(userId, projectId);
	}

	@Override
	public void savePatientsProjects(PatientsProjects patientsProjects) {
		patientsProjectsRepository.save(patientsProjects);
	}

	@Override
	public void deletePatientsProjectsById(int id) {
		patientsProjectsRepository.deleteById(id);
	}

	@Override
	public ConsentFile findFileById(int id) {
		Optional<ConsentFile> result = consentFileRepository.findById(id);

		ConsentFile consentFile;

		if (result.isPresent()) {
			consentFile = result.get();
		}
		else {
			throw new RuntimeException("Did not find file id - " + id);
		}

		return consentFile;
	}


	//------------------------------------------------------------------------------------------------------------------

	@Override
	public PatientTest findByPatientUserIdAndTestInProjectId(int patientId, int testInProjectId) {
		return patientTestRepository.findByPatientUserIdAndTestInProjectId(patientId, testInProjectId);
	}

	@Override
	public void savePatientTest(PatientTest patientTest) {
		patientTestRepository.save(patientTest);
	}

	@Override
	public void deletePatientTest(PatientTest patientTest) {
		patientTestRepository.delete(patientTest);
	}

	@Override
	public PatientTest findPatientTestById(int patientTestId) {
		Optional<PatientTest> result = patientTestRepository.findById(patientTestId);

		PatientTest patientTest;

		if (result.isPresent()) {
			patientTest = result.get();
		}
		else {
			throw new RuntimeException("Did not find patient test id - " + patientTestId);
		}

		return patientTest;
	}


	//------------------------------------------------------------------------------------------------------------------

	private void changeRoles(DtoUser dtoUser, PatientUser user) {
		Collection<Role> collection = new ArrayList<>();
		collection.add(roleRepository.findRoleByName("ROLE_USER"));
		user.setRoles(collection);

		if(Objects.equals(dtoUser.getRole(), "ROLE_EMPLOYEE") || Objects.equals(dtoUser.getRole(), "ROLE_ADMIN")){
			collection.add(roleRepository.findRoleByName("ROLE_EMPLOYEE"));
			user.setRoles(collection);
		}

		if(Objects.equals(dtoUser.getRole(), "ROLE_ADMIN")){
			collection.add(roleRepository.findRoleByName("ROLE_ADMIN"));
			user.setRoles(collection);
		}
	}




	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		PatientUser user = userRepository.findUserByLogin(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new CustomUserDetails(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()),
				user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}






