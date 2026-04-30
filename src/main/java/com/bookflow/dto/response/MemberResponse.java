package com.bookflow.dto.response;

import java.time.LocalDate;

public record MemberResponse (Long id, String name, String email, String phone, LocalDate registrationDate){ }