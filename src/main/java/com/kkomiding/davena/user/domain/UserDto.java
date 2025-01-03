package com.kkomiding.davena.user.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

	
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^[a-z0-9]{7,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 7~20자리여야 합니다.")
	private String join_idInput;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String join_PwInput;
	
	@NotBlank(message = "비밀번호 확인을 해주세요.")
	private String join_checkPwInput;
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String join_userName;
	
	
	@NotBlank(message = "직책을 선택해주세요.")
	private String positionBtn;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "방 이름은 특수문자를 제외한 2~10자리여야 합니다.")
	private String join_roomName;
	
	@PositiveOrZero(message = "방 비밀번호는 숫자만 가능합니다.")
	private String join_roomPw;
	


}
