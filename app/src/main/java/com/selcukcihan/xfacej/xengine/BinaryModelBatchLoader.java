/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is XfaceApp Application Library.
 *
 * The Initial Developer of the Original Code is
 * ITC-irst, TCC Division (http://tcc.fbk.eu) Trento / ITALY.
 * For info, contact: xface-info@fbk.eu or http://xface.fbk.eu
 * Portions created by the Initial Developer are Copyright (C) 2004 - 2008
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * - Koray Balci (koraybalci@gmail.com)
 * ***** END LICENSE BLOCK ***** */

package com.selcukcihan.xfacej.xengine;

/*
 * XEngine::BinaryModelBatchLoader
 * bitti.
 * sorun olabilecek yerler:
 * 		GL gl gecirdim parametre olarak init'e, bu isi bir yerde baglamam lazim, bu gl nerden gelecek
 * 		adam fread(fp, &vector) falan yapmis) ben readFloat diye okuya okuya memberlere yazdim
 * 			ama sira onemli, belki sira yanlis olmustur falan bilemiyorum ki c++daki davranisi
 * 
 */

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;


import com.selcukcihan.xfacej.xmath.Quaternion;
import com.selcukcihan.xfacej.xmath.Vector3;

import javax.microedition.khronos.opengles.GL11;

public class BinaryModelBatchLoader implements IModelLoader
{
	class DrawablePair
	{
		public String m_s;
		public LinkedList<Drawable> m_d;
		public DrawablePair(final String s, final LinkedList<Drawable> d)
		{
			m_s = s;
			m_d = d;
		}
	}
	private boolean m_bLoaded;
	/*
	 * typedef std::list<boost::shared_ptr<Drawable> > DRAWABLES;
	 * std::list< std::pair< std::string, DRAWABLES> > m_data;
	 */
	private LinkedList<DrawablePair> m_data;

	private final Context mContext;
	public BinaryModelBatchLoader(Context context)
	{
		mContext = context;
		m_bLoaded = false;
		m_data = new LinkedList<DrawablePair>();
	}
	
	public boolean isLoaded()
	{
		/*
		 * bool isLoaded() const {return m_bLoaded;}
		 */
		return m_bLoaded;
	}

	public static short readShort(RandomAccessFile p_fp) throws IOException
	{
		short retVal = 0;
		
		int b = p_fp.readUnsignedByte();
		retVal += b * 0x1;
		
		b = p_fp.readUnsignedByte();
		retVal += b * 0x100;
		
		return retVal;
	}
	
	public static int readUInt(RandomAccessFile p_fp) throws IOException
	{
		int retVal = 0;
		
		int b = p_fp.readUnsignedByte();
		retVal += b * 0x1;
		
		b = p_fp.readUnsignedByte();
		retVal += b * 0x100;
		
		b = p_fp.readUnsignedByte();
		retVal += b * 0x10000;
		
		b = p_fp.readUnsignedByte();
		retVal += b * 0x1000000;
		
		return retVal;
	}

	private File createCacheFile(int resourceId, String filename)
			throws IOException {
		File cacheFile = new File(mContext.getCacheDir(), filename);

		if (cacheFile.createNewFile() == false) {
			cacheFile.delete();
			cacheFile.createNewFile();
		}

		// from: InputStream to: FileOutputStream.
		InputStream inputStream = mContext.getResources().openRawResource(resourceId);
		FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);

		byte[] buffer = new byte[1024 * 512];
		while (inputStream.read(buffer, 0, 1024 * 512) != -1) {
			fileOutputStream.write(buffer);
		}

		fileOutputStream.close();
		inputStream.close();

		return cacheFile;
	}
	
	public boolean init(final String filename, final String path, final GL11 p_gl)
	{
		/*
		 * bool init(const std::string& filename, const std::string& path);
		 */
		m_bLoaded = false;
		RandomAccessFile fp = null;
        File cacheFile = null;
		try
		{
            cacheFile = createCacheFile(mContext.getResources().getIdentifier("alice_dat", "raw", mContext.getPackageName()), "delete-me-please");
            fp = new RandomAccessFile(cacheFile, "r");
		}
		catch(FileNotFoundException fnfe)
		{
			/* fnfe.printStackTrace(); */
		}
        catch (IOException ioex) {

        }
		if(fp == null)
			return false;
		
		try
		{
			m_data.clear();
					
			int sz_files = readUInt(fp); /* size_t unsigned int c++da, biz int yaptik bakalim sorun olmaz insallah */
			for(int i = 0; i < sz_files; ++i)
			{
				int sz = readUInt(fp);
				byte [] fname = new byte[sz];
				fp.read(fname);
	
				int sz_drawables = readUInt(fp);
				LinkedList<Drawable> drawables = new LinkedList<Drawable>();
				for(int j = 0; j < sz_drawables; ++j)
				{
					Drawable dr = new Drawable();
					// mesh name
					sz = readUInt(fp);
					byte [] drname = new byte[sz];
					fp.read(drname);
					dr.setMeshName(new String(drname));
	
					// tex name
					sz = readUInt(fp);
					drname = new byte[sz];
					fp.read(drname);
					dr.setTexName(new String(drname), 0);
					TextureManager.getInstance(mContext).load(new String(drname), new String(drname), p_gl);
	
					// transform (translation and rotation)
					Transform tr = new Transform();
					Vector3 trans = new Vector3();
					Quaternion q = new Quaternion();
					trans.x = Float.intBitsToFloat(readUInt(fp));
					trans.y = Float.intBitsToFloat(readUInt(fp));
					trans.z = Float.intBitsToFloat(readUInt(fp));
					tr.setTranslation(trans);
					q.x = Float.intBitsToFloat(readUInt(fp));
					q.y = Float.intBitsToFloat(readUInt(fp));
					q.z = Float.intBitsToFloat(readUInt(fp));
					q.w = Float.intBitsToFloat(readUInt(fp));
					
					tr.setRotation(q);
					dr.setTransform(tr);
	
					drawables.add(dr);
				}
				String dummy = new String(fname);
				m_data.add(new DrawablePair(dummy, drawables));
			}
	
			// process the data now
			MeshManager pMM = MeshManager.getInstance();
			for(DrawablePair dp : m_data)
			{
				for(Drawable d : dp.m_d)
				{
					DeformableGeometry pGeo = new DeformableGeometry(d.getMeshName());
					pGeo.readBinary(fp);
					pMM.registerMesh(pGeo);
				}
			}
			fp.close();
			m_bLoaded = true;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
        if (cacheFile != null) {
            cacheFile.delete();
        }
		return true;
	}
	
	public LinkedList<Drawable> loadModel(final String filename, final String dir, GL11 p_gl)
	{
		/*
		 * std::list<boost::shared_ptr<Drawable> > loadModel(const std::string &filename, const std::string& dir="./");
		 */
		for(DrawablePair dp : m_data)
		{
			if(dp.m_s.equals(filename))
				return dp.m_d;
		}
		
		LinkedList<Drawable> retVal = new LinkedList<Drawable>();
		return retVal;
	}
}
