/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer;

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
public class Testouillages
{
	public static void main(String[] args)
	{
		String url = "smb://192.168.0.25/fichiers/Aymeric";
		String userName = "aymeric.malchrowicz";
		String password = "mdpPTSTQV";
		String domain = "WORKGROUP";

		NtlmPasswordAuthenticator auth = new NtlmPasswordAuthenticator(domain, userName, password);
		try
		{
			SmbFile smbF = new SmbFile(url, SingletonContext.getInstance().withCredentials(auth));
			if (smbF.isDirectory() && !smbF.getCanonicalPath().endsWith(File.separator))
			{
				smbF = new SmbFile(url + File.separator, SingletonContext.getInstance().withCredentials(auth));
			}
			explore(smbF, 0, true);
		} catch (SmbException | MalformedURLException ex)
		{
			Logger.getLogger(Testouillages.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void explore(SmbFile smb, int inc, boolean recursivly)
	{
		try
		{
			if (smb.isHidden()) return;
			System.out.println(incStr(inc) + smb.getName() + " " + smb.getCanonicalPath());
			if (smb.isDirectory() && recursivly)
			{
				inc++;
				for (var f : smb.listFiles())
				{
					explore(f, inc, true);
				}
			}
		} catch (SmbException e)
		{
			Logger.getLogger(Testouillages.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private static String incStr(int inc)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inc; i++)
		{
			sb.append("   ");
		}
		sb.append("- ");
		return sb.toString();
	}
}
