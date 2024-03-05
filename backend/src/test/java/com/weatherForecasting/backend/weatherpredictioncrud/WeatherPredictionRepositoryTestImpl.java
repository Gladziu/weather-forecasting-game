package com.weatherForecasting.backend.weatherpredictioncrud;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class WeatherPredictionRepositoryTestImpl implements WeatherPredictionRepository {

    Map<UUID, WeatherPrediction> database = new ConcurrentHashMap<>();

    @Override
    public WeatherPrediction save(WeatherPrediction weatherPrediction) {
        return database.put(weatherPrediction.getId(), weatherPrediction);
    }

    @Override
    public List<WeatherPrediction> findAllByUsername(String username) {
        return database.values().stream()
                .filter(key -> key.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID uuid) {
        return database.keySet().stream()
                .anyMatch(key -> key.equals(uuid));
    }

    @Override
    public void deleteById(UUID uuid) {
        database.remove(uuid);
    }

    @Override
    public List<WeatherPrediction> findByForecastDateLessThanEqual(LocalDate forecastDate) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends WeatherPrediction> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<WeatherPrediction> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public WeatherPrediction getOne(UUID uuid) {
        return null;
    }

    @Override
    public WeatherPrediction getById(UUID uuid) {
        return null;
    }

    @Override
    public WeatherPrediction getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WeatherPrediction> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WeatherPrediction> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WeatherPrediction, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends WeatherPrediction> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WeatherPrediction> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<WeatherPrediction> findAll() {
        return null;
    }

    @Override
    public List<WeatherPrediction> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(WeatherPrediction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends WeatherPrediction> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<WeatherPrediction> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WeatherPrediction> findAll(Pageable pageable) {
        return null;
    }
}