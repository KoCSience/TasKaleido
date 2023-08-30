package com.github.kocsience.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@Configuration
class WebSecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val passwordEncoder =
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        passwordEncoder.setEncodeHashAsBase64(true);
        return passwordEncoder;
    }
}