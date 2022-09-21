/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;

/**
 *
 * @author amalchrowicz
 * @param <T> the typed AbstractFile
 */
public abstract class DirectoryConnector<T extends AbstractFile<?>>
{
	public static enum Type
	{
		NETWORK,
		LOCAL
	}

	private final Type type;
	protected final String basePath;

	public DirectoryConnector(Type type, String path)
	{
		this.type = type;
		this.basePath = path + (!path.endsWith(File.separator) ? File.separator : "");
	}

	public Type getType()
	{
		return type;
	}

	/**
	 *
	 * @param recursivly
	 * @return
	 */
	public final Collection<T> explore(boolean recursivly)
	{
		return find((f) -> true, recursivly);
	}

	/**
	 *
	 * @param matcher
	 * @param recursivly
	 * @return
	 */
	public abstract Collection<T> find(Function<T, Boolean> matcher, boolean recursivly);
}
