package com.challenge.book.catalog.dto;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import com.challenge.book.catalog.model.PriceChannel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceChannelRequestDTO {
    @NotNull(message = "O canal é obrigatorio.")
    @NotBlank(message = "O canal é obrigatorio.")
    @NotEmpty(message = "O canal é obrigatorio.")
    @Size(min = 1, max = 10, message = "O tamanho do campo canal deve ser de 1 a 10 caracteres.")
    private String channel;

    @NotNull(message = "O preço é obrigatorio.")
    @NumberFormat(style = Style.CURRENCY)
    private double price;

    public static PriceChannel toPriceChannel(PriceChannelRequestDTO request) {
        return PriceChannel.builder().price(request.getPrice()).channel(request.getChannel()).build();
    }

}
