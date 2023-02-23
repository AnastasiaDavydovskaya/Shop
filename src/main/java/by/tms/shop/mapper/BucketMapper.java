package by.tms.shop.mapper;

import by.tms.shop.dto.BucketDto;
import by.tms.shop.entities.Bucket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BucketMapper {

    Bucket toEntity(BucketDto bucketDto);
}
