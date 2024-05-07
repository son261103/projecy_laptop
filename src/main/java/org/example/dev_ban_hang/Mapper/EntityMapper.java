package org.example.dev_ban_hang.Mapper;

import java.util.List;

public interface EntityMapper<E , D> {
    E toEntity(D dto);
    D toDto(E entity);
    List<E> toEntity(List<D> Dto);
    List<D> toDto(List<E> entity);
}
