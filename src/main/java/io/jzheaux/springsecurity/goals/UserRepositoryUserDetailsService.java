package io.jzheaux.springsecurity.goals;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class UserRepositoryUserDetailsService implements UserDetailsService {

  public UserRepositoryUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    return userRepository.findByUsername(userName).map(UserAdapter::new)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
  }

  private static class UserAdapter extends User implements UserDetails {

    public UserAdapter(User user) {
      super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return AuthorityUtils.createAuthorityList("app");
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
  }
}
