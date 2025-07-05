package com.x10.demo.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MenuRepositoryTests {

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void clean() {
        menuRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Menu menu = Menu.builder().name("Pizza").price(10.0).category("Food").build();
        Menu saved = menuRepository.save(menu);
        assertNotNull(saved.getId());
        Optional<Menu> found = menuRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Pizza", found.get().getName());
    }

    @Test
    void testFindAll() {
        menuRepository.save(Menu.builder().name("Pizza").price(10.0).category("Food").build());
        menuRepository.save(Menu.builder().name("Burger").price(8.0).category("Food").build());
        Iterable<Menu> all = menuRepository.findAll();
        int count = 0;
        for (Menu m : all) count++;
        assertEquals(2, count);
    }

    @Test
    void testDeleteById() {
        Menu menu = menuRepository.save(Menu.builder().name("Pizza").price(10.0).category("Food").build());
        UUID id = menu.getId();
        menuRepository.deleteById(id);
        assertFalse(menuRepository.findById(id).isPresent());
    }
}
