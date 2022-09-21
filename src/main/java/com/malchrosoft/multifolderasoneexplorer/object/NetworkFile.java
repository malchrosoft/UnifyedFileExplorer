/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import com.malchrosoft.multifolderasoneexplorer.Testouillages;
import static com.malchrosoft.multifolderasoneexplorer.Testouillages.explore;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.context.SingletonContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 *
 * @author amalchrowicz
 */
public final class NetworkFile extends AbstractFile<SmbFile>
{	
	public NetworkFile(String fullPath, NtlmPasswordAuthenticator auth)
	{
		super(fullPath, (t) -> build(t, auth));
	}

	private static SmbFile build(String fullPath, NtlmPasswordAuthenticator auth)
	{
		try
		{
			SmbFile smbF = new SmbFile(fullPath, SingletonContext.getInstance().withCredentials(auth));
			if (smbF.isDirectory() && !smbF.getCanonicalPath().endsWith(File.separator))
			{
				smbF = new SmbFile(fullPath + File.separator, SingletonContext.getInstance().withCredentials(auth));
			}
			return smbF;
		} catch (SmbException | MalformedURLException ex)
		{
			Logger.getLogger(Testouillages.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
	}
}
