package com.jnu.festival.global.security.auth;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 수정사항
    // 2. UsernameNotFoundException을 custom한 Exception으로 변경
    // 3. UserDetailsImpl에 user 객체를 인자로 줄 것인가, user와 관련된 dto를 생성하여 인자로 줄 것인가
    @Override
    public UserDetails loadUserByUsername(String nickname) throws BusinessException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        return new UserDetailsImpl(user);
    }
}
