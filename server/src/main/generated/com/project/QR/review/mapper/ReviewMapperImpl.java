package com.project.QR.review.mapper;

import com.project.QR.review.dto.ReviewResponseDto;
import com.project.QR.review.entity.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:29:33+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewResponseDto.ReviewInfoDto reviewToReviewInfoDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponseDto.ReviewInfoDto.ReviewInfoDtoBuilder reviewInfoDto = ReviewResponseDto.ReviewInfoDto.builder();

        reviewInfoDto.reviewId( review.getReviewId() );
        reviewInfoDto.contents( review.getContents() );
        reviewInfoDto.score( review.getScore() );

        return reviewInfoDto.build();
    }
}
