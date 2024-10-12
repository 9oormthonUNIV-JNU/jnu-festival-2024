package com.jnu.festival.domain.feedback.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedbackCategory {
    FESTIVAL_COMMITTEE("festival-committee"),
    JNU_FESTIVAL("jnu-festival"),
    FESTIVAL_SITE("festival-site");

    private final String value;

    @JsonCreator
    public static FeedbackCategory from(String value) {
        for (FeedbackCategory category: FeedbackCategory.values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        return null;
    }
}
