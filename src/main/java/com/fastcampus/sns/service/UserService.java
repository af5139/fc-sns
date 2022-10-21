package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName,String password){
        //회원가입하려는 userName으로 회원가입된 user가 있는지 있으면 exception 발생
        userEntityRepository.findByUserName(userName).ifPresent(it ->{
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated", userName));
        });

        //회원가입 진행 - user등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,password));

        return User.fromEntity(userEntity);

    }

    public String login(String userName,String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,""));
        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,"");
        }

        // 토큰 생성
        return "";
    }
}
