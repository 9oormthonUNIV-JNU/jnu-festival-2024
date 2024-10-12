package com.jnu.festival.domain.booth.dto;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.Category;
import com.jnu.festival.domain.booth.entity.Location;
import com.jnu.festival.domain.booth.entity.Period;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class BoothResponseDTO {
    @Getter
    @Setter
    public static class BoothDetailDTO {

        private int id;
        private String name;
        private Location location;
        private Integer index;
        private Date startDate;
        private Date endDate;
        private Date startTime;
        private Date endTime;
        private String description;
        private Category category;
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

        //부스 id
        private int id;
        //부스이름
        private String name;
        //부스 index
        private Integer index;
        // 부스 location
        private Location location;
        // 시작 일자
        private Date startDate;
        // 종료 일자
        private Date endDate;
        // 시작 시간
        private Date startTime;
        // 종료 시간
        private Date endTime;

        private Category category;
        private Period period;


        // Booth 엔티티로부터 BoothListDTO로 변환하는 팩토리 메서드
        public static BoothListDTO from(Booth booth) {
            BoothListDTO dto = new BoothListDTO();
            dto.id = booth.getId();
            dto.name = booth.getName();
            dto.location = booth.getLocation();
            dto.period = booth.getPeriod();
            dto.category = booth.getCategory();
            return dto;
        }
    }
}
