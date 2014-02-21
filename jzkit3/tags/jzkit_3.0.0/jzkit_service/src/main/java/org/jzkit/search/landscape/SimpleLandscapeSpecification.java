/*
 * $Log: SimpleLandscapeSpecification.java,v $
 * Revision 1.2  2005/08/24 08:40:49  ibbo
 * Improved cache hit mechanism for stateless search
 *
 * Revision 1.1  2004/09/16 10:00:08  ibbo
 * Added landscape classes
 *
 *
 */

package org.jzkit.search.landscape;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:       SimpleLandscapeSpecification
 * @version:    $Id: SimpleLandscapeSpecification.java,v 1.2 2005/08/24 08:40:49 ibbo Exp $
 * Copyright:   Copyright 2003, Ian Ibbotson, Knowledge Integration Ltd
 * @author:     Ian Ibbotson (ian.ibbotson@k-int.com)
 * Description:
 *              
 */

public class SimpleLandscapeSpecification implements org.jzkit.search.LandscapeSpecification, java.io.Serializable {

  private List collection_id_list = null;

  public SimpleLandscapeSpecification() {
    collection_id_list = new ArrayList();
  }

  public SimpleLandscapeSpecification(List collection_id_list) {
    this.collection_id_list = collection_id_list;
  }

  public SimpleLandscapeSpecification(String collection_id) {
    collection_id_list = new ArrayList();
    collection_id_list.add(collection_id);
  }

  public void addCollectionID(String coll_id) {
    collection_id_list.add(coll_id);
  }

  public void removeCollectionID(String coll_id) {
    collection_id_list.remove(coll_id);
  }

  public List getCollectionList() {
    return collection_id_list;
  }

  public List toCollectionList() {
    return collection_id_list;
  }

  public String toString() {
    return collection_id_list.toString();
  }
}
