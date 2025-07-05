package com.x10.demo.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuController.class)
public class MenuControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateMenu() throws Exception {
        Menu menu = Menu.builder().name("Pizza").price(10.0).category("Food").build();
        Menu createdMenu = Menu.builder().id(UUID.randomUUID()).name("Pizza").price(10.0).category("Food").build();
        Mockito.when(menuService.createMenu(any(Menu.class))).thenReturn(createdMenu);

        mockMvc.perform(post("/v1/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("create menu successfully"))
                .andExpect(jsonPath("$.data.name").value("Pizza"));
    }

    @Test
    void testGetMenus() throws Exception {
        Menu menu = Menu.builder().id(UUID.randomUUID()).name("Pizza").price(10.0).category("Food").build();
        Mockito.when(menuService.getAllMenus()).thenReturn(Collections.singletonList(menu));

        mockMvc.perform(get("/v1/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("get menus successfully"))
                .andExpect(jsonPath("$.data[0].name").value("Pizza"));
    }

    @Test
    void testGetMenuById() throws Exception {
        UUID id = UUID.randomUUID();
        Menu menu = Menu.builder().id(id).name("Pizza").price(10.0).category("Food").build();
        Mockito.when(menuService.getMenuById(eq(id))).thenReturn(menu);

        mockMvc.perform(get("/v1/menus/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("get menu successfully"))
                .andExpect(jsonPath("$.data.name").value("Pizza"));
    }

    @Test
    void testUpdateMenu() throws Exception {
        UUID id = UUID.randomUUID();
        Menu menu = Menu.builder().name("Burger").price(8.0).category("Food").build();
        Menu updatedMenu = Menu.builder().id(id).name("Burger").price(8.0).category("Food").build();
        Mockito.when(menuService.updateMenu(eq(id), any(Menu.class))).thenReturn(updatedMenu);

        mockMvc.perform(put("/v1/menus/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("update menu successfully"))
                .andExpect(jsonPath("$.data.name").value("Burger"));
    }

    @Test
    void testDeleteMenu() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doNothing().when(menuService).deleteMenu(eq(id));

        mockMvc.perform(delete("/v1/menus/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("delete menu successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
