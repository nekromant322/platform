package com.override.repository;

import com.override.model.VkCall;
import org.springframework.data.repository.CrudRepository;

public interface VkCallRepository extends CrudRepository<VkCall, Long> {

    VkCall findVkCallByReviewId(Long reviewId);
}
