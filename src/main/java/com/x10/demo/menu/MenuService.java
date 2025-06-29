package com.x10.demo.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Iterable<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(UUID id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu updateMenu(UUID id, Menu updateMenu) {
        Menu menu = menuRepository.findById(id).orElseThrow();
        menu.setName(updateMenu.getName());
        menu.setPrice(updateMenu.getPrice());
        menu.setCategory(updateMenu.getCategory());
        return menuRepository.save(menu);
    }

    public void deleteMenu(UUID id) {
        menuRepository.deleteById(id);
    }

}
