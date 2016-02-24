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
 * - Selcuk Cihan (selcukcihan@gmail.com)
 * ***** END LICENSE BLOCK ***** */

package com.selcukcihan.xfacej.xengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

public class ShortBuffer
{
	/*
	 * we need these *Buffer classes to be able to pass them to opengl calls
	 * each Integer is 4 bytes,
	 */
	private java.nio.ShortBuffer m_buffer; /* the underlying buffer, should be direct buffer */
	private ByteBuffer m_byteBuf; /* the direct buffer that backs FloatBuffer */

	public ShortBuffer()
	{
		this(0);
	}

	public ShortBuffer(int sz)
	{
		/*
		 * underlying m_buffer size = sizeof(a Integer in terms of bytes) * sz
		 * Integer is 4 bytes
		 */
		if(size() != sz)
		{
			m_byteBuf = ByteBuffer.allocateDirect(sz << 2);
			m_byteBuf.order(ByteOrder.nativeOrder());
		}
		m_buffer = m_byteBuf.asShortBuffer();
		m_buffer.rewind();
	}

	public ShortBuffer(final ShortBuffer p_rhs)
	{
		/*
		 * like a copy constructor
		 */
		this(p_rhs.size());
		p_rhs.rewind();
		for(int i = 0; i < p_rhs.size(); i++)
		{
			put(p_rhs.get());
		}
		m_buffer.rewind();
	}

	public ShortBuffer(final Vector<Short> p_indices)
	{
		this(p_indices.size());
		for(short i : p_indices)
			put(i);
		m_buffer.rewind();
	}

	public boolean hasRemaining()
	{
		return m_buffer.hasRemaining();
	}

	public short get()
	{
		return m_buffer.get();
	}
	
	public short get(int p_index) /* absolute get */
	{
		return m_buffer.get(p_index);
	}
	
	public void rewind()
	{
		m_buffer.rewind();
	}
	
	public int size()
	{
		/*
		 * how many integers can this buffer hold
		 */
		if(m_buffer == null)
			return -1;
		else
			return m_buffer.capacity();
	}

	public void put(int p_index, short p_value)
	{
		/*
		 * absolute put method
		 */
		m_buffer.put(p_index, p_value);
	}

	public void put(short p_value)
	{
		/*
		 * relative put method
		 */
		m_buffer.put(p_value);
	}

	public java.nio.ShortBuffer shortBuffer()
	{
		return m_buffer;
	}

	public byte [] byteArray()
	{
		return m_byteBuf.array();
	}
}
