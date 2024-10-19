package com.kkomiding.davena.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.kkomiding.davena.user.domain.UserDto;
import com.kkomiding.davena.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class CheckLoginIdValidator extends AbstractValidator<UserDto> {
	
	private final UserRepository userRepository;
	
	@Override
	public void doValidate(UserDto dto, Errors errors) {
		if(userRepository.existByLoginId(dto.getLoginId())) {
			errors.rejectValue("loginId", "아이디 중복오류", "이미 사용 중인 아이디입니다.");
		}
	}

}
