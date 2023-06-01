package com.example.springjpa;

import com.example.springjpa.domain.Member;
import com.example.springjpa.properties.RsaKeyProperties;
import com.example.springjpa.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
public class SpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            memberRepository.save(new Member("someadmin", passwordEncoder.encode("password"), Member.ADMIN_ROLE + "," + Member.USER_ROLE));
            memberRepository.save(new Member("someuser", passwordEncoder.encode("password"), Member.USER_ROLE));
        };
    }

}
