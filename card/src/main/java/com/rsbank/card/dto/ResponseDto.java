package com.rsbank.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data
public class ResponseDto {

    @Schema(
            description = "Status code in the response"
    )
    private int status;
    
    @Schema(
            description = "Status message in the response"
    )
    private String message;

    private ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseDto build(String statusMessage) {
        String[] statusParts = statusMessage.split("\\|"); // Split
        int status = Integer.parseInt(statusParts[0]);
        String message = statusParts[1];

        return new ResponseDto(status, message);
    }

}
