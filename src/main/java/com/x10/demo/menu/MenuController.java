package com.x10.demo.menu;

import com.x10.demo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<ApiResponse<Menu>> createMenu(@RequestBody Menu menu) {
        Menu createdMenu = menuService.createMenu(menu);
        return ResponseEntity.ok(ApiResponse.<Menu>builder().code("0000").message("create menu successfully").data(createdMenu).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<Menu>>> getMenus() {
        Iterable<Menu> menus = menuService.getAllMenus();
        return ResponseEntity.ok(ApiResponse.<Iterable<Menu>>builder().code("0000").message("get menus successfully").data(menus).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Menu>> getMenu(@PathVariable UUID id) {
        Menu menu = menuService.getMenuById(id);
        return ResponseEntity.ok(ApiResponse.<Menu>builder().code("0000").message("get menu successfully").data(menu).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Menu>> updateMenu(@PathVariable UUID id, @RequestBody Menu menu) {
        Menu updatedMenu = menuService.updateMenu(id, menu);
        return ResponseEntity.ok(ApiResponse.<Menu>builder().code("0000").message("update menu successfully").data(updatedMenu).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMenU(@PathVariable UUID id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().code("0000").message("delete menu successfully").data(null).build());
    }

}
