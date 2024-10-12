package com.jnu.festival.global.security;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 수정사항
    // 2. UsernameNotFoundException을 custom한 Exception으로 변경
    // 3. UserDetailsImpl에 user 객체를 인자로 줄 것인가, user와 관련된 dto를 생성하여 인자로 줄 것인가
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        return new UserDetailsImpl(user);
    }
}
