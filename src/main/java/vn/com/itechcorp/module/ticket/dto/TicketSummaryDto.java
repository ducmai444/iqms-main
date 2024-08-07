package vn.com.itechcorp.module.ticket.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

@Getter @Setter
public class TicketSummaryDto extends Dto {

    private Long modalityID;

    private String modalityType;

    private String roomName;

    private String firstNumber;

    private String secondNumber;
}
