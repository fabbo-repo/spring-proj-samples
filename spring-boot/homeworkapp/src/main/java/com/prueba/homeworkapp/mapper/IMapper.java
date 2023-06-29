package com.prueba.homeworkapp.mapper;

/**
 * 
 * @author Fabbo
 *
 * @param <I> in data
 * @param <O> out data
 */
public interface IMapper<I, O> {
	public O map(I in);
}
