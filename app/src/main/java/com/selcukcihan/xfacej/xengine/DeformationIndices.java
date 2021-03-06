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
 * struct DeformationIndices
 */

public class DeformationIndices
{
	public int [] id = null;
	
	public DeformationIndices(DeformationIndices curElement)
	{
		id = new int[DeformableGeometry.MAXWEIGHT];
		System.arraycopy(curElement.id, 0, id, 0, DeformableGeometry.MAXWEIGHT);
	}
	
	public DeformationIndices()
	{
		id = new int[DeformableGeometry.MAXWEIGHT];
	}

/*
	struct DeformationIndices
	{
		DeformationIndices() {memset(&id[0], 0, MAXWEIGHT*sizeof(unsigned short));}
		unsigned short operator[](size_t i) const {assert(i < MAXWEIGHT); return id[i];}
		unsigned short& operator[](size_t i) {assert(i < MAXWEIGHT); return id[i];}
	private:
		unsigned short id[MAXWEIGHT];
	};
 */
}
