// Title:       InformationFragmentImpl
// @version:    $Id: DOMTree.java,v 1.3 2004/10/27 14:41:20 ibbo Exp $
// Copyright:   Copyright (C) 1999,2000 Knowledge Integration Ltd.
// @author:     Ian Ibbotson (ibbo@k-int.com)
// Company:     Knowledge Integration Ltd.
// Description: 

//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2.1 of
// the license, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite
// 330, Boston, MA  02111-1307, USA.
// 


package org.jzkit.search.util.RecordModel;

import org.w3c.dom.Document;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InformationFragmentImpl implements InformationFragment, Serializable {

  private String source_repository = null;
  private String source_collection_name = null;
  private Object handle = null;
  private Object native_object = null;
  private ExplicitRecordFormatSpecification spec = null;
  public Map additional_info = new HashMap();
  private long hit_no = 0;

  public InformationFragmentImpl(long hit_no,
                                 String source_repository,
                                 String source_collection_name,
                                 Object handle,
                                 Object native_object,
                                 ExplicitRecordFormatSpecification spec) {
    this.hit_no = hit_no;
    this.source_repository = source_repository;
    this.source_collection_name = source_collection_name;
    this.handle = handle;
    this.native_object=native_object;
    this.spec = spec;
  }

  // Get hold of the native_objectinal (maybe blob) object
  public Object getOriginalObject() {
    return native_object;
  }

  // Name of class for above object
  public String getOriginalObjectClassName() {
    return "Document";
  }

  // We will use the root node to determine any applicable namespace

  // Get DOM Document for object
  // public Document getDocument() {
  //   return native_object;
  // }

  // public String getDocumentSchema() {
    // Should really use xml docoument to get schema
  //   return spec.getSchema().toString();
  // }
  //


  public String toString() {
    return native_object.toString();
  }

  public String getSourceRepositoryID() {
    return source_repository;
  }

  public String getSourceCollectionName() {
    return source_collection_name;
  }
 
  public Object getSourceFragmentID() {
    if ( handle != null )
      return handle;
    else
      return null;
  } 

  public ExplicitRecordFormatSpecification getFormatSpecification() {
    return spec;
  }

  public void setFormatSpecification(ExplicitRecordFormatSpecification spec) {
    this.spec = spec;
  }

  public void setSourceRepositoryID(String id) {
    this.source_repository = id;
  }

  public void setSourceCollectionName(String collection_name) {
    this.source_collection_name = collection_name;
  } 

  public void setSourceFragmentID(Object id) {
    this.handle = id;
  }

  /**
   *  Any extended record info such as hit highligting points, OAI Header record info etc that
   *  the search provider may return.
   */
  public Map getExtendedInfo() {
    return additional_info;
  }

  public long getHitNo() {
    return hit_no;
  }

  public void setHitNo(long hit_no) {
    this.hit_no = hit_no;
  }

}