package com.jobtracker.dto;


import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder
public class MatchResultResponse {
    private int score;
    private String summary;
    private List<String> strengths;
    private List<String> gaps;
}
