package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;

public class UpdateDescriptionDto {

    @NotBlank(message = "{problem.null}")
    private String problem;

    public UpdateDescriptionDto() {
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
