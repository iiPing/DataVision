package jimm.datavision.testdata.odbc;
import jimm.datavision.testdata.Office;
import jimm.datavision.testdata.Job;
import jimm.datavision.testdata.AggregateTestData;
import java.util.Calendar;

/**
 * WARNING: this file is just a copy of the MySQL CreateData.java. It
 * probably won't work for ODBC databases.
 */
class CreateData {

public static void main(String[] args) {
    System.out.println("delete from office;");
    for (Office o : Office.offices())
	System.out.println("insert into office values (" + o.id + ", '"
			   + o.name + "', '" + o.abbrev + "', '" + o.fax
			   + "', '" + o.email + "', "
			   + (o.visible ? 1 : 0) + ");");

    System.out.println("delete from jobs;");
    for (Job j : Job.jobs())
	System.out.println("insert into jobs values (" + j.id + ", '"
			   + j.title + "', " + j.fk_office_id + ", '"
			   + j.company + "', '" + j.location + "', '"
			   + j.description + "', "
			   + (j.visible ? 1 : 0) + ", '"
			   + j.post_date.get(Calendar.YEAR) + '-'
			   + j.post_date.get(Calendar.MONTH) + '-'
			   + j.post_date.get(Calendar.DATE) + "', "
			   + (j.hourly_rate == null ? "NULL"
			      : j.hourly_rate.toString())
			   + ");");

    System.out.println("delete from aggregate_test;");
    for (AggregateTestData data : AggregateTestData.aggregateTestData("../aggregate_test.dat"))
	System.out.println("insert into aggregate_test values ('" + data.col1()
			   + "', '" + data.col2() + "', " + data.value()
			   + ");");
}

}
