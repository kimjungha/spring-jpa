package com.example.accessingdatajpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(updatable = false)
    @CreatedBy
    private String createBy; //생성자

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate; //생성 시간

    @LastModifiedBy
    private String lastModifiedBy; //수정자

    @LastModifiedDate
    private LocalDateTime lastModifiedDate; //수정 시간

    @Override
    public String toString() {
        return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s',createBy='%s',createDate='%s',lastModifiedBy='%s',lastModifiedDate='%s']",
            id, firstName, lastName,createBy,createdDate,lastModifiedBy,lastModifiedDate);
    }
    void updateName(String name){
        firstName = name;
    }
    void updatePlusDay(Integer plusDay){
        createdDate = LocalDateTime.now().plusDays(plusDay);
        lastModifiedDate = LocalDateTime.now().plusDays(plusDay);
    }
}