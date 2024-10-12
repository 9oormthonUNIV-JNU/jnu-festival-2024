package com.jnu.festival.domain.booth.dto;

import com.jnu.festival.domain.booth.entity.*;
import com.jnu.festival.domain.common.Location;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class BoothResponseDTO {
    @Getter
    @Setter
    public static class BoothDetailDTO {

        private Long id;
        private String name;
        private Location location;
        private Integer index;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private String description;
        private BoothCategory category;
        private Period period;
        private String image;


        public BoothDetailDTO(Booth booth) {
            this.id = booth.getId();
            this.name = booth.getName();
            this.location = booth.getLocation();
            this.index = booth.getIndex();
            this.startDate = booth.getStartDate();
            this.endDate = booth.getEndDate();
            this.startTime = booth.getStartTime();
            this.endTime = booth.getEndTime();
            this.description = booth.getDescription();
            this.category = booth.getCategory();
            this.period = booth.getPeriod();
            this.image = booth.getImage();
        }
    }


    @Getter
    @Setter
    public static class BoothListDTO {


        private Long id;
        private String name;
        private Location location;
        private Integer index;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private BoothCategory category;
        private Period period;


        // Booth 엔티티로부터 BoothListDTO로 변환하는 팩토리 메서드
        public static BoothListDTO from(Booth booth) {
            BoothListDTO dto = new BoothListDTO();
            dto.id = booth.getId();
            dto.name = booth.getName();
            dto.location = booth.getLocation();
            dto.index = booth.getIndex();
            dto.startDate = booth.getStartDate();
            dto.endDate = booth.getEndDate();
            dto.startTime = booth.getStartTime();
            dto.endTime = booth.getEndTime();
            dto.category = booth.getCategory();
            dto.period = booth.getPeriod();

            return dto;
        }
    }
}
