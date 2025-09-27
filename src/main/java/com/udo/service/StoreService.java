package com.udo.service;

import com.udo.exceptions.UserException;
import com.udo.model.Store;
import com.udo.model.User;
import com.udo.payload.DTO.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long id) throws Exception;
    List<StoreDTO> getAllStore();
    Store getStoreByAdmin() throws UserException;
    StoreDTO updateStore(Long id, StoreDTO storeDTO) throws Exception;
    StoreDTO deleteStore(Long id);
    StoreDTO getStoreByEmployee() throws UserException;
}
