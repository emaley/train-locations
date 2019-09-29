package com.ewan.timetable.repository;

import com.ewan.timetable.model.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Long> {
}
