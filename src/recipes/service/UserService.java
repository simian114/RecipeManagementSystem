package recipes.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recipes.domain.User;
import recipes.repository.UserRepository;
import recipes.web.dto.UserDto;
import recipes.web.exception.exceptions.badRequest.BadRequestBasicException;
import recipes.web.exception.exceptions.notFound.NotFoundBasicException;
import recipes.web.mapper.UserMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void addUser(UserDto userDto) {
        if (this.userRepository.existsByEmailIgnoreCase(userDto.getEmail())) {
            throw new BadRequestBasicException("already have email");
        }
        this.userRepository.save(this.userMapper.toEntity(userDto));
    }

    public void blockUser(String email) {
        User user = this.userRepository.findByEmailIgnoreCase(email).orElseThrow(NotFoundBasicException::new);
        user.setBlock(LocalDateTime.now().plusSeconds(3));
//        user.setBlock(LocalDateTime.now().plusDays(3));
        this.userRepository.save(user);
    }

    public void unblockUser(String email) {
        User user = this.userRepository.findByEmailIgnoreCase(email).orElseThrow(NotFoundBasicException::new);
        user.setBlock(null);
        this.userRepository.save(user);
    }
}
