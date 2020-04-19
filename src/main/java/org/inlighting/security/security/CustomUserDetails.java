package org.inlighting.security.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails extends Admin implements UserDetails {

    public CustomUserDetails() {
    	  super();
    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
    	super( username, password, authorities);
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> listGrantedAuth = new ArrayList<>();
        List<SimpleGrantedAuthority> listGrantedAuth2 = new ArrayList<SimpleGrantedAuthority>();
//        listGrantedAuth2=(List<SimpleGrantedAuthority>) super.getAuthorities();
//        for(Collection<SimpleGrantedAuthority> element : super.getAuthorities()) {
//            System.out.println(element);
//        }
//        for (int i = 0;i<listGrantedAuth2.size()  ; i++)
//        {
//        			 listGrantedAuth.add(new SimpleGrantedAuthority(listGrantedAuth2.get(i).toString()));
//        	
//        }
        
        for(GrantedAuthority role :super.getAuthorities()) {
          //  grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
            listGrantedAuth.add(new SimpleGrantedAuthority(role.toString()));
        }
//        forEach(auth -> {
//            listGrantedAuth.add(new SimpleGrantedAuthority(auth.toString()));
//        });
        return listGrantedAuth;
    }
 

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
	    return super.getPassword();
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}
}
