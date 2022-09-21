/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 *
 * @author amalchrowicz
 */
public class LocalDirectory extends DirectoryConnector<LocalFile>
{
	private final LocalFile baseFile;

	public LocalDirectory(String path)
	{
		super(Type.LOCAL, path);
		this.baseFile = new LocalFile(basePath);
	}

	@Override
	public Collection<LocalFile> find(Function<LocalFile, Boolean> matcher, boolean recursivly)
	{
		return find(baseFile.getFile(), matcher, recursivly);
	}

	private Collection<LocalFile> find(File dir, Function<LocalFile, Boolean> matcher, boolean recursivly)
	{
		if (dir.isHidden()) return Collections.emptyList();
		Collection<LocalFile> coll = new ArrayList();
		if (dir.isDirectory() && recursivly)
		{
			for (var f : dir.listFiles())
			{
				coll.addAll(find(f, matcher, true));
			}
		}
		else if (dir.isFile())
		{
			var nf = new LocalFile(basePath);
			if (matcher.apply(nf)) coll.add(nf);
		}
		return coll;
	}
}
