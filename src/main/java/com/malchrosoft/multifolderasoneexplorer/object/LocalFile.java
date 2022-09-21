/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malchrosoft.multifolderasoneexplorer.object;

import java.io.File;

/**
 *
 * @author amalchrowicz
 */
public final class LocalFile extends AbstractFile<File>
{
	public LocalFile(String fullPath)
	{
		super(fullPath, (t) -> new File(t));
	}
}
