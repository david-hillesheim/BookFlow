package com.bookflow.dto.response;

import java.time.LocalDate;

public record MemberResponse (Long id, String name, String mail, String phone, LocalDate registrationDate){ }