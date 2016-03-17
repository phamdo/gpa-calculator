import javax.swing.*;


/**
 * Currently calculates semester GPA
 * Could also calculate overall GPA? (Similar to how the GPA calculator
 * on testudo does it)
 */
public class GPAcalculator {
	private static int numClasses = 0;
	private static double totalGrade = 0.0;
	private static double totalCredits = 0.0;
	
	private static double numericGrade(String grade) {
		double numericGrade = 0;
		switch (grade) {
			case "A+": case "A": case "a+": case "a": numericGrade = 4.0; break;
			case "A-": case "a-": numericGrade = 3.7; break;
			case "B+": case "b+": numericGrade = 3.3; break;
			case "B": case "b": numericGrade = 3.0; break;
			case "B-": case "b-": numericGrade = 2.7; break;
			case "C+": case "c+": numericGrade = 2.3; break;
			case "C": case "c": numericGrade = 2.0; break;
			case "C-": case "c-": numericGrade = 1.7; break;
			case "D+": case "d+": numericGrade = 1.3; break;
			case "D": case "d": numericGrade = 1.0; break;
			case "D-": case "d-": numericGrade = 0.7; break;
			case "F": case "f": numericGrade = 0; break;
			default: numericGrade = -1;
		}
		return numericGrade;
	}
	
	public static void cumulative() {
		boolean input = false;
		while (input == false) {
			try {
				JTextField cumulativeGPA = new JTextField();
				JTextField attemptedCredits = new JTextField();
				Object[] fields = {
						"Current Cumulative GPA: ", cumulativeGPA,
						"Current Attempted Credits (Excluding this semester): ",  attemptedCredits};
				
				int option = JOptionPane.showConfirmDialog(null, fields, 
						"GPA Calculator", JOptionPane.OK_CANCEL_OPTION);
				
				if (option == JOptionPane.OK_OPTION) {
					totalCredits += Integer.parseInt(attemptedCredits.getText());
					totalGrade += (Float.parseFloat(cumulativeGPA.getText())) * totalCredits;
					if (totalCredits < 0 || totalGrade < 0) {
						totalCredits = 0;
						totalGrade = 0;
						throw new NumberFormatException();
					}
					input = true;
				}
				else {
					System.exit(0);
				}
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "That is not a valid "
						+ "number. Please try again.");
			}
		}
	}
	
	/**
	 * Finds out total number of classes to be calculated in GPA based on input 
	 * from user
	 */
	public static void numClasses() {
		boolean input = false;
		while (input == false) {
			try { 
				JTextField classes = new JTextField();
				Object[] fields = {
					"Enter your current number of classes for this semester: ", classes };
				
				int option = JOptionPane.showConfirmDialog(null, fields, 
						"GPA Calculator", JOptionPane.OK_CANCEL_OPTION);

				if (option == JOptionPane.OK_OPTION) {
					numClasses = Integer.parseInt(classes.getText());
					if (numClasses < 1) {
						throw new NumberFormatException();
					}
					input = true;
				}
				else {
					System.exit(0);
				}
			}
			
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "That is not a valid number"
						+ "of classes. Try again.");
			}
		}
	}
	

	public static void gradesAndCredits() {
		for (int i = 0; i < numClasses; i++) {
			SpinnerNumberModel spin = new SpinnerNumberModel(1, 1, 4, 1);
			JSpinner field = new JSpinner(spin);
			JFormattedTextField field1 = new JFormattedTextField();
			JTextField field2 = new JTextField();
			
			Object[] labels = {
					"Credits for class " + (i+1) + ": ", field,
					"Grade for class " + (i+1) + ": ", field2,
				};
			
			boolean input = false;
			int credits = 0; String grade = "";
			
			while (input == false) {
				
				int option = JOptionPane.showConfirmDialog(null, labels, 
						"Class " + (i + 1), JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					try {
						credits = (int) spin.getValue();
						grade = field2.getText();	
						if (numericGrade(grade) == -1) {
							JOptionPane.showMessageDialog(null, "You entered an"
									+ " invalid grade. Try again");
						}
						else {
							totalCredits += credits;
							totalGrade += credits * numericGrade(grade);
							input = true;
						}
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "You entered an "
								+ "invalid number of credits. Try again");
					}
					
				}
				else if (option == JOptionPane.CANCEL_OPTION) {
					System.exit(0);
				}
					
			}
		}
		double GPA = Math.round(totalGrade/((double)(totalCredits)) * 1000.0)/1000.0;
		
		JOptionPane.showMessageDialog(null, "Final GPA: " + GPA + "\n"
				+ "Total Credits: "  + (int) totalCredits, "GPA Results",
				JOptionPane.INFORMATION_MESSAGE);	
	}


	
	public static void main(String[] args) {
		cumulative();
		numClasses();
		gradesAndCredits();
		
	}
}
