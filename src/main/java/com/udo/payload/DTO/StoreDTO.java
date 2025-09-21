package com.udo.payload.DTO;

import com.udo.domain.StoreStatus;
import com.udo.model.StoreContact;
import com.udo.model.User;

import java.time.LocalDateTime;

public class StoreDTO {
    private Long id;
    private String brand;
    private User storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact;
}
