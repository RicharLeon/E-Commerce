package dev.richar.auth.models.services;

import dev.richar.auth.models.dao.OurUsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUsersDetailsService implements UserDetailsService {

    @Autowired
    private OurUsersDao usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepo.findByEmail(username).orElseThrow();
    }

}
