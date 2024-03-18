package com.weatherForecasting.backend.historicalpredictioncr;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class HistoricalPredictionRepositoryForTestImpl implements HistoricalPredictionRepository {

    Map<UUID, PredictionHistory> database = new ConcurrentHashMap<>();

    @Override
    public List<PredictionHistory> findAllByUsername(String username) {
        return database.values().stream()
                .filter(history -> history.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public PredictionHistory save(PredictionHistory predictionHistory) {
        return database.put(predictionHistory.getId(), predictionHistory);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PredictionHistory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PredictionHistory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PredictionHistory getOne(UUID uuid) {
        return null;
    }

    @Override
    public PredictionHistory getById(UUID uuid) {
        return null;
    }

    @Override
    public PredictionHistory getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PredictionHistory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PredictionHistory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PredictionHistory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends PredictionHistory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<PredictionHistory> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<PredictionHistory> findAll() {
        return null;
    }

    @Override
    public List<PredictionHistory> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(PredictionHistory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends PredictionHistory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PredictionHistory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PredictionHistory> findAll(Pageable pageable) {
        return null;
    }
}
