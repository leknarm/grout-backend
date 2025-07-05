package com.x10.demo.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MenuServiceTests {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMenu() {
        Menu menu = Menu.builder().name("Pizza").price(10.0).category("Food").build();
        Menu savedMenu = Menu.builder().id(UUID.randomUUID()).name("Pizza").price(10.0).category("Food").build();
        when(menuRepository.save(any(Menu.class))).thenReturn(savedMenu);
        Menu result = menuService.createMenu(menu);
        assertNotNull(result.getId());
        assertEquals("Pizza", result.getName());
    }

    @Test
    void testGetAllMenus() {
        Menu menu = Menu.builder().id(UUID.randomUUID()).name("Pizza").price(10.0).category("Food").build();
        when(menuRepository.findAll()).thenReturn(Collections.singletonList(menu));
        Iterable<Menu> result = menuService.getAllMenus();
        assertTrue(result.iterator().hasNext());
        assertEquals("Pizza", result.iterator().next().getName());
    }

    @Test
    void testGetMenuById_Found() {
        UUID id = UUID.randomUUID();
        Menu menu = Menu.builder().id(id).name("Pizza").price(10.0).category("Food").build();
        when(menuRepository.findById(eq(id))).thenReturn(Optional.of(menu));
        Menu result = menuService.getMenuById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetMenuById_NotFound() {
        UUID id = UUID.randomUUID();
        when(menuRepository.findById(eq(id))).thenReturn(Optional.empty());
        Menu result = menuService.getMenuById(id);
        assertNull(result);
    }

    @Test
    void testUpdateMenu_Found() {
        UUID id = UUID.randomUUID();
        Menu existingMenu = Menu.builder().id(id).name("Pizza").price(10.0).category("Food").build();
        Menu updateMenu = Menu.builder().name("Burger").price(8.0).category("Food").build();
        Menu savedMenu = Menu.builder().id(id).name("Burger").price(8.0).category("Food").build();
        when(menuRepository.findById(eq(id))).thenReturn(Optional.of(existingMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(savedMenu);
        Menu result = menuService.updateMenu(id, updateMenu);
        assertEquals("Burger", result.getName());
        assertEquals(8.0, result.getPrice());
    }

    @Test
    void testUpdateMenu_NotFound() {
        UUID id = UUID.randomUUID();
        Menu updateMenu = Menu.builder().name("Burger").price(8.0).category("Food").build();
        when(menuRepository.findById(eq(id))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> menuService.updateMenu(id, updateMenu));
    }

    @Test
    void testDeleteMenu() {
        UUID id = UUID.randomUUID();
        doNothing().when(menuRepository).deleteById(eq(id));
        menuService.deleteMenu(id);
        verify(menuRepository, times(1)).deleteById(eq(id));
    }
}
