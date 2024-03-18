package com.weatherForecasting.backend.scoremanagementcru;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class ScoreManagementRepositoryForTestImpl implements ScoreManagementRepository {

    Map<UUID, Score> database = new ConcurrentHashMap<>();

    @Override
    public Score save(Score score) {
        return database.put(score.getId(), score);
    }

    @Override
    public List<Score> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<Score> findByUsername(String username) {
        return database.values().stream()
                .filter(score -> score.getUsername().equals(username))
                .findAny();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Score> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Score> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Score> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Score getOne(UUID uuid) {
        return null;
    }

    @Override
    public Score getById(UUID uuid) {
        return null;
    }

    @Override
    public Score getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Score> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Score> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Score> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Score> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Score> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Score> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Score, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Score> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Score> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<Score> findAllById(Iterable<UUID> uuids) {
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
    public void delete(Score entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Score> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Score> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Score> findAll(Pageable pageable) {
        return null;
    }
}
