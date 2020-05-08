package final_exam;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String request;
	private ArrayList<String> items;
	private ArrayList<Double> currentBids;
	private String itemDescription;
}
