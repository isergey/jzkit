package org.jzkit.search.provider.z3950.test;

import junit.framework.*;
import junit.extensions.*;
import java.util.*;
import org.jzkit.search.util.ResultSet.*;

import org.jzkit.search.util.RecordModel.*;
import org.jzkit.search.provider.z3950.*;
import org.jzkit.search.provider.iface.*;
import org.jzkit.z3950.QueryModel.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.*;
import org.jzkit.search.provider.iface.SearchableFactory;
import org.jzkit.search.provider.iface.Searchable;

public class Z3950Test extends TestCase {

  private static ApplicationContext app_context = null;

  public Z3950Test(String name) {
    super (name);

  }
  
  public static void main(String[] args) {
  }

  public void testLOC1() throws Exception {
    app_context = new ClassPathXmlApplicationContext( "TestApplicationContext.xml" );
    SearchableFactory sf = (org.jzkit.search.provider.iface.SearchableFactory) app_context.getBean("SearchableFactory");
    Searchable s = sf.create(new IRServiceDescriptor("proto=Z3950,host=z3950.loc.gov,port=7090,code=LC,shortname=Library of congress,longname=Library of congress,defaultRecordSyntax=usmarc,defaultElementSetName=F,recordArchetypes(Default)=usmarc::F"));

    if ( s != null ) {
      IRQuery query = new IRQuery(new org.jzkit.search.util.QueryModel.PrefixString.PrefixString("@attrset bib-1 @attr 1=4 \"brain\""),"Voyager");
      IRResultSet result = s.evaluate(query);
      result.waitForStatus(IRResultSetStatus.COMPLETE|IRResultSetStatus.FAILURE,0);
      Enumeration e = new org.jzkit.search.util.ResultSet.ReadAheadEnumeration(result, new ArchetypeRecordFormatSpecification("Default"));
      for ( int i=0; ( ( e.hasMoreElements() ) && ( i < 20 ) ); i++) {
        Object o = e.nextElement();
        System.err.println(o);
      }
    }
    else {
      System.err.println("No search created by factory");
    }
  }

  public void testLOC2() throws Exception {
    System.err.println("\n\nTest Client 2");
    app_context = new ClassPathXmlApplicationContext( "TestApplicationContext.xml" );
    System.err.println("Got app context: "+app_context);
    if ( app_context == null ) 
      throw new RuntimeException("Unable to locate TestApplicationContext.xml definition file");

    System.err.println("Setting up Z3950 factory");
    Z3950ServiceFactory factory = new Z3950ServiceFactory("z3950.loc.gov",7090);
    factory.setApplicationContext(app_context);
    factory.setDefaultRecordSyntax("usmarc");
    factory.setDefaultElementSetName("F");
    factory.getRecordArchetypes().put("Default","usmarc::F");
    factory.getRecordArchetypes().put("FullDisplay","usmarc::F");
    factory.getRecordArchetypes().put("BriefDisplay","usmarc::B");
    factory.getRecordArchetypes().put("Holdings","usmarc::F");

    System.err.println("Build IR Query");
    IRQuery query = new IRQuery();
    query.collections = new Vector();
    query.collections.add("Voyager");
    query.query = new org.jzkit.search.util.QueryModel.PrefixString.PrefixString("@attrset bib-1 @attr 1=4 \"brain\"");

    // Quick subtest...
    System.err.println("Test: convert to type 1 query");
    Z3950QueryModel zqm = Type1QueryModelBuilder.buildFrom(app_context, query.query, "utf-8");
    System.err.println("result of conversion back :"+zqm.toInternalQueryModel(app_context));
    

    System.err.println("Obtain instance from factory");
    Searchable s = factory.newSearchable();
    s.setApplicationContext(app_context);

    System.err.println("Evaluate query...");
    IRResultSet result = s.evaluate(query);

    System.err.println("Waiting for result set to complete, current status = "+result.getStatus());
    // Wait without timeout until result set is complete or failure
    result.waitForStatus(IRResultSetStatus.COMPLETE|IRResultSetStatus.FAILURE,0);

    System.err.println("Iterate over results (status="+result.getStatus()+"), count="+result.getFragmentCount());

    // Enumeration e = new org.jzkit.search.util.ResultSet.ReadAheadEnumeration(result);
    Enumeration e = new org.jzkit.search.util.ResultSet.ReadAheadEnumeration(result, new ArchetypeRecordFormatSpecification("Default"));

    for ( int i=0; ( ( e.hasMoreElements() ) && ( i < 60 ) ); i++) {
      System.err.println("Processing result "+i);
      InformationFragment f = (InformationFragment) e.nextElement();
      System.err.println(f.getOriginalObject());
    }
  }
}
