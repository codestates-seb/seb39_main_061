package com.project.QR.member.mapper;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-27T18:18:32+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member loginDtoToMember(MemberRequestDto.LoginDto loginDto) {
        if ( loginDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.email( loginDto.getEmail() );
        member.password( loginDto.getPassword() );

        return member.build();
    }
}
