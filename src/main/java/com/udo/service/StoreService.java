package com.udo.service;

import com.udo.model.Store;
import com.udo.model.User;
import com.udo.payload.DTO.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long id);
    List<StoreDTO> getAllStore();
    Store getStoreByAdmin();
    StoreDTO updateStore(Long id, StoreDTO storeDTO);
    StoreDTO deleteStore(Long id);
    StoreDTO getStoreByEmployee();
}
