package uz.nt.courseregistration.service;

import org.springframework.stereotype.Service;
import uz.nt.courseregistration.domain.entity.user.UserEntity;

import java.util.List;

/**
 * @param <RD> RD read dto
 * @param <CD> CD create dto
 * @author Tojiahmedov Nodir
 */

@Service
public interface BaseService<RD, CD> {

    RD save(CD createDto);

    void delete(Long id);

    RD update(CD createDto, Long id);

    RD getById(Long id);

    List<RD> getAll();

}
