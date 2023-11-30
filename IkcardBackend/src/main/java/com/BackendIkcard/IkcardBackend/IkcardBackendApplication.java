package com.BackendIkcard.IkcardBackend;

import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.User;
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
		if(roleRepos.findByName(ERole.ROLE_USER)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ROLE_USER);
			roleRepos.save(userRole);
		};

		if(roleRepos.findByName(ERole.ROLE_ADMIN1)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ROLE_ADMIN1);
			roleRepos.save(userRole);
		};

		if(roleRepos.findByName(ERole.ROLE_SUPERADMIN)==null){
			// TODO: handle exception
			Role userRole = new Role();
			// userRole.setId(1L);
			userRole.setName(ERole.ROLE_SUPERADMIN);
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
