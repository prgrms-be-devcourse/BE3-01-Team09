package org.programmer.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.programmer.cafe.domain.item.entity.ItemStatus;
import org.programmer.cafe.domain.item.entity.dto.CreateItemResponse;
import org.programmer.cafe.domain.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private final ItemService itemService = Mockito.mock(ItemService.class);

    @Test
    void addItem() throws Exception {
        final CreateItemResponse createDto = CreateItemResponse.builder().name("Coffee")
            .price(10000).stock(15).status(ItemStatus.ON_SALE).image("/images/coffee.png").build();

        given(itemService.save(any())).willReturn(createDto);
        mockMvc.perform(post("/api/admin/items").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))).andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.name").value("Coffee"))
            .andExpect(jsonPath("$.data.price").value(10000))
            .andExpect(jsonPath("$.data.stock").value(15))
            .andExpect(jsonPath("$.data.status").value("ON_SALE"))
            .andExpect(jsonPath("$.data.image").value("/images/coffee.png"));
    }
}