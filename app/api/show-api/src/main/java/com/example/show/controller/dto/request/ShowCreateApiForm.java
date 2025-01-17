package com.example.show.controller.dto.request;

import com.example.show.controller.vo.TicketingApiType;
import com.example.show.service.dto.request.ShowCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.web.multipart.MultipartFile;

public record ShowCreateApiForm(

    @NotBlank(message = "공연 제목은 필수 요청값 입니다.")
    String title,

    @NotBlank(message = "공연 내용은 필수 요청값 입니다.")
    String content,

    @NotNull(message = "공연 시작 날짜는 필수 요청값 입니다.")
    LocalDate startDate,

    @NotNull(message = "공연 종료 날짜는 필수 요청값 입니다.")
    LocalDate endDate,

    @NotNull(message = "공연 장소는 필수 요청값 입니다.")
    String location,

    @NotNull(message = "공연 포스터는 필수 요청값 입니다.")
    MultipartFile post,

    @NotNull(message = "공연 티켓팅 종류는 필수 요청값 입니다.")
    List<TicketingApiType> ticketingTypes,

    @NotNull(message = "공연 티켓팅 종류는 필수 요청값 입니다.")
    List<LocalDateTime> ticketingDates,

    @NotNull(message = "티켓팅 예약 사이트명은 필수 요청값 입니다.")
    List<String> ticketBookingSites,

    @NotNull(message = "티켓팅 예약 사이트 URL는 필수 요청값 입니다.")
    List<String> ticketingSiteURL,

    @NotNull(message = "공연 좌석 타입은 필수 요청값 입니다.")
    List<String> seatTypes,

    @NotNull(message = "공연 좌석별 가격은 필수 요청값 입니다.")
    List<Integer> pricesPerSeatType,

    @NotNull(message = "아티스트 ID는 필수 요청값 입니다.")
    List<UUID> artistIds,

    @NotNull(message = "장르 ID는 필수 요청값 입니다.")
    List<UUID> genreIds
) {

    public ShowCreateServiceRequest toServiceRequest() {
        return ShowCreateServiceRequest.builder()
            .title(title)
            .content(content)
            .startDate(startDate)
            .endDate(endDate)
            .location(location)
            .post(post)
            .priceInformation(getPriceInformation())
            .showTicketingSites(getTicketingSitesApiResponse())
            .ticketingTimes(getTicketingDatesApiResponse())
            .artistIds(artistIds)
            .genreIds(genreIds)
            .build();
    }

    private Map<String, Integer> getPriceInformation() {
        return IntStream.range(0, seatTypes.size())
            .boxed()
            .collect(Collectors.toMap(seatTypes::get, pricesPerSeatType::get));
    }

    private Map<String, String> getTicketingSitesApiResponse() {
        return IntStream.range(0, ticketBookingSites.size())
            .boxed()
            .collect(Collectors.toMap(ticketBookingSites::get, ticketingSiteURL::get));
    }

    private Map<TicketingApiType, LocalDateTime> getTicketingDatesApiResponse() {
        return IntStream.range(0, ticketingTypes.size())
            .boxed()
            .collect(Collectors.toMap(ticketingTypes::get, ticketingDates::get));
    }
}
