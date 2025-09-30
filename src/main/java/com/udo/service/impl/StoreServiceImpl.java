package com.udo.service.impl;

import com.udo.exceptions.UserException;
import com.udo.mapper.StoreMapper;
import com.udo.model.Store;
import com.udo.model.StoreContact;
import com.udo.model.User;
import com.udo.payload.DTO.StoreDTO;
import com.udo.repository.StoreRepository;
import com.udo.service.StoreService;
import com.udo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toEntity(storeDTO, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) throws Exception {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found")
        );
        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStore() {
        List<Store> dtos = storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) throws Exception {
        User currentUser = userService.getCurrentUser();
        Store existingStore = storeRepository.findByStoreAdminId(currentUser.getId());
        if(existingStore == null){
            throw new Exception("Store not found");
        }
        existingStore.setBrand(storeDTO.getBrand());
        existingStore.setDescription(storeDTO.getDescription());

        if(storeDTO.getStoreType() != null){
            existingStore.setStoreType(storeDTO.getStoreType());
        }
        if(storeDTO.getContact() != null){
            StoreContact contact = StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();
            existingStore.setContact(contact);
        }
        Store updateStore = storeRepository.save(existingStore);
        return StoreMapper.toDTO(updateStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {
        User currentUser = userService.getCurrentUser();

        if(currentUser == null){
            throw new UserException("You don't have permission to access this store");
        }
        return StoreMapper.toDTO(currentUser.getStore());
    }
}
