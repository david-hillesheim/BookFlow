package com.bookflow.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LoanStatus {
    ACTIVE,
    RETURNED,
    OVERDUE
}
