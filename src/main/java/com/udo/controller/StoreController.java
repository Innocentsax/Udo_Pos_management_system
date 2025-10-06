package com.udo.controller;

import com.udo.exceptions.UserException;
import com.udo.mapper.StoreMapper;
import com.udo.model.User;
import com.udo.payload.DTO.StoreDTO;
import com.udo.service.StoreService;
import com.udo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO,
                                                @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDTO, user));
    }

    @GetMapping
    public ResponseEntity<StoreDTO> getStoreById(
            @PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStore(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getAllStore());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id,
                                                @RequestBody StoreDTO storeDTO) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(id, storeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StoreDTO> deleteStore(@PathVariable Long id,
                                                @RequestBody StoreDTO storeDTO) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(id, storeDTO));
    }
}
