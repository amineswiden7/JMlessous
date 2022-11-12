package com.jmlessous.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jmlessous.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UtilisateurServiceImpl utilisateurService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur user = utilisateurService.loadUser(username);
        List<GrantedAuthority> authorities = getUserAuthority(user);
        if (user!= null) {
            return new User(user.getUsername(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
    private List<GrantedAuthority> getUserAuthority(  Utilisateur user) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

            roles.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
}
