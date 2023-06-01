package com.example.springjpa.service;

import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.SecurityUser;
import com.example.springjpa.domain.dto.AuthSuccessDto;
import com.example.springjpa.domain.dto.MemberDto;
import com.example.springjpa.repository.BookRepository;
import com.example.springjpa.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final TokenService tokenService;

    public MemberService(MemberRepository memberRepository, BookRepository bookRepository, PasswordEncoder passwordEncoder, JpaUserDetailsService jpaUserDetailsService, TokenService tokenService) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.tokenService = tokenService;
    }

    public AuthSuccessDto signup(MemberDto newMember) {
        Member member = new Member(newMember.getUsername(), passwordEncoder.encode(newMember.getPassword()), Member.USER_ROLE);
        memberRepository.save(member);
        SecurityUser securityUser = (SecurityUser) jpaUserDetailsService.loadUserByUsername(member.getUsername());
        String token = tokenService.generateToken(securityUser);
        return new AuthSuccessDto(token, member.getUsername());
    }

    public List<MemberDto> getAllMembers() {
        return memberRepository
                .findAll()
                .stream()
                .map(Member::dtoWithBorrowedBooks)
                .collect(Collectors.toList());
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findMemberByUsername(username);
    }
}