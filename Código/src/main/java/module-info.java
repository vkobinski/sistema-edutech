module pr.gov.edutech {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.lang3;
	requires java.sql;

    opens pr.gov.edutech to javafx.fxml;
    opens classes to javafx.base;
    exports pr.gov.edutech;
}
