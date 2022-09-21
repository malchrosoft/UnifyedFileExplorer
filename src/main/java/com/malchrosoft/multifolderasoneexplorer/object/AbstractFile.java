/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import java.io.File;
import java.util.function.Function;

/**
 *
 * @author amalchrowicz
 * @param <T> 
 */
public class AbstractFile<T>
{
	private final T file;
	
	public AbstractFile(T file)
	{
		this.file = file;
	}
	
	public AbstractFile(String fullPath, Function<String, T> buildFunc)
	{
		this.file = buildFunc.apply(fullPath);
	}
	
	protected T getFile()
	{
		return this.file;
	}
}
