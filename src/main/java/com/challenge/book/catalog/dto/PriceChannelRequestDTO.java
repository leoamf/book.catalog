package com.challenge.book.catalog.dto;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import com.challenge.book.catalog.model.PriceChannel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceChannelRequestDTO {

    @NotEmpty(message = "The Title is required.")
    @Size(min = 1, max = 10, message = "The length of channel must be between 1 and 10 characters.")
    private String channel;

    @NotEmpty(message = "The price is required.")
    @NumberFormat(style = Style.CURRENCY)
    private double price;

    public static PriceChannel toPriceChannel(PriceChannelRequestDTO request) {
        return PriceChannel.builder().price(request.getPrice()).channel(request.getChannel()).build();
    }

}
