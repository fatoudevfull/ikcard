package com.BackendIkcard.IkcardBackend;

import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class IkcardBackendApplication {

	public static void main(String[] args) {
		ApplicationContext ctx =SpringApplication.run(IkcardBackendApplication.class, args);

		RoleRepository roleRepos = ctx.getBean(RoleRepository.class);

		UserService userService = ctx.getBean(UserService.class);

		PasswordEncoder encoder=ctx.getBean(PasswordEncoder.class);

		///creation des roles

		if(roleRepos.findByName(ERole.SUPERADMIN)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.SUPERADMIN);
			roleRepos.save(userRole);
		};
		if(roleRepos.findByName(ERole.ADMINIVEAU1)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ADMINIVEAU1);
			roleRepos.save(userRole);
		};
		if(roleRepos.findByName(ERole.ADMINIVEAU2)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ADMINIVEAU2);
			roleRepos.save(userRole);
		};

		if(roleRepos.findByName(ERole.ENTREPRISE)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ENTREPRISE);
			roleRepos.save(userRole);
		};
		if(roleRepos.findByName(ERole.AMBASSADEUR)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.AMBASSADEUR);
			roleRepos.save(userRole);
		};

		if(roleRepos.findByName(ERole.USER)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.USER);
			roleRepos.save(userRole);
		};



		//creation du super Admin
	/*	try {
			userService.getByUserName("fs");
		} catch (Exception e) {
			// TODO: handle exception
			User admin=new User();

			admin.setNom("Fatoumata sy");
			admin.setUsername("fs");
			//	admin.setPrenom("Ballo");
			admin.setPassword(encoder.encode("syfatou") );
			//admin.setPoint(0l);
			//admin.setNiveau(0l);

			admin.getRoles().add(roleRepos.findByName(ERole.ROLE_SUPERADMIN));

			userService.saveUser(admin);
		}*/
	}
}
