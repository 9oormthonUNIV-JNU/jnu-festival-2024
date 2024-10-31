package com.jnu.festival.api.domain.booth.service;

import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.booth.service.BoothService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  //mocking 작업을 수행하게 도와줌
public class BoothServiceTest {

    @Mock  //전체 repository를 mocking 해와야함 -> 전체 DB를 건드리지 않기 위해
    private BoothRepository boothRepository;

    @InjectMocks
    private BoothService boothService;

    @Test
    public void BoothService_ReadBoothList_ReturnBoothListDto() {
        
    }

}
