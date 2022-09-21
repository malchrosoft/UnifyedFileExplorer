/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import com.malchrosoft.multifolderasoneexplorer.Testouillages;
import static com.malchrosoft.multifolderasoneexplorer.Testouillages.explore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * @author amalchrowicz
 */
public class NetworkDirectory extends DirectoryConnector<NetworkFile>
{
	private final NtlmPasswordAuthenticator auth;
	private final NetworkFile baseFile;
		
	public NetworkDirectory(String fullPath, NtlmPasswordAuthenticator auth)
	{
		super(Type.NETWORK, fullPath);
		this.auth = auth;
		this.baseFile = new NetworkFile(basePath, auth);
	}

	@Override
	public Collection<NetworkFile> find(Function<NetworkFile, Boolean> matcher, boolean recursivly)
	{
		return find(baseFile.getFile(), matcher, recursivly);
	}
	
	private Collection<NetworkFile> find(SmbFile dir, Function<NetworkFile, Boolean> matcher, boolean recursivly)
	{
		try
		{
			if (dir.isHidden()) return Collections.emptyList();
			Collection<NetworkFile> coll = new ArrayList();
			if (dir.isDirectory() && recursivly)
			{
				for (var f : dir.listFiles())
				{
					coll.addAll(find(f, matcher, true));
				}
			}
			else if (dir.isFile())
			{
				var nf = new NetworkFile(basePath, auth);
				if (matcher.apply(nf)) coll.add(nf);
			}
			return coll;
			
		} catch (SmbException e)
		{
			Logger.getLogger(NetworkDirectory.class.getName()).log(Level.SEVERE, null, e);
		}
		return Collections.emptyList();
	}
}
