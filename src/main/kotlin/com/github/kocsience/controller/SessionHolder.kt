package com.github.kocsience.controller

import com.github.kocsience.domain.Account
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@SessionScope
@Component
class SessionHolder {
    var account: Account? = null
}